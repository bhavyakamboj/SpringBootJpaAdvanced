package com.bhavyakamboj.springboot2.springboot2jpacrudexample;

import com.bhavyakamboj.springboot2.springboot2jpacrudexample.model.Employee;
import net.bytebuddy.asm.Advice;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Springboot2JpaCrudExampleApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class Springboot2JpaCrudExampleApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	private String getRootURL(){
		return "http://localhost:" + port;
	}
	@Test
	public void contextLoads() {
	}

	@Test
	public void testGetAllEmployees(){
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(getRootURL() + "/employees", HttpMethod.GET, entity, String.class);

		assertNotNull(response.getBody());
	}

	@Test
	public void testGetEmployeeByID(){
		Employee employee = restTemplate.getForObject(getRootURL() + "/employees/1", Employee.class);
		System.out.println(employee.getFirstName());
		assertNotNull(employee);
	}

	@Test
	public void testCreateEmployee(){
		Employee employee = new Employee();
		employee.setEmailID("admin@example.com");
		employee.setFirstName("admin3");
		employee.setLastName("admin3");
		employee.setCreatedBy("admin3");
		employee.setLastModifiedBy("admin3");

		ResponseEntity<Employee> postResponse = restTemplate.postForEntity(getRootURL() + "/employees", employee, Employee.class);
		assertNotNull(postResponse);
		assertNotNull(postResponse.getBody());
	}

	@Test
	public void testUpdateEmployee(){
		int id = 1;
		Employee employee = restTemplate.getForObject(getRootURL() + "/employees/" + id, Employee.class);
		employee.setFirstName("admin1");
		employee.setLastName("admin2");

		restTemplate.put(getRootURL() + "/employees/" + id, employee);

		Employee updatedEmployee = restTemplate.getForObject(getRootURL() + "/employees/" + id, Employee.class);
		assertNotNull(updatedEmployee);
	}

	@Test
	public void testDeleteEmployee(){
		int id = 2;
		Employee employee = restTemplate.getForObject(getRootURL() + "/employees/" + id, Employee.class);
		assertNotNull(employee);

		restTemplate.delete(getRootURL() + "/employee/" + id);

		try{
			employee = restTemplate.getForObject(getRootURL() + "/employee/" + id, Employee.class);
		} catch (HttpClientErrorException e){
			assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
		}
	}

}
