package com.mindex.challenge;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.service.ReportingStructureService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ChallengeApplicationTests {

	private String reportingStructureURL;
	private String compensationUrl;
	private String employeeUrl;

	@Autowired
	private EmployeeService employeeService;
	private ReportingStructureService reportingStructureService;

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Before
	public void setup() {
		reportingStructureURL = "http://localhost:" + port + "/reportingStructure/{id}";
		compensationUrl = "http://localhost:" + port + "/compensation/{id}";
		employeeUrl = "http://localhost:" + port + "/employee";
	}

	@Test
	public void testReportingStructure_BadID() {
		String badId = "___+++~~~ This Id Does not Follow any Standard ~~~+++___";
		ReportingStructure reportingStructure = restTemplate.getForEntity(reportingStructureURL, ReportingStructure.class, badId).getBody();

		assertEquals(null, reportingStructure.getEmployee());
		assertEquals(0, reportingStructure.getNumberOfReports());
	}

	@Test
	public void testReportingStructure_ValidID() {
		String validId = "16a596ae-edd3-4847-99fe-c4518e82c86f";
		ReportingStructure reportingStructure = restTemplate.getForEntity(reportingStructureURL, ReportingStructure.class, validId).getBody();

		// Testing with expected id provided in the ReadMe with John Lennon, expected 4 total reports
		assertEquals("John", reportingStructure.getEmployee().getFirstName());
		assertEquals(4, reportingStructure.getNumberOfReports());
	}

	@Test
	public void testCompensationCreateReadUpdate() {
		Employee testEmployee = new Employee();
		testEmployee.setFirstName("John");
		testEmployee.setLastName("Doe");
		testEmployee.setDepartment("Engineering");
		testEmployee.setPosition("Developer");

		Compensation testCompensation = new Compensation();
		testCompensation.setSalary(BigDecimal.valueOf(65000.00));
		testCompensation.setEffectiveDate(LocalDate.of(2024, 9, 1));
		testEmployee.setCompensation(testCompensation);

		// Create checks
		Employee createdEmployee = restTemplate.postForEntity(employeeUrl, testEmployee, Employee.class).getBody();

		assertNotNull(createdEmployee.getEmployeeId());
		assertEmployeeEquivalence(testEmployee, createdEmployee);

		// Read checks
		Compensation readCompensation = restTemplate.getForEntity(compensationUrl, Compensation.class, createdEmployee.getEmployeeId()).getBody();
		assertEquals(createdEmployee.getCompensation().getSalary(), readCompensation.getSalary());
		assertEquals(createdEmployee.getCompensation().getEffectiveDate(), readCompensation.getEffectiveDate());

		// Update checks
		readCompensation.setSalary(BigDecimal.valueOf(90000.00));
		readCompensation.setEffectiveDate(LocalDate.of(2035, 5, 25));

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		Compensation updatedCompensation =
				restTemplate.exchange(compensationUrl,
						HttpMethod.PUT,
						new HttpEntity<Compensation>(readCompensation, headers),
						Compensation.class,
						createdEmployee.getEmployeeId()).getBody();

		assertEquals(readCompensation.getSalary(), updatedCompensation.getSalary());
		assertEquals(readCompensation.getEffectiveDate(), updatedCompensation.getEffectiveDate());
	}

	@Test
	public void testCompensationCreateReadUpdate_BadInputs() {
		Employee testEmployee = new Employee();
		testEmployee.setFirstName("John");
		testEmployee.setLastName("Doe");
		testEmployee.setDepartment("Engineering");
		testEmployee.setPosition("Developer");
		testEmployee.setEmployeeId("Very Improper UUID that is bad");

		Compensation testCompensation = new Compensation();
		testCompensation.setSalary(BigDecimal.valueOf(65000.00));
		testCompensation.setEffectiveDate(LocalDate.of(2024, 9, 1));
		testEmployee.setCompensation(testCompensation);

		// Read checks
		Compensation readCompensation = restTemplate.getForEntity(compensationUrl, Compensation.class, testEmployee.getEmployeeId()).getBody();
		assertEquals(null, readCompensation.getSalary());
		assertEquals(null, readCompensation.getEffectiveDate());

		// Update checks
		readCompensation.setSalary(BigDecimal.valueOf(90000.00));
		readCompensation.setEffectiveDate(LocalDate.of(2035, 5, 25));

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		Compensation updatedCompensation =
				restTemplate.exchange(compensationUrl,
						HttpMethod.PUT,
						new HttpEntity<Compensation>(readCompensation, headers),
						Compensation.class,
						testEmployee.getEmployeeId()).getBody();

		assertEquals(null, updatedCompensation.getSalary());
		assertEquals(null, updatedCompensation.getEffectiveDate());
	}

	private static void assertEmployeeEquivalence(Employee expected, Employee actual) {
		assertEquals(expected.getFirstName(), actual.getFirstName());
		assertEquals(expected.getLastName(), actual.getLastName());
		assertEquals(expected.getDepartment(), actual.getDepartment());
		assertEquals(expected.getPosition(), actual.getPosition());
		// These two lines are holdovers from when I was trying to get the update method to return an employee object
		// rather than a compensation object. I still find this valuable though and will keep it for future additons.
		assertEquals(expected.getCompensation().getSalary(), actual.getCompensation().getSalary());
		assertEquals(expected.getCompensation().getEffectiveDate(), actual.getCompensation().getEffectiveDate());
	}
}
