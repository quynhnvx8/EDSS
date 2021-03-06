package eone.base.model;

import static eone.base.model.SystemIDs.REFERENCE_AD_USER;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.Properties;
import java.util.logging.Level;

import eone.util.CLogger;
import eone.util.DisplayType;
import eone.util.Env;


public class GridFieldVO implements Serializable
{

	private static final long serialVersionUID = -7810037179946135749L;

	
	public static String getSQL (Properties ctx)
	{
		StringBuilder sql;
		if (!Env.isBaseLanguage(ctx, "AD_Tab")){
			sql = new StringBuilder("SELECT * FROM AD_Field_vt WHERE AD_Tab_ID=?")
				.append(" AND AD_Language='" + Env.getAD_Language(ctx) + "'");
			sql.append(" ORDER BY IsDisplayed DESC, SeqNo");
		}
		else{
			sql = new StringBuilder("SELECT * FROM AD_Field_v WHERE AD_Tab_ID=?");
			sql.append(" ORDER BY IsDisplayed DESC, SeqNo");
		}
		return sql.toString();
	}   //  getSQL


	public static String getSQLReference (Properties ctx, String ColumnName)
	{
		if (ColumnName.toUpperCase().contains("_ID")) {
			
		}
		StringBuilder sql = new StringBuilder();
		
		return sql.toString();
	}
	
	
	public static GridFieldVO create (Properties ctx, int WindowNo, int TabNo, 
		int AD_Window_ID, int AD_Tab_ID, boolean readOnly, ResultSet rs)
	{
		GridFieldVO vo = new GridFieldVO (ctx, WindowNo, TabNo, 
			AD_Window_ID, AD_Tab_ID, readOnly);
		String columnName = "ColumnName";
		int AD_Field_ID = 0;
		try
		{
			vo.ColumnName = rs.getString("ColumnName");
			if (vo.ColumnName == null)
				return null;

			CLogger.get().fine(vo.ColumnName);

			ResultSetMetaData rsmd = rs.getMetaData();
			for (int i = 1; i <= rsmd.getColumnCount(); i++)
			{
				columnName = rsmd.getColumnName (i);
				if (columnName.equalsIgnoreCase("Name"))
					vo.Header = rs.getString (i);
				else if (columnName.equalsIgnoreCase("AD_Reference_ID"))
					vo.displayType = rs.getInt (i);
				else if (columnName.equalsIgnoreCase("AD_Column_ID"))
					vo.AD_Column_ID = rs.getInt (i);
				else if (columnName.equalsIgnoreCase("AD_Table_ID"))
					vo.AD_Table_ID = rs.getInt (i);
				else if (columnName.equalsIgnoreCase("DisplayLength"))
					vo.DisplayLength = rs.getInt (i);
				else if (columnName.equalsIgnoreCase("IsSameLine"))
					vo.IsSameLine = "Y".equals(rs.getString (i));
				else if (columnName.equalsIgnoreCase("IsDisplayed"))
					vo.IsDisplayed = "Y".equals(rs.getString (i));
				else if (columnName.equalsIgnoreCase("IsDisplayedGrid"))
					vo.IsDisplayedGrid = "Y".equals(rs.getString (i));
				else if (columnName.equalsIgnoreCase("SeqNo"))
					vo.SeqNo = rs.getInt (i);
				else if (columnName.equalsIgnoreCase("SeqNoGrid"))
					vo.SeqNoGrid = rs.getInt (i);
				else if (columnName.equalsIgnoreCase("DisplayLogic"))
					vo.DisplayLogic = rs.getString (i);
				else if (columnName.equalsIgnoreCase("DefaultValue"))
					vo.DefaultValue = rs.getString (i);
				else if (columnName.equalsIgnoreCase("IsMandatory"))
					vo.IsMandatory = "Y".equals(rs.getString (i));
				else if (columnName.equalsIgnoreCase("IsReadOnly"))
					vo.IsReadOnly = "Y".equals(rs.getString (i));
				else if (columnName.equalsIgnoreCase("IsUpdateable"))
					vo.IsUpdateable = "Y".equals(rs.getString (i));
				else if (columnName.equalsIgnoreCase("IsAlwaysUpdateable"))
					vo.IsAlwaysUpdateable = "Y".equals(rs.getString (i));
				else if (columnName.equalsIgnoreCase("IsHeading"))
					vo.IsHeading = "Y".equals(rs.getString (i));
				else if (columnName.equalsIgnoreCase("IsFieldOnly"))
					vo.IsFieldOnly = "Y".equals(rs.getString (i));
				else if (columnName.equalsIgnoreCase("IsEncryptedField"))
					vo.IsEncryptedField = "Y".equals(rs.getString (i));
				else if (columnName.equalsIgnoreCase("IsEncryptedColumn"))
					vo.IsEncryptedColumn = "Y".equals(rs.getString (i));
				else if (columnName.equalsIgnoreCase("IsSelectionColumn"))
					vo.IsSelectionColumn = "Y".equals(rs.getString (i));
				else if (columnName.equalsIgnoreCase("SeqNoSelection"))
					vo.SeqNoSelection = rs.getInt (i);
				else if (columnName.equalsIgnoreCase("SortNo"))
					vo.SortNo = rs.getInt (i);
				else if (columnName.equalsIgnoreCase("FieldLength"))
					vo.FieldLength = rs.getInt (i);
				else if (columnName.equalsIgnoreCase("VFormat"))
					vo.VFormat = rs.getString (i);
				else if (columnName.equalsIgnoreCase("FormatPattern"))
					vo.FormatPattern = rs.getString (i);
				else if (columnName.equalsIgnoreCase("ValueMin"))
					vo.ValueMin = rs.getString (i);
				else if (columnName.equalsIgnoreCase("ValueMax"))
					vo.ValueMax = rs.getString (i);
				else if (columnName.equalsIgnoreCase("FieldGroup"))
					vo.FieldGroup = rs.getString (i);
				else if (columnName.equalsIgnoreCase("FieldGroupType"))
					vo.FieldGroupType = rs.getString (i);
				else if (columnName.equalsIgnoreCase("IsKey"))
					vo.IsKey = "Y".equals(rs.getString (i));
				else if (columnName.equalsIgnoreCase("IsSetContext"))
					vo.IsSetContext = "Y".equals(rs.getString (i));
				else if (columnName.equalsIgnoreCase("IsParent"))
					vo.IsParent = "Y".equals(rs.getString (i));
				else if (columnName.equalsIgnoreCase("Description"))
				{
					String s = rs.getString (i);
					vo.Description = s != null ? s.intern() : s;
				}
				else if (columnName.equalsIgnoreCase("Help"))
				{
					String s = rs.getString (i);
					vo.Help = s != null ? s.intern() : s; 
				}
				else if (columnName.equalsIgnoreCase("Callout"))
					vo.Callout = rs.getString (i);
				else if (columnName.equalsIgnoreCase("AD_Process_ID"))
					vo.AD_Process_ID = rs.getInt (i);
				else if (columnName.equalsIgnoreCase("ReadOnlyLogic"))
					vo.ReadOnlyLogic = rs.getString (i);
				else if (columnName.equalsIgnoreCase("MandatoryLogic"))
					vo.MandatoryLogic = rs.getString (i);	
				else if (columnName.equalsIgnoreCase("ObscureType"))
					vo.ObscureType = rs.getString (i);
				else if (columnName.equalsIgnoreCase("IsDefaultFocus"))
					vo.IsDefaultFocus = "Y".equals(rs.getString(i));
				//
				else if (columnName.equalsIgnoreCase("AD_Reference_Value_ID"))
					vo.AD_Reference_Value_ID = rs.getInt(i);
				else if (columnName.equalsIgnoreCase("ValidationCode"))
					vo.ValidationCode = rs.getString(i);
				else if (columnName.equalsIgnoreCase("IsQuickForm"))
					vo.IsQuickForm = "Y".equals(rs.getString (i));
				else if (columnName.equalsIgnoreCase("ColumnSQL")) {
					vo.ColumnSQL = rs.getString(i);
					if (vo.ColumnSQL != null && !vo.ColumnSQL.startsWith("@SQL=") && !vo.ColumnSQL.startsWith("@SQLFIND=") && vo.ColumnSQL.contains("@")) {
						vo.ColumnSQL = Env.parseContext(ctx, -1, vo.ColumnSQL, false, true);
					}
				} else if (columnName.equalsIgnoreCase("Included_Tab_ID"))
					vo.Included_Tab_ID = rs.getInt(i);
				else if (columnName.equalsIgnoreCase("IsCollapsedByDefault"))
					vo.IsCollapsedByDefault = "Y".equals(rs.getString(i));
				else if (columnName.equalsIgnoreCase("IsAutocomplete"))
					vo.IsAutocomplete  = "Y".equals(rs.getString(i));
				else if (columnName.equalsIgnoreCase("IsAllowCopy"))
					vo.IsAllowCopy  = "Y".equals(rs.getString(i));
				else if (columnName.equalsIgnoreCase("AD_Field_ID"))
					vo.AD_Field_ID = rs.getInt(i);
				else if (columnName.equalsIgnoreCase("ZO_Window_ID"))
					vo.ZO_Window_ID = rs.getInt(i);
				else if (columnName.equalsIgnoreCase("XPosition"))
					vo.XPosition=rs.getInt(i);
				else if (columnName.equalsIgnoreCase("ColumnSpan"))
					vo.ColumnSpan=rs.getInt(i);
				else if (columnName.equalsIgnoreCase("NumLines"))
					vo.NumLines=rs.getInt(i);
				else if (columnName.equalsIgnoreCase("IsToolbarButton"))
					vo.IsToolbarButton  = rs.getString(i);
				else if (columnName.equalsIgnoreCase("AD_Chart_ID"))
					vo.AD_Chart_ID = rs.getInt (i);
				else if (columnName.equalsIgnoreCase("PA_DashboardContent_ID"))
					vo.PA_DashboardContent_ID = rs.getInt (i);
				else if (columnName.equalsIgnoreCase("placeholder"))
					vo.Placeholder = rs.getString(i);
				else if (columnName.equalsIgnoreCase("IsHtml"))
					vo.IsHtml = "Y".equals(rs.getString(i));
			}
			if (vo.Header == null)
				vo.Header = vo.ColumnName;
			AD_Field_ID  = rs.getInt("AD_Field_ID");
		}
		catch (SQLException e)
		{
			CLogger.get().log(Level.SEVERE, "ColumnName=" + columnName, e);
			return null;
		}
		if (vo.IsDisplayed) {
			MClient client = MClient.get(ctx);
			if (! client.isDisplayField(AD_Field_ID))
				vo.IsDisplayed = false;
		}
		
		vo.initFinish();
		return vo;
	}   //  create

