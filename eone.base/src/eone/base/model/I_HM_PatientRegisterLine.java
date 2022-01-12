/******************************************************************************
 * Product: EONE ERP & CRM Smart Business Solution	                        *
 * Copyright (C) 2020, Inc. All Rights Reserved.				                *
 *****************************************************************************/
package eone.base.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.util.KeyNamePair;

/** Generated Interface for HM_PatientRegisterLine
 *  @author EOne (generated) 
 *  @version Version 1.0
 */
public interface I_HM_PatientRegisterLine 
{

    /** TableName=HM_PatientRegisterLine */
    public static final String Table_Name = "HM_PatientRegisterLine";

    /** AD_Table_ID=1200395 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

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

    /** Column name Amount */
    public static final String COLUMNNAME_Amount = "Amount";

	/** Set Amount.
	  * Amount in a defined currency
	  */
	public void setAmount (BigDecimal Amount);

	/** Get Amount.
	  * Amount in a defined currency
	  */
	public BigDecimal getAmount();

    /** Column name btnFinish */
    public static final String COLUMNNAME_btnFinish = "btnFinish";

	/** Set btnFinish	  */
	public void setbtnFinish (String btnFinish);

	/** Get btnFinish	  */
	public String getbtnFinish();

    /** Column name btnResult */
    public static final String COLUMNNAME_btnResult = "btnResult";

	/** Set btnResult	  */
	public void setbtnResult (String btnResult);

	/** Get btnResult	  */
	public String getbtnResult();

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

    /** Column name Detail */
    public static final String COLUMNNAME_Detail = "Detail";

	/** Set Detail	  */
	public void setDetail (String Detail);

	/** Get Detail	  */
	public String getDetail();

    /** Column name HM_PatientRegister_ID */
    public static final String COLUMNNAME_HM_PatientRegister_ID = "HM_PatientRegister_ID";

	/** Set Patient Register	  */
	public void setHM_PatientRegister_ID (int HM_PatientRegister_ID);

	/** Get Patient Register	  */
	public int getHM_PatientRegister_ID();

	public eone.base.model.I_HM_PatientRegister getHM_PatientRegister() throws RuntimeException;

    /** Column name HM_PatientRegisterLine_ID */
    public static final String COLUMNNAME_HM_PatientRegisterLine_ID = "HM_PatientRegisterLine_ID";

	/** Set PatientRegisterLine	  */
	public void setHM_PatientRegisterLine_ID (int HM_PatientRegisterLine_ID);

	/** Get PatientRegisterLine	  */
	public int getHM_PatientRegisterLine_ID();

    /** Column name HR_Employee_ID */
    public static final String COLUMNNAME_HR_Employee_ID = "HR_Employee_ID";

	/** Set Employee	  */
	public void setHR_Employee_ID (int HR_Employee_ID);

	/** Get Employee	  */
	public int getHR_Employee_ID();

	public eone.base.model.I_HR_Employee getHR_Employee() throws RuntimeException;

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

    /** Column name IsSchedule */
    public static final String COLUMNNAME_IsSchedule = "IsSchedule";

	/** Set Schedule	  */
	public void setIsSchedule (boolean IsSchedule);

	/** Get Schedule	  */
	public boolean isSchedule();

    /** Column name M_Product_Category_ID */
    public static final String COLUMNNAME_M_Product_Category_ID = "M_Product_Category_ID";

	/** Set Product Category.
	  * Category of a Product
	  */
	public void setM_Product_Category_ID (int M_Product_Category_ID);

	/** Get Product Category.
	  * Category of a Product
	  */
	public int getM_Product_Category_ID();

	public eone.base.model.I_M_Product_Category getM_Product_Category() throws RuntimeException;

    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/** Set Product.
	  * Product, Service, Item
	  */
	public void setM_Product_ID (int M_Product_ID);

	/** Get Product.
	  * Product, Service, Item
	  */
	public int getM_Product_ID();

	public eone.base.model.I_M_Product getM_Product() throws RuntimeException;

    /** Column name PerformStatus */
    public static final String COLUMNNAME_PerformStatus = "PerformStatus";

	/** Set Perform Status	  */
	public void setPerformStatus (String PerformStatus);

	/** Get Perform Status	  */
	public String getPerformStatus();

    /** Column name Price */
    public static final String COLUMNNAME_Price = "Price";

	/** Set Price.
	  * Price
	  */
	public void setPrice (BigDecimal Price);

	/** Get Price.
	  * Price
	  */
	public BigDecimal getPrice();

    /** Column name PrintName */
    public static final String COLUMNNAME_PrintName = "PrintName";

	/** Set Print Text.
	  * The label text to be printed on a document or correspondence.
	  */
	public void setPrintName (String PrintName);

	/** Get Print Text.
	  * The label text to be printed on a document or correspondence.
	  */
	public String getPrintName();

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

    /** Column name Qty */
    public static final String COLUMNNAME_Qty = "Qty";

	/** Set Quantity.
	  * Quantity
	  */
	public void setQty (BigDecimal Qty);

	/** Get Quantity.
	  * Quantity
	  */
	public BigDecimal getQty();

    /** Column name QtyCount */
    public static final String COLUMNNAME_QtyCount = "QtyCount";

	/** Set Quantity count.
	  * Counted Quantity
	  */
	public void setQtyCount (BigDecimal QtyCount);

	/** Get Quantity count.
	  * Counted Quantity
	  */
	public BigDecimal getQtyCount();

    /** Column name ResultCLS */
    public static final String COLUMNNAME_ResultCLS = "ResultCLS";

	/** Set ResultCLS	  */
	public void setResultCLS (String ResultCLS);

	/** Get ResultCLS	  */
	public String getResultCLS();

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
}
