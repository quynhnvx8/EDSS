
package eone.rest.api.json;

import javax.xml.bind.DatatypeConverter;

import com.google.gson.JsonElement;

import eone.base.model.GridField;
import eone.base.model.MColumn;

/**
 * type converter for DisplayType.Binary
 * @author Client
 *
 */
public class BinaryTypeConverter implements ITypeConverter<byte[]> {

	public BinaryTypeConverter() {
	}

	@Override
	public Object toJsonValue(MColumn column, byte[] value) {
		if (value != null) {
			return DatatypeConverter.printBase64Binary(value);
		}
		return null;
	}

	@Override
	public Object toJsonValue(GridField field, byte[] value) {
		if (value != null) {
			return DatatypeConverter.printBase64Binary(value);
		}
		return null;
	}

	@Override
	public Object fromJsonValue(MColumn column, JsonElement value) {
		if (value != null && value.isJsonPrimitive()) {
			String base64Value = value.getAsString();
			byte[] data = DatatypeConverter.parseBase64Binary(base64Value);
			return data;
		}
		return null;
	}

	@Override
	public Object fromJsonValue(GridField field, JsonElement value) {
		if (value != null && value.isJsonPrimitive()) {
			String base64Value = value.getAsString();
			byte[] data = DatatypeConverter.parseBase64Binary(base64Value);
			return data;
		}
		return null;
	}

}
