package com.molinacorp.simplerestapp;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.molinacorp.simplerestapp.entity.Employee;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SimpleRestAppTests {
	
	@LocalServerPort
	private int port;
	
	TestRestTemplate restTemplate = new TestRestTemplate();

	HttpHeaders headers = new HttpHeaders();
	
	@LocalServerPort
	int randomServerPort;
 
	@Test
	public void testInsertEmployeeSuccess() throws URISyntaxException {
		final String baseUrl = "http://localhost:"+randomServerPort+"/employees/";
		URI uri = new URI(baseUrl);
		Employee employee = new Employee("John Doe", "Markets", 1000.56);
		HttpEntity<Employee> request = new HttpEntity<>(employee, new HttpHeaders());
		ResponseEntity<String> result = this.restTemplate.postForEntity(uri, request, String.class);
		Assert.assertEquals(200, result.getStatusCodeValue());
	}
	
	@Test
	public void testSelectAllEmployeeSuccess() throws URISyntaxException {
		final String baseUrl = "http://localhost:"+randomServerPort+"/employees/";
		URI uri = new URI(baseUrl);
		ResponseEntity<String> result = this.restTemplate.getForEntity(uri, String.class);
		Assert.assertTrue(result.getBody().contains("JDM"));
		Assert.assertTrue(result.getBody().contains("John Doe"));
		Assert.assertTrue(result.getBody().contains("Markets"));
		Assert.assertTrue(result.getBody().contains("1000.56"));
	}
	
	@Test
	public void testUpdateEmployeeSuccess() throws URISyntaxException {
		final String baseUrl = "http://localhost:"+randomServerPort+"/employees/JDM";
		URI uri = new URI(baseUrl);
		Employee employee = new Employee("John Doe", "Sales", 3500.12);
		this.restTemplate.put(uri, employee);
		ResponseEntity<String> result = this.restTemplate.getForEntity(uri, String.class);
		Assert.assertEquals(200, result.getStatusCodeValue());
		Assert.assertTrue(result.getBody().contains("3500.12"));
		Assert.assertTrue(result.getBody().contains("Sales"));
	}
	
	@Test
	public void testSelectEmployeeSuccess() throws URISyntaxException {
		final String baseUrl = "http://localhost:"+randomServerPort+"/employees/JDM";
		URI uri = new URI(baseUrl);
		ResponseEntity<String> result = this.restTemplate.getForEntity(uri, String.class);
		Assert.assertEquals(200, result.getStatusCodeValue());
		Assert.assertTrue(result.getBody().contains("JDM"));
		Assert.assertTrue(result.getBody().contains("John Doe"));
		Assert.assertTrue(result.getBody().contains("3500.12"));
		Assert.assertTrue(result.getBody().contains("Sales"));
	}
	
	@Test
	public void testSearchEmployeeSuccesss() throws URISyntaxException {
		final String baseUrl = "http://localhost:"+randomServerPort+"/employees/search?dept=sales";
		URI uri = new URI(baseUrl);
		ResponseEntity<String> result = this.restTemplate.getForEntity(uri, String.class);
		Assert.assertTrue(result.getBody().contains("Tolkien")); //Found on the default database!
		Assert.assertTrue(result.getBody().contains("King")); //Found on the default database!
	}
	
	@Test
	public void testSelectEmployeeError() throws URISyntaxException {
		final String baseUrl = "http://localhost:"+randomServerPort+"/employees/XXX";
		URI uri = new URI(baseUrl);
		ResponseEntity<String> result = this.restTemplate.getForEntity(uri, String.class);
		Assert.assertEquals(404, result.getStatusCodeValue());
	}
	
	@Test
	public void testInsertEmployeeError() throws URISyntaxException {
		final String baseUrl = "http://localhost:"+randomServerPort+"/employees/";
		URI uri = new URI(baseUrl);
		Employee employee = new Employee("John123456789012345678901234567890", "Markets1234567890", 123456789012.12);
		HttpEntity<Employee> request = new HttpEntity<>(employee, new HttpHeaders());
		ResponseEntity<String> result = this.restTemplate.postForEntity(uri, request, String.class);
		Assert.assertEquals(400, result.getStatusCodeValue());
	}
	
	@Test
	public void testDeleteEmployeeSuccess() throws URISyntaxException {
		final String baseUrl = "http://localhost:"+randomServerPort+"/employees/JDM";
		URI uri = new URI(baseUrl);
		this.restTemplate.delete(uri);
		ResponseEntity<String> result = this.restTemplate.getForEntity(uri, String.class);
		Assert.assertEquals(404, result.getStatusCodeValue());
	}
}
