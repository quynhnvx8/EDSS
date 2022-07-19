package eone.rest.api.v2.entities;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

public class DataType {
	public static final String STRING_TYPE = String.class.getName();
	public static final String INTEGER_TYPE = Integer.class.getName();
	public static final String LONG_TYPE = Long.class.getName();
	public static final String DOUBLE_TYPE = Double.class.getName();
	public static final String BIGDECIMAL_TYPE = BigDecimal.class.getName();
	public static final String DATE_TYPE = Date.class.getName();
	public static final String TIMESTAMP_TYPE = Timestamp.class.getName();
	public static final String BOOLEAN_TYPE	= Boolean.class.getName();
}