	/**
	 *  Init Field for Process Parameter
	 *  @param ctx context
	 *  @param WindowNo window
	 *  @param rs result set AD_Process_Para
	 *  @return MFieldVO
	 */
	public static GridFieldVO createParameter (Properties ctx, int WindowNo, int ProcessIDOfPanel, int WindowIDOfPanel, int adInfoPaneId, ResultSet rs)
	{
		GridFieldVO vo = new GridFieldVO (ctx, WindowNo, 0, 0, 0, false);
		vo.isProcess = true;
		vo.IsDisplayed = true;
		vo.IsReadOnly = false;
		vo.IsUpdateable = true;
		vo.AD_Process_ID_Of_Panel = ProcessIDOfPanel;
		vo.AD_Window_ID_Of_Panel = WindowIDOfPanel;
		vo.AD_Infowindow_ID = adInfoPaneId;
		
		try
		{
			vo.AD_Table_ID = 0;
			vo.AD_Column_ID = rs.getInt("AD_Process_Para_ID");	//	**
			vo.ColumnName = rs.getString("ColumnName");
			vo.Header = rs.getString("Name");
			vo.Description = rs.getString("Description");
			vo.Help = rs.getString("Help");
			vo.SeqNo = rs.getInt("SeqNo");
			vo.displayType = rs.getInt("AD_Reference_ID");
			vo.IsMandatory = rs.getString("IsMandatory").equals("Y");
			vo.FieldLength = rs.getInt("FieldLength");
			vo.DisplayLength = vo.FieldLength;
			vo.DefaultValue = rs.getString("DefaultValue");
			vo.DefaultValue2 = rs.getString("DefaultValue2");
			vo.VFormat = rs.getString("VFormat");
			vo.FormatPattern = rs.getString("FormatPattern");
			vo.ValueMin = rs.getString("ValueMin");
			vo.ValueMax = rs.getString("ValueMax");
			vo.isRange = rs.getString("IsRange").equals("Y");
			//
			vo.AD_Reference_Value_ID = rs.getInt("AD_Reference_Value_ID");
			vo.ValidationCode = rs.getString("ValidationCode");
			vo.ReadOnlyLogic = rs.getString("ReadOnlyLogic");
			vo.DisplayLogic= rs.getString("DisplayLogic");
			vo.IsEncryptedField=rs.getString("IsEncrypted").equals("Y");
			vo.MandatoryLogic = rs.getString("MandatoryLogic");
			vo.Placeholder = rs.getString("Placeholder");
			vo.Placeholder2 = rs.getString("Placeholder2");
		}
		catch (SQLException e)
		{
			CLogger.get().log(Level.SEVERE, "createParameter", e);
		}
		
		vo.initFinish();
		if (vo.DefaultValue2 == null)
			vo.DefaultValue2 = "";
		if (vo.Placeholder2 == null)
			vo.Placeholder2 = "";

		return vo;
	}   //  createParameter

