/*
 * Created on 27.11.2006
 */
package one4all.fipa.net;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

//import one4all.fipa.parser.ParserUtil;

public class GenericFipaAgent {

    private ArrayList fipaMessages;
    protected Hashtable<String,FiPaMethod> fipaHash = new Hashtable<String,FiPaMethod>();
    
    private One4AllAgent agent;
    
    
    
    /**
     * @param agent
     */
    public GenericFipaAgent(One4AllAgent agent) {
        super();
        this.agent = agent;
    }

    /**
     * send a messsage with the parser message format
     * @param fipaMethod
     * @param atts
     */
    public void sendGenericFiPaMessage(String fipaMethod, String[] atts){
        FiPaMethod msg = (FiPaMethod)fipaHash.get(fipaMethod);
        if(msg == null){
            System.err.println("no such method/message type: "+fipaMethod);
            return;
        }
        String stringContent = msg.msgContent.toString();
        if(atts != null){
            for(int i=0;i<atts.length;i++){
                stringContent = stringContent.replaceFirst("%s",atts[i]);
            }
        }
        agent.sendContentMessage(stringContent,msg.group);
    }

    /**
     * loads the method description from converted fipa tcl file
     * @throws IOException
     */
  /*  private void loadMessages() throws IOException{
  //      fipaMessages = ParserUtil.importFiPaMethods();
        fipaHash = new Hashtable<String,FiPaMethod>();
        for(Iterator i=fipaMessages.iterator();i.hasNext();){
            FiPaMethod m = (FiPaMethod)i.next();
            fipaHash.put(m.method,m);
        }
    }*/

    
}
