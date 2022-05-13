/******************************************************************************
 * Product: EONE ERP & CRM Smart Business Solution	                        *
 * Copyright (C) 2020, Inc. All Rights Reserved.				                *
 *****************************************************************************/
package eone.base.model;

import eone.util.KeyNamePair;
import java.math.BigDecimal;
import java.sql.Timestamp;

/** Generated Interface for A_Asset
 *  @author EOne (generated) 
 *  @version Version 1.0
 */
public interface I_A_Asset 
{

    /** TableName=A_Asset */
    public static final String Table_Name = "A_Asset";

    /** AD_Table_ID=539 */
    public static final int Table_ID = 539;

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 7 - System - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(7);

    /** Load Meta Data */

    /** Column name A_Asset_Group_ID */
    public static final String COLUMNNAME_A_Asset_Group_ID = "A_Asset_Group_ID";

	/** Set Asset Group.
	  * Group of Assets
	  */
	public void setA_Asset_Group_ID (int A_Asset_Group_ID);

	/** Get Asset Group.
	  * Group of Assets
	  */
	public int getA_Asset_Group_ID();

	public eone.base.model.I_A_Asset_Group getA_Asset_Group() throws RuntimeException;

    /** Column name A_Asset_ID */
    public static final String COLUMNNAME_A_Asset_ID = "A_Asset_ID";

	/** Set Asset.
	  * Asset used internally or by customers
	  */
	public void setA_Asset_ID (int A_Asset_ID);

	/** Get Asset.
	  * Asset used internally or by customers
	  */
	public int getA_Asset_ID();

    /** Column name AccumulateAmt */
    public static final String COLUMNNAME_AccumulateAmt = "AccumulateAmt";

	/** Set AccumulateAmt	  */
	public void setAccumulateAmt (BigDecimal AccumulateAmt);

	/** Get AccumulateAmt	  */
	public BigDecimal getAccumulateAmt();

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

    /** Column name Approved */
    public static final String COLUMNNAME_Approved = "Approved";

	/** Set Approved	  */
	public void setApproved (String Approved);

	/** Get Approved	  */
	public String getApproved();

    /** Column name BaseAmtCurrent */
    public static final String COLUMNNAME_BaseAmtCurrent = "BaseAmtCurrent";

	/** Set BaseAmtCurrent	  */
	public void setBaseAmtCurrent (BigDecimal BaseAmtCurrent);

	/** Get BaseAmtCurrent	  */
	public BigDecimal getBaseAmtCurrent();

    /** Column name BaseAmtOriginal */
    public static final String COLUMNNAME_BaseAmtOriginal = "BaseAmtOriginal";

	/** Set BaseAmtOriginal	  */
	public void setBaseAmtOriginal (BigDecimal BaseAmtOriginal);

	/** Get BaseAmtOriginal	  */
	public BigDecimal getBaseAmtOriginal();

    /** Column name C_TypeCost_ID */
    public static final String COLUMNNAME_C_TypeCost_ID = "C_TypeCost_ID";

	/** Set TypeCost	  */
	public void setC_TypeCost_ID (int C_TypeCost_ID);

	/** Get TypeCost	  */
	public int getC_TypeCost_ID();

	public eone.base.model.I_C_TypeCost getC_TypeCost() throws RuntimeException;

    /** Column name Canceled */
    public static final String COLUMNNAME_Canceled = "Canceled";

	/** Set Canceled	  */
	public void setCanceled (String Canceled);

	/** Get Canceled	  */
	public String getCanceled();

    /** Column name Country */
    public static final String COLUMNNAME_Country = "Country";

	/** Set Country	  */
	public void setCountry (String Country);

	/** Get Country	  */
	public String getCountry();

    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Created.
	  * Date this record was created
	  */
	public Timestamp getCreated();

    /** Column name CreateDate */
    public static final String COLUMNNAME_CreateDate = "CreateDate";

	/** Set Create Date	  */
	public void setCreateDate (Timestamp CreateDate);

	/** Get Create Date	  */
	public Timestamp getCreateDate();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Get Created By.
	  * User who created this records
	  */
	public int getCreatedBy();

    /** Column name DepreciationSplit */
    public static final String COLUMNNAME_DepreciationSplit = "DepreciationSplit";

	/** Set Depreciation Split	  */
	public void setDepreciationSplit (boolean DepreciationSplit);

	/** Get Depreciation Split	  */
	public boolean isDepreciationSplit();

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

    /** Column name DocStatus */
    public static final String COLUMNNAME_DocStatus = "DocStatus";

	/** Set Document Status.
	  * The current status of the document
	  */
	public void setDocStatus (String DocStatus);

