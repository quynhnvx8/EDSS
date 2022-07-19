
package eone.rest.api.json.filter;

import java.util.List;

import eone.base.Service;

public interface IQueryConverter {

    
	public ConvertedQuery convertStatement(String tableName, String queryStatement);
	
	public static IQueryConverter getQueryConverter(String converterName) {
		IQueryConverter serializer = null;
		List<IQueryConverterFactory> factories = Service.locator().list(IQueryConverterFactory.class).getServices();
		for (IQueryConverterFactory  factory : factories) {
			serializer = factory.getQueryConverter(converterName);
			if (serializer != null) {
				break;
			}
		}
		if (serializer == null) {
			for (IQueryConverterFactory  factory : factories) {
				serializer = factory.getQueryConverter("DEFAULT");
				if (serializer != null) {
					break;
				}
			}
		}

		return serializer;
	}
}
