/******************************************************************************
 * Product: EONE ERP & CRM Smart Business Solution	                        *
 * Copyright (C) 2020, Inc. All Rights Reserved.				                *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package eone.base.model;

import eone.util.KeyNamePair;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_Role
 *  @author EOne (generated) 
 *  @version Version 1.0 - $Id$ */
public class X_AD_Role extends PO implements I_AD_Role, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20220518L;

    /** Standard Constructor */
    public X_AD_Role (Properties ctx, int AD_Role_ID, String trxName)
    {
      super (ctx, AD_Role_ID, trxName);
      /** if (AD_Role_ID == 0)
        {
			setAD_Role_ID (0);
			setIsAccessAllOrgs (false);
// N
			setIsAdminClient (false);
// N
			setIsCanExport (true);
// Y
			setIsDelItem (false);
// N
			setIsShowAcct (false);
// N
			setIsUseUserOrgAccess (false);
// N
			setName (null);
			setUserLevel (null);
        } */
    }

    /** Load Constructor */
    public X_AD_Role (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 6 - System - Client 
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
      StringBuilder sb = new StringBuilder ("X_AD_Role[")
        .append(get_ID()).append(",Name=").append(getName()).append("]");
      return sb.toString();
    }

	/** Set Role.
		@param AD_Role_ID 
		Responsibility Role
	  */
	public void setAD_Role_ID (int AD_Role_ID)
	{
		if (AD_Role_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_AD_Role_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Role_ID, Integer.valueOf(AD_Role_ID));
	}

	/** Get Role.
		@return Responsibility Role
	  */
	public int getAD_Role_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Role_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public eone.base.model.I_AD_Tree getAD_Tree_Menu() throws RuntimeException
    {
		return (eone.base.model.I_AD_Tree)MTable.get(getCtx(), eone.base.model.I_AD_Tree.Table_Name)
			.getPO(getAD_Tree_Menu_ID(), get_TrxName());	}

	/** Set Menu Tree.
		@param AD_Tree_Menu_ID 
		Tree of the menu
	  */
	public void setAD_Tree_Menu_ID (int AD_Tree_Menu_ID)
	{
		if (AD_Tree_Menu_ID < 1) 
			set_Value (COLUMNNAME_AD_Tree_Menu_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Tree_Menu_ID, Integer.valueOf(AD_Tree_Menu_ID));
	}

	/** Get Menu Tree.
		@return Tree of the menu
	  */
	public int getAD_Tree_Menu_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Tree_Menu_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public eone.base.model.I_AD_Tree getAD_Tree_Org() throws RuntimeException
    {
		return (eone.base.model.I_AD_Tree)MTable.get(getCtx(), eone.base.model.I_AD_Tree.Table_Name)
			.getPO(getAD_Tree_Org_ID(), get_TrxName());	}

	/** Set Organization Tree.
		@param AD_Tree_Org_ID 
		Trees are used for (financial) reporting and security access (via role)
	  */
	public void setAD_Tree_Org_ID (int AD_Tree_Org_ID)
	{
		if (AD_Tree_Org_ID < 1) 
			set_Value (COLUMNNAME_AD_Tree_Org_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Tree_Org_ID, Integer.valueOf(AD_Tree_Org_ID));
	}

	/** Get Organization Tree.
		@return Trees are used for (financial) reporting and security access (via role)
	  */
	public int getAD_Tree_Org_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Tree_Org_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Access all Orgs.
		@param IsAccessAllOrgs 
		Access all Organizations (no org access control) of the client
	  */
	public void setIsAccessAllOrgs (boolean IsAccessAllOrgs)
	{
		set_Value (COLUMNNAME_IsAccessAllOrgs, Boolean.valueOf(IsAccessAllOrgs));
	}

	/** Get Access all Orgs.
		@return Access all Organizations (no org access control) of the client
	  */
	public boolean isAccessAllOrgs () 
	{
		Object oo = get_Value(COLUMNNAME_IsAccessAllOrgs);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set Can Export.
		@param IsCanExport 
		Users with this role can export data
	  */
	public void setIsCanExport (boolean IsCanExport)
	{
		set_Value (COLUMNNAME_IsCanExport, Boolean.valueOf(IsCanExport));
	}

	/** Get Can Export.
		@return Users with this role can export data
	  */
	public boolean isCanExport () 
	{
		Object oo = get_Value(COLUMNNAME_IsCanExport);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsConfigAcct.
		@param IsConfigAcct IsConfigAcct	  */
	public void setIsConfigAcct (boolean IsConfigAcct)
	{
		set_Value (COLUMNNAME_IsConfigAcct, Boolean.valueOf(IsConfigAcct));
	}

	/** Get IsConfigAcct.
		@return IsConfigAcct	  */
	public boolean isConfigAcct () 
	{
		Object oo = get_Value(COLUMNNAME_IsConfigAcct);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Delete Item special.
		@param IsDelItem Delete Item special	  */
	public void setIsDelItem (boolean IsDelItem)
	{
		set_Value (COLUMNNAME_IsDelItem, Boolean.valueOf(IsDelItem));
	}

	/** Get Delete Item special.
		@return Delete Item special	  */
	public boolean isDelItem () 
	{
		Object oo = get_Value(COLUMNNAME_IsDelItem);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsDragDropMenu.
		@param IsDragDropMenu IsDragDropMenu	  */
	public void setIsDragDropMenu (boolean IsDragDropMenu)
	{
		set_Value (COLUMNNAME_IsDragDropMenu, Boolean.valueOf(IsDragDropMenu));
	}

	/** Get IsDragDropMenu.
		@return IsDragDropMenu	  */
	public boolean isDragDropMenu () 
	{
		Object oo = get_Value(COLUMNNAME_IsDragDropMenu);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Show Accounting.
		@param IsShowAcct 
		Users with this role can see accounting information
	  */
	public void setIsShowAcct (boolean IsShowAcct)
	{
		set_Value (COLUMNNAME_IsShowAcct, Boolean.valueOf(IsShowAcct));
	}

	/** Get Show Accounting.
		@return Users with this role can see accounting information
	  */
	public boolean isShowAcct () 
	{
		Object oo = get_Value(COLUMNNAME_IsShowAcct);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsShowPrice.
		@param IsShowPrice IsShowPrice	  */
	public void setIsShowPrice (boolean IsShowPrice)
	{
		set_Value (COLUMNNAME_IsShowPrice, Boolean.valueOf(IsShowPrice));
	}

	/** Get IsShowPrice.
		@return IsShowPrice	  */
	public boolean isShowPrice () 
	{
		Object oo = get_Value(COLUMNNAME_IsShowPrice);
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

	/** Level1 = 01 */
	public static final String ROLELEVEL_Level1 = "01";
	/** Level2 = 02 */
	public static final String ROLELEVEL_Level2 = "02";
	/** Level3 = 03 */
	public static final String ROLELEVEL_Level3 = "03";
	/** Level4 = 04 */
	public static final String ROLELEVEL_Level4 = "04";
	/** Level5 = 05 */
	public static final String ROLELEVEL_Level5 = "05";
	/** Level6 = 06 */
	public static final String ROLELEVEL_Level6 = "06";
	/** Set RoleLevel.
		@param RoleLevel RoleLevel	  */
	public void setRoleLevel (String RoleLevel)
	{

		set_Value (COLUMNNAME_RoleLevel, RoleLevel);
	}

	/** Get RoleLevel.
		@return RoleLevel	  */
	public String getRoleLevel () 
	{
		return (String)get_Value(COLUMNNAME_RoleLevel);
	}

	/** Finacial = FN */
	public static final String ROLETYPE_Finacial = "FN";
	/** Human Resource = HR */
	public static final String ROLETYPE_HumanResource = "HR";
	/** Asset = AS */
	public static final String ROLETYPE_Asset = "AS";
	/** Manufacturing = MA */
	public static final String ROLETYPE_Manufacturing = "MA";
	/** Document = DO */
	public static final String ROLETYPE_Document = "DO";
	/** System = SY */
	public static final String ROLETYPE_System = "SY";
	/** Administrator = AD */
	public static final String ROLETYPE_Administrator = "AD";
	/** Set Role Type.
		@param RoleType Role Type	  */
	public void setRoleType (String RoleType)
	{

		set_Value (COLUMNNAME_RoleType, RoleType);
	}

	/** Get Role Type.
		@return Role Type	  */
	public String getRoleType () 
	{
		return (String)get_Value(COLUMNNAME_RoleType);
	}

	/** UserLevel AD_Reference_ID=226 */
	public static final int USERLEVEL_AD_Reference_ID=226;
	/** System = S   */
	public static final String USERLEVEL_System = "S  ";
	/** Client =  C  */
	public static final String USERLEVEL_Client = " C ";
	/** Organization =   O */
	public static final String USERLEVEL_Organization = "  O";
	/** Client+Organization =  CO */
	public static final String USERLEVEL_ClientPlusOrganization = " CO";
	/** Set User Level.
		@param UserLevel 
		System Client Organization
	  */
	public void setUserLevel (String UserLevel)
	{

		set_Value (COLUMNNAME_UserLevel, UserLevel);
	}

	/** Get User Level.
		@return System Client Organization
	  */
	public String getUserLevel () 
	{
		return (String)get_Value(COLUMNNAME_UserLevel);
	}
}