	/**
	 *  Create range "to" Parameter Field from "from" Parameter Field
	 *  @param voF field value object
	 *  @return to MFieldVO
	 */
	public static GridFieldVO createParameter (GridFieldVO voF)
	{
		GridFieldVO voT = new GridFieldVO (voF.ctx, voF.WindowNo, voF.TabNo, 
			voF.AD_Window_ID, voF.AD_Tab_ID, voF.tabReadOnly);
		voT.isProcess = true;
		voT.IsDisplayed = true;
		voT.IsReadOnly = false;
		voT.IsUpdateable = true;
		//
		voT.AD_Table_ID = voF.AD_Table_ID;
		voT.AD_Column_ID = voF.AD_Column_ID;    //  AD_Process_Para_ID
		voT.ColumnName = voF.ColumnName;
		voT.Header = voF.Header;
		voT.Description = voF.Description;
		voT.Help = voF.Help;
		voT.displayType = voF.displayType;
		voT.IsMandatory = voF.IsMandatory;
		voT.FieldLength = voF.FieldLength;
		voT.DisplayLength = voF.FieldLength;
		voT.DefaultValue = voF.DefaultValue2;
		voT.Placeholder2 = voF.Placeholder2;
		voT.VFormat = voF.VFormat;
		voT.FormatPattern = voF.FormatPattern;
		voT.ValueMin = voF.ValueMin;
		voT.ValueMax = voF.ValueMax;
		voT.isRange = voF.isRange;
		voT.AD_Reference_Value_ID = voF.AD_Reference_Value_ID;
		voT.ValidationCode = voF.ValidationCode;
		voT.IsEncryptedField = voF.IsEncryptedField;
		voT.ReadOnlyLogic = voF.ReadOnlyLogic;
		voT.DisplayLogic = voF.DisplayLogic;
		voT.AD_Process_ID_Of_Panel = voF.AD_Process_ID_Of_Panel;
		voT.initFinish();
		
		return voT;
	}   //  createParameter

