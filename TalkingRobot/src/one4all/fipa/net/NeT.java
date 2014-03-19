package one4all.fipa.net;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Interactive Systems Labs, Universität Karlsruhe
 * @author Hartwig Holzapfel
 * @version 1.0
 */
@Deprecated
public class NeT {
	
	public static final boolean TRACE_ANY = true;
	public static final boolean TRACE_PARSE = TRACE_ANY && false;
	public static final boolean TRACE_COMM = TRACE_ANY && true;

	static int tickCounter = 0;

	@Deprecated
	public static void ick( String s )
	{
		System.out.print(s);
	}
	@Deprecated
	public static void ick( String s, int linebreak )
	{
		System.out.print(s);
		if(++tickCounter>linebreak){
			System.out.print("\n");
			tickCounter = 0;
		}
	}
	@Deprecated
	public static void race( String s )
	{
		System.out.println(s);
	}

	@Deprecated
	public static void error( Exception e, String s )
	{
		System.err.println("===========================================");
		System.err.println(e+"");
		System.err.println(s);
	}
	@Deprecated
	public static void error( String s )
	{
		System.err.println("===========================================");
		System.err.println(s);
	}

}