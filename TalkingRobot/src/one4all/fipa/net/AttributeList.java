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

public class AttributeList {

	Hashtable<String,String> attributes;
	String[] keys;

	public AttributeList( String[] p_keys ) {
		attributes = new Hashtable<String,String>();
		keys = new String[p_keys.length];
		System.arraycopy( p_keys, 0, keys, 0, p_keys.length );
		for(int i=0;i<p_keys.length;i++)
		{
			attributes.put(p_keys[0],"");
		}
	}

	public void setAttribute( String p_key, String p_value )
	{
		if(! attributes.containsKey(p_key) )
		{
			 return;
		}
		else
		{
			attributes.put( p_key, p_value );
		}
	}

	public String getAttribute( String p_key )
	{
		return attributes.get(p_key);
	}

}