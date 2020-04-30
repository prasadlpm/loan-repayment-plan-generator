package com.lendico.plangenerator.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lendico.plangenerator.exception.ApplicationParseException;
import com.lendico.plangenerator.exception.PlanGeneratorException;
import com.lendico.plangenerator.model.LoanInfoRequest;
import com.lendico.plangenerator.model.Repayment;
import com.lendico.plangenerator.model.RepaymentResponse;
import com.lendico.plangenerator.service.PlanGeneratorService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/v1")
@Api(value = "Lendico Generate Loan Repayment Plan")
public class PlanGeneratorController {

	private static final Logger LOGGER = LoggerFactory.getLogger(PlanGeneratorController.class);

	@Autowired
	PlanGeneratorService planGeneratorService;

	@ApiOperation(value = "PlanGenerator Rest endpoint for generate Loan Repayment Plan", response = RepaymentResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Sucessfully generated loan repayment plan"),
			@ApiResponse(code = 401, message = "User are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource user were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource user were trying to reach is not found"),
			@ApiResponse(code = 1001, message = "Application parse exception in planGeneratorService"),
			@ApiResponse(code = 1002, message = "Application plan Generator exception in planGeneratorService "), })
	@PostMapping(value = "/generate-plan", consumes = { "application/json" }, produces = { "application/json" })
	public RepaymentResponse planGenerator(@Valid @RequestBody LoanInfoRequest loaninfo) {

		LOGGER.debug("LoanInfoRequest {} ", loaninfo);
		List<Repayment> list = null;
		try {
			list = planGeneratorService.planGenerator(loaninfo);
		} catch (ApplicationParseException exception) {
			LOGGER.error("ApplicationParseException in planGenerator :: {} ", exception.getMessage());
			throw new ApplicationParseException(exception.getMessage());
		} catch (Exception exception) {
			LOGGER.error("PlanGeneratorException in planGenerator :: {} ", exception.getMessage());
			throw new PlanGeneratorException(exception.getMessage());
		}
		return new RepaymentResponse(list);
	}

}
