/******************************************************************************
 * Product: EONE ERP & CRM Smart Business Solution	                        *
 * Copyright (C) 2020, Inc. All Rights Reserved.				                *
 *****************************************************************************/
package eone.base.model;

import eone.util.KeyNamePair;
import java.math.BigDecimal;
import java.sql.Timestamp;

/** Generated Interface for A_Asset_BuildLine
 *  @author EOne (generated) 
 *  @version Version 1.0
 */
public interface I_A_Asset_BuildLine 
{

    /** TableName=A_Asset_BuildLine */
    public static final String Table_Name = "A_Asset_BuildLine";

    /** AD_Table_ID=1200422 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 7 - System - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(7);

    /** Load Meta Data */

    /** Column name A_Asset_Build_ID */
    public static final String COLUMNNAME_A_Asset_Build_ID = "A_Asset_Build_ID";

	/** Set Asset Build	  */
	public void setA_Asset_Build_ID (int A_Asset_Build_ID);

	/** Get Asset Build	  */
	public int getA_Asset_Build_ID();

	public eone.base.model.I_A_Asset_Build getA_Asset_Build() throws RuntimeException;

    /** Column name A_Asset_BuildLine_ID */
    public static final String COLUMNNAME_A_Asset_BuildLine_ID = "A_Asset_BuildLine_ID";

	/** Set Line	  */
	public void setA_Asset_BuildLine_ID (int A_Asset_BuildLine_ID);

	/** Get Line	  */
	public int getA_Asset_BuildLine_ID();

    /** Column name Account_Cr_ID */
    public static final String COLUMNNAME_Account_Cr_ID = "Account_Cr_ID";

	/** Set Account Cr.
	  * Account Cr
	  */
	public void setAccount_Cr_ID (int Account_Cr_ID);

	/** Get Account Cr.
	  * Account Cr
	  */
	public int getAccount_Cr_ID();

	public eone.base.model.I_C_ElementValue getAccount_Cr() throws RuntimeException;

    /** Column name Account_Dr_ID */
    public static final String COLUMNNAME_Account_Dr_ID = "Account_Dr_ID";

	/** Set Account Dr.
	  * Account Dr
	  */
	public void setAccount_Dr_ID (int Account_Dr_ID);

	/** Get Account Dr.
	  * Account Dr
	  */
	public int getAccount_Dr_ID();

	public eone.base.model.I_C_ElementValue getAccount_Dr() throws RuntimeException;

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

    /** Column name C_Construction_ID */
    public static final String COLUMNNAME_C_Construction_ID = "C_Construction_ID";

	/** Set Construction	  */
	public void setC_Construction_ID (int C_Construction_ID);

	/** Get Construction	  */
	public int getC_Construction_ID();

	public eone.base.model.I_C_Construction getC_Construction() throws RuntimeException;

    /** Column name C_DocTypeSub_ID */
    public static final String COLUMNNAME_C_DocTypeSub_ID = "C_DocTypeSub_ID";

	/** Set Sub Document.
	  * Document type for generating in dispute Shipments
	  */
	public void setC_DocTypeSub_ID (int C_DocTypeSub_ID);

	/** Get Sub Document.
	  * Document type for generating in dispute Shipments
	  */
	public int getC_DocTypeSub_ID();

	public eone.base.model.I_C_DocTypeSub getC_DocTypeSub() throws RuntimeException;

    /** Column name C_Tax_ID */
    public static final String COLUMNNAME_C_Tax_ID = "C_Tax_ID";

	/** Set Tax.
	  * Tax identifier
	  */
	public void setC_Tax_ID (int C_Tax_ID);

	/** Get Tax.
	  * Tax identifier
	  */
	public int getC_Tax_ID();

	public eone.base.model.I_C_Tax getC_Tax() throws RuntimeException;

    /** Column name C_TypeCost_ID */
    public static final String COLUMNNAME_C_TypeCost_ID = "C_TypeCost_ID";

	/** Set TypeCost	  */
	public void setC_TypeCost_ID (int C_TypeCost_ID);

	/** Get TypeCost	  */
	public int getC_TypeCost_ID();

	public eone.base.model.I_C_TypeCost getC_TypeCost() throws RuntimeException;

    /** Column name C_TypeRevenue_ID */
    public static final String COLUMNNAME_C_TypeRevenue_ID = "C_TypeRevenue_ID";

	/** Set Type Revenue	  */
	public void setC_TypeRevenue_ID (int C_TypeRevenue_ID);

	/** Get Type Revenue	  */
	public int getC_TypeRevenue_ID();

	public eone.base.model.I_C_TypeRevenue getC_TypeRevenue() throws RuntimeException;

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

    /** Column name DateInvoiced */
    public static final String COLUMNNAME_DateInvoiced = "DateInvoiced";

	/** Set Date Invoiced.
	  * Date printed on Invoice
	  */
	public void setDateInvoiced (Timestamp DateInvoiced);

	/** Get Date Invoiced.
	  * Date printed on Invoice
	  */
	public Timestamp getDateInvoiced();

    /** Column name InvoiceNo */
    public static final String COLUMNNAME_InvoiceNo = "InvoiceNo";

	/** Set InvoiceNo	  */
	public void setInvoiceNo (String InvoiceNo);

	/** Get InvoiceNo	  */
	public String getInvoiceNo();

    /** Column name InvoiceSymbol */
    public static final String COLUMNNAME_InvoiceSymbol = "InvoiceSymbol";

	/** Set InvoiceSymbol	  */
	public void setInvoiceSymbol (String InvoiceSymbol);

	/** Get InvoiceSymbol	  */
	public String getInvoiceSymbol();

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

    /** Column name M_Warehouse_Cr_ID */
    public static final String COLUMNNAME_M_Warehouse_Cr_ID = "M_Warehouse_Cr_ID";

	/** Set Warehouse Cr.
	  * Storage Warehouse and Service Point
	  */
	public void setM_Warehouse_Cr_ID (int M_Warehouse_Cr_ID);

	/** Get Warehouse Cr.
	  * Storage Warehouse and Service Point
	  */
	public int getM_Warehouse_Cr_ID();

	public eone.base.model.I_M_Warehouse getM_Warehouse_Cr() throws RuntimeException;

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
}
