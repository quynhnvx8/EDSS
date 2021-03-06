
package eone.base.model;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.Properties;
import java.util.logging.Level;

import eone.util.CLogger;
import eone.util.Env;

/**
 *	Callout Engine.
 *	
 *  @author Jorg Janke
 *  @version $Id: CalloutEngine.java,v 1.3 2006/07/30 00:51:05 jjanke Exp $
 *  
 *  @author Teo Sarca, SC ARHIPAC SERVICE SRL
 *  		<li>BF [ 2104021 ] CalloutEngine returns null if the exception has null message
 */
public class CalloutEngine implements Callout
{
	/** No error return value. Use this when you are returning from a callout without error */
	public static final String NO_ERROR = "";
	
	/**
	 *	Constructor
	 */
	public CalloutEngine()
	{
		super();
	}

	/** Logger					*/
	protected CLogger		log = CLogger.getCLogger(getClass());
	private GridTab m_mTab;
	private GridField m_mField;

	/**
	 *	Start Callout.
	 *  <p>
	 *	Callout's are used for cross field validation and setting values in other fields
	 *	when returning a non empty (error message) string, an exception is raised
	 *  <p>
	 *	When invoked, the Tab model has the new value!
	 *
	 *  @param ctx      Context
	 *  @param methodName   Method name
	 *  @param WindowNo current Window No
	 *  @param mTab     Model Tab
	 *  @param mField   Model Field
	 *  @param value    The new value
	 *  @param oldValue The old value
	 *  @return Error message or ""
	 */
	public String start (Properties ctx, String methodName, int WindowNo,
		GridTab mTab, GridField mField, Object value, Object oldValue)
	{
		if (methodName == null || methodName.length() == 0)
			throw new IllegalArgumentException ("No Method Name");
		
		m_mTab = mTab;
		m_mField = mField;
		
		//
		String retValue = "";
		StringBuilder msg = new StringBuilder(methodName).append(" - ")
			.append(mField.getColumnName())
			.append("=").append(value)
			.append(" (old=").append(oldValue)
			.append(") {active=").append(isCalloutActive()).append("}");
		if (!isCalloutActive())
			if (log.isLoggable(Level.INFO)) log.info (msg.toString());
		
		//	Find Method
		Method method = getMethod(methodName);
		if (method == null)
			throw new IllegalArgumentException ("Method not found: " + methodName);
		int argLength = method.getParameterTypes().length;
		if (!(argLength == 5 || argLength == 6))
			throw new IllegalArgumentException ("Method " + methodName 
				+ " has invalid no of arguments: " + argLength);

		//	Call Method
		try
		{
			Object[] args = null;
			if (argLength == 6)
				args = new Object[] {ctx, Integer.valueOf(WindowNo), mTab, mField, value, oldValue};
			else
				args = new Object[] {ctx, Integer.valueOf(WindowNo), mTab, mField, value}; 
			retValue = (String)method.invoke(this, args);
		}
		catch (Exception e)
		{
			Throwable ex = e.getCause();	//	InvocationTargetException
			if (ex == null)
				ex = e;
			log.log(Level.SEVERE, "start: " + methodName, ex);
			retValue = ex.getLocalizedMessage();
			if (retValue == null)
			{
				retValue = ex.toString();
			}
		}
		finally
		{
			m_mTab = null;
			m_mField = null;
		}
		return retValue;
	}	//	start
	
