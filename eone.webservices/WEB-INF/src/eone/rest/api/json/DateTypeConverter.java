
package eone.rest.api.json;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.JsonElement;

import eone.base.model.GridField;
import eone.base.model.MColumn;
import eone.util.DisplayType;

/**
 * 
 * Type converter for DisplayType.Date and DisplayType.DateTime
 *
 */
public class DateTypeConverter implements ITypeConverter<Date> {
	public static final String ISO8601_DATE_PATTERN = "yyyy-MM-dd";
	public static final String ISO8601_TIME_PATTERN = "HH:mm:ss'Z'";
	public static final String ISO8601_DATETIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'";

	/**
	 * 
	 */
	public DateTypeConverter() {
	}

	private Object toJsonValue(int displayType, Date value) {
		String pattern = getPattern(displayType);
		
		if (DisplayType.isDate(displayType) && pattern != null && value != null) {
			String formatted = new SimpleDateFormat(pattern).format(value);
			return formatted;
		} else {
			return null;
		}
	}

	@Override
	public Object toJsonValue(MColumn column, Date value) {
		return toJsonValue(column.getAD_Reference_ID(), value);
	}

	@Override
	public Object toJsonValue(GridField field, Date value) {
		return toJsonValue(field.getDisplayType(), value);
	}

	private Timestamp fromJsonValue(int displayType, JsonElement value) {
		String pattern = getPattern(displayType);
		
		if (DisplayType.isDate(displayType) && pattern != null && value != null) {
			Date parsed = null;
			try {
				parsed = new SimpleDateFormat(pattern).parse(value.getAsString());
			} catch (ParseException e) {
				return null;
			}
			return new Timestamp(parsed.getTime());
		} else {
			return null;
		}
	}
	
	@Override
	public Object fromJsonValue(MColumn column, JsonElement value) {
		return fromJsonValue(column.getAD_Reference_ID(), value);
	}

	@Override
	public Object fromJsonValue(GridField field, JsonElement value) {
		return fromJsonValue(field.getDisplayType(), value);
	}
	
	/**
	 * Returns an ISO-8601 format pattern according to the display type.
	 * @param displayType Display Type
	 * @return formatting pattern
	 */
	private String getPattern(int displayType) {
		if (displayType == DisplayType.Date)
			return ISO8601_DATE_PATTERN;
		else if (displayType == DisplayType.Time)
			return ISO8601_TIME_PATTERN;
		else if (displayType == DisplayType.DateTime)
			return ISO8601_DATETIME_PATTERN;
		else
			return null;
	}
}
