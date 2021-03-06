package com.lendico.plangenerator.util;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

public final class Constants {
	
	public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
	public static final int APPLICATION_PARSE_EXCEPTION_CODE=1001;
    public static final int PLAN_GENERATOR_EXCEPTION_CODE=1002;
    public static final BigDecimal DAYS_IN_MONTH = new BigDecimal(30);
	public static final BigDecimal DAYS_IN_YEAR = new BigDecimal(360);
}
