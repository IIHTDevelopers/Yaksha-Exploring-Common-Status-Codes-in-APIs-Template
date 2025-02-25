package com.yaksha.assignment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class AppController {

	private final RestTemplate restTemplate;

	public AppController(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	// Method to handle 200 OK status code
	@GetMapping("/handleSuccess")
	public String handleSuccess(@RequestParam String apiUrl) {
		ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);

		// Check if the status code is 200 OK
		if (response.getStatusCodeValue() == 200) {
			return "Success: Data retrieved.";
		}
		return "Error: Unexpected status code " + response.getStatusCodeValue();
	}

	// Method to handle 404 Not Found status code
	@GetMapping("/handleNotFound")
	public String handleNotFound(@RequestParam String apiUrl) {
		try {
			// Perform the GET request to the provided URL
			ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);

			// Check if the status code is 404 Not Found
			if (response.getStatusCodeValue() == 404) {
				return "Error: Resource not found.";
			}

			return "Error: Unexpected status code " + response.getStatusCodeValue();
		} catch (Exception e) {
			// Handle other errors gracefully
			return "Error: An exception occurred - " + e.getMessage();
		}
	}

	// Method to handle 500 Internal Server Error status code
	@GetMapping("/handleServerError")
	public String handleServerError(@RequestParam String apiUrl) {
		try {
			// Perform the GET request to the provided URL
			ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);

			// Check if the status code is 500 Internal Server Error
			if (response.getStatusCodeValue() == 500) {
				return "Error: Server issue, please try again later.";
			}

			return "Error: Unexpected status code " + response.getStatusCodeValue();

		} catch (Exception e) {
			// Handle any exceptions, like server issues, timeouts, etc.
			return "Error: Unable to reach the server or network issue.";
		}
	}

	// Method to handle any other unexpected status codes
	@GetMapping("/handleUnexpectedStatusCode")
	public String handleUnexpectedStatusCode(@RequestParam String apiUrl) {
		try {
			// Perform the GET request to the provided URL
			ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);

			// Handle unexpected status codes
			return "Error: Unexpected status code " + response.getStatusCodeValue();

		} catch (Exception e) {
			// Handle the exception for any errors like connection issues, invalid URLs,
			// etc.
			return "Error: Unable to reach the server or network issue.";
		}
	}
}
