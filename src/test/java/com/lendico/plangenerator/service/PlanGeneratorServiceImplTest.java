package com.lendico.plangenerator.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.lendico.plangenerator.model.LoanInfoRequest;
import com.lendico.plangenerator.model.Repayment;
import com.lendico.plangenerator.util.LoanCalculationUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PlanGeneratorServiceImplTest {

	@Autowired
	private PlanGeneratorService planGeneratorService;
	
	@Test
	public void planGeneratorBasedOnLoanInfo() {
		LoanInfoRequest loanInfoRequest=new LoanInfoRequest("5000", "5.0", 24, "2018-01-01T01:00:01Z");
		List<Repayment> repayment=planGeneratorService.planGenerator(loanInfoRequest);
		assertTrue(repayment.size()==24);
	}
	
	@Test
	public void testNextRepaymentDate() throws Exception {
		LocalDateTime startDate = LoanCalculationUtil.parseStartDate("2018-01-01T00:00:00Z");
		LocalDateTime expectedDate = LoanCalculationUtil.parseStartDate("2018-02-01T00:00:00Z");
		LocalDateTime actualDate = planGeneratorService.getNextRepaymentDate(startDate);
		assertThat(expectedDate).isEqualTo(actualDate);
	}

	@Test
	public void testMonthlyInterest() throws Exception {
		BigDecimal expectedMonthlyInterest = new BigDecimal("20.83");
		BigDecimal actualMonthlyInterest = planGeneratorService.getMonthlyInterest(new BigDecimal("5000"),
				new BigDecimal("5.0").divide(new BigDecimal(100)));
		assertThat(expectedMonthlyInterest).isEqualTo(actualMonthlyInterest);
	}

	@Test
	public void testAnnuityPayment() throws Exception {
		BigDecimal expectedAnnuityPayment = new BigDecimal("219.36");
		BigDecimal actualAnnuityPayment = planGeneratorService.getAnnuityPayment(new BigDecimal("5000"),
				new BigDecimal("5.0").divide(new BigDecimal(1200), 8, RoundingMode.HALF_UP), 24);
		assertThat(expectedAnnuityPayment).isEqualTo(actualAnnuityPayment);
	}
}
