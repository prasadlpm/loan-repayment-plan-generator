package com.lendico.plangenerator.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lendico.plangenerator.model.LoanInfoRequest;
import com.lendico.plangenerator.service.PlanGeneratorService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PlanGeneratorControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	PlanGeneratorService planGeneratorService;


	@Test
	public void whenNullLoan_thenOneConstrainViolation() throws Exception {
		mockMvc.perform(post("/api/v1/generate-plan").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsBytes(new LoanInfoRequest("", "5.0", 24, "2018-01-01T01:00:01Z"))))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errors").value("Loan Amount may not be null")).andReturn();
	}

	@Test
	public void whenZeroLoan_thenOneConstrainViolation() throws Exception {
		mockMvc.perform(post("/api/v1/generate-plan").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsBytes(new LoanInfoRequest("0", "5.0", 24, "2018-01-01T01:00:01Z"))))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errors").value("Loan Amount must be a positive number")).andReturn();
	}

	@Test
	public void whenNegitiveLoan_thenOneConstrainViolation() throws Exception {
		mockMvc.perform(post("/api/v1/generate-plan").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsBytes(new LoanInfoRequest("-5000", "5.0", 24, "2018-01-01T01:00:01Z"))))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errors").value("Loan Amount must be a positive number")).andReturn();
	}

	@Test
	public void whenNullNominalRate_thenOneConstrainViolation() throws Exception {
		mockMvc.perform(post("/api/v1/generate-plan").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsBytes(new LoanInfoRequest("5000", "", 24, "2018-01-01T01:00:01Z"))))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errors").value("Nominal Rate may not be null")).andReturn();
	}

	@Test
	public void whenZeroNominalRate_thenOneConstrainViolation() throws Exception {
		mockMvc.perform(post("/api/v1/generate-plan").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsBytes(new LoanInfoRequest("5000", "0", 24, "2018-01-01T01:00:01Z"))))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errors").value("Normal Rate must be a positive number")).andReturn();
	}

	@Test
	public void whenNegitiveNominalRate_thenOneConstrainViolation() throws Exception {
		mockMvc.perform(post("/api/v1/generate-plan").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsBytes(new LoanInfoRequest("5000", "-5", 24, "2018-01-01T01:00:01Z"))))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errors").value("Normal Rate must be a positive number")).andReturn();
	}

	@Test
	public void whenNullDuration_thenOneConstrainViolation() throws Exception {

		mockMvc.perform(post("/api/v1/generate-plan").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsBytes(new LoanInfoRequest("5000", "5.0", "2018-01-01T01:00:01Z"))))
				.andExpect(status().isBadRequest()).andExpect(jsonPath("$.errors").value("Duration may not be null"))
				.andReturn();
	}

	@Test
	public void whenZeroDuration_thenOneConstrainViolation() throws Exception {

		mockMvc.perform(post("/api/v1/generate-plan").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsBytes(new LoanInfoRequest("5000", "5.0", 0, "2018-01-01T01:00:01Z"))))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errors").value("Duration must be a positive number")).andReturn();
	}

	@Test
	public void whenNegitiveDuration_thenOneConstrainViolation() throws Exception {

		mockMvc.perform(post("/api/v1/generate-plan").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsBytes(new LoanInfoRequest("5000", "5.0", -24, "2018-01-01T01:00:01Z"))))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errors").value("Duration must be a positive number")).andReturn();
	}

	@Test
	public void whenNullStartDate_thenOneConstrainViolation() throws Exception {
		mockMvc.perform(post("/api/v1/generate-plan").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsBytes(new LoanInfoRequest("5000", "5.0", 24, ""))))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errors").value("Start Date may not be null")).andReturn();
	}

}
