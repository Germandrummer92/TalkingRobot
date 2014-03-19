package one4all.fipa.net;

/**
 * Title:
 s* Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Interactive Systems Labs, Universit�t Karlsruhe
 * @author Hartwig Holzapfel
 * @version 1.0
 */

import java.io.*;
import java.util.ArrayList;

//import org.apache.log4j.Logger;


public class BasicMessageParser {
    
    //FIPA msg top-level attributes: sender receiver content reply-with

	public static final char LEFT_BRACK = '(';
	public static final char RIGHT_BRACK = ')';
	public static final String LEFT_BRACK_STRING = "(";
	public static final String RIGHT_BRACK_STRING = ")";
	//private static final Logger log = Logger.getLogger(BasicMessageParser.class);

	public BasicMessageParser() {
	}
	
	public static boolean canBeParsed( StringBuffer sb ){
		boolean parseable = false;
		String s = sb.toString();
		String msg = getMatchingBrackets(s);
	//	if(log.isDebugEnabled()) log.debug("canBeParsed? on msg "+s+"\n-returns "+msg);
		if (msg != null )
			parseable = true;
		return parseable;
	}

	/**
	 * can parse only one single message in the buffer
	 * doesn't check for well-formedness ->
	 * has to be checked for canBeParsed first
	 */
	public static BasicMessage parseMessage( String s ) throws IOException
	{
		BasicMessage m = new BasicMessage(null);
		s = s.trim();
		int start = s.indexOf(LEFT_BRACK_STRING)+1;
		int end = s.lastIndexOf(RIGHT_BRACK_STRING);
		s = s.substring(start,end).trim();
	//	if(log.isDebugEnabled()) log.debug("parse trimmed String: \n"+s);
		parseTrimmedMessage(s,m);
		return m;
	}

	/**
	 * can parse only one single message in the buffer
	 * doesn't check for well-formedness ->
	 * has to be checked for canBeParsed first
	 */
	public static TypeValueNode parseContentFromMessage( String s ) throws IOException
	{
		s = s.trim();
		int start = s.indexOf(LEFT_BRACK_STRING)+1;
		int end = s.lastIndexOf(RIGHT_BRACK_STRING);
		s = s.substring(start,end).trim();
	//	if(log.isDebugEnabled()) log.debug("parse trimmed String: \n"+s);
		start = s.indexOf(":content");
		s=s.substring(start).trim();
	//	if(log.isDebugEnabled()) log.debug(":content: \n"+s);
		s=getMatchingBrackets(s).trim();
		start = s.indexOf(LEFT_BRACK_STRING)+1;
		end = s.lastIndexOf(RIGHT_BRACK_STRING);
		s = s.substring(start,end).trim();
		//if(log.isDebugEnabled()) log.debug("TODO: currently ignoring content length, checking for parenthesis");
	//	if(log.isDebugEnabled()) log.debug("trimmed content: \n"+s);
		return parseTypeValueNode_content(s);
	}
	
	protected static TypeValueNode parseTypeValueNode_content(String s) {
		String msg;
		Object content=null;
//		-1  extract type
		int split = s.indexOf(':');
		//if ':' is not contained in the input string, take the whole string and return
		if( split < 0 )	msg = s;
		else{
			msg =s.substring(0,split).trim();
			//if(log.isDebugEnabled()) log.debug("Message-Name:"+msg);
//			-1  parseAttributes
			try{
				content = parseAttributes( s.substring(split) );
			}catch(Exception e_e){
		//		log.error("malformed attribute format:"+s.substring(split),e_e);
			}
		}
		return new TypeValueNode(msg,content);
	}
	
	/**
	 * parse the attributes for a message String into the Message object.
	 * format is
	 * ATTS -> ":" att-type " " ATTC " " ATTS
	 * ATTS -> ""
	 * ATTC -> "(" text ")"
	 * ATTC -> wort
	 * @returns a list of TypeValueNode
	 */
	private static ArrayList<TypeValueNode> parseAttributes( String attsStr )
	{
		ArrayList<TypeValueNode> atts = new ArrayList<TypeValueNode>();
		//if(log.isDebugEnabled()) log.debug( "parseAtts \n"+attsStr );

		String type="";
		String value="";
		int endType;
		int endAtt;

		if(attsStr.startsWith(":"))
			attsStr = attsStr.substring(1).trim();

		//end of type:
		endType = attsStr.indexOf(" ");
		type = attsStr.substring(0,endType);

		//extact att
		attsStr = attsStr.substring(endType+1).trim();
		if(attsStr.startsWith(""+LEFT_BRACK)){
			//find correspoding right bracket for end of attribute
			value = matchBrackets(attsStr);
			attsStr = attsStr.substring(value.length());
		}
		else{
			//no brackets, value is a single string
			endAtt = attsStr.indexOf(" ");
			if(endAtt > 0){
				value = attsStr.substring(0,endAtt);
				attsStr = attsStr.substring(endAtt).trim();
			}else{
				value = attsStr;
				attsStr = ""; //reset, we've reached the end
			}
		}
	//	if(log.isDebugEnabled()) log.debug("new Att: "+type+":"+value);
		atts.add(new TypeValueNode(type,value));

		int startNext = attsStr.indexOf(":");
	//	if(log.isDebugEnabled()) log.debug("remaining to parse: "+attsStr);
		if(startNext >= 0){
			atts.addAll(parseAttributes(attsStr.substring(startNext)));
		}
		return atts;
	}

