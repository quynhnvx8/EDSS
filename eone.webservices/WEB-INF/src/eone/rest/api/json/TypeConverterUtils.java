
package eone.rest.api.json;

import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.regex.Pattern;

import javax.ws.rs.core.Response.Status;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonPrimitive;

import eone.base.Service;
import eone.base.ServiceQuery;
import eone.base.model.GridField;
import eone.base.model.MColumn;
import eone.base.model.MTable;
import eone.base.model.PO;
import eone.base.model.Query;
import eone.util.DisplayType;
import eone.util.Env;
import eone.util.Util;

/**
 * @author Client
 *
 */
public class TypeConverterUtils {

	private static final Pattern NONLATIN = Pattern.compile("[^\\w_-]");  
	private static final Pattern SEPARATORS = Pattern.compile("[\\s\\p{Punct}&&[^-]&&[^_]]");
	
	/**
	 * private constructor
	 */
	private TypeConverterUtils() {
	}

	/**
	 * Convert table's column name to json property name
	 * @param columnName
	 * @return propertyName
	 */
	public static String toPropertyName(String columnName) {
		String propertyName = columnName;
		if (!propertyName.contains("_")) {
			String initial = propertyName.substring(0, 1).toLowerCase();
			propertyName = initial + propertyName.substring(1);
		}
		return propertyName;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	/**
	 * Convert db column value to json value
	 * @param column
	 * @param value
	 * @return Object
	 */
	public static Object toJsonValue(MColumn column, Object value) {		
		ITypeConverter typeConverter = getTypeConverter(column.getAD_Reference_ID(), value);
		
		if (typeConverter != null) {
			return typeConverter.toJsonValue(column, value);
		} else if (value != null && DisplayType.isText(column.getAD_Reference_ID())) {
			return value.toString();
		} else if (value != null && column.getAD_Reference_ID() == DisplayType.ID && value instanceof Number) {
			return ((Number)value).intValue();
		} else {
			return null;
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	/**
	 * Convert db column value to json value
	 * @param field
	 * @param value
	 * @return Object
	 */
	public static Object toJsonValue(GridField field, Object value) {		
		ITypeConverter typeConverter = getTypeConverter(field.getDisplayType(), value);
		
		if (typeConverter != null) {
			return typeConverter.toJsonValue(field, value);
		} else if (value != null && DisplayType.isText(field.getDisplayType())) {
			return value.toString();
		} else if (value != null && field.getDisplayType() == DisplayType.ID && value instanceof Number) {
			return ((Number)value).intValue();
		} else {
			return null;
		}
	}
	
	@SuppressWarnings("rawtypes")
	/**
	 * Convert json value to db column value
	 * @param column
	 * @param value
	 * @return Object
	 */
	public static Object fromJsonValue(MColumn column, JsonElement value) {		
		ITypeConverter typeConverter = getTypeConverter(column.getAD_Reference_ID(), value);
		
		if (typeConverter != null) {
			return typeConverter.fromJsonValue(column, value);
		} else if (value != null && !(value instanceof JsonNull) && DisplayType.isText(column.getAD_Reference_ID())) {
			return value.getAsString();
		} else {
			return null;
		}
	}
	
	@SuppressWarnings("rawtypes")
	/**
	 * Convert json value to db column value
	 * @param gridField
	 * @param value
	 * @return Object
	 */
	public static Object fromJsonValue(GridField gridField, JsonElement value) {		
		ITypeConverter typeConverter = getTypeConverter(gridField.getDisplayType(), value);
		
		if (typeConverter != null) {
			return typeConverter.fromJsonValue(gridField, value);
		} else if (value != null && !(value instanceof JsonNull) && DisplayType.isText(gridField.getDisplayType())) {
			return value.getAsString();
		} else {
			return null;
		}
	}
	
	/**
	 * convert arbitrary text to slug
	 * @param input
	 * @return slug
	 */
	public static String slugify(String input) {  
		String noseparators = SEPARATORS.matcher(input).replaceAll("-");
	    String normalized = Normalizer.normalize(noseparators, Form.NFD);
	    String slug = NONLATIN.matcher(normalized).replaceAll("");
	    return slug.toLowerCase(Locale.ENGLISH).replaceAll("-{2,}","-").replaceAll("^-|-$","");
	}
	
	@SuppressWarnings({ "rawtypes" })
	private static ITypeConverter getTypeConverter(int displayType, Object value) {
		ITypeConverter typeConverter = null;
		ServiceQuery query = new ServiceQuery();
		query.put("displayType", Integer.toString(displayType));
		typeConverter = Service.locator().locate(ITypeConverter.class, query).getService();
		if (typeConverter == null) {
			if (DisplayType.isNumeric(displayType) && value instanceof Number) {
				typeConverter = new NumericTypeConverter();
			} else if (DisplayType.isDate(displayType) && value instanceof Date) {
				typeConverter = new DateTypeConverter();
			} else if (DisplayType.YesNo == displayType) {
				typeConverter = new YesNoTypeConverter();
			} else if (DisplayType.isLookup(displayType)) {
				return new LookupTypeConverter();
			}
		}
		return typeConverter;
	}
	
	@SuppressWarnings({ "rawtypes" })
	private static ITypeConverter getTypeConverter(int displayType, JsonElement value) {
		ITypeConverter typeConverter = null;
		ServiceQuery query = new ServiceQuery();
		query.put("displayType", Integer.toString(displayType));
		typeConverter = Service.locator().locate(ITypeConverter.class, query).getService();
		if (typeConverter == null) {
			if (DisplayType.isNumeric(displayType) && (isNumber(value) || isString(value))) {
				typeConverter = new NumericTypeConverter();
			} else if (DisplayType.isDate(displayType) && isString(value)) {
				typeConverter = new DateTypeConverter();
			} else if (DisplayType.YesNo == displayType && (isBoolean(value) || isString(value))) {
				typeConverter = new YesNoTypeConverter();
			} else if (DisplayType.isLookup(displayType)) {
				return new LookupTypeConverter();
			}
		}
		return typeConverter;
	}

	private static boolean isBoolean(JsonElement value) {
		if (value instanceof JsonPrimitive) {
			JsonPrimitive primitive = (JsonPrimitive) value;
			return primitive.isBoolean();
		}
		return false;
	}

	private static boolean isString(JsonElement value) {
		if (value instanceof JsonPrimitive) {
			JsonPrimitive primitive = (JsonPrimitive) value;
			return primitive.isString();
		}
		return false;
	}

	private static boolean isNumber(JsonElement value) {
		if (value instanceof JsonPrimitive) {
			JsonPrimitive primitive = (JsonPrimitive) value;
			return primitive.isNumber();
		}
		return false;
	}		  	
	
	private final static String UUID_REGEX="[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}";
	
	/**
	 * @param value
	 * @return true if value is a UUID identifier
	 */
	private static boolean isUUID(String value) {
		return value == null ? false : value.matches(UUID_REGEX);
	}
	
	public static PO getPO(String tableName, String recordID, boolean fullyQualified, boolean RW) {
		boolean isUUID = isUUID(recordID);
		
		String keyColumn = getKeyColumn(tableName, isUUID);
		
		Query query = new Query(Env.getCtx(), tableName, keyColumn + "=?", null, false);
		
		//if (fullyQualified || RW)
		//	query.setApplyAccessFilter(fullyQualified, RW);
		
		if (isUUID)
			query.setParameters(recordID);
		else
			query.setParameters(Integer.parseInt(recordID));
		
		return query.first();
	}
	
	private static String getKeyColumn(String tableName, boolean isUUID) {
		return isUUID ? PO.getUUIDColumnName(tableName) : tableName + "_ID";
	}
	
	public static HashMap<String, ArrayList<String>> getIncludes(String tableName, String select, String details) {
		
		if (Util.isEmpty(select, true) || Util.isEmpty(tableName, true))
			return null;

		HashMap<String, ArrayList<String>> tableSelect = new HashMap<>();

		boolean hasDetail = !Util.isEmpty(details, true); 
		MTable mTable = MTable.get(Env.getCtx(), tableName);
		String[] columnNames = select.split("[,]");
		for(String columnName : columnNames) {
			MTable table = mTable;
			if (hasDetail && columnName.contains("/")) { //Detail select
				String selectTableName = columnName.substring(0, columnName.indexOf("/")).trim();
				if (details.toLowerCase().contains(selectTableName.toLowerCase())) {
					table = MTable.get(Env.getCtx(), selectTableName);
					columnName = columnName.substring(columnName.indexOf("/")+1, columnName.length());
				} else {
					throw new EONERestException(selectTableName + " does not make part of the request body.", Status.BAD_REQUEST);
				}
			}
			if (table.getColumnIndex(columnName.trim()) < 0)
				throw new EONERestException(columnName + " is not a valid column of table " + table.getTableName(), Status.BAD_REQUEST);
			
		}

		return tableSelect;
	}
}
