package com.molinacorp.simplerestapp.data.search;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.molinacorp.simplerestapp.entity.Employee;


/**
 * Enables a way to compare Employees and return the ones required on a search.
 * Only equal is used but "less than" and "greater than" are left for future implementation.
 * Search strings are "lowered" to be case insensitive.
 * It retrieves all Employees if the search criteria is null or the categories are not valid.
 * @author dm05765
 *
 */
public class EmployeeSpecification implements Specification<Employee> {
	
	private static final long serialVersionUID = 8486075115525859575L;
	private EmployeeSearchCriteria criteria;

	public EmployeeSpecification(EmployeeSearchCriteria criteria) {
		super();
		this.criteria = criteria;
	}

	@Override
	public Predicate toPredicate (Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		if (criteria.getOperation().equalsIgnoreCase(">")) {
			return builder.greaterThanOrEqualTo(
					root.<String> get(criteria.getKey()), criteria.getValue().toString());
		} else if (criteria.getOperation().equalsIgnoreCase("<")) {
			return builder.lessThanOrEqualTo(
					root.<String> get(criteria.getKey()), criteria.getValue().toString());
		} else if (criteria.getOperation().equalsIgnoreCase(":")) {
			if (root.get(criteria.getKey()).getJavaType() == String.class) {
				return builder.like(
					builder.lower(root.<String>get(criteria.getKey())), "%" + criteria.getValue().toString().toLowerCase() + "%");
				} else {
					return builder.equal(root.get(criteria.getKey()), criteria.getValue());
				}
		}
		return null;
	}
}
