/******************************************************************************
 * Product: EONE ERP & CRM Smart Business Solution	                        *
 * Copyright (C) 2020, Inc. All Rights Reserved.				                *
 *****************************************************************************/
package eone.base.model;

import eone.util.KeyNamePair;
import java.math.BigDecimal;
import java.sql.Timestamp;

/** Generated Interface for AD_Tree
 *  @author EOne (generated) 
 *  @version Version 1.0
 */
public interface I_AD_Tree 
{

    /** TableName=AD_Tree */
    public static final String Table_Name = "AD_Tree";

    /** AD_Table_ID=288 */
    public static final int Table_ID = 288;

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 4 - System 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(4);

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

    /** Column name AD_Table_ID */
    public static final String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

	/** Set Table.
	  * Database Table information
	  */
	public void setAD_Table_ID (int AD_Table_ID);

	/** Get Table.
	  * Database Table information
	  */
	public int getAD_Table_ID();

	public eone.base.model.I_AD_Table getAD_Table() throws RuntimeException;

    /** Column name AD_Tree_ID */
    public static final String COLUMNNAME_AD_Tree_ID = "AD_Tree_ID";

	/** Set Tree.
	  * Identifies a Tree
	  */
	public void setAD_Tree_ID (int AD_Tree_ID);

	/** Get Tree.
	  * Identifies a Tree
	  */
	public int getAD_Tree_ID();

    /** Column name CreateCopy */
    public static final String COLUMNNAME_CreateCopy = "CreateCopy";

	/** Set Create Copy	  */
	public void setCreateCopy (String CreateCopy);

	/** Get Create Copy	  */
	public String getCreateCopy();

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

    /** Column name IsAdminClient */
    public static final String COLUMNNAME_IsAdminClient = "IsAdminClient";

	/** Set Admin Company.
	  * Admin Company
	  */
	public void setIsAdminClient (boolean IsAdminClient);

	/** Get Admin Company.
	  * Admin Company
	  */
	public boolean isAdminClient();

    /** Column name IsAllNodes */
    public static final String COLUMNNAME_IsAllNodes = "IsAllNodes";

	/** Set All Nodes.
	  * All Nodes are included (Complete Tree)
	  */
	public void setIsAllNodes (boolean IsAllNodes);

	/** Get All Nodes.
	  * All Nodes are included (Complete Tree)
	  */
	public boolean isAllNodes();

    /** Column name IsDefault */
    public static final String COLUMNNAME_IsDefault = "IsDefault";

	/** Set Default.
	  * Default value
	  */
	public void setIsDefault (boolean IsDefault);

	/** Get Default.
	  * Default value
	  */
	public boolean isDefault();

    /** Column name IsLoadAllNodesImmediately */
    public static final String COLUMNNAME_IsLoadAllNodesImmediately = "IsLoadAllNodesImmediately";

	/** Set Loads directly all nodes.
	  * If checked, all nodes are loaded before tree is displayed
	  */
	public void setIsLoadAllNodesImmediately (boolean IsLoadAllNodesImmediately);

	/** Get Loads directly all nodes.
	  * If checked, all nodes are loaded before tree is displayed
	  */
	public boolean isLoadAllNodesImmediately();

    /** Column name IsTreeDrivenByValue */
    public static final String COLUMNNAME_IsTreeDrivenByValue = "IsTreeDrivenByValue";

	/** Set Driven by Search Key	  */
	public void setIsTreeDrivenByValue (boolean IsTreeDrivenByValue);

	/** Get Driven by Search Key	  */
	public boolean isTreeDrivenByValue();

    /** Column name IsValueDisplayed */
    public static final String COLUMNNAME_IsValueDisplayed = "IsValueDisplayed";

	/** Set Display Value.
	  * Displays Value column with the Display column
	  */
	public void setIsValueDisplayed (boolean IsValueDisplayed);

	/** Get Display Value.
	  * Displays Value column with the Display column
	  */
	public boolean isValueDisplayed();

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

    /** Column name Parent_Column_ID */
    public static final String COLUMNNAME_Parent_Column_ID = "Parent_Column_ID";

	/** Set Parent Column.
	  * The link column on the parent tab.
	  */
	public void setParent_Column_ID (int Parent_Column_ID);

	/** Get Parent Column.
	  * The link column on the parent tab.
	  */
	public int getParent_Column_ID();

	public eone.base.model.I_AD_Column getParent_Column() throws RuntimeException;

    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/** Set Process Now	  */
	public void setProcessing (boolean Processing);

	/** Get Process Now	  */
	public boolean isProcessing();

    /** Column name TreeType */
    public static final String COLUMNNAME_TreeType = "TreeType";

	/** Set Type | Area.
	  * Element this tree is built on (i.e Product, Business Partner)
	  */
	public void setTreeType (String TreeType);

	/** Get Type | Area.
	  * Element this tree is built on (i.e Product, Business Partner)
	  */
	public String getTreeType();

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
