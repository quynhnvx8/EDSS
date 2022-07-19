
package eone.rest.api.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

import eone.base.model.GridField;
import eone.base.model.MColumn;

/**
 * 
 * json type converter for AD yes/no type
 * @author Client
 *
 */
public class YesNoTypeConverter implements ITypeConverter<Object> {

	/**
	 * default constructor
	 */
	public YesNoTypeConverter() {
	}

	@Override
	public Object toJsonValue(MColumn column, Object value) {
		return toJsonValue(column.getAD_Reference_ID(), value);
	}

	@Override
	public Object toJsonValue(GridField field, Object value) {
		return toJsonValue(field.getDisplayType(), value);
	}
	
	@Override
	public Object fromJsonValue(MColumn column, JsonElement value) {
		return fromJsonValue(column.getAD_Reference_ID(), value);
	}

	@Override
	public Object fromJsonValue(GridField field, JsonElement value) {
		return fromJsonValue(field.getDisplayType(), value);
	}
	
	private Object toJsonValue(int displayType, Object value) {
		if (value instanceof Boolean) {
			return (Boolean)value;
		} else {
			return ("y".equalsIgnoreCase(value.toString()) || "true".equalsIgnoreCase(value.toString()));
		}
	}
	
	private Object fromJsonValue(int displayType, JsonElement value) {
		JsonPrimitive primitive = (JsonPrimitive) value;
		if (primitive.isBoolean()) {
			return primitive.getAsBoolean() ? "Y" : "N";
		} else {
			return ("y".equalsIgnoreCase(value.toString()) || "true".equalsIgnoreCase(value.toString())) ? "Y" : "N";
		}
	}
}
