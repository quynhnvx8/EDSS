/******************************************************************************
 * Product: EONE ERP & CRM Smart Business Solution	                        *
 * Copyright (C) 2020, Inc. All Rights Reserved.				                *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package eone.base.model;

import eone.util.KeyNamePair;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_Tree
 *  @author EOne (generated) 
 *  @version Version 1.0 - $Id$ */
public class X_AD_Tree extends PO implements I_AD_Tree, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20220413L;

    /** Standard Constructor */
    public X_AD_Tree (Properties ctx, int AD_Tree_ID, String trxName)
    {
      super (ctx, AD_Tree_ID, trxName);
      /** if (AD_Tree_ID == 0)
        {
			setAD_Tree_ID (0);
			setIsAllNodes (false);
			setIsDefault (false);
// N
			setIsLoadAllNodesImmediately (true);
// Y
			setIsTreeDrivenByValue (false);
// N
			setIsValueDisplayed (false);
// N
			setName (null);
			setTreeType (null);
        } */
    }

    /** Load Constructor */
    public X_AD_Tree (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_AD_Tree[")
        .append(get_ID()).append(",Name=").append(getName()).append("]");
      return sb.toString();
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
			set_Value (COLUMNNAME_AD_Table_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Table_ID, Integer.valueOf(AD_Table_ID));
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

	/** Set Tree.
		@param AD_Tree_ID 
		Identifies a Tree
	  */
	public void setAD_Tree_ID (int AD_Tree_ID)
	{
		if (AD_Tree_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Tree_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Tree_ID, Integer.valueOf(AD_Tree_ID));
	}

	/** Get Tree.
		@return Identifies a Tree
	  */
	public int getAD_Tree_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Tree_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Create Copy.
		@param CreateCopy Create Copy	  */
	public void setCreateCopy (String CreateCopy)
	{
		set_Value (COLUMNNAME_CreateCopy, CreateCopy);
	}

	/** Get Create Copy.
		@return Create Copy	  */
	public String getCreateCopy () 
	{
		return (String)get_Value(COLUMNNAME_CreateCopy);
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

	/** Set Admin Company.
		@param IsAdminClient 
		Admin Company
	  */
	public void setIsAdminClient (boolean IsAdminClient)
	{
		set_Value (COLUMNNAME_IsAdminClient, Boolean.valueOf(IsAdminClient));
	}

	/** Get Admin Company.
		@return Admin Company
	  */
	public boolean isAdminClient () 
	{
		Object oo = get_Value(COLUMNNAME_IsAdminClient);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set All Nodes.
		@param IsAllNodes 
		All Nodes are included (Complete Tree)
	  */
	public void setIsAllNodes (boolean IsAllNodes)
	{
		set_Value (COLUMNNAME_IsAllNodes, Boolean.valueOf(IsAllNodes));
	}

	/** Get All Nodes.
		@return All Nodes are included (Complete Tree)
	  */
	public boolean isAllNodes () 
	{
		Object oo = get_Value(COLUMNNAME_IsAllNodes);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Default.
		@param IsDefault 
		Default value
	  */
	public void setIsDefault (boolean IsDefault)
	{
		set_Value (COLUMNNAME_IsDefault, Boolean.valueOf(IsDefault));
	}

	/** Get Default.
		@return Default value
	  */
	public boolean isDefault () 
	{
		Object oo = get_Value(COLUMNNAME_IsDefault);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Loads directly all nodes.
		@param IsLoadAllNodesImmediately 
		If checked, all nodes are loaded before tree is displayed
	  */
	public void setIsLoadAllNodesImmediately (boolean IsLoadAllNodesImmediately)
	{
		set_Value (COLUMNNAME_IsLoadAllNodesImmediately, Boolean.valueOf(IsLoadAllNodesImmediately));
	}

	/** Get Loads directly all nodes.
		@return If checked, all nodes are loaded before tree is displayed
	  */
	public boolean isLoadAllNodesImmediately () 
	{
		Object oo = get_Value(COLUMNNAME_IsLoadAllNodesImmediately);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Driven by Search Key.
		@param IsTreeDrivenByValue Driven by Search Key	  */
	public void setIsTreeDrivenByValue (boolean IsTreeDrivenByValue)
	{
		set_Value (COLUMNNAME_IsTreeDrivenByValue, Boolean.valueOf(IsTreeDrivenByValue));
	}

	/** Get Driven by Search Key.
		@return Driven by Search Key	  */
	public boolean isTreeDrivenByValue () 
	{
		Object oo = get_Value(COLUMNNAME_IsTreeDrivenByValue);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Display Value.
		@param IsValueDisplayed 
		Displays Value column with the Display column
	  */
	public void setIsValueDisplayed (boolean IsValueDisplayed)
	{
		set_Value (COLUMNNAME_IsValueDisplayed, Boolean.valueOf(IsValueDisplayed));
	}

	/** Get Display Value.
		@return Displays Value column with the Display column
	  */
	public boolean isValueDisplayed () 
	{
		Object oo = get_Value(COLUMNNAME_IsValueDisplayed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getName());
    }

	public eone.base.model.I_AD_Column getParent_Column() throws RuntimeException
    {
		return (eone.base.model.I_AD_Column)MTable.get(getCtx(), eone.base.model.I_AD_Column.Table_Name)
			.getPO(getParent_Column_ID(), get_TrxName());	}

	/** Set Parent Column.
		@param Parent_Column_ID 
		The link column on the parent tab.
	  */
	public void setParent_Column_ID (int Parent_Column_ID)
	{
		if (Parent_Column_ID < 1) 
			set_Value (COLUMNNAME_Parent_Column_ID, null);
		else 
			set_Value (COLUMNNAME_Parent_Column_ID, Integer.valueOf(Parent_Column_ID));
	}

	/** Get Parent Column.
		@return The link column on the parent tab.
	  */
	public int getParent_Column_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Parent_Column_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Process Now.
		@param Processing Process Now	  */
	public void setProcessing (boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Boolean.valueOf(Processing));
	}

	/** Get Process Now.
		@return Process Now	  */
	public boolean isProcessing () 
	{
		Object oo = get_Value(COLUMNNAME_Processing);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** TreeType AD_Reference_ID=120 */
	public static final int TREETYPE_AD_Reference_ID=120;
	/** Menu = MM */
	public static final String TREETYPE_Menu = "MM";
	/** Element Value = EV */
	public static final String TREETYPE_ElementValue = "EV";
	/** Product = PR */
	public static final String TREETYPE_Product = "PR";
	/** BPartner = BP */
	public static final String TREETYPE_BPartner = "BP";
	/** Organization = OO */
	public static final String TREETYPE_Organization = "OO";
	/** BoM = BB */
	public static final String TREETYPE_BoM = "BB";
	/** Project = PJ */
	public static final String TREETYPE_Project = "PJ";
	/** Sales Region = SR */
	public static final String TREETYPE_SalesRegion = "SR";
	/** Product Category = PC */
	public static final String TREETYPE_ProductCategory = "PC";
	/** Campaign = MC */
	public static final String TREETYPE_Campaign = "MC";
	/** Activity = AY */
	public static final String TREETYPE_Activity = "AY";
	/** User 1 = U1 */
	public static final String TREETYPE_User1 = "U1";
	/** User 2 = U2 */
	public static final String TREETYPE_User2 = "U2";
	/** User 3 = U3 */
	public static final String TREETYPE_User3 = "U3";
	/** User 4 = U4 */
	public static final String TREETYPE_User4 = "U4";
	/** CM Container = CC */
	public static final String TREETYPE_CMContainer = "CC";
	/** CM Container Stage = CS */
	public static final String TREETYPE_CMContainerStage = "CS";
	/** CM Template = CT */
	public static final String TREETYPE_CMTemplate = "CT";
	/** CM Media = CM */
	public static final String TREETYPE_CMMedia = "CM";
	/** Custom Table = TL */
	public static final String TREETYPE_CustomTable = "TL";
	/** Set Type | Area.
		@param TreeType 
		Element this tree is built on (i.e Product, Business Partner)
	  */
	public void setTreeType (String TreeType)
	{

		set_Value (COLUMNNAME_TreeType, TreeType);
	}

	/** Get Type | Area.
		@return Element this tree is built on (i.e Product, Business Partner)
	  */
	public String getTreeType () 
	{
		return (String)get_Value(COLUMNNAME_TreeType);
	}
}