	/**
	 *	Conversion Rules.
	 *	Convert a String
	 *
	 *	@param methodName   method name
	 *  @param value    the value
	 *	@return converted String or Null if no method found
	 */
	public String convert (String methodName, String value)
	{
		if (methodName == null || methodName.length() == 0)
			throw new IllegalArgumentException ("No Method Name");
		//
		String retValue = null;
		StringBuilder msg = new StringBuilder(methodName).append(" - ").append(value);
		if (log.isLoggable(Level.INFO)) log.info (msg.toString());
		//
		//	Find Method
		Method method = getMethod(methodName);
		if (method == null)
			throw new IllegalArgumentException ("Method not found: " + methodName);
		int argLength = method.getParameterTypes().length;
		if (argLength != 1)
			throw new IllegalArgumentException ("Method " + methodName 
				+ " has invalid no of arguments: " + argLength);

		//	Call Method
		try
		{
			Object[] args = new Object[] {value};
			retValue = (String)method.invoke(this, args);
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, "convert: " + methodName, e);
			e.printStackTrace(System.err);
		}
		return retValue;
	}   //  convert
	
	/**
	 * 	Get Method
	 *	@param methodName method name
	 *	@return method or null
	 */
	private Method getMethod (String methodName)
	{
		Method[] allMethods = getClass().getMethods();
		for (int i = 0; i < allMethods.length; i++)
		{
			if (methodName.equals(allMethods[i].getName()))
				return allMethods[i];
		}
		return null;
	}	//	getMethod

	/*************************************************************************/
	
	//private static boolean s_calloutActive = false;
	
	/**
	 * 	Is the current callout being called in the middle of 
     *  another callout doing her works.
     *  Callout can use GridTab.getActiveCalloutInstance() method
     *  to find out callout for which field is running.
	 *	@return true if active
	 */
	protected boolean isCalloutActive()
	{
		//greater than 1 instead of 0 to discount this callout instance
		return m_mTab != null ? m_mTab.getActiveCallouts().length > 1 : false;
	}	//	isCalloutActive

	/**
	 * 	Set Callout (in)active.
     *  Depreciated as the implementation is not thread safe and
     *  fragile - break other callout if developer forget to call
     *  setCalloutActive(false) after calling setCalloutActive(true).
	 *  @deprecated
	 *	@param active active
	 */
	protected static void setCalloutActive (boolean active)
	{
		;
	}	//	setCalloutActive
	
	/**
	 *  Set Account Date Value.
	 *	@param ctx context
	 *	@param WindowNo window no
	 *	@param mTab tab
	 *	@param mField field
	 *	@param value value
	 *	@return null or error message
	 */
	public String dateAcct (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		if (isCalloutActive())		//	assuming it is resetting value
			return NO_ERROR;
		if (value == null || !(value instanceof Timestamp))
			return NO_ERROR;
		mTab.setValue("DateAcct", value);
		return checkPeriodOpen (ctx, WindowNo, mTab, mField, value);
	}	//	dateAcct

	/**
	 *  Check Account Date is on a opened period
	 *	@param ctx context
	 *	@param WindowNo window no
	 *	@param mTab tab
	 *	@param mField field
	 *	@param value value
	 *	@return null or error message
	 */
	public String checkPeriodOpen (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		if (isCalloutActive())		//	assuming it is resetting value
			return NO_ERROR;
		if (value == null || !(value instanceof Timestamp))
			return NO_ERROR;

		int orgID = 0;
		if (mTab.getValue("AD_Org_ID") != null)
			orgID = (Integer) mTab.getValue("AD_Org_ID");

		int doctypeID = -1;
		if (mTab.getValue("C_DocTypeTarget_ID") != null)
			doctypeID = (Integer) mTab.getValue("C_DocTypeTarget_ID");
		else if (mTab.getValue("C_DocType_ID") != null)
			doctypeID = (Integer) mTab.getValue("C_DocType_ID");

		

		if (doctypeID > 0) {
			MPeriod.isOpen(ctx, (Timestamp)value, orgID);
		}
		return NO_ERROR;
	}

	/**
	 *	Rate - set Multiply Rate from Divide Rate and vice versa
	 *	@param ctx context
	 *	@param WindowNo window no
	 *	@param mTab tab
	 *	@param mField field
	 *	@param value value
	 *	@return null or error message
	 */
	public String rate (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		if (isCalloutActive() || value == null)		//	assuming it is Conversion_Rate
			return NO_ERROR;

		BigDecimal rate1 = (BigDecimal)value;
		BigDecimal rate2 = Env.ZERO;

		if (rate1.signum() != 0.0)	//	no divide by zero
			rate2 = Env.ONE.divide(rate1, 12, RoundingMode.HALF_UP);
		//
		if (mField.getColumnName().equals("MultiplyRate"))
			mTab.setValue("DivideRate", rate2);
		else
			mTab.setValue("MultiplyRate", rate2);
		if (log.isLoggable(Level.INFO)) log.info(mField.getColumnName() + "=" + rate1 + " => " + rate2);
		return NO_ERROR;
	}	//	rate
	
	/**
	 * 
	 * @return gridTab
	 */
	public GridTab getGridTab() 
	{
		return m_mTab;
	}
	
	/**
	 * 
	 * @return gridField
	 */
	public GridField getGridField() 
	{
		return m_mField;
	}

}	//	CalloutEngine
