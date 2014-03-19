/*
 * Created on 16.06.2003
 */
package one4all.fipa.net;

/**
 * @author hartwig
 */
public class TypeValueNode {

	public String type;
	public Object value;

	public TypeValueNode(String p_type, Object p_value)
	{
		type = p_type;
		value = p_value;
	}
	
	/*
	 * creates a new TypeValue node with replaced %s
	 * param atts
	 * return
	 */
	/*
	public TypeValueNode convert(String[] atts){
		Object newValue;
		if(value instanceof TypeValueNode){
			newValue = ((TypeValueNode)value).convert(atts);
		}else{
			String sv = ""+value;
			if(atts != null){
				for(int i=0;i<atts.length;i++){
					sv = sv.replaceFirst("%s",atts[i]);
				}
			}
			newValue = sv;
		}
		TypeValueNode newNode = new TypeValueNode(type,newValue);
		return newNode;
	}
	*/
	
	public String toString()
	{
		//assumption: type is correctly put into ()
		return type+" "+value;
	}

}