	/**
	 * Create parameter for infoWindow
	 * @param ctx ctx
	 * @param WindowNo WindowNo
	 * @param AD_Column_ID AD_Column_ID
	 * @param ColumnName ColumnName
	 * @param Name  Name
	 * @param AD_Reference_ID AD_Reference_ID
	 * @param AD_Reference_Value_ID AD_Reference_Value_ID
	 * @param IsMandatory  IsMandatory
	 * @param IsEncrypted IsEncrypted
	 * @return GridFieldV0 v0
	 */
	public static GridFieldVO createParameter (Properties ctx, int WindowNo, int WindowIDOfPanel, int infoWindowID,
			int AD_Column_ID, String ColumnName, String Name, int AD_Reference_ID, int AD_Reference_Value_ID, 
			boolean IsMandatory, boolean IsEncrypted, String Placeholder)
	{
		GridFieldVO vo = new GridFieldVO (ctx, WindowNo, 0, 0, 0, false);
		vo.isProcess = true;
		vo.IsDisplayed = true;
		vo.IsReadOnly = false;
		vo.IsUpdateable = true;
		vo.AD_Table_ID = 0;
		vo.AD_Column_ID = AD_Column_ID;	//	**
		vo.ColumnName = ColumnName;
		vo.Header = Name;
		vo.displayType = AD_Reference_ID;
		vo.AD_Reference_Value_ID = AD_Reference_Value_ID;
		vo.IsMandatory = IsMandatory;
		vo.IsEncryptedField= IsEncrypted;			
		vo.AD_Infowindow_ID = infoWindowID;
		vo.AD_Window_ID_Of_Panel = WindowIDOfPanel;
		vo.Placeholder = Placeholder;
		//
		vo.initFinish();
		return vo;
	}

