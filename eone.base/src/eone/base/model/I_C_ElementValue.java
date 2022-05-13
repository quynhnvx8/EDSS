/******************************************************************************
 * Product: EONE ERP & CRM Smart Business Solution	                        *
 * Copyright (C) 2020, Inc. All Rights Reserved.				                *
 *****************************************************************************/
package eone.base.model;

import eone.util.KeyNamePair;
import java.math.BigDecimal;
import java.sql.Timestamp;

/** Generated Interface for C_ElementValue
 *  @author EOne (generated) 
 *  @version Version 1.0
 */
public interface I_C_ElementValue 
{

    /** TableName=C_ElementValue */
    public static final String Table_Name = "C_ElementValue";

    /** AD_Table_ID=188 */
    public static final int Table_ID = 188;

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 4 - System 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(4);

    /** Load Meta Data */

    /** Column name AccountType */
    public static final String COLUMNNAME_AccountType = "AccountType";

	/** Set Account Type.
	  * Indicates the type of account
	  */
	public void setAccountType (String AccountType);

	/** Get Account Type.
	  * Indicates the type of account
	  */
	public String getAccountType();

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Client.
	  * Client/Tenant for this installation.
	  */
	public int getAD_Client_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Organization.
	  * Organizational entity within client
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Organization.
	  * Organizational entity within client
	  */
	public int getAD_Org_ID();

    /** Column name C_Currency_ID */
    public static final String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/** Set Currency.
	  * The Currency for this record
	  */
	public void setC_Currency_ID (int C_Currency_ID);

	/** Get Currency.
	  * The Currency for this record
	  */
	public int getC_Currency_ID();

	public eone.base.model.I_C_Currency getC_Currency() throws RuntimeException;

    /** Column name C_Element_ID */
    public static final String COLUMNNAME_C_Element_ID = "C_Element_ID";

	/** Set Element.
	  * Accounting Element
	  */
	public void setC_Element_ID (int C_Element_ID);

	/** Get Element.
	  * Accounting Element
	  */
	public int getC_Element_ID();

	public eone.base.model.I_C_Element getC_Element() throws RuntimeException;

    /** Column name C_ElementValue_ID */
    public static final String COLUMNNAME_C_ElementValue_ID = "C_ElementValue_ID";

	/** Set Account Element.
	  * Account Element
	  */
	public void setC_ElementValue_ID (int C_ElementValue_ID);

	/** Get Account Element.
	  * Account Element
	  */
	public int getC_ElementValue_ID();

    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Created.
	  * Date this record was created
	  */
	public Timestamp getCreated();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Get Created By.
	  * User who created this records
	  */
	public int getCreatedBy();

    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/** Set Description.
	  * Optional short description of the record
	  */
	public void setDescription (String Description);

	/** Get Description.
	  * Optional short description of the record
	  */
	public String getDescription();

    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/** Set Active.
	  * The record is active in the system
	  */
	public void setIsActive (boolean IsActive);

	/** Get Active.
	  * The record is active in the system
	  */
	public boolean isActive();

    /** Column name IsBankAccount */
    public static final String COLUMNNAME_IsBankAccount = "IsBankAccount";

	/** Set Bank Account.
	  * Indicates if this is the Bank Account
	  */
	public void setIsBankAccount (boolean IsBankAccount);

	/** Get Bank Account.
	  * Indicates if this is the Bank Account
	  */
	public boolean isBankAccount();

    /** Column name IsDetailAsset */
    public static final String COLUMNNAME_IsDetailAsset = "IsDetailAsset";

	/** Set IsDetailAsset	  */
	public void setIsDetailAsset (boolean IsDetailAsset);

	/** Get IsDetailAsset	  */
	public boolean isDetailAsset();

    /** Column name IsDetailBPartner */
    public static final String COLUMNNAME_IsDetailBPartner = "IsDetailBPartner";

	/** Set Manage Business Partners	  */
	public void setIsDetailBPartner (boolean IsDetailBPartner);

	/** Get Manage Business Partners	  */
	public boolean isDetailBPartner();

    /** Column name IsDetailConstruction */
    public static final String COLUMNNAME_IsDetailConstruction = "IsDetailConstruction";

	/** Set IsDetailConstruction	  */
	public void setIsDetailConstruction (boolean IsDetailConstruction);

	/** Get IsDetailConstruction	  */
	public boolean isDetailConstruction();

    /** Column name IsDetailConstructionPharse */
    public static final String COLUMNNAME_IsDetailConstructionPharse = "IsDetailConstructionPharse";

	/** Set IsDetailConstructionPharse	  */
	public void setIsDetailConstructionPharse (boolean IsDetailConstructionPharse);

	/** Get IsDetailConstructionPharse	  */
	public boolean isDetailConstructionPharse();

    /** Column name IsDetailContract */
    public static final String COLUMNNAME_IsDetailContract = "IsDetailContract";

	/** Set IsDetailContract	  */
	public void setIsDetailContract (boolean IsDetailContract);

