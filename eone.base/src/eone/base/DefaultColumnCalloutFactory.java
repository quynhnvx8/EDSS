
package eone.base;

import java.util.List;

import eone.base.equinox.EquinoxExtensionLocator;

/**
 * @author hengsin
 *
 */
public class DefaultColumnCalloutFactory implements IColumnCalloutFactory {

	/**
	 * default constructor
	 */
	public DefaultColumnCalloutFactory() {
	}

	/* (non-Javadoc)
	 * @see org.adempiere.base.IColumnCalloutFactory#getColumnCallouts(java.lang.String, java.lang.String)
	 */
	@Override
	public IColumnCallout[] getColumnCallouts(String tableName,
			String columnName) {
		ServiceQuery query = new ServiceQuery();
		query.put("tableName", tableName);
		query.put("columnName", columnName);

		List<IColumnCallout> list = EquinoxExtensionLocator.instance().list(IColumnCallout.class, query).getExtensions();
		return list != null ? list.toArray(new IColumnCallout[0]) : new IColumnCallout[0]; 
	}

}
