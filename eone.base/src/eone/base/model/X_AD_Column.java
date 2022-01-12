/******************************************************************************
 * Product: EONE ERP & CRM Smart Business Solution	                        *
 * Copyright (C) 2020, Inc. All Rights Reserved.				                *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package eone.base.model;

import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.util.KeyNamePair;

/** Generated Model for AD_Column
 *  @author EOne (generated) 
 *  @version Version 1.0 - $Id$ */
public class X_AD_Column extends PO implements I_AD_Column, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20211007L;

    /** Standard Constructor */
    public X_AD_Column (Properties ctx, int AD_Column_ID, String trxName)
    {
      super (ctx, AD_Column_ID, trxName);
      /** if (AD_Column_ID == 0)
        {
			setAD_Column_ID (0);
			setAD_Element_ID (0);
			setAD_Reference_ID (0);
			setAD_Table_ID (0);
			setColumnName (null);
			setIsAllowCopy (true);
// Y
			setIsAlwaysUpdateable (false);
// N
			setIsAutocomplete (false);
// N
			setIsEncrypted (false);
// N
			setIsIdentifier (false);
			setIsKey (false);
			setIsMandatory (false);
			setIsParent (false);
			setIsSecure (false);
// N
			setIsSelectionColumn (false);
			setIsToolbarButton (null);
// Y
			setIsTranslated (false);
			setIsUpdateable (true);
// Y
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_AD_Column (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 4 - System 
      */
    protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    protected POInfo initPO (Properties ctx)
    {
      POInfo poi = POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    public String toString()
    {
      StringBuilder sb = new StringBuilder ("X_AD_Column[")
        .append(get_ID()).append(",Name=").append(getName()).append("]");
      return sb.toString();
    }

	public eone.base.model.I_AD_Chart getAD_Chart() throws RuntimeException
    {
		return (eone.base.model.I_AD_Chart)MTable.get(getCtx(), eone.base.model.I_AD_Chart.Table_Name)
			.getPO(getAD_Chart_ID(), get_TrxName());	}

	/** Set Chart.
		@param AD_Chart_ID Chart	  */
	public void setAD_Chart_ID (int AD_Chart_ID)
	{
		if (AD_Chart_ID < 1) 
			set_Value (COLUMNNAME_AD_Chart_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Chart_ID, Integer.valueOf(AD_Chart_ID));
	}

	/** Get Chart.
		@return Chart	  */
	public int getAD_Chart_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Chart_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Column.
		@param AD_Column_ID 
		Column in the table
	  */
	public void setAD_Column_ID (int AD_Column_ID)
	{
		if (AD_Column_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Column_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Column_ID, Integer.valueOf(AD_Column_ID));
	}

	/** Get Column.
		@return Column in the table
	  */
	public int getAD_Column_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Column_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public eone.base.model.I_AD_Element getAD_Element() throws RuntimeException
    {
		return (eone.base.model.I_AD_Element)MTable.get(getCtx(), eone.base.model.I_AD_Element.Table_Name)
			.getPO(getAD_Element_ID(), get_TrxName());	}

	/** Set System Element.
		@param AD_Element_ID 
		System Element enables the central maintenance of column description and help.
	  */
	public void setAD_Element_ID (int AD_Element_ID)
	{
		if (AD_Element_ID < 1) 
			set_Value (COLUMNNAME_AD_Element_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Element_ID, Integer.valueOf(AD_Element_ID));
	}

	/** Get System Element.
		@return System Element enables the central maintenance of column description and help.
	  */
	public int getAD_Element_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Element_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public eone.base.model.I_AD_Process getAD_Process() throws RuntimeException
    {
		return (eone.base.model.I_AD_Process)MTable.get(getCtx(), eone.base.model.I_AD_Process.Table_Name)
			.getPO(getAD_Process_ID(), get_TrxName());	}

	/** Set Process.
		@param AD_Process_ID 
		Process or Report
	  */
	public void setAD_Process_ID (int AD_Process_ID)
	{
		if (AD_Process_ID < 1) 
			set_Value (COLUMNNAME_AD_Process_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Process_ID, Integer.valueOf(AD_Process_ID));
	}

	/** Get Process.
		@return Process or Report
	  */
	public int getAD_Process_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Process_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public eone.base.model.I_AD_Reference getAD_Reference() throws RuntimeException
    {
		return (eone.base.model.I_AD_Reference)MTable.get(getCtx(), eone.base.model.I_AD_Reference.Table_Name)
			.getPO(getAD_Reference_ID(), get_TrxName());	}

	/** Set Reference.
		@param AD_Reference_ID 
		System Reference and Validation
	  */
	public void setAD_Reference_ID (int AD_Reference_ID)
	{
		if (AD_Reference_ID < 1) 
			set_Value (COLUMNNAME_AD_Reference_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Reference_ID, Integer.valueOf(AD_Reference_ID));
	}

	/** Get Reference.
		@return System Reference and Validation
	  */
	public int getAD_Reference_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Reference_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public eone.base.model.I_AD_Reference getAD_Reference_Value() throws RuntimeException
    {
		return (eone.base.model.I_AD_Reference)MTable.get(getCtx(), eone.base.model.I_AD_Reference.Table_Name)
			.getPO(getAD_Reference_Value_ID(), get_TrxName());	}

	/** Set Reference Key.
		@param AD_Reference_Value_ID 
		Required to specify, if data type is Table or List
	  */
	public void setAD_Reference_Value_ID (int AD_Reference_Value_ID)
	{
		if (AD_Reference_Value_ID < 1) 
			set_Value (COLUMNNAME_AD_Reference_Value_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Reference_Value_ID, Integer.valueOf(AD_Reference_Value_ID));
	}

	/** Get Reference Key.
		@return Required to specify, if data type is Table or List
	  */
	public int getAD_Reference_Value_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Reference_Value_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public eone.base.model.I_AD_Table getAD_Table() throws RuntimeException
    {
		return (eone.base.model.I_AD_Table)MTable.get(getCtx(), eone.base.model.I_AD_Table.Table_Name)
			.getPO(getAD_Table_ID(), get_TrxName());	}

	/** Set Table.
		@param AD_Table_ID 
		Database Table information
	  */
	public void setAD_Table_ID (int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, Integer.valueOf(AD_Table_ID));
	}

	/** Get Table.
		@return Database Table information
	  */
	public int getAD_Table_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Table_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public eone.base.model.I_AD_Val_Rule getAD_Val_Rule() throws RuntimeException
    {
		return (eone.base.model.I_AD_Val_Rule)MTable.get(getCtx(), eone.base.model.I_AD_Val_Rule.Table_Name)
			.getPO(getAD_Val_Rule_ID(), get_TrxName());	}

	/** Set Dynamic Validation.
		@param AD_Val_Rule_ID 
		Dynamic Validation Rule
	  */
	public void setAD_Val_Rule_ID (int AD_Val_Rule_ID)
	{
		if (AD_Val_Rule_ID < 1) 
			set_Value (COLUMNNAME_AD_Val_Rule_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Val_Rule_ID, Integer.valueOf(AD_Val_Rule_ID));
	}

	/** Get Dynamic Validation.
		@return Dynamic Validation Rule
	  */
	public int getAD_Val_Rule_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Val_Rule_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Callout.
		@param Callout 
		Fully qualified class names and method - separated by semicolons
	  */
	public void setCallout (String Callout)
	{
		set_Value (COLUMNNAME_Callout, Callout);
	}

	/** Get Callout.
		@return Fully qualified class names and method - separated by semicolons
	  */
	public String getCallout () 
	{
		return (String)get_Value(COLUMNNAME_Callout);
	}

	/** Set DB Column Name.
		@param ColumnName 
		Name of the column in the database
	  */
	public void setColumnName (String ColumnName)
	{
		set_Value (COLUMNNAME_ColumnName, ColumnName);
	}

	/** Get DB Column Name.
		@return Name of the column in the database
	  */
	public String getColumnName () 
	{
		return (String)get_Value(COLUMNNAME_ColumnName);
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getColumnName());
    }

	/** Set Column SQL.
		@param ColumnSQL 
		Virtual Column (r/o)
	  */
	public void setColumnSQL (String ColumnSQL)
	{
		set_Value (COLUMNNAME_ColumnSQL, ColumnSQL);
	}

	/** Get Column SQL.
		@return Virtual Column (r/o)
	  */
	public String getColumnSQL () 
	{
		return (String)get_Value(COLUMNNAME_ColumnSQL);
	}

	/** Set Default Logic.
		@param DefaultValue 
		Default value hierarchy, separated by ;
	  */
	public void setDefaultValue (String DefaultValue)
	{
		set_Value (COLUMNNAME_DefaultValue, DefaultValue);
	}

	/** Get Default Logic.
		@return Default value hierarchy, separated by ;
	  */
	public String getDefaultValue () 
	{
		return (String)get_Value(COLUMNNAME_DefaultValue);
	}

	/** Set Description.
		@param Description 
		Optional short description of the record
	  */
	public void setDescription (String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Description.
		@return Optional short description of the record
	  */
	public String getDescription () 
	{
		return (String)get_Value(COLUMNNAME_Description);
	}

	/** Set Length.
		@param FieldLength 
		Length of the column in the database
	  */
	public void setFieldLength (int FieldLength)
	{
		set_Value (COLUMNNAME_FieldLength, Integer.valueOf(FieldLength));
	}

	/** Get Length.
		@return Length of the column in the database
	  */
	public int getFieldLength () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_FieldLength);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Constraint Name.
		@param FKConstraintName Constraint Name	  */
	public void setFKConstraintName (String FKConstraintName)
	{
		set_Value (COLUMNNAME_FKConstraintName, FKConstraintName);
	}

	/** Get Constraint Name.
		@return Constraint Name	  */
	public String getFKConstraintName () 
	{
		return (String)get_Value(COLUMNNAME_FKConstraintName);
	}

	/** FKConstraintType AD_Reference_ID=200075 */
	public static final int FKCONSTRAINTTYPE_AD_Reference_ID=200075;
	/** Do Not Create = D */
	public static final String FKCONSTRAINTTYPE_DoNotCreate = "D";
	/** No Action = N */
	public static final String FKCONSTRAINTTYPE_NoAction = "N";
	/** Cascade = C */
	public static final String FKCONSTRAINTTYPE_Cascade = "C";
	/** Set Null = S */
	public static final String FKCONSTRAINTTYPE_SetNull = "S";
	/** Model Cascade = M */
	public static final String FKCONSTRAINTTYPE_ModelCascade = "M";
	/** Set Constraint Type.
		@param FKConstraintType Constraint Type	  */
	public void setFKConstraintType (String FKConstraintType)
	{

		set_Value (COLUMNNAME_FKConstraintType, FKConstraintType);
	}

	/** Get Constraint Type.
		@return Constraint Type	  */
	public String getFKConstraintType () 
	{
		return (String)get_Value(COLUMNNAME_FKConstraintType);
	}

	/** Set Format Pattern.
		@param FormatPattern 
		The pattern used to format a number or date.
	  */
	public void setFormatPattern (String FormatPattern)
	{
		set_Value (COLUMNNAME_FormatPattern, FormatPattern);
	}

	/** Get Format Pattern.
		@return The pattern used to format a number or date.
	  */
	public String getFormatPattern () 
	{
		return (String)get_Value(COLUMNNAME_FormatPattern);
	}

	/** Set Allow Copy.
		@param IsAllowCopy 
		Determine if a column must be copied when pushing the button to copy record
	  */
	public void setIsAllowCopy (boolean IsAllowCopy)
	{
		set_Value (COLUMNNAME_IsAllowCopy, Boolean.valueOf(IsAllowCopy));
	}

	/** Get Allow Copy.
		@return Determine if a column must be copied when pushing the button to copy record
	  */
	public boolean isAllowCopy () 
	{
		Object oo = get_Value(COLUMNNAME_IsAllowCopy);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Allow Logging.
		@param IsAllowLogging 
		Determine if a column must be recorded into the change log
	  */
	public void setIsAllowLogging (boolean IsAllowLogging)
	{
		set_Value (COLUMNNAME_IsAllowLogging, Boolean.valueOf(IsAllowLogging));
	}

	/** Get Allow Logging.
		@return Determine if a column must be recorded into the change log
	  */
	public boolean isAllowLogging () 
	{
		Object oo = get_Value(COLUMNNAME_IsAllowLogging);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Always Updatable.
		@param IsAlwaysUpdateable 
		The column is always updateable, even if the record is not active or processed
	  */
	public void setIsAlwaysUpdateable (boolean IsAlwaysUpdateable)
	{
		set_Value (COLUMNNAME_IsAlwaysUpdateable, Boolean.valueOf(IsAlwaysUpdateable));
	}

	/** Get Always Updatable.
		@return The column is always updateable, even if the record is not active or processed
	  */
	public boolean isAlwaysUpdateable () 
	{
		Object oo = get_Value(COLUMNNAME_IsAlwaysUpdateable);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Autocomplete.
		@param IsAutocomplete 
		Automatic completion for textfields
	  */
	public void setIsAutocomplete (boolean IsAutocomplete)
	{
		set_Value (COLUMNNAME_IsAutocomplete, Boolean.valueOf(IsAutocomplete));
	}

	/** Get Autocomplete.
		@return Automatic completion for textfields
	  */
	public boolean isAutocomplete () 
	{
		Object oo = get_Value(COLUMNNAME_IsAutocomplete);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Encrypted.
		@param IsEncrypted 
		Display or Storage is encrypted
	  */
	public void setIsEncrypted (boolean IsEncrypted)
	{
		set_Value (COLUMNNAME_IsEncrypted, Boolean.valueOf(IsEncrypted));
	}

	/** Get Encrypted.
		@return Display or Storage is encrypted
	  */
	public boolean isEncrypted () 
	{
		Object oo = get_Value(COLUMNNAME_IsEncrypted);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set HTML.
		@param IsHtml 
		Text has HTML tags
	  */
	public void setIsHtml (boolean IsHtml)
	{
		set_Value (COLUMNNAME_IsHtml, Boolean.valueOf(IsHtml));
	}

	/** Get HTML.
		@return Text has HTML tags
	  */
	public boolean isHtml () 
	{
		Object oo = get_Value(COLUMNNAME_IsHtml);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Identifier.
		@param IsIdentifier 
		This column is part of the record identifier
	  */
	public void setIsIdentifier (boolean IsIdentifier)
	{
		set_Value (COLUMNNAME_IsIdentifier, Boolean.valueOf(IsIdentifier));
	}

	/** Get Identifier.
		@return This column is part of the record identifier
	  */
	public boolean isIdentifier () 
	{
		Object oo = get_Value(COLUMNNAME_IsIdentifier);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Search InfoPanel.
		@param IsInfoPanel Search InfoPanel	  */
	public void setIsInfoPanel (boolean IsInfoPanel)
	{
		set_Value (COLUMNNAME_IsInfoPanel, Boolean.valueOf(IsInfoPanel));
	}

	/** Get Search InfoPanel.
		@return Search InfoPanel	  */
	public boolean isInfoPanel () 
	{
		Object oo = get_Value(COLUMNNAME_IsInfoPanel);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Key column.
		@param IsKey 
		This column is the key in this table
	  */
	public void setIsKey (boolean IsKey)
	{
		set_Value (COLUMNNAME_IsKey, Boolean.valueOf(IsKey));
	}

	/** Get Key column.
		@return This column is the key in this table
	  */
	public boolean isKey () 
	{
		Object oo = get_Value(COLUMNNAME_IsKey);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Mandatory.
		@param IsMandatory 
		Data entry is required in this column
	  */
	public void setIsMandatory (boolean IsMandatory)
	{
		set_Value (COLUMNNAME_IsMandatory, Boolean.valueOf(IsMandatory));
	}

	/** Get Mandatory.
		@return Data entry is required in this column
	  */
	public boolean isMandatory () 
	{
		Object oo = get_Value(COLUMNNAME_IsMandatory);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Parent link column.
		@param IsParent 
		This column is a link to the parent table (e.g. header from lines) - incl. Association key columns
	  */
	public void setIsParent (boolean IsParent)
	{
		set_Value (COLUMNNAME_IsParent, Boolean.valueOf(IsParent));
	}

	/** Get Parent link column.
		@return This column is a link to the parent table (e.g. header from lines) - incl. Association key columns
	  */
	public boolean isParent () 
	{
		Object oo = get_Value(COLUMNNAME_IsParent);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Secure content.
		@param IsSecure 
		Defines whether content must be treated as secure
	  */
	public void setIsSecure (boolean IsSecure)
	{
		set_Value (COLUMNNAME_IsSecure, Boolean.valueOf(IsSecure));
	}

	/** Get Secure content.
		@return Defines whether content must be treated as secure
	  */
	public boolean isSecure () 
	{
		Object oo = get_Value(COLUMNNAME_IsSecure);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Selection Column.
		@param IsSelectionColumn 
		Is this column used for finding rows in windows
	  */
	public void setIsSelectionColumn (boolean IsSelectionColumn)
	{
		set_Value (COLUMNNAME_IsSelectionColumn, Boolean.valueOf(IsSelectionColumn));
	}

	/** Get Selection Column.
		@return Is this column used for finding rows in windows
	  */
	public boolean isSelectionColumn () 
	{
		Object oo = get_Value(COLUMNNAME_IsSelectionColumn);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Set Context.
		@param IsSetContext Set Context	  */
	public void setIsSetContext (boolean IsSetContext)
	{
		set_Value (COLUMNNAME_IsSetContext, Boolean.valueOf(IsSetContext));
	}

	/** Get Set Context.
		@return Set Context	  */
	public boolean isSetContext () 
	{
		Object oo = get_Value(COLUMNNAME_IsSetContext);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Synch Database.
		@param IsSyncDatabase 
		Change database table definition when changing dictionary definition
	  */
	public void setIsSyncDatabase (String IsSyncDatabase)
	{
		set_Value (COLUMNNAME_IsSyncDatabase, IsSyncDatabase);
	}

	/** Get Synch Database.
		@return Change database table definition when changing dictionary definition
	  */
	public String getIsSyncDatabase () 
	{
		return (String)get_Value(COLUMNNAME_IsSyncDatabase);
	}

	/** IsToolbarButton AD_Reference_ID=200099 */
	public static final int ISTOOLBARBUTTON_AD_Reference_ID=200099;
	/** Toolbar = Y */
	public static final String ISTOOLBARBUTTON_Toolbar = "Y";
	/** Window = N */
	public static final String ISTOOLBARBUTTON_Window = "N";
	/** Both = B */
	public static final String ISTOOLBARBUTTON_Both = "B";
	/** Set Toolbar Button.
		@param IsToolbarButton 
		Show the button on the toolbar, the window, or both
	  */
	public void setIsToolbarButton (String IsToolbarButton)
	{

		set_Value (COLUMNNAME_IsToolbarButton, IsToolbarButton);
	}

	/** Get Toolbar Button.
		@return Show the button on the toolbar, the window, or both
	  */
	public String getIsToolbarButton () 
	{
		return (String)get_Value(COLUMNNAME_IsToolbarButton);
	}

	/** Set Translated.
		@param IsTranslated 
		This column is translated
	  */
	public void setIsTranslated (boolean IsTranslated)
	{
		set_Value (COLUMNNAME_IsTranslated, Boolean.valueOf(IsTranslated));
	}

	/** Get Translated.
		@return This column is translated
	  */
	public boolean isTranslated () 
	{
		Object oo = get_Value(COLUMNNAME_IsTranslated);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Updatable.
		@param IsUpdateable 
		Determines, if the field can be updated
	  */
	public void setIsUpdateable (boolean IsUpdateable)
	{
		set_Value (COLUMNNAME_IsUpdateable, Boolean.valueOf(IsUpdateable));
	}

	/** Get Updatable.
		@return Determines, if the field can be updated
	  */
	public boolean isUpdateable () 
	{
		Object oo = get_Value(COLUMNNAME_IsUpdateable);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Mandatory Logic.
		@param MandatoryLogic Mandatory Logic	  */
	public void setMandatoryLogic (String MandatoryLogic)
	{
		set_Value (COLUMNNAME_MandatoryLogic, MandatoryLogic);
	}

	/** Get Mandatory Logic.
		@return Mandatory Logic	  */
	public String getMandatoryLogic () 
	{
		return (String)get_Value(COLUMNNAME_MandatoryLogic);
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	public void setName (String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	public String getName () 
	{
		return (String)get_Value(COLUMNNAME_Name);
	}

	public eone.base.model.I_PA_DashboardContent getPA_DashboardContent() throws RuntimeException
    {
		return (eone.base.model.I_PA_DashboardContent)MTable.get(getCtx(), eone.base.model.I_PA_DashboardContent.Table_Name)
			.getPO(getPA_DashboardContent_ID(), get_TrxName());	}

	/** Set Dashboard Content.
		@param PA_DashboardContent_ID Dashboard Content	  */
	public void setPA_DashboardContent_ID (int PA_DashboardContent_ID)
	{
		if (PA_DashboardContent_ID < 1) 
			set_Value (COLUMNNAME_PA_DashboardContent_ID, null);
		else 
			set_Value (COLUMNNAME_PA_DashboardContent_ID, Integer.valueOf(PA_DashboardContent_ID));
	}

	/** Get Dashboard Content.
		@return Dashboard Content	  */
	public int getPA_DashboardContent_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PA_DashboardContent_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Placeholder.
		@param Placeholder Placeholder	  */
	public void setPlaceholder (String Placeholder)
	{
		set_Value (COLUMNNAME_Placeholder, Placeholder);
	}

	/** Get Placeholder.
		@return Placeholder	  */
	public String getPlaceholder () 
	{
		return (String)get_Value(COLUMNNAME_Placeholder);
	}

	/** Set Read Only Logic.
		@param ReadOnlyLogic 
		Logic to determine if field is read only (applies only when field is read-write)
	  */
	public void setReadOnlyLogic (String ReadOnlyLogic)
	{
		set_Value (COLUMNNAME_ReadOnlyLogic, ReadOnlyLogic);
	}

	/** Get Read Only Logic.
		@return Logic to determine if field is read only (applies only when field is read-write)
	  */
	public String getReadOnlyLogic () 
	{
		return (String)get_Value(COLUMNNAME_ReadOnlyLogic);
	}

	/** Set Sequence.
		@param SeqNo 
		Method of ordering records; lowest number comes first
	  */
	public void setSeqNo (int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
	}

	/** Get Sequence.
		@return Method of ordering records; lowest number comes first
	  */
	public int getSeqNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SeqNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Column Sequence.
		@param SeqNoSelection 
		Selection Column Sequence
	  */
	public void setSeqNoSelection (int SeqNoSelection)
	{
		set_Value (COLUMNNAME_SeqNoSelection, Integer.valueOf(SeqNoSelection));
	}

	/** Get Column Sequence.
		@return Selection Column Sequence
	  */
	public int getSeqNoSelection () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SeqNoSelection);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Max. Value.
		@param ValueMax 
		Maximum Value for a field
	  */
	public void setValueMax (String ValueMax)
	{
		set_Value (COLUMNNAME_ValueMax, ValueMax);
	}

	/** Get Max. Value.
		@return Maximum Value for a field
	  */
	public String getValueMax () 
	{
		return (String)get_Value(COLUMNNAME_ValueMax);
	}

	/** Set Min. Value.
		@param ValueMin 
		Minimum Value for a field
	  */
	public void setValueMin (String ValueMin)
	{
		set_Value (COLUMNNAME_ValueMin, ValueMin);
	}

	/** Get Min. Value.
		@return Minimum Value for a field
	  */
	public String getValueMin () 
	{
		return (String)get_Value(COLUMNNAME_ValueMin);
	}

	/** Set Value Format.
		@param VFormat 
		Format of the value; Can contain fixed format elements, Variables: "_lLoOaAcCa09"
	  */
	public void setVFormat (String VFormat)
	{
		set_Value (COLUMNNAME_VFormat, VFormat);
	}

	/** Get Value Format.
		@return Format of the value; Can contain fixed format elements, Variables: "_lLoOaAcCa09"
	  */
	public String getVFormat () 
	{
		return (String)get_Value(COLUMNNAME_VFormat);
	}
}