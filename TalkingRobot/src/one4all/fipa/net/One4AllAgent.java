/*
 * Created on 11.06.2003
 */
package one4all.fipa.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

//import org.apache.log4j.Logger;


/**
 * @author hartwig
 *
 * How to use the One4AllAgent:
 * To receive messages use the method addMessageListener to add a listener.
 * To send a message call ? - see send implementations
 */
public class One4AllAgent {

    public static final String PROP_COM_ENCODING = "o4a.commserver.encoding";
    
    /** TODO o4a - Logger for Log4j-logging */
  //  private static final Logger log = Logger.getLogger(One4AllAgent.class);

//	////////////////////////////
//	comunication settings
//	////////////////////////////

	int commServerPort = 9090;
	String commServerHost = "129.13.32.234";
//	static String commServerHost = "localhost";
	String componentName = "RoVis";
	
	static final String FIPA_LANG = "one4all";

	//will be overwritten by method connectToCommServer()
	static Object[] connectMessageParameter = {"componentName", new Integer(0)};
	static MessageFormat connectMessageFormat = new MessageFormat(
	"(inform\n"+
	"  :sender {0}\n"+
	"  :receiver "+FIPA_LANG+"\n"+
	"  :language "+FIPA_LANG+"\n"+
	"  :reply-with {0}-msg-{1}\n"+
	"  :content #6 \"(init)\"\n"+
	")\n"
	);


	//will be overwritten by method subscribe...()
	//Parameters: name, msg-counter, content-length, content-with-brackets, receiver
	static Object[] msgContentParameter = {"componentName", new Integer(1), new Integer(7), "subscribe nlin", FIPA_LANG};
	static MessageFormat msgContentFormat = new MessageFormat(
	"(inform\n"+
	"  :sender {0}\n"+
	"  :receiver {4}\n"+
	"  :language "+FIPA_LANG+"\n"+
	"  :reply-with {0}-msg-{1}\n"+
	"  :content #{2} \"{3}\"\n"+
	")\n"
	);



//	////////////////////////////
//
//	properties
//
//	////////////////////////////

	static int msgCounter = 0;
	ServerSocket serverSocket=null;
	ArrayList<MessageListener> messageListeners = new ArrayList<MessageListener>();
	Socket theConnection; //the connection is always null while disconnected (equals null <=> not connected)
	PrintStream writer=null;
	InputStream reader=null;
	private String subscription="";
	
	private boolean sysout = false;
	
	private String encoding = null;//encoding to talk to server
    private boolean useEncoding = true;//if encoding is null, read it from system properties
    private boolean initEconding = false;//set to true, once system properties have been read
	

//	///////////////////////////////////////////////
//
//	 CONSTRUCTOR
//
//	///////////////////////////////////////////////

	public One4AllAgent(String _componentName, String _commServerHost, int _commServerPort){
		this.componentName = _componentName;
		this.commServerHost = _commServerHost;
		this.commServerPort = _commServerPort;
	}

    /**
     * 
     * @param _componentName
     * @param _commServerHost
     * @param _commServerPort
     * @param encoding        set the encoding to be used to encode and decode messages to and from commServer
     */
    public One4AllAgent(String _componentName, String _commServerHost, int _commServerPort, String encoding){
        this.componentName = _componentName;
        this.commServerHost = _commServerHost;
        this.commServerPort = _commServerPort;
        this.encoding = encoding;
    }
    
    /**
     * @param _componentName
     * @param _commServerHost
     * @param _commServerPort
     * @param useEncoding     set true if encoding should be used, if not set, it is read from system properties, default: true
     */
    public One4AllAgent(String _componentName, String _commServerHost, int _commServerPort, boolean useEncoding){
        this.componentName = _componentName;
        this.commServerHost = _commServerHost;
        this.commServerPort = _commServerPort;
        this.useEncoding = useEncoding;
    }
    
//	///////////////////////////////////////////////
//
//	 message interface
//
//	///////////////////////////////////////////////

	public void addMessageListener( MessageListener l )
	{
	/*	if(log.isDebugEnabled()){
			log.debug(String.format("%2$s (%3$s): adding %1$s",l.getClass().getName(),this,this.componentName));
		}*/
		messageListeners.add(l);
	}

	public void removeMessageListener( MessageListener l )
	{
	/*	if(log.isDebugEnabled()){
			log.debug(String.format("%2$s (%3$s): removing %1$s",l.getClass().getName(),this,this.componentName));
		}*/
		messageListeners.remove(l);
	}

	protected void fireMessage( BasicMessage m )
	{
		//do we need the sync here?		
        for (MessageListener ml : messageListeners) {
    	/*	if(log.isDebugEnabled()){
    			log.debug(String.format("%2$s (%3$s): try %1$s",ml.getClass().getName(),this,this.componentName));
    			
    		}*/

            try {
				ml.messageArrived(m);
			} catch (Exception e) {
				System.err.println("ERROR: unexpected exception occured in one4all message listener: "+ml);
				e.printStackTrace();
			}
        }
	}

