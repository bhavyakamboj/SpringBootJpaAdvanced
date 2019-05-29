package com.bhavyakamboj.springboot2.springboot2jpacrudexample;

import com.bhavyakamboj.springboot2.springboot2jpacrudexample.model.Employee;
import net.bytebuddy.asm.Advice;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Springboot2JpaCrudExampleApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class Springboot2JpaCrudExampleApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	private String getRootURL() {return "http://localhost:" + port;}
	@Test
	public void contextLoads() {
	}

	@Test
	public void testCreateEmployee(){
	Employee employee = new Employee();
	employee.setEmailID("bhavyakamboj@gmail.com");
	employee.setFirstName("bhavya");
	employee.setLastName("kamboj");

		ResponseEntity<Employee> postResponse = restTemplate.postForEntity(getRootURL(),employee,Employee.class);
		assertNotNull(postResponse);
		assertNotNull(postResponse.getBody());
	}

}
