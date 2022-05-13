/******************************************************************************
 * Product: EONE ERP & CRM Smart Business Solution	                        *
 * Copyright (C) 2020, Inc. All Rights Reserved.				                *
 *****************************************************************************/
package eone.base.model;

import eone.util.KeyNamePair;
import java.math.BigDecimal;
import java.sql.Timestamp;

/** Generated Interface for A_Asset_History
 *  @author EOne (generated) 
 *  @version Version 1.0
 */
public interface I_A_Asset_History 
{

    /** TableName=A_Asset_History */
    public static final String Table_Name = "A_Asset_History";

    /** AD_Table_ID=1200328 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 7 - System - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(7);

    /** Load Meta Data */

    /** Column name A_Asset_History_ID */
    public static final String COLUMNNAME_A_Asset_History_ID = "A_Asset_History_ID";

	/** Set Asset History	  */
	public void setA_Asset_History_ID (int A_Asset_History_ID);

	/** Get Asset History	  */
	public int getA_Asset_History_ID();

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

	public eone.base.model.I_A_Asset getA_Asset() throws RuntimeException;

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

    /** Column name ChangeDate */
    public static final String COLUMNNAME_ChangeDate = "ChangeDate";

	/** Set ChangeDate	  */
	public void setChangeDate (Timestamp ChangeDate);

	/** Get ChangeDate	  */
	public Timestamp getChangeDate();

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

    /** Column name IsFirst */
    public static final String COLUMNNAME_IsFirst = "IsFirst";

	/** Set IsFirst	  */
	public void setIsFirst (boolean IsFirst);

	/** Get IsFirst	  */
	public boolean isFirst();

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

    /** Column name UseLifes */
    public static final String COLUMNNAME_UseLifes = "UseLifes";

	/** Set UseLifes	  */
	public void setUseLifes (BigDecimal UseLifes);

	/** Get UseLifes	  */
	public BigDecimal getUseLifes();
}
