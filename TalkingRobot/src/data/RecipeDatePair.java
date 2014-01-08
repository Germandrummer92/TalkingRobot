package data;

import java.util.Date;

/**
 * 
 * @author Daniel Draper
 * @version 1.0
 * This class represents an instance of Access to a certain recipe by a user and the date of access.
 */
public class RecipeDatePair {

  private Date date;

  private RecipeData data;

  
/**
 * @param date
 * @param data
 * Creates the recipeDatePair with the specified parameters.
 */
public RecipeDatePair(Date date, RecipeData data) {
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
public RecipeData getData() {
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
public void setData(RecipeData data) {
	this.data = data;
}



}