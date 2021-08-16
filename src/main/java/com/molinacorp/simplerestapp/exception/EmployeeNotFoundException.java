package com.molinacorp.simplerestapp.exception;

/**
 * Used by the EmployeeController to return a 404 on an employee not found.
 * Check the EmployeeNotFoundAdvice class for the controller advice.
 * @author dm05765
 *
 */

public class EmployeeNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -6920932505101798571L;

	public EmployeeNotFoundException(String id) {
		super("Could not find employee " + id);
	}
}