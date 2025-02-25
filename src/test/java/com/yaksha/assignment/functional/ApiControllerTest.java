package com.yaksha.assignment.functional;

import static com.yaksha.assignment.utils.TestUtils.businessTestFile;
import static com.yaksha.assignment.utils.TestUtils.currentTest;
import static com.yaksha.assignment.utils.TestUtils.testReport;
import static com.yaksha.assignment.utils.TestUtils.yakshaAssert;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.yaksha.assignment.controller.AppController;
import com.yaksha.assignment.utils.JavaParserUtils;

@WebMvcTest(AppController.class)
public class ApiControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@AfterAll
	public static void afterAll() {
		testReport();
	}

	@Test
	public void testHandleSuccess() throws Exception {
		String apiUrl = "https://jsonplaceholder.typicode.com/posts/1"; // A sample valid URL

		// Declare boolean flags to track each assertion result
		boolean statusOk = false;
		boolean containsSuccessMessage = false;

		try {
			// Perform the GET request to handle success (200 OK)
			MvcResult result = mockMvc.perform(get("/handleSuccess").param("apiUrl", apiUrl)).andExpect(status().isOk()) // Check
																															// if
																															// the
																															// status
																															// is
																															// OK
																															// (200)
					.andReturn(); // Capture the result

			// Check if the HTTP status is OK (200)
			statusOk = result.getResponse().getStatus() == 200;

			// Check if the response contains success message
			containsSuccessMessage = result.getResponse().getContentAsString().contains("Success");

		} catch (Exception ex) {
			System.out.println("Error occurred: " + ex.getMessage());
		}

		// Combine all the results and pass them to yakshaAssert
		boolean finalResult = statusOk && containsSuccessMessage;

		// Use yakshaAssert to check if all assertions passed
		yakshaAssert(currentTest(), finalResult ? "true" : "false", businessTestFile);
	}

	@Test
	public void testHandleNotFound() throws Exception {
		String apiUrl = "https://jsonplaceholder.typicode.com/invalidendpoint"; // A sample invalid URL

		// Declare boolean flags to track each assertion result
		boolean statusOk = false;
		boolean containsErrorMessage = false;

		try {
			// Perform the GET request to handle 404 Not Found
			MvcResult result = mockMvc.perform(get("/handleNotFound").param("apiUrl", apiUrl))
					.andExpect(status().isOk()) // Check if the status is OK (200)
					.andReturn(); // Capture the result

			System.out.println(result.getResponse().getContentAsString());
			// Check if the response contains "Resource not found."
			containsErrorMessage = result.getResponse().getContentAsString().contains("404");

		} catch (Exception ex) {
			System.out.println("Error occurred: " + ex.getMessage());
		}

		// Combine all the results and pass them to yakshaAssert
		boolean finalResult = containsErrorMessage;

		// Use yakshaAssert to check if all assertions passed
		yakshaAssert(currentTest(), finalResult ? "true" : "false", businessTestFile);
	}

	@Test
	public void testHandleServerError() throws Exception {
		String apiUrl = "https://jsonplaceholder.typicode.com/posts/invalid"; // A URL that simulates server error

		// Declare boolean flags to track each assertion result
		boolean statusOk = false;
		boolean containsErrorMessage = false;

		try {
			// Perform the GET request to handle 500 Internal Server Error
			MvcResult result = mockMvc.perform(get("/handleServerError").param("apiUrl", apiUrl))
					.andExpect(status().isOk()) // Check if the status is OK (200)
					.andReturn(); // Capture the result

			// Check if the response contains "Server issue"
			System.out.println(result.getResponse().getContentAsString());
			containsErrorMessage = result.getResponse().getContentAsString().contains("issue");

		} catch (Exception ex) {
			System.out.println("Error occurred: " + ex.getMessage());
		}

		// Combine all the results and pass them to yakshaAssert
		boolean finalResult = containsErrorMessage;

		// Use yakshaAssert to check if all assertions passed
		yakshaAssert(currentTest(), finalResult ? "true" : "false", businessTestFile);
	}

	@Test
	public void testHandleUnexpectedStatusCode() throws Exception {
		String apiUrl = "https://jsonplaceholder.typicode.com/posts"; // A valid URL that might return unexpected status
																		// codes

		// Declare boolean flags to track each assertion result
		boolean statusOk = false;
		boolean containsUnexpectedMessage = false;

		try {
			// Perform the GET request to handle unexpected status codes
			MvcResult result = mockMvc.perform(get("/handleUnexpectedStatusCode").param("apiUrl", apiUrl))
					.andExpect(status().isOk()) // Check if the status is OK (200)
					.andReturn(); // Capture the result

			// Check if the response contains "Unexpected status code"
			containsUnexpectedMessage = result.getResponse().getContentAsString().contains("Unexpected status code");

		} catch (Exception ex) {
			System.out.println("Error occurred: " + ex.getMessage());
		}

		// Combine all the results and pass them to yakshaAssert
		boolean finalResult = containsUnexpectedMessage;

		// Use yakshaAssert to check if all assertions passed
		yakshaAssert(currentTest(), finalResult ? "true" : "false", businessTestFile);
	}

	@Test
	public void testControllerStructure() throws Exception {
		String filePath = "src/main/java/com/yaksha/assignment/controller/AppController.java"; // Update path to your
																								// file
		boolean result = JavaParserUtils.checkControllerStructure(filePath, // Pass the class file path
				"RestController", // Check if @RestController is used on the class
				"handleSuccess", // Check if the method name is correct
				"GetMapping", // Check if @GetMapping is present on the method
				"apiUrl", // Check if the parameter has @RequestParam annotation
				"String" // Ensure the return type is String
		);
		// checkControllerStructure
		yakshaAssert(currentTest(), result, businessTestFile);
	}
}
