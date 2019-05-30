package com.bhavyakamboj.springboot2.springboot2jpacrudexample.model;

import io.swagger.annotations.ApiModel;

import java.util.List;

@ApiModel
public abstract class EmployeeList implements List<Employee> {
}
