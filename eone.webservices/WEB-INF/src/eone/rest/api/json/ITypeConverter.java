
package eone.rest.api.json;

import com.google.gson.JsonElement;

import eone.base.model.GridField;
import eone.base.model.MColumn;

/**
 * 
 * Interface for type converter between AD type and Json type
 * @author Client
 *
 */
public interface ITypeConverter<T> {

	public Object toJsonValue(MColumn column, T value);
	
	public Object toJsonValue(GridField field, T value);

	public Object fromJsonValue(MColumn column, JsonElement value);
	
	public Object fromJsonValue(GridField field, JsonElement value);
}
