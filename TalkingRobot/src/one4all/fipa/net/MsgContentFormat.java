/*
 * Created on 11.07.2003
 *
 */
package one4all.fipa.net;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * class that represents the contentt field of a "content-message"
 * @author hartwig
 */
public class MsgContentFormat {
	
	public String message;
	private ArrayList<TypeValueNode> atts=new ArrayList<TypeValueNode>();
	
	
	public ArrayList<TypeValueNode> getAtts(){
		return atts;
	}
	public void addAtt(TypeValueNode att){
		atts.add(att);
	}
	public void addAll(ArrayList<TypeValueNode> l){
		for(Iterator<TypeValueNode> i=l.iterator();i.hasNext();){
			atts.add(i.next());
		}
	}
	public void setAtts(ArrayList<TypeValueNode> l){
		atts = l;
	}
	
	public String toString(){
		StringBuffer sb=new StringBuffer();
		sb.append(message+" ");
		for(Iterator i=atts.iterator();i.hasNext();){
			sb.append(":"+i.next());
		}
		return sb.toString();
	}

}
