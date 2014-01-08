package data;

import java.util.Date;

/**
 * 
 * @author Daniel Draper
 * @version 1.0
 * This class represents a certain meal that was suggested to an user, in the canteen and its date.
 */
public class MealDatePair {

  private Date date;

  private MealData data;

/**
 * @param date
 * @param data
 * Creates the MealDatePair with the passed Parameters.
 */
public MealDatePair(Date date, MealData data) {
	this.date = date;
	this.data = data;
}

/**
 * @return the date
 */
public Date getDate() {
	return date;
}

/**
 * @return the data
 */
public MealData getData() {
	return data;
}

/**
 * @param date the date to set
 */
public void setDate(Date date) {
	this.date = date;
}

/**
 * @param data the data to set
 */
public void setData(MealData data) {
	this.data = data;
}


}