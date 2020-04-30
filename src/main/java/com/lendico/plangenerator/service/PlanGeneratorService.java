package com.lendico.plangenerator.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lendico.plangenerator.model.LoanInfoRequest;
import com.lendico.plangenerator.model.Repayment;

@Service
public interface PlanGeneratorService {
	public List<Repayment> planGenerator(LoanInfoRequest loan);

	public LocalDate getNextRepaymentDate(LocalDate date);

	public BigDecimal getAnnuityPayment(BigDecimal loanAmount, BigDecimal rate, int time);

	public BigDecimal getMonthlyInterest(BigDecimal initialOutstandingPrincipal, BigDecimal normalRate);
}
