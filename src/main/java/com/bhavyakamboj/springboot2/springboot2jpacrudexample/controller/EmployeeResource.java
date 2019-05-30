package com.bhavyakamboj.springboot2.springboot2jpacrudexample.controller;

import com.bhavyakamboj.springboot2.springboot2jpacrudexample.exception.ResourceNotFoundException;
import com.bhavyakamboj.springboot2.springboot2jpacrudexample.model.Employee;
import com.bhavyakamboj.springboot2.springboot2jpacrudexample.model.EmployeeList;
import com.bhavyakamboj.springboot2.springboot2jpacrudexample.model.OperationStatus;
import com.bhavyakamboj.springboot2.springboot2jpacrudexample.repository.EmployeeRepository;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Path("/api/v1")
@Api(value="Employee Management System", description = "Operations pertaining to manage employees")
public class EmployeeResource {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeResource.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Path("/health")
    @RequestMapping(method = RequestMethod.HEAD,path = "/health")
    @HEAD
    @ApiOperation(value="View the health status of api", response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "API is in good health"),
            @ApiResponse(code=500, message = "The server is facing problems currently. Please try later.")
    })
    public Response healthStatus(){
        return Response.ok().header("My header",new HashMap<Integer, String>(){
            {
                put(0,"test data");
                put(1,"a");
                put(2,"b");
                put(3,"c");
            }
        }).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON) //@Produces defines a media-type that the resource method can produce.
    @Path("/employees")//@Path is used to identify the URI path (relative) that a resource class or class method will serve requests for
    @GetMapping("/employees")
    @ApiOperation(value="View a list of available employees", response = EmployeeList.class)
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "Employees retrieved successfully"),
            @ApiResponse(code=401, message = "You are not authorized"),
            @ApiResponse(code=403, message = "Accessing the employee you were trying to reach is forbidden"),
            @ApiResponse(code=404, message = "The employee you were trying to reach is not found"),
            @ApiResponse(code=500, message = "The server is facing problems currently. Please try later.")
    })
    public List<Employee> getAllEmployees(){
        logger.info("returned all employees");
        return employeeRepository.findAll();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/employees/{id}")
    @GetMapping("/employees/{id}")
    @ApiOperation(value="View a particular Employee", response = Employee.class)
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "Employee retrieved successfully"),
            @ApiResponse(code=401, message = "You are not authorized"),
            @ApiResponse(code=403, message = "Accessing the employee you were trying to reach is forbidden"),
            @ApiResponse(code=404, message = "The employee you were trying to reach is not found"),
            @ApiResponse(code=500, message = "The server is facing problems currently. Please try later.")
    })
    public ResponseEntity<Employee> getEmployeeByID(@PathParam(value = "id") Long employeeId)throws ResourceNotFoundException {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee details not found for this id :: "+employeeId));
        logger.info("returned a single employee");
        return ResponseEntity.ok().body(employee);
    }
//@Valid annotation enables the hibernate validation
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON) //@Consumes defines a media-type that the resource method can accept.
    @Path("/employees")
    @PostMapping("/employees")
    @ApiOperation(value="Add a new Employee", response = Employee.class)
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "Employee added successfully"),
            @ApiResponse(code=401, message = "You are not authorized"),
            @ApiResponse(code=403, message = "Adding the employee is forbidden"),
            @ApiResponse(code=404, message = "The employee you were trying to add is not found"),
            @ApiResponse(code=500, message = "The server is facing problems currently. Please try later.")
    })
    public Employee createEmployee(@ApiParam(value="Employee object stored in database table", required=true) @Valid @RequestBody Employee employee){
        logger.info("created an employee");
        return employeeRepository.save(employee);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/employees/{id}")
    @PutMapping("/employees/{id}")
    @ApiOperation(value="Update the details of an employee")
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "Employee retrieved successfully"),
            @ApiResponse(code=401, message = "You are not authorized"),
            @ApiResponse(code=403, message = "Updating the employee you were trying to reach is forbidden"),
            @ApiResponse(code=404, message = "The employee you were trying to update is not found"),
            @ApiResponse(code=500, message = "The server is facing problems currently. Please try later.")
    })
    public ResponseEntity<Employee> updateEmployee(@PathParam(value = "id") Long employeeId,
            @Valid @RequestBody Employee employeeDetails) throws ResourceNotFoundException {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id::" +employeeId));
        employee.setEmailID(employeeDetails.getEmailID());
        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        final Employee updatedEmployee = employeeRepository.save(employee);
        logger.info("updated an employee");
        return ResponseEntity.ok().body(updatedEmployee);
    }

    @DELETE
    @Path("/employees/{id}")
    @DeleteMapping("/employees/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value="Remove an Employee from the system", response = OperationStatus.class)
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "Employee removed successfully"),
            @ApiResponse(code=401, message = "You are not authorized to remove the employee"),
            @ApiResponse(code=403, message = "Accessing the employee you were trying to reach is forbidden"),
            @ApiResponse(code=404, message = "The employee you were trying to remove is not found"),
            @ApiResponse(code=500, message = "The server is facing problems currently. Please try later.")
    })
    public OperationStatus deleteEmployee(@PathParam(value = "id") Long employeeId) throws ResourceNotFoundException{
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(()-> new ResourceNotFoundException("Employee not found for id::" + employeeId));
        employeeRepository.delete(employee);
        OperationStatus status = new OperationStatus("deleted",Boolean.TRUE);
        logger.info("deleted an employee");
        return status;
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/employees/form")
    @PostMapping("/employees/form")
    @ApiOperation(value="Add a new employee using web form", response = Employee.class)
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "Employee added successfully using form"),
            @ApiResponse(code=401, message = "You are not authorized"),
            @ApiResponse(code=403, message = "Adding the employee is forbidden"),
            @ApiResponse(code=404, message = "The employee you were trying to add using form is not found"),
            @ApiResponse(code=500, message = "The server is facing problems currently. Please try later.")
    })
    public Employee createEmployeeUsingForm(@FormParam("emailID") String emailID, @FormParam("firstName") String firstName, @FormParam("lastName") String lastName){
        logger.info("created an employee");
        Employee employee = new Employee(firstName, lastName, emailID);
        return employeeRepository.save(employee);
    }
}
