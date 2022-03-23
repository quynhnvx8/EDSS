
package eone.base;

/**
 * 
 * @author hengsin
 *
 */
public interface IColumnCalloutFactory {

	/**
	 * 
	 * @param tableName
	 * @param columnName
	 * @return array of matching callouts
	 */
	public IColumnCallout[] getColumnCallouts(String tableName, String columnName);
	
}
