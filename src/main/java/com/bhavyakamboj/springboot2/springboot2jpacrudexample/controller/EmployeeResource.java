package com.bhavyakamboj.springboot2.springboot2jpacrudexample.controller;

import com.bhavyakamboj.springboot2.springboot2jpacrudexample.exception.ResourceNotFoundException;
import com.bhavyakamboj.springboot2.springboot2jpacrudexample.model.Employee;
import com.bhavyakamboj.springboot2.springboot2jpacrudexample.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Path("/api/v1")
public class EmployeeResource {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeResource.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Path("/health")
    @HEAD
    public Response healthStatus(){
        return Response.ok().header("My header",new HashMap<Integer, String>(){
            {
                put(1,"a");
                put(2,"b");
                put(3,"c");
            }
        }).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON) //@Produces defines a media-type that the resource method can produce.
    @Path("/employees") //@Path is used to identify the URI path (relative) that a resource class or class method will serve requests for
    public List<Employee> getAllEmployees(){
        logger.info("returned all employees");
        return employeeRepository.findAll();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/employees/{id}")
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
    public Employee createEmployee(@Valid @RequestBody Employee employee){
        logger.info("created an employee");
        return employeeRepository.save(employee);
    }

    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathParam(value = "id") Long employeeId,@Valid @RequestBody Employee employeeDetails) throws ResourceNotFoundException {
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
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String,Boolean> deleteEmployee(@PathParam(value = "id") Long employeeId) throws ResourceNotFoundException{
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(()-> new ResourceNotFoundException("Employee not found for id::" + employeeId));
        employeeRepository.delete(employee);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        logger.info("deleted an employee");
        return response;
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/employees/form")
//    @PostMapping("/employees/form")
    public Employee createEmployeeUsingForm(@FormParam("emailID") String emailID, @FormParam("firstName") String firstName, @FormParam("lastName") String lastName){
        logger.info("created an employee");
        Employee employee = new Employee(firstName, lastName, emailID);
        return employeeRepository.save(employee);
    }
}
