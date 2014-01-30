package dm;

/**
 * @author Meng Meng Yan
 * @version 1.0
 * the different possible error handling strategies.
 */
public enum ErrorHandling {
	
	REPEAT,
	
	REPHRASE,
	
	RESTART_START,
	
	RESTART_CI,
	
	RESTART_CR,
	
	RESTART_RA,
	
	RESTART_RL,
	
	RESTART_KA,
	
	CHOICE,
	
	Explicit_Verification,
	
	Indirect_Verification;

}
