package com.molinacorp.simplerestapp.entity;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.molinacorp.simplerestapp.utility.Constants;
import com.molinacorp.simplerestapp.utility.Messages;

/**
 * Base Employee Entity. Bean validation implemented by hibernate-validator.
 * @author dm05765
 *
 */
@Entity
public class Employee {
	/*
		Id     Char(3)
		Name   Char(30)
		Salary Number(12,2)
		Dept   Char(10)
	 */
	private @Id String id;
	@NotNull @Size(max=Constants.NAME_LENGTH, message = Messages.NAME_MAX_LENGTH)
	private String name;
	@NotNull @Digits(integer=Constants.NUMBER_INT_LENGTH, fraction=Constants.NUMBER_FRA_LENGTH, message = Messages.SALARY_FORMAT)
	private Double salary;
	@NotNull @Size(max=Constants.DEPT_LENGTH, message = Messages.DEPT_MAX_LENGTH)
	private String dept;

	public Employee() {}

	public Employee(String name, String dept) {
		this.id = name.substring(0, 1) + name.substring(name.indexOf(" ") + 1, name.indexOf(" ") + 2) + dept.substring(0,1); 
		this.name = name;
		this.dept = dept;
		this.salary = 0.0;
	}
	
	public Employee(String name, String dept, Double salary) {
		this(name, dept);
		this.salary = salary;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}
	
	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	@Override
	public boolean equals(Object o) {

	if (this == o)
		return true;
	if (!(o instanceof Employee))
		return false;
	Employee employee = (Employee) o;
		return Objects.equals(this.id, employee.id) && Objects.equals(this.name, employee.name)
			&& Objects.equals(this.salary, employee.salary) && Objects.equals(this.dept, employee.dept);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id, this.name, this.salary, this.dept);
	}

	@Override
	public String toString() {
		return String.format("Employee{id=%s, name=%s, salary=%s, dept=%s}",
				this.id, this.name, this.salary, this.dept);
	}

}
