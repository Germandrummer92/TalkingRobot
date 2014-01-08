package dm;
import java.util.Vector;

enum Start implements DialogStates {

  ENTRY,

  WAITING_FOR_USERNAME,

  USER_FOUND,

  WAITING_FOR_EMPLOYEE_STATUS,

  USER_NOT_FOUND,

  USER_SAVED,

  EXIT,

  USER_WANTS_TO_BE_SAVED,

  USER_DOESNT_WANT_TO_BE_SAVED;

    public Vector  myStartState;
  
}