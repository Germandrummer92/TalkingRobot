/*
 * Created on 17.06.2003
 */
package one4all.fipa.net;

//import java.util.*;

/**
 * @author hartwig
*/
public class FiPaMethod {
	public String method;
	public String group;
	public MsgContentFormat msgContent;
	//public String message;
	//public ArrayList atts = new ArrayList();
	public String toString(){
		return "FiPaMethod "+method+" group="+group+" msgContent="+msgContent+"\n";
	}
}