	/** Get IsDetailContract	  */
	public boolean isDetailContract();

    /** Column name IsDetailContractSchedule */
    public static final String COLUMNNAME_IsDetailContractSchedule = "IsDetailContractSchedule";

	/** Set IsDetailContractSchedule	  */
	public void setIsDetailContractSchedule (boolean IsDetailContractSchedule);

	/** Get IsDetailContractSchedule	  */
	public boolean isDetailContractSchedule();

    /** Column name IsDetailProduct */
    public static final String COLUMNNAME_IsDetailProduct = "IsDetailProduct";

	/** Set Manage Products	  */
	public void setIsDetailProduct (boolean IsDetailProduct);

	/** Get Manage Products	  */
	public boolean isDetailProduct();

    /** Column name IsDetailProject */
    public static final String COLUMNNAME_IsDetailProject = "IsDetailProject";

	/** Set IsDetailProject	  */
	public void setIsDetailProject (boolean IsDetailProject);

	/** Get IsDetailProject	  */
	public boolean isDetailProject();

    /** Column name IsDetailProjectPharse */
    public static final String COLUMNNAME_IsDetailProjectPharse = "IsDetailProjectPharse";

	/** Set IsDetailProjectPharse	  */
	public void setIsDetailProjectPharse (boolean IsDetailProjectPharse);

	/** Get IsDetailProjectPharse	  */
	public boolean isDetailProjectPharse();

    /** Column name IsDetailTax */
    public static final String COLUMNNAME_IsDetailTax = "IsDetailTax";

	/** Set DetailTax	  */
	public void setIsDetailTax (boolean IsDetailTax);

	/** Get DetailTax	  */
	public boolean isDetailTax();

    /** Column name IsDetailTypeCost */
    public static final String COLUMNNAME_IsDetailTypeCost = "IsDetailTypeCost";

	/** Set IsDetailTypeCost	  */
	public void setIsDetailTypeCost (boolean IsDetailTypeCost);

	/** Get IsDetailTypeCost	  */
	public boolean isDetailTypeCost();

    /** Column name IsDetailTypeRevenue */
    public static final String COLUMNNAME_IsDetailTypeRevenue = "IsDetailTypeRevenue";

	/** Set IsDetailTypeRevenue	  */
	public void setIsDetailTypeRevenue (boolean IsDetailTypeRevenue);

	/** Get IsDetailTypeRevenue	  */
	public boolean isDetailTypeRevenue();

    /** Column name IsDetailWarehouse */
    public static final String COLUMNNAME_IsDetailWarehouse = "IsDetailWarehouse";

	/** Set IsDetailWarehouse	  */
	public void setIsDetailWarehouse (boolean IsDetailWarehouse);

	/** Get IsDetailWarehouse	  */
	public boolean isDetailWarehouse();

    /** Column name IsDistribute */
    public static final String COLUMNNAME_IsDistribute = "IsDistribute";

	/** Set Distribute	  */
	public void setIsDistribute (boolean IsDistribute);

	/** Get Distribute	  */
	public boolean isDistribute();

    /** Column name IsSummary */
    public static final String COLUMNNAME_IsSummary = "IsSummary";

	/** Set Summary Level.
	  * This is a summary entity
	  */
	public void setIsSummary (boolean IsSummary);

	/** Get Summary Level.
	  * This is a summary entity
	  */
	public boolean isSummary();

    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/** Set Name.
	  * Alphanumeric identifier of the entity
	  */
	public void setName (String Name);

	/** Get Name.
	  * Alphanumeric identifier of the entity
	  */
	public String getName();

    /** Column name Parent_ID */
    public static final String COLUMNNAME_Parent_ID = "Parent_ID";

	/** Set Parent.
	  * Parent of Entity
	  */
	public void setParent_ID (int Parent_ID);

	/** Get Parent.
	  * Parent of Entity
	  */
	public int getParent_ID();

	public eone.base.model.I_C_ElementValue getParent() throws RuntimeException;

    /** Column name PostActual */
    public static final String COLUMNNAME_PostActual = "PostActual";

	/** Set Post Actual.
	  * Actual Values can be posted
	  */
	public void setPostActual (boolean PostActual);

	/** Get Post Actual.
	  * Actual Values can be posted
	  */
	public boolean isPostActual();

    /** Column name SeqNo */
    public static final String COLUMNNAME_SeqNo = "SeqNo";

	/** Set Sequence.
	  * Method of ordering records;
 lowest number comes first
	  */
	public void setSeqNo (int SeqNo);

	/** Get Sequence.
	  * Method of ordering records;
 lowest number comes first
	  */
	public int getSeqNo();

    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Updated.
	  * Date this record was updated
	  */
	public Timestamp getUpdated();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Get Updated By.
	  * User who updated this records
	  */
	public int getUpdatedBy();

    /** Column name Value */
    public static final String COLUMNNAME_Value = "Value";

	/** Set Code.
	  * Code
	  */
	public void setValue (String Value);

	/** Get Code.
	  * Code
	  */
	public String getValue();
}
