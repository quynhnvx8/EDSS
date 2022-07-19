
package eone.rest.api.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

import eone.base.model.GridField;
import eone.base.model.MColumn;
import eone.util.DisplayType;

/**
 * 
 * json type converter for AD numeric type
 * @author Client
 *
 */
public class NumericTypeConverter implements ITypeConverter<Number> {

	/**
	 * 
	 */
	public NumericTypeConverter() {
	}

	@Override
	public Object toJsonValue(MColumn column, Number value) {
		return toJsonValue(column.getAD_Reference_ID(), value);
	}

	@Override
	public Object toJsonValue(GridField field, Number value) {
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
	
	private Object toJsonValue(int displayType, Number value) {
		if (!DisplayType.isNumeric(displayType))
			return null;
		
		if (displayType == DisplayType.Integer) {
			return value.intValue();
		} else {
			return value;
		}
	}
	
	private Object fromJsonValue(int displayType, JsonElement value) {
		if (!DisplayType.isNumeric(displayType))
			return null;
		
		JsonPrimitive primitive = (JsonPrimitive) value;
		if (displayType == DisplayType.Integer) {
			if (primitive.isString())
				return Integer.parseInt(primitive.getAsString());
			else
				return primitive.getAsInt();
		} else {
			return primitive.getAsBigDecimal();
		}
	}
}
