package com.molinacorp.simplerestapp.utility;

/**
 * Messages.
 * @author dm05765
 *
 */
public class Messages {
	
	private Messages () { }
	
	public static final String NAME_MAX_LENGTH = "Name should not have length greater than " + Constants.NAME_LENGTH;
	public static final String DEPT_MAX_LENGTH = "Dept. should not have length greater than " + Constants.DEPT_LENGTH;
	public static final String SALARY_FORMAT = "Salary must be in the format (" 
							+ Constants.NUMBER_INT_LENGTH + "." +  Constants.NUMBER_FRA_LENGTH + ")";
}