	/**
	 * parse the message String into the Message object.
	 * format is
	 * MSG -> msg-type " " ATTS
	 * ATTS -> ":" att-type " " ATTC " " ATTS
	 * ATTS -> ""
	 * ATTC -> "(" text ")"
	 * ATTC -> wort
	 */
	private static void parseTrimmedMessage( String msgStr, BasicMessage m )
	{
//-1  extract type
		int split = msgStr.indexOf(':');
		//if ':' is not contained in the input string, take the whole string and return
		if( split < 0 ){
			m.setType( msgStr );
			return;
		}
		String name = msgStr.substring(0,split).trim();
	//	if(log.isDebugEnabled()) log.debug("Message-Name:"+name);
		m.setType( name );

//-1  parseAttributes
		try{
			parseAttributes( msgStr.substring(split), m );
		}catch(Exception e_e)
		{
	//		log.error(String.format("malformed attribute format:>%1$s< \n%1$s",msgStr.substring(split)),e_e);
		}

	}

	/**
	 * parse the attributes for a message String into the Message object.
	 * format is
	 * ATTS -> ":" att-type " " ATTC " " ATTS
	 * ATTS -> ""
	 * ATTC -> "(" text ")"
	 * ATTC -> wort
	 */
	private static void parseAttributes( String attsStr, BasicMessage m )
	{
	//	if(log.isDebugEnabled()) log.debug( "parseAtts \n"+attsStr );
		if(attsStr.length()==0)
			return;

		String type="";
		String value="";
		int endType;
		int endAtt;

		if(attsStr.startsWith(":"))
			attsStr = attsStr.substring(1).trim();

		//end of type:
		endType = attsStr.indexOf(" ");
		type = attsStr.substring(0,endType);

		//extact att
		attsStr = attsStr.substring(endType+1).trim();
        if(attsStr.startsWith("#")){
            endType = attsStr.indexOf(" ");
            int num = Integer.parseInt(attsStr.substring(1,endType).trim());
            attsStr = attsStr.substring(endType+1).trim();
            if(attsStr.startsWith("\"")){
                num+=2;
            }
            value = "#"+num+" "+attsStr.substring(0,num);
            attsStr = attsStr.substring(num).trim();
        }
        else if(attsStr.startsWith(""+LEFT_BRACK))
		{
			//find correspoding right bracket for end of attribute
			value = matchBrackets(attsStr);
			attsStr = attsStr.substring(value.length());
		}
		else
		{
			//no brackets, value is a single string
			endAtt = attsStr.indexOf(" ");
			if(endAtt > 0){
				value = attsStr.substring(0,endAtt);
				attsStr = attsStr.substring(endAtt).trim();

			}else{
				value = attsStr;
				attsStr = ""; //reset, we've reached the end
			}
		}
	//	if(log.isDebugEnabled()) log.debug("new Att: "+type+":"+value);
		m.addAttribute(type,value);
		int startNext = attsStr.indexOf(":");
	//	if(log.isDebugEnabled()) log.debug("remaining: "+attsStr);
		if(startNext >= 0){
			parseAttributes(attsStr.substring(startNext),m);
		}
	}


	private static String matchBrackets( String base )
	{
		int left = base.indexOf( LEFT_BRACK_STRING );
		if(left < 0 )
				return "";
		int diff = 1;
		int right=left;
		while (diff > 0 && (++right)<base.length())
		{
			if(base.charAt(right) == LEFT_BRACK){
				diff++;
			}
			if(base.charAt(right) == RIGHT_BRACK){
				diff--;
			}
		}
	//			if(log.isDebugEnabled()) log.debug("matches "+base.substring(left,right+1));
		return base.substring(left,right+1);
	}

	/**
	 * returns the substring that matches the sequence between the first opening
	 * and the corresponding closing bracket
	 * null if no mathing can be found
	 * @param base
	 * @return String - first substring of input with correct brackets
	 */
	private static String getMatchingBrackets( String base )
	{
		int left = base.indexOf( LEFT_BRACK_STRING );
		if(left < 0 )
				return null;
		int diff = 1;
		int right=left;
		while (diff > 0 && (++right)<base.length())
		{
			if(base.charAt(right) == LEFT_BRACK){
				diff++;
			}
			if(base.charAt(right) == RIGHT_BRACK){
				diff--;
			}
		}
		if(diff > 0 )
			return null;
		else
			return base.substring(left,right+1);
	}


}