    ///////////////////////////////////////////////////
    //
    //  properties
    //
    ///////////////////////////////////////////////////

    /**
     * @return Returns the sysout.
     */
    public boolean isSysout() {
        return sysout;
    }

    /**
     * @param sysout The sysout to set.
     */
    public void setSysout(boolean sysout) {
        this.sysout = sysout;
    }

    /////////////////////////////////////////////////
    //
    //	connection methods
    //
    /////////////////////////////////////////////////
	
	public boolean isConnected(){
	    return theConnection != null;
	}
	
	/**
	 * connect via socket to the CommServer
	 * and send INIT message 
	 * NEXT step: subscribe to groups
	 */
	public void connectToCommServer() throws IOException{
		if (isConnected()) {
			disconnect();
		}
		//if(log.isDebugEnabled()) log.debug("loading method descriptions");
        //if(log.isDebugEnabled()) log.debug("o4a agent - connectToCommServer() - loading method descriptions");
		//loadMessages();

		//if(log.isDebugEnabled()) log.debug(String.format("%1$s (%2$s):connecting to %3$s : %4$d",this,this.componentName,commServerHost,commServerPort));
		
		//new impl: wait for server for 5 seconds
		Date start = new Date();
		Date curr = start;
		boolean proceed = false;
		while(!proceed){
			try{
				theConnection = new Socket(commServerHost, commServerPort);
				proceed = true;
			}catch(IOException eio){
				try{ 
					Thread.sleep(1000);
					curr = new Date();
					if(curr.getTime() - start.getTime() > 5000 ){
						proceed = true;
					}
				}catch(InterruptedException e){
					e.printStackTrace();
					proceed = true;
				}
			}
		}
		if(theConnection == null){
			throw new IOException("could not connect to CommServer");
		}
		connectMessageParameter[0] = componentName;
		sendMessage(connectMessageFormat.format(connectMessageParameter));
		processConnection(theConnection);
	}
	
	/**
	 * disconnect from CommServer
	 */
	public void disconnect()
	{
		/*if(log.isDebugEnabled()) log.debug("========================================");
        if(log.isDebugEnabled()) log.debug("disconnecting from CommServer");*/
		try{
			theConnection.close();
		}catch(Exception e){}
		//in each case (success or not) set the connection to null
		theConnection=null;
		subscription="";
	}

	
	/**
	 * subscribe to the specified groups. <br> 
	 * Format: a space-separated list.
	 * INFO: don't call this multiple times with the same group names, 
	 * because then the server will also send messages multiple times.
	 * @param groups a space-separated list of group names
	 */
	public void sendSubscription(String groups)
	{
		if(groups==null){
			groups = "";
		}
		String content = "(subscribe "+groups+")";
		sendContentMessage(content, FIPA_LANG);
		subscription=groups;
	}
	
	/**
	 * subscribe to the specified groups. <br> 
	 * @param groups a set of group names
	 */
	public void sendSubscription(HashSet<String> groups){
		String groupStr = "";
		if(groups!=null){
			for (String string : groups) {
				groupStr += " "+string;
			}
			groupStr = groupStr.trim();
		}
		sendSubscription(groupStr);
	}
	
	
	/**
	 * returns the subscription variable that is set by each sendSubscription call.
	 * Doesn't apply server logic
	 * @return String, may be empty but not null
	 */
	public String getSubscriptions(){
		return subscription;
	}

	/**
	 * send a fipa message to receiver with the specified content
	 * @param content
	 * @param receiver
	 */
	public void sendContentMessage( String content, String receiver )
	{
		if(! (content.trim().charAt(0)=='(') ){
			content = "("+content+")";
		}
		msgContentParameter[0]=componentName;
		msgContentParameter[1]=new Integer(msgCounter++);
		msgContentParameter[2]=new Integer(content.length());
		msgContentParameter[3]=content;
		msgContentParameter[4]=receiver;
		sendMessage(msgContentFormat.format(msgContentParameter));
	}
	
	public void sendFormattedMessage( BasicMessage message )
	{
		sendMessage(message.toCommFormat());
	}

	/**
	 * basic send-implementation
	 * @param message
	 */
	private void sendMessage( String message )
	{
		try{
			//if(log.isDebugEnabled()) log.debug("sending "+message);
            
            if(theConnection == null){
                throw new IOException("o4a: no connection to server established - could not send message");
            }
            if(encoding == null && useEncoding && !initEconding){
                encoding = System.getProperty(PROP_COM_ENCODING);
                if(encoding !=null){
             //       if(log.isDebugEnabled()) log.debug("o4a client use encoding specified by system property "+PROP_COM_ENCODING+": "+encoding);
                }
                initEconding=true;
            }
            if(encoding!=null){
    			PrintStream writer = new PrintStream(theConnection.getOutputStream(),false,encoding);
    			writer.println(message);
            }else{
    			PrintStream writer = new PrintStream(theConnection.getOutputStream());
    			writer.println(message);
            }
		}catch(IOException e_io)
		{
		//	log.error("could not send message "+message,e_io);
		}
	}



