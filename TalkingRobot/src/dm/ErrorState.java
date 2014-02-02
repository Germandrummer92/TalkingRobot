package dm;

/**
 * 
 * @author Meng Meng Yan
 * @version 1.0
 * the errorstate of the system.
 * Only states which last for longer than one turn are listed here, since it is insignificant 
 * for the system if it, for example, has used the repeat-strategy.
 */
enum ErrorState {

	/**
	 * no errorstate.
	 */
	ZERO,

	/**
	 * last error was tried to be handled with Choice-strategy.
	 */
	CHOICE,
	
	/**
	 * last error was tried to be handled with explicit verification.
	 */
	EXPLICIT_VERIFICATION,
	
	/**
	 * last error was tried to be handled with indirect verification.
	 */
	INDIRECT_VERIFICATION,
	
	/**
	 * error was handled with restart-strategy.
	 */
	RESTART;
  
}