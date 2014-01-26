/**
 * 
 */
package dm;

/**
 * This Class represents an error that occurs if a Dialog doesn't have the fitting DialogState class attached.
 * @author Daniel Draper
 * @version 1.1
 *
 */
public class WrongStateClassException extends Exception {

	
	private static final long serialVersionUID = 1L;
	private String newClassName = null;

	/**
	 * Creates a new WrongStateClassException with the String specified
	 * @param newClassName the Class of which the state was throwing the exception
	 */
	public WrongStateClassException(String newClassName) {
		this.newClassName = newClassName;
	}
	/**
	 * @return the Class name of the State which threw the exception
	 */
	public String getNewClassName() {
		return newClassName;
	}

}
