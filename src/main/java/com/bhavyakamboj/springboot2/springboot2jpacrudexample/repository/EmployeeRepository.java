package com.bhavyakamboj.springboot2.springboot2jpacrudexample.repository;

import com.bhavyakamboj.springboot2.springboot2jpacrudexample.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findByFirstName(String username);
}
