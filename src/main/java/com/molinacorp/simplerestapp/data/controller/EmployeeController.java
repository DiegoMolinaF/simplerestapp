package com.molinacorp.simplerestapp.data.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.molinacorp.simplerestapp.data.search.EmployeeSearchCriteria;
import com.molinacorp.simplerestapp.data.search.EmployeeSpecification;
import com.molinacorp.simplerestapp.entity.Employee;
import com.molinacorp.simplerestapp.exception.EmployeeNotFoundException;
import com.molinacorp.simplerestapp.utility.StringUtil;

/**
 * Employee Controller definition
 * @author dm05765
 *
 */
@RestController
class EmployeeController {

	private final EmployeeRepository repository;
	private final EmployeeModelAssembler assembler;
	private static Logger logger = LogManager.getLogger(EmployeeController.class);
	EmployeeController(EmployeeRepository repository, EmployeeModelAssembler assembler) {
		this.repository = repository;
		this.assembler = assembler;
	}

	
	@GetMapping("/employees")
	CollectionModel<EntityModel<Employee>> all() {
		List<EntityModel<Employee>> employees = repository.findAll().stream()
			.map(assembler::toModel)
			.collect(Collectors.toList());
		return CollectionModel.of(employees, linkTo(methodOn(EmployeeController.class).all()).withSelfRel());
	}
	
	@GetMapping("/employees/search")
	CollectionModel<EntityModel<Employee>> searchEmployee(
			@RequestParam(value="id", required = false) String id,
			@RequestParam(value="name", required = false) String name,
			@RequestParam(value="salary", required= false) String salary,
			@RequestParam(value="dept", required = false) String dept) {
		
		logger.info(String.format("RequestParameters [Id: %s, Name: %s, Dept: %s, Salary: %s]", id, name, dept, salary));
		
		Specification<Employee> spec = null;
		
		if (!StringUtil.isNullOrEmpty(id)) spec = addSpec(spec, new EmployeeSearchCriteria("id", ":", id));
		if (!StringUtil.isNullOrEmpty(name)) spec = addSpec(spec, new EmployeeSearchCriteria("name", ":", name));
		if (!StringUtil.isNullOrEmpty(salary)) spec = addSpec(spec, new EmployeeSearchCriteria("salary", ":", salary));
		if (!StringUtil.isNullOrEmpty(dept)) spec = addSpec(spec, new EmployeeSearchCriteria("dept", ":", dept));
		
		List<EntityModel<Employee>> employees = repository.findAll(spec).stream()
			.map(assembler::toModel)
			.collect(Collectors.toList());
		return CollectionModel.of(employees, linkTo(methodOn(EmployeeController.class).all()).withSelfRel());
	}
	
	private Specification<Employee> addSpec(Specification<Employee> spec, EmployeeSearchCriteria criteria) {
		logger.info(String.format("Adding criteria [%s] ", criteria));
		if (spec == null) {
			return Specification.where(new EmployeeSpecification(criteria));
		}
		return spec.and(new EmployeeSpecification(criteria));
	}
	
	@PostMapping("/employees")
	Employee newEmployee(@RequestBody @Valid Employee newEmployee) {
		return repository.save(newEmployee);
	}

	
	@GetMapping("/employees/{id}")
	EntityModel<Employee> one(@PathVariable String id) {
		Employee employee = repository.findById(id).orElseThrow(() ->
			new EmployeeNotFoundException(id)
		);
		return assembler.toModel(employee);
	}
	
	@PutMapping("/employees/{id}")
	Employee replaceEmployee(@RequestBody @Valid Employee newEmployee, @PathVariable String id) {
		return repository.findById(id)
			.map(employee -> {
				employee.setName(newEmployee.getName());
				employee.setSalary(newEmployee.getSalary());
				employee.setDept(newEmployee.getDept());
				return repository.save(employee);
			})
			.orElseGet(() -> {
				newEmployee.setId(id);
				return repository.save(newEmployee);
			});
	}

	@DeleteMapping("/employees/{id}")
	void deleteEmployee(@PathVariable String id) {
		repository.deleteById(id);
	}
}