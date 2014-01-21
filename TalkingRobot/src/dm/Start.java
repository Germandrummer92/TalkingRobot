package dm;

import java.util.Random;

/**
 * nur zum wissen ob bei NLG funktionier 
 * @author Xizhe
 *
 */
public enum Start {

	
  ENTRY{
		  @Override 
		  public int getIndex(){
			  Random rn = new Random();
			  return rn.nextInt(5); // generator a random int between 0 to 4
		  }
	  },

  WAITING_FOR_USERNAME {
	@Override
	public int getIndex() {
		Random rn = new Random();
		  return rn.nextInt(5); // generator a random int between 0 to 4
	}
},

  USER_FOUND {
	@Override
	public int getIndex() {
		Random rn = new Random();
		  return rn.nextInt(5); // generator a random int between 0 to 4
	}
},

  WAITING_FOR_EMPLOYEE_STATUS {
	@Override
	public int getIndex() {
		Random rn = new Random();
		  return rn.nextInt(5); // generator a random int between 0 to 4
	}
},

  USER_NOT_FOUND {
	@Override
	public int getIndex() {
		Random rn = new Random();
		  return rn.nextInt(5); // generator a random int between 0 to 4
	}
},

  USER_SAVED {
	@Override
	public int getIndex() {
		Random rn = new Random();
		  return rn.nextInt(5); // generator a random int between 0 to 4
	}
},

  EXIT {
	@Override
	public int getIndex() {
		Random rn = new Random();
		  return rn.nextInt(5); // generator a random int between 0 to 4
	}
},

  USER_WANTS_TO_BE_SAVED {
	@Override
	public int getIndex() {
		Random rn = new Random();
		  return rn.nextInt(5); // generator a random int between 0 to 4
	}
},

  USER_DOESNT_WANT_TO_BE_SAVED {
	@Override
	public int getIndex() {
		Random rn = new Random();
		  return rn.nextInt(5); // generator a random int between 0 to 4
	}
};

  abstract public int getIndex();
  
  /*
  public static void main (String args[]) {
	  	System.out.println(ENTRY.getIndex());
	  	System.out.println(WAITING_FOR_USERNAME.getIndex());
	  	System.out.println(USER_FOUND.getIndex());
	  	System.out.println(USER_NOT_FOUND.getIndex());
	  	System.out.println(USER_SAVED.getIndex());
	  	System.out.println(EXIT.getIndex());
	  	System.out.println(USER_WANTS_TO_BE_SAVED.getIndex());
	  	System.out.println(USER_DOESNT_WANT_TO_BE_SAVED.getIndex());
	  	}*/
}

