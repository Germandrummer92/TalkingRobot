package one4all.fipa.net;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Interactive Systems Labs, Universit�t Karlsruhe
 * @author Hartwig Holzapfel
 * @version 1.0
 */

import java.util.*;

public class BasicMessage {

	String type;
	Hashtable<String,Object> attributes;
	private static final boolean DEBUG_OUT = false;
	private static final boolean NEWLINE_OUT = false;
    private BasicMessage parentMessage;

	public BasicMessage(BasicMessage parentMessage) {
        this.parentMessage = parentMessage;
		type = "";
		//attributes = new AttributeList();
		attributes = new Hashtable<String,Object>();
	}
    
    /**
     * @return parent message, e.g. if this is a content message the parent message is the FIPA container message
     */
    public BasicMessage getParentMessage(){
        return parentMessage;
    }

	public void setType( String type )
	{
		this.type = type;
	}

	/**
	 * adds a new attribute to the argument list.
	 * Note: for sending data this method requires that value is already correctly put into parentheses.
	 * No format checking is done in this method 
	 * @param key
	 * @param value
	 */
	public void addAttribute( String key, Object value )
	{
//		attributes.add( new TypeValueString(key,value) );
//		attributes.add( createTypeValueString(key,value) );
		attributes.put(key,value);
	}

	public void addAttributes( ArrayList l )
	{
		if(l!=null){
			for(Iterator i=l.iterator();i.hasNext();){
				TypeValueNode tvn = (TypeValueNode)i.next();
				if(tvn!=null)
					attributes.put(tvn.type,tvn.value);
			}
		}
	}

	public String getType()
	{
		return type;
	}
	
	public Object getAttribute(String key){
		Object att = attributes.get(key);
		return att; 
	}

	public ArrayList<TypeValueString> getAttributes()
	{
		ArrayList<TypeValueString> list = new ArrayList<TypeValueString>();
		for(Iterator<String> i = attributes.keySet().iterator();i.hasNext();){
			String key = i.next();
			list.add(createTypeValueString(key,""+attributes.get(key))); 
		}
		return list;
	}

	public static TypeValueString createTypeValueString (String p_type, String p_value)
	{
		TypeValueString tv = new TypeValueString(p_type,p_value);
		return tv;
	}

	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(type);
		for(TypeValueString str: getAttributes()){
			if( DEBUG_OUT ) sb.append("\n - ");
			try{
				sb.append(" :"+str);
			}catch(Exception e){ e.printStackTrace();}
		}
		return sb.toString();
	}

	public String toCommFormat()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("("+type);
		for(TypeValueString str: getAttributes()){
			if( NEWLINE_OUT ) sb.append("\n ");
			try{
				sb.append(" :"+str);
			}catch(Exception e){ e.printStackTrace();}
		}
		if( NEWLINE_OUT ) sb.append("\n ");
		sb.append(")");

		return sb.toString();
	}
	
	public int size(){
		return this.attributes.size();
	}

}



/*class TypeValueString
{

	public String type;
	public String value;

	public TypeValueString(String p_type, String p_value)
	{
		type = p_type;
		value = p_value;
	}

	public String toString()
	{
		//assumption: type is correctly put into ()
		return type+" "+value;
	}*/

//}