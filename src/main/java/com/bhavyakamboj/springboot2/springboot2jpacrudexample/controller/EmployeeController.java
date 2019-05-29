package com.bhavyakamboj.springboot2.springboot2jpacrudexample.controller;

import com.bhavyakamboj.springboot2.springboot2jpacrudexample.exception.ResourceNotFoundException;
import com.bhavyakamboj.springboot2.springboot2jpacrudexample.model.Employee;
import com.bhavyakamboj.springboot2.springboot2jpacrudexample.repository.EmployeeRepository;
import jdk.management.resource.ResourceRequestDeniedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

//    @GetMapping("/employees")
//    public List<Employee> getAllEmployees(){
//        return employeeRepository.findAll();
//    }

    @GetMapping("/employees")
    public List<Employee> getEmployeeByID(@RequestParam(value = "id",required = false) Long employeeId)throws ResourceNotFoundException {
        if(employeeId==null){
            return employeeRepository.findAll();
        }
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee details not found for this id :: "+employeeId));

        return Arrays.asList(employee);
    }
//@Valid annotation enables the hibernate validation
    @PostMapping("/employees")
    public Employee createEmployee(@Valid @RequestBody Employee employee){
        return employeeRepository.save(employee);
    }

    @PutMapping("/employees")
    public ResponseEntity<Employee> updateEmployee(@RequestParam(value = "id") Long employeeId,@Valid @RequestBody Employee employeeDetails) throws ResourceNotFoundException {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id::" +employeeId));
        employee.setEmailID(employeeDetails.getEmailID());
        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        final Employee updatedEmployee = employeeRepository.save(employee);
        return ResponseEntity.ok().body(updatedEmployee);
    }

    @DeleteMapping("/employees")
    public Map<String,Boolean> deleteEmployee(@RequestParam(value = "id") Long employeeId, @Valid @RequestBody Employee employeeDetails) throws ResourceNotFoundException{
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(()-> new ResourceNotFoundException("Employee not found for id::" + employeeId));
        employeeRepository.delete(employee);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}