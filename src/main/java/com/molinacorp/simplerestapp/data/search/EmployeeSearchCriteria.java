package com.molinacorp.simplerestapp.data.search;

/**
 * Search criteria to do /search on the application.
 * @author dm05765
 *
 */
public class EmployeeSearchCriteria {
	private String key;
	private String operation;
	private Object value;
	
	public EmployeeSearchCriteria(String key, String operation, Object value) {
		super();
		this.key = key;
		this.operation = operation;
		this.value = value;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return String.format("SearchCriteria{Key=%s, Operation=%s, Value=%s}", getKey(), getOperation(), getValue());
 	}
	
}