	/**
	 *  Make a standard field (Created/Updated/By)
	 *  @param ctx context
	 *  @param WindowNo window
	 *  @param TabNo tab
	 *  @param AD_Window_ID window
	 *  @param AD_Tab_ID tab
	 *  @param tabReadOnly rab is r/o
	 *  @param isCreated is Created field
	 *  @param isTimestamp is the timestamp (not by)
	 *  @return MFieldVO
	 */
	public static GridFieldVO createStdField (Properties ctx, int WindowNo, int TabNo, 
		int AD_Window_ID, int AD_Tab_ID, boolean tabReadOnly,
		boolean isCreated, boolean isTimestamp)
	{
		GridFieldVO vo = new GridFieldVO (ctx, WindowNo, TabNo, 
			AD_Window_ID, AD_Tab_ID, tabReadOnly);
		vo.ColumnName = isCreated ? "Created" : "Updated";
		if (!isTimestamp)
			vo.ColumnName += "By";
		vo.displayType = isTimestamp ? DisplayType.DateTime : DisplayType.Table;
		if (!isTimestamp)
			vo.AD_Reference_Value_ID = REFERENCE_AD_USER;		//	AD_User Table Reference
		vo.IsDisplayed = false;
		vo.IsMandatory = false;
		vo.IsReadOnly = false;
		vo.IsUpdateable = true;
		vo.initFinish();
		return vo;
	}   //  initStdField

	
	/**************************************************************************
	 *  Private constructor.
	 *  @param Ctx context
	 *  @param windowNo window
	 *  @param tabNo tab
	 *  @param ad_Window_ID window
	 *  @param ad_Tab_ID tab
	 *  @param TabReadOnly tab read only
	 */
	private GridFieldVO (Properties Ctx, int windowNo, int tabNo, 
		int ad_Window_ID, int ad_Tab_ID, boolean TabReadOnly)
	{
		ctx = Ctx;
		WindowNo = windowNo;
		TabNo = tabNo;
		AD_Window_ID = ad_Window_ID;
		AD_Tab_ID = ad_Tab_ID;
		tabReadOnly = TabReadOnly;
	}   //  MFieldVO

	public Properties   ctx = null;
	public int          WindowNo;
	public int          TabNo;
	public int          AD_Window_ID;
	public int          ZO_Window_ID;
	public int          AD_Process_ID_Of_Panel;
	public int          AD_Window_ID_Of_Panel;
	public int          AD_Infowindow_ID;
	public int			AD_Tab_ID;
	public boolean      tabReadOnly = false;

	public boolean      isProcess = false;

	public String       ColumnName = "";
	public String       ColumnSQL;
	public String       Header = "";
	public int          displayType = 0;
	public int          AD_Table_ID = 0;
	public int          AD_Column_ID = 0;
	public int          DisplayLength = 0;
	public boolean      IsSameLine = false;
	public boolean      IsDisplayed = false;
	public boolean      IsDisplayedGrid = false;
	public int			SeqNo = 0;
	public int			SeqNoGrid = 0;
	public String       DisplayLogic = "";
	public String       DefaultValue = "";
	public boolean      IsMandatory = false;
	public boolean      IsReadOnly = false;
	
	public boolean      IsUpdateable = false;
	public boolean      IsAlwaysUpdateable = false;
	public boolean      IsHeading = false;
	public boolean      IsFieldOnly = false;
	public boolean      IsEncryptedField = false;
	public boolean      IsEncryptedColumn = false;
	public boolean		IsSelectionColumn = false;
	public int			SeqNoSelection = 0;
	public int          SortNo = 0;
	public int          FieldLength = 0;
	public String       VFormat = "";
	public String FormatPattern;
	public String       ValueMin = "";
	public String       ValueMax = "";
	public String       FieldGroup = "";
	public String       FieldGroupType = "";
	public boolean      IsKey = false;
	
