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
			  return rn.nextInt(4); // generator a random int between 0 to 5
		  }
	  },

  WAITING_FOR_USERNAME {
	@Override
	public int getIndex() {
		Random rn = new Random();
		  return rn.nextInt(4); // generator a random int between 0 to 5
	}
},

  USER_FOUND {
	@Override
	public int getIndex() {
		Random rn = new Random();
		  return rn.nextInt(4); // generator a random int between 0 to 5
	}
},

  WAITING_FOR_EMPLOYEE_STATUS {
	@Override
	public int getIndex() {
		Random rn = new Random();
		  return rn.nextInt(4); // generator a random int between 0 to 5
	}
},

  USER_NOT_FOUND {
	@Override
	public int getIndex() {
		Random rn = new Random();
		  return rn.nextInt(4); // generator a random int between 0 to 5
	}
},

  USER_SAVED {
	@Override
	public int getIndex() {
		Random rn = new Random();
		  return rn.nextInt(4); // generator a random int between 0 to 5
	}
},

  EXIT {
	@Override
	public int getIndex() {
		Random rn = new Random();
		  return rn.nextInt(4); // generator a random int between 0 to 5
	}
},

  USER_WANTS_TO_BE_SAVED {
	@Override
	public int getIndex() {
		Random rn = new Random();
		  return rn.nextInt(4); // generator a random int between 0 to 5
	}
},

  USER_DOESNT_WANT_TO_BE_SAVED {
	@Override
	public int getIndex() {
		Random rn = new Random();
		  return rn.nextInt(4); // generator a random int between 0 to 5
	}
};

  abstract public int getIndex();
}

