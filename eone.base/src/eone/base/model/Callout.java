
package eone.base.model;

import java.util.Properties;

/**
 *  Callout Interface for Callout.
 *  Used in MTab and ImpFormatRow
 *
 *  @author     Jorg Janke
 *  @version    $Id: Callout.java,v 1.2 2006/07/30 00:51:05 jjanke Exp $
 */
public interface Callout
{
	/**
	 *	Start Callout.
	 *  <p>
	 *	Callout's are used for cross field validation and setting values in other fields
	 *	when returning a non empty (error message) string, an exception is raised
	 *  <p>
	 *	When invoked, the Tab model has the new value!
	 *
	 *  @param ctx      Context
	 *  @param method   Method name
	 *  @param WindowNo current Window No
	 *  @param mTab     Model Tab
	 *  @param mField   Model Field
	 *  @param value    The new value
	 *  @param oldValue The old value
	 *  @return Error message or ""
	 */
	public String start (Properties ctx, String method, int WindowNo,
		GridTab mTab, GridField mField, Object value, Object oldValue);

	/**
	 *	Conversion Rules.
	 *	Convert a String
	 *
	 *	@param method   in notation User_Function
	 *  @param value    the value
	 *	@return converted String or Null if no method found
	 */
	public String convert (String method, String value);

}   //  callout