	public boolean      IsSetContext = false;
	/**	FK				*/
	public boolean      IsParent = false;
	/**	Callout			*/
	public String       Callout = "";
	/**	Process			*/
	public int          AD_Process_ID = 0;
	/**	Description		*/
	public String       Description = "";
	/**	Help			*/
	public String       Help = "";
	/**	Mandatory Logic	*/
	public String 		MandatoryLogic = "";
	/**	Read Only Logic	*/
	public String       ReadOnlyLogic = "";
	/**	Display Obscure	*/
	public String		ObscureType = null;
	/** Default Focus	*/
	public boolean		IsDefaultFocus = false;

	/**	Lookup Validation code	*/
	public String		ValidationCode = "";
	/**	Reference Value			*/
	public int			AD_Reference_Value_ID = 0;

	/**	Process Parameter Range		*/
	public boolean      isRange = false;
	/**	Process Parameter Value2	*/
	public String       DefaultValue2 = "";

	/** Lookup Value Object     */
	public MLookupInfo  lookupInfo = null;
	
	/** Field ID 				*/
	public int AD_Field_ID = 0;
	
	public int XPosition=0;
	
	public int ColumnSpan=0;
	
    public int NumLines=0; 
	
	public int          Included_Tab_ID = 0;

	public boolean IsCollapsedByDefault = false;
	public boolean IsAutocomplete = false;
	public boolean IsAllowCopy = false;
	public String IsToolbarButton = MColumn.ISTOOLBARBUTTON_Window;
	
	public int AD_Chart_ID = 0;
	
	
	
	public int PA_DashboardContent_ID = 0;

	public String Placeholder = "";

	public String Placeholder2 = "";

	/* Is HTML String */
	public boolean		IsHtml = false;

	/* Allow to show field in Quick Form */
	public boolean IsQuickForm = false;

	/**
	 *  Set Context including contained elements
	 *  @param newCtx new context
	 */
	public void setCtx (Properties newCtx)
	{
		ctx = newCtx;
		if (lookupInfo != null)
			lookupInfo.ctx = newCtx;
	}   //  setCtx

	/**
	 *  Validate Fields and create LookupInfo if required
	 */
	protected void initFinish()
	{
		//  Not null fields
		if (DisplayLogic == null)
			DisplayLogic = "";
		if (DefaultValue == null)
			DefaultValue = "";
		if (FieldGroup == null)
			FieldGroup = "";
		if (FieldGroupType == null)
			FieldGroupType = "";
		if (Description == null)
			Description = "";
		if (Help == null)
			Help = "";
		if (Callout == null)
			Callout = "";
		if (ReadOnlyLogic == null)
			ReadOnlyLogic = "";
		if (MandatoryLogic == null)
			MandatoryLogic = "";
		if (Placeholder == null)
			Placeholder = "";


		//  Create Lookup, if not ID
		if (DisplayType.isLookup(displayType) && IsDisplayed)
		{
			loadLookupInfo();
		}
	}   //  initFinish

	/**
	 * load lookup info.
	 * used by findwindow to loadlookupinfo for invisible field
	 */
	public void loadLookupInfo() {
		try
		{
			lookupInfo = MLookupFactory.getLookupInfo (ctx, WindowNo, TabNo, AD_Column_ID, displayType,
				Env.getLanguage(ctx), ColumnName, AD_Reference_Value_ID,
				IsParent, ValidationCode);
			if (lookupInfo == null)
				displayType = DisplayType.ID;
		}
		catch (Exception e)     //  Cannot create Lookup
		{
			CLogger.get().log(Level.SEVERE, "No LookupInfo for " + ColumnName, e);
			displayType = DisplayType.ID;
		}
	}

