package com.lendico.plangenerator.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.lendico.plangenerator.model.LoanInfoRequest;
import com.lendico.plangenerator.model.Repayment;
import com.lendico.plangenerator.util.LoanCalculationUtil;

@Service
public class PlanGeneratorServiceImpl implements PlanGeneratorService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PlanGeneratorServiceImpl.class);

	@Override
	public List<Repayment> planGenerator(LoanInfoRequest loanInfoRequest) {

		List<Repayment> repaymentList = new ArrayList<>();
		BigDecimal interest;
		BigDecimal emiPrinciple;
		BigDecimal borrowerPaymentAmount;
		BigDecimal remainingOutstandingPrincipal;

		BigDecimal monthlyRateOfInterest = loanInfoRequest.getNominalRate().divide(new BigDecimal(1200), 8,
				RoundingMode.HALF_UP);
		LOGGER.debug("monthlyRateOfInterest {} ", monthlyRateOfInterest);
		BigDecimal rateOfInterestPercentage = loanInfoRequest.getNominalRate().divide(new BigDecimal(100));
		LOGGER.debug("rateOfInterestPercentage {} ", rateOfInterestPercentage);
		BigDecimal initialOutstandingPrincipal = loanInfoRequest.getLoanAmount();

		LocalDateTime startDate = LoanCalculationUtil.parseStartDate(loanInfoRequest.getStartDate());

		borrowerPaymentAmount = getAnnuityPayment(initialOutstandingPrincipal, monthlyRateOfInterest,
				loanInfoRequest.getDuration());
		LOGGER.debug("borrowerPaymentAmount {} ", borrowerPaymentAmount);
		for (int i = 1; i <= loanInfoRequest.getDuration(); i++) {
			interest = LoanCalculationUtil.getMonthlyInterest(initialOutstandingPrincipal, rateOfInterestPercentage);
			if (borrowerPaymentAmount.compareTo(initialOutstandingPrincipal) < 0) {
				emiPrinciple = borrowerPaymentAmount.subtract(interest);
			} else {
				borrowerPaymentAmount = initialOutstandingPrincipal.add(interest);
				emiPrinciple = initialOutstandingPrincipal;
				LOGGER.debug("borrowerPaymentAmount modified{} ", borrowerPaymentAmount);
			}
			LOGGER.debug("emiPrincipal {} ", emiPrinciple);
			remainingOutstandingPrincipal = initialOutstandingPrincipal.subtract(emiPrinciple);
			Repayment repaymentPay = new Repayment(borrowerPaymentAmount, initialOutstandingPrincipal, interest,
					emiPrinciple, remainingOutstandingPrincipal, startDate.toString());
			repaymentList.add(repaymentPay);

			if (remainingOutstandingPrincipal.compareTo(new BigDecimal(0)) > 0) {
				initialOutstandingPrincipal = remainingOutstandingPrincipal;
			}
			startDate = getNextRepaymentDate(startDate);
		}
		return repaymentList;

	}

	@Override
	public LocalDateTime getNextRepaymentDate(LocalDateTime startDate) {
		Function<LocalDateTime, LocalDateTime> nextRepaymentDate = LoanCalculationUtil::getNextRepaymentDate;
		return nextRepaymentDate.apply(startDate);
	}

	@Override
	public BigDecimal getAnnuityPayment(BigDecimal loanAmount, BigDecimal rate, int time) {
		return LoanCalculationUtil.getAnnuityPayment(loanAmount, rate, time);
	}

	@Override
	public BigDecimal getMonthlyInterest(BigDecimal initialOutstandingPrincipal, BigDecimal normalRate) {
		BiFunction<BigDecimal, BigDecimal, BigDecimal> monthlyInterest = LoanCalculationUtil::getMonthlyInterest;
		return monthlyInterest.apply(initialOutstandingPrincipal, normalRate);
	}

}
