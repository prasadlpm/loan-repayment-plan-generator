package com.lendico.plangenerator.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public final class LoanCalculationUtilTest {

	@Test
	public void parseStartDate() {
		LocalDate startDate = LoanCalculationUtil.parseStartDate("2018-01-01T00:00:00Z");
		LocalDate expectedDate = LoanCalculationUtil.parseStartDate("2018-01-01T00:00:00Z");
		assertThat(startDate).isEqualTo(expectedDate);
	}

	@Test
	public void getMonthlyInterest() {
		BigDecimal monthlyInterest = LoanCalculationUtil.getMonthlyInterest(new BigDecimal(5000),
				new BigDecimal("5.0"));
		assertThat(monthlyInterest).isGreaterThan(new BigDecimal(20.83));
	}

	@Test
	public void getAnnuityPayment() {
		BigDecimal monthlyInterest = LoanCalculationUtil.getMonthlyInterest(new BigDecimal(5000),
				new BigDecimal("5.0"));
		BigDecimal annuityPayment = LoanCalculationUtil.getAnnuityPayment(new BigDecimal(5000), monthlyInterest, 24);
		assertTrue(annuityPayment.longValue()>0);
	}

	@Test
	public void getNextRepaymentDate() {
		LocalDate startDate = LoanCalculationUtil.parseStartDate("2018-01-01T00:00:00Z");
		LocalDate nextDate = LoanCalculationUtil.getNextRepaymentDate(startDate);
		assertThat(nextDate.minusMonths(1)).isEqualTo(startDate);
	}

}