	/**
	 * 	Clone Field.
	 *	@param Ctx ctx
	 *	@param windowNo window no
	 *	@param tabNo tab no
	 *	@param ad_Window_ID window id
	 *	@param ad_Tab_ID tab id
	 *	@param TabReadOnly r/o
	 *	@return Field or null
	 */
	public GridFieldVO clone(Properties Ctx, int windowNo, int tabNo, 
			int ad_Window_ID, int ad_Tab_ID, 
			boolean TabReadOnly)
		{
			GridFieldVO clone = new GridFieldVO(Ctx, windowNo, tabNo, 
				ad_Window_ID, ad_Tab_ID, TabReadOnly);
			//
			clone.isProcess = false;
			//  Database Fields
			clone.ColumnName = ColumnName;
			clone.ColumnSQL = ColumnSQL;
			clone.Header = Header;
			clone.displayType = displayType;
			clone.AD_Table_ID = AD_Table_ID;
			clone.AD_Column_ID = AD_Column_ID;
			clone.DisplayLength = DisplayLength;
			clone.IsSameLine = IsSameLine;
			clone.IsDisplayed = IsDisplayed;
			clone.IsDisplayedGrid = IsDisplayedGrid;
			clone.AD_Field_ID = AD_Field_ID;
			clone.SeqNo = SeqNo;
			clone.SeqNoGrid = SeqNoGrid;
			clone.DisplayLogic = DisplayLogic;
			clone.DefaultValue = DefaultValue;
			clone.IsMandatory = IsMandatory;
			clone.IsReadOnly = IsReadOnly;
			clone.AD_Chart_ID = AD_Chart_ID;
			clone.IsUpdateable = IsUpdateable;
			clone.IsAlwaysUpdateable = IsAlwaysUpdateable;
			clone.IsHeading = IsHeading;
			clone.IsFieldOnly = IsFieldOnly;
			clone.IsEncryptedField = IsEncryptedField;
			clone.IsEncryptedColumn = IsEncryptedColumn;
			clone.IsSelectionColumn = IsSelectionColumn;
			clone.SeqNoSelection = SeqNoSelection;
			clone.IsAutocomplete = IsAutocomplete;
			clone.IsAllowCopy = IsAllowCopy;
			clone.SortNo = SortNo;
			clone.FieldLength = FieldLength;
			clone.VFormat = VFormat;
			clone.FormatPattern = FormatPattern;
			clone.ValueMin = ValueMin;
			clone.ValueMax = ValueMax;
			clone.FieldGroup = FieldGroup;
			clone.FieldGroupType = FieldGroupType;
			clone.IsKey = IsKey;
			clone.IsSetContext = IsSetContext;
			clone.IsParent = IsParent;
			clone.Callout = Callout;
			clone.AD_Process_ID = AD_Process_ID;
			clone.Description = Description;
			clone.Help = Help;
			clone.ReadOnlyLogic = ReadOnlyLogic;
			clone.MandatoryLogic = MandatoryLogic;
			clone.ObscureType = ObscureType;
			clone.IsDefaultFocus = IsDefaultFocus;
			clone.PA_DashboardContent_ID = PA_DashboardContent_ID;
			clone.Placeholder = Placeholder;
			clone.IsHtml = IsHtml;
			clone.IsQuickForm = IsQuickForm;
			clone.ZO_Window_ID = ZO_Window_ID;
			
			//	Lookup
			clone.ValidationCode = ValidationCode;
			clone.AD_Reference_Value_ID = AD_Reference_Value_ID;
			clone.lookupInfo = lookupInfo;

			//  Process Parameter
			clone.isRange = isRange;
			clone.DefaultValue2 = DefaultValue2;
			clone.Placeholder2 = Placeholder2;
			clone.AD_Process_ID_Of_Panel = AD_Process_ID_Of_Panel;
			clone.AD_Window_ID_Of_Panel = AD_Window_ID_Of_Panel;
			clone.AD_Infowindow_ID = AD_Infowindow_ID;
			return clone;
		}	//	clone
	
	
	@Override
	public GridFieldVO clone() {
		try {
			GridFieldVO clone = (GridFieldVO) super.clone();
			clone.ctx = Env.getCtx();
			if ( lookupInfo != null) {
				clone.lookupInfo = lookupInfo.clone();
				clone.lookupInfo.ctx = clone.ctx;
			}
			return clone;
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}		
	}


	/**
	 * 	String Representation
	 *	@return info
	 */
	public String toString ()
	{
		StringBuilder sb = new StringBuilder ("MFieldVO[");
		sb.append(AD_Column_ID).append("-").append(ColumnName)
			.append ("]");
		return sb.toString ();
	}	//	toString
	
	
	public static class SeqNoComparator implements Comparator<GridFieldVO> {
		@Override
		public int compare(GridFieldVO gf1, GridFieldVO gf2) {
			return (Integer.valueOf(gf1.SeqNo)).compareTo(Integer.valueOf(gf2.SeqNo));
		}
	}

}   //  GridFieldVO
