package eone.rest.api.v2.entities;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DataParse {
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
	public static Object parseVariable(String name, String type, String value) {
		Object ret = null;
		if (DataType.BOOLEAN_TYPE.equalsIgnoreCase(type)) {
			if ("Y".equals(value) || "true".equalsIgnoreCase(value)) {
				ret = true;
			} else if ("N".equals(value) || "false".equalsIgnoreCase(value)) {
				ret = false;
			} else {
				ret = null;
			}
		} else if (DataType.STRING_TYPE.equalsIgnoreCase(type)) {
			ret = value;
		} else if (DataType.INTEGER_TYPE.equalsIgnoreCase(type)) {
			ret = Integer.parseInt(value);
		} else if (DataType.LONG_TYPE.equalsIgnoreCase(type)) {
			ret = Long.parseLong(value);
		} else if (DataType.DOUBLE_TYPE.equalsIgnoreCase(type) || DataType.BIGDECIMAL_TYPE.equalsIgnoreCase(type)) {
			ret = new BigDecimal(value);
		} else if (DataType.DATE_TYPE.equalsIgnoreCase(type)) {
			try {
				ret = sdf.parse(value);
			} catch (ParseException e) {
				e.printStackTrace();
				ret = null;
			}
		} else if (DataType.TIMESTAMP_TYPE.equalsIgnoreCase(type)) {
			ret = Timestamp.valueOf(value);
		} else {
			ret = null;
		}
		return ret;
	}

}