	/** Get Document Status.
	  * The current status of the document
	  */
	public String getDocStatus();

    /** Column name GuaranteeDate */
    public static final String COLUMNNAME_GuaranteeDate = "GuaranteeDate";

	/** Set Guarantee Date.
	  * Date when guarantee expires
	  */
	public void setGuaranteeDate (Timestamp GuaranteeDate);

	/** Get Guarantee Date.
	  * Date when guarantee expires
	  */
	public Timestamp getGuaranteeDate();

    /** Column name InventoryNo */
    public static final String COLUMNNAME_InventoryNo = "InventoryNo";

	/** Set Inventory No	  */
	public void setInventoryNo (String InventoryNo);

	/** Get Inventory No	  */
	public String getInventoryNo();

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

    /** Column name IsDepreciated */
    public static final String COLUMNNAME_IsDepreciated = "IsDepreciated";

	/** Set Depreciate.
	  * The asset will be depreciated
	  */
	public void setIsDepreciated (boolean IsDepreciated);

	/** Get Depreciate.
	  * The asset will be depreciated
	  */
	public boolean isDepreciated();

    /** Column name IsDisposed */
    public static final String COLUMNNAME_IsDisposed = "IsDisposed";

	/** Set Disposed.
	  * The asset is disposed
	  */
	public void setIsDisposed (boolean IsDisposed);

	/** Get Disposed.
	  * The asset is disposed
	  */
	public boolean isDisposed();

    /** Column name IsRecordUsed */
    public static final String COLUMNNAME_IsRecordUsed = "IsRecordUsed";

	/** Set IsRecordUsed	  */
	public void setIsRecordUsed (boolean IsRecordUsed);

	/** Get IsRecordUsed	  */
	public boolean isRecordUsed();

    /** Column name IsTransferred */
    public static final String COLUMNNAME_IsTransferred = "IsTransferred";

	/** Set Transferred.
	  * Transferred to General Ledger (i.e. accounted)
	  */
	public void setIsTransferred (boolean IsTransferred);

	/** Get Transferred.
	  * Transferred to General Ledger (i.e. accounted)
	  */
	public boolean isTransferred();

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

    /** Column name PendingDate */
    public static final String COLUMNNAME_PendingDate = "PendingDate";

	/** Set Pending Date.
	  * Pending date
	  */
	public void setPendingDate (Timestamp PendingDate);

	/** Get Pending Date.
	  * Pending date
	  */
	public Timestamp getPendingDate();

    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/** Set Processed.
	  * The document has been processed
	  */
	public void setProcessed (boolean Processed);

	/** Get Processed.
	  * The document has been processed
	  */
	public boolean isProcessed();

    /** Column name RemainAmt */
    public static final String COLUMNNAME_RemainAmt = "RemainAmt";

	/** Set RemainAmt	  */
	public void setRemainAmt (BigDecimal RemainAmt);

	/** Get RemainAmt	  */
	public BigDecimal getRemainAmt();

    /** Column name SerNo */
    public static final String COLUMNNAME_SerNo = "SerNo";

	/** Set Serial No.
	  * Product Serial Number 
	  */
	public void setSerNo (String SerNo);

	/** Get Serial No.
	  * Product Serial Number 
	  */
	public String getSerNo();

    /** Column name StatusUse */
    public static final String COLUMNNAME_StatusUse = "StatusUse";

	/** Set StatusUse	  */
	public void setStatusUse (String StatusUse);

	/** Get StatusUse	  */
	public String getStatusUse();

    /** Column name TypeCalculate */
    public static final String COLUMNNAME_TypeCalculate = "TypeCalculate";

	/** Set TypeCalculate	  */
	public void setTypeCalculate (String TypeCalculate);

	/** Get TypeCalculate	  */
	public String getTypeCalculate();

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

    /** Column name UseDate */
    public static final String COLUMNNAME_UseDate = "UseDate";

	/** Set UseDate	  */
	public void setUseDate (Timestamp UseDate);

	/** Get UseDate	  */
	public Timestamp getUseDate();

    /** Column name UseLifed */
    public static final String COLUMNNAME_UseLifed = "UseLifed";

	/** Set UseLifed	  */
	public void setUseLifed (int UseLifed);

	/** Get UseLifed	  */
	public int getUseLifed();

    /** Column name UseLifes */
    public static final String COLUMNNAME_UseLifes = "UseLifes";

	/** Set UseLifes	  */
	public void setUseLifes (BigDecimal UseLifes);

	/** Get UseLifes	  */
	public BigDecimal getUseLifes();

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