    ///////////////////////////////////////////////////
    //
    //	 server stuff
    //
    ///////////////////////////////////////////////////

	
	/**
	 * instantiates a new reader thread that reads from the input stream
	 * and writes the answer to its ouput stream
	 */
	protected void processConnection(Socket theConnection) {
		try {
			//copy all listeners
		//	if(log.isDebugEnabled()) log.debug(String.format("%1$s (%2$s): O4A Agent - listening on open connection ... ",One4AllAgent.this,One4AllAgent.this.componentName));
			//Vector msgListeners = (Vector)messageListeners.clone();
			//don't clone, so listeners can be added afterwards
			SocketReader newServerThread = new SocketReader(theConnection);
			newServerThread.start();
		} catch (Exception e) {
		//	log.error("",e);
		}
	}
	
	class SocketReader extends Thread {
		Socket myConnection;
		BufferedReader myReader;
		One4AllAgent owner;
		protected SocketReader(Socket _myConnection)throws IOException{
			myConnection = _myConnection;
            if(encoding == null && useEncoding && !initEconding){
                encoding = System.getProperty(PROP_COM_ENCODING);
                if(encoding !=null){
           //         if(log.isDebugEnabled()) log.debug("o4a client use encoding specified by system property "+PROP_COM_ENCODING+": "+encoding);
                }
                initEconding=true;
            }
			if(encoding!=null){
				myReader = new BufferedReader(new InputStreamReader(myConnection.getInputStream(),encoding));
			}else{
				myReader = new BufferedReader(new InputStreamReader(myConnection.getInputStream()));
			}
		}
		public void run(){
			//ArrayList lines = new ArrayList();
			StringBuffer sb = new StringBuffer();
			//thread impl:
			//do forever
			int cnt = 0;
			while( !myConnection.isClosed() && myConnection.isConnected() ){
				//read line by line
				try{
					String nextLine = myReader.readLine();
					if(nextLine == null) break;
					if(sysout)System.out.print(".");
					if(cnt++ > 50){
						if(sysout)System.out.println();
						cnt = 0;
					}
//					lines.add(nextLine);
					sb.append(nextLine+"\n");
					//if canBeParsed
					if(BasicMessageParser.canBeParsed(sb)){
						if(sysout)System.out.println();
						cnt = 0;
						//	take those lines and parse; then reset the StringBuffer
						//BasicMessage m = BasicMessageParser.parseMessage(sb.toString());
                        String message = sb.toString();
                        BasicMessage rootMsg = BasicMessageParser.parseMessage(message);
						TypeValueNode tvn = BasicMessageParser.parseContentFromMessage(message);
			//			if(log.isDebugEnabled()) log.debug(String.format("%1$s (%2$s @ socket %4$d, port %5$s): Parsed content:%3$s",One4AllAgent.this,One4AllAgent.this.componentName,tvn,myConnection.getLocalPort(),myConnection.getLocalSocketAddress().toString()));
						BasicMessage m = new BasicMessage(rootMsg);
						m.type = tvn.type;
						if(tvn.type!=null){
							m.addAttributes((ArrayList)tvn.value);
						}
					//	if(log.isDebugEnabled()) log.debug("resetting input");
						sb = new StringBuffer();
						fireMessage(m);
					}
				}catch(SocketException e){
					e.printStackTrace();
					sb = new StringBuffer();
					break;
				}catch(IOException e){
					e.printStackTrace();
					sb = new StringBuffer();
				}
			}
	//		if(log.isDebugEnabled()) log.debug(String.format("%1$s (%2$s): O4A Agent - closed Connection ",One4AllAgent.this,One4AllAgent.this.componentName));
			System.err.println("========================================");
			System.err.println("WARNING: connection closed, restarting");
			disconnect();
			try {
				connectToCommServer();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	

//	/////////////////////////////////////////////////
//
//	   MAIN - test application
//
//	/////////////////////////////////////////////////


	public static void main(String[] args) {
		System.out.println("Test Application");
        One4AllAgent agent = new One4AllAgent("TESTer","i13pc166",9000);
		agent.addMessageListener(new MessageListener(){
			public void messageArrived(BasicMessage m){
				System.out.println("parsed message:\n"+m.toCommFormat());
			}
		});
		try{
			agent.connectToCommServer();
		}catch(IOException e){
			e.printStackTrace();
			return;
		}
		agent.sendSubscription("nlin speech");
		
		//after 5 seconds send test message:
		agent.sendContentMessage("query :text (hello) :language (english)", "nlin");
		//agent.sendContentMessage("(parsetree :tree ({ [emosfb:act_greet,_,_]::EMO ( [emosfb:hi_NT]::EMO ( hello ) ) }) :language (english))","nlin");
	}

}
