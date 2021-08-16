package com.molinacorp.simplerestapp.data.controller;

/**
 * Used to create specific queries to the H2 database
 */
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.molinacorp.simplerestapp.entity.Employee;

interface EmployeeRepository extends JpaRepository<Employee, String>, JpaSpecificationExecutor<Employee> {

}