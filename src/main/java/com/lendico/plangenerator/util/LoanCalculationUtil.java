package com.lendico.plangenerator.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class LoanCalculationUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoanCalculationUtil.class);

	private LoanCalculationUtil() {
	}

	public static LocalDateTime parseStartDate(String startDateString) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern(Constants.DATE_FORMATER);
		LocalDateTime startDate = LocalDateTime.parse(startDateString, format);
		return startDate;
	}

	public static BigDecimal getMonthlyInterest(BigDecimal initialOutstandingPrincipal, BigDecimal normalRate) {
		BigDecimal monthlyInterest = initialOutstandingPrincipal.multiply(Constants.DAYS_IN_MONTH).multiply(normalRate)
				.divide(Constants.DAYS_IN_YEAR, 2, RoundingMode.HALF_UP);
		LOGGER.debug("getMonthlyInterest {}", monthlyInterest);
		return monthlyInterest;
	}

	public static BigDecimal getAnnuityPayment(BigDecimal loanAmount, BigDecimal rate, int time) {
		BigDecimal powNum = (new BigDecimal(1).add(rate)).pow(time);
		BigDecimal annuityPayment = (loanAmount.multiply(rate).multiply(powNum))
				.divide((powNum.subtract(new BigDecimal(1))), 2, RoundingMode.HALF_UP);
		LOGGER.debug("getAnnuityPayment {} ", annuityPayment);
		return annuityPayment;
	}

	public static LocalDateTime getNextRepaymentDate(LocalDateTime date) {
		date = date.plusMonths(1);
		LOGGER.debug("getNextRepaymentDate {} ", date);
		return date;
	}

}
