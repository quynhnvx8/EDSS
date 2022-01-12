package eone.base.model;

import java.awt.Color;
import java.util.Enumeration;

import javax.swing.Icon;
import javax.swing.tree.DefaultMutableTreeNode;

import org.compiere.util.Env;

public class MTreeNode extends DefaultMutableTreeNode
{
	private static final long serialVersionUID = -6871590404494812487L;

	public MTreeNode (int node_ID, String name, String description,
		int parent_ID, boolean isSummary, String imageIndicator, Color color)
	{
		super();
		m_node_ID = node_ID;
		m_name = name;
		m_description = description;
		if (m_description == null)
			m_description = "";
		m_parent_ID = parent_ID;
		setSummary(isSummary);
		setImageIndicator(imageIndicator);
		m_color = color;
	}   //  MTreeNode
	
	public MTreeNode (int node_ID, String name, String description,
			int parent_ID, boolean isSummary, String imageIndicator, boolean isByRole, boolean isReadOnly)
		{
			super();
			m_node_ID = node_ID;
			m_name = name;
			m_description = description;
			if (m_description == null)
				m_description = "";
			m_parent_ID = parent_ID;
			setSummary(isSummary);
			setImageIndicator(imageIndicator);
			m_ByRole = isByRole;
			m_ReadOnly = isReadOnly;
		}

	/** Node ID         */
	private int     	m_node_ID;
	/** Name			*/
	private String  	m_name;
	/** Description		*/
	private String  	m_description;
	/**	Parent ID		*/
	private int     	m_parent_ID;
	/**	Summaty			*/
	private boolean 	m_isSummary;
	/** Image Indicator				*/
	private String      m_imageIndicator;
	/** Index to Icon               */
	private int 		m_imageIndex = 0;
	/**	On Bar			*/
	private Color 		m_color;
	
	private boolean 	m_ByRole;
	private boolean 	m_ReadOnly;

	
	/*************************************************************************/

	/**	Window - 1			*/
	public static int		TYPE_WINDOW = 1;
	/**	Report - 2			*/
	public static int		TYPE_REPORT = 2;
	/**	Process - 3			*/
	public static int		TYPE_PROCESS = 3;
	/**	Workflow - 4		*/
	public static int		TYPE_WORKFLOW = 4;
	/**	Workbench - 5		*/
	public static int		TYPE_WORKBENCH = 5;
	/**	Variable - 6		*/
	public static int		TYPE_SETVARIABLE = 6;
	/**	Choice - 7			*/
	public static int		TYPE_USERCHOICE = 7;
	/**	Action - 8			*/
	public static int		TYPE_DOCACTION = 8;
	/** Info - 9            */
	public static int		TYPE_INFO = 9;

	public static String[] 	PATHS = new String[]
	{
		null,
		"mWindow.png",
		"mReport.png",
		"mProcess.png",
		"mWorkFlow.png",
		"mWorkbench.png",
		"mSetVariable.png",
		"mUserChoice.png",
		"mDocAction.png"
	};

	/** 16* 16 Icons		*/
	public static Icon[] 	IMAGES = new Icon[]
	{
		null,
		Env.getImageIcon("mWindow.png"),
		Env.getImageIcon("mReport.png"),
		Env.getImageIcon("mProcess.png"),
		Env.getImageIcon("mWorkFlow.png"),
		Env.getImageIcon("mWorkbench.png"),
		Env.getImageIcon("mSetVariable.png"),
		Env.getImageIcon("mUserChoice.png"),
		Env.getImageIcon("mDocAction.png")
	};


	/**************************************************************************
	 *  Get Node ID
	 *  @return node id (e.g. AD_Menu_ID)
	 */
	public int getNode_ID()
	{
		return m_node_ID;
	}   //  getID

	/**
	 *  Set Name
	 *  @param name name
	 */
	public void setName (String name)
	{
		if (name == null)
			m_name = "";
		else
			m_name = name;
	}   //  setName

	/**
	 *  Get Name
	 *  @return name
	 */
	public String getName()
	{
		return m_name;
	}   //  setName

	
	public int getParent_ID()
	{
		return m_parent_ID;
	}	//	getParent

	/**
	 *  Print Name
	 *  @return info
	 */
	public String toString()
	{
		return //   m_node_ID + "/" + m_parent_ID + " " + m_seqNo + " - " +
			m_name;
	}   //  toString

	/**
	 *	Get Description
	 *  @return description
	 */
	public String getDescription()
	{
		return m_description;
	}	//	getDescription

	/**
	 *  Set Description
	 *  @param name name
	 */
	public void setDescription (String description)
	{
		if (description == null)
			m_description = "";
		else
			m_description = description;
	}   //  setDescription

	/**************************************************************************
	 *  Set Summary (allow children)
	 *  @param isSummary summary node
	 */
	public void setSummary (boolean isSummary)
	{
		m_isSummary = isSummary;
		super.setAllowsChildren(isSummary);
	}   //  setSummary

	/**
	 *  Set Summary (allow children)
	 *  @param isSummary true if summary
	 */
	public void setAllowsChildren (boolean isSummary)
	{
		super.setAllowsChildren (isSummary);
		m_isSummary = isSummary;
	}   //  setAllowsChildren

	/**
	 *  Allow children to be added to this node
	 *  @return true if summary node
	 */
	public boolean isSummary()
	{
		return m_isSummary;
	}   //  isSummary


	/**************************************************************************
	 *  Get Image Indicator/Index
	 *  @param imageIndicator image indicator (W/X/R/P/F/T/B) MWFNode.ACTION_
	 *  @return index of image
	 */
	public static int getImageIndex (String imageIndicator)
	{
		int imageIndex = 0;
		if (imageIndicator == null)
			;
		
		return imageIndex;
	}   //  getImageIndex

	/**
	 *  Set Image Indicator and Index
	 *  @param imageIndicator image indicator (W/X/R/P/F/T/B) MWFNode.ACTION_
	 */
	public void setImageIndicator (String imageIndicator)
	{
		if (imageIndicator != null)
		{
			m_imageIndicator = imageIndicator;
			m_imageIndex = getImageIndex(m_imageIndicator);
		}
	}   //  setImageIndicator

	/**
	 *  Get Image Indicator
	 *  @return image indicator
	 */
	public String getImageIndiactor()
	{
		return m_imageIndicator;
	}   //  getImageIndiactor

	/**
	 *  Get Image Path
	 *  @return image path
	 */
	public String getImagePath()
	{
		if (m_imageIndex == 0 || PATHS == null || m_imageIndex > PATHS.length)
			return "/images/Folder16.png";
		return "/images/" + PATHS[m_imageIndex];
	}   //  getImageIndiactor

	/**
	 *	Get Image Icon
	 *  @param index image index
	 *  @return Icon
	 */
	public static Icon getIcon (int index)
	{
		if (index == 0 || IMAGES == null || index > IMAGES.length)
			return null;
		return IMAGES[index];
	}	//	getIcon

	/**
	 *	Get Image Icon
	 *  @return Icon
	 */
	public Icon getIcon()
	{
		return getIcon(m_imageIndex);
	}	//	getIcon

	
	public boolean isByRole() {
		return m_ByRole;
	}
	
	public boolean isReadOnly() {
		return m_ReadOnly;
	}
	/**
	 * 	Is Process
	 *	@return true if Process
	 */
	public boolean isProcess()
	{
		return X_AD_Menu.ACTION_Process.equals(m_imageIndicator);
	}	//	isProcess

	/**
	 * 	Is Report
	 *	@return true if report
	 */
	public boolean isReport()
	{
		return X_AD_Menu.ACTION_Report.equals(m_imageIndicator);
	}	//	isReport
	
	/**
	 * 	Is Window
	 *	@return true if Window
	 */
	public boolean isWindow()
	{
		return X_AD_Menu.ACTION_Window.equals(m_imageIndicator);
	}	//	isWindow
	
	/**
	 * 	Is Workbench
	 *	@return true if Workbench
	 */
	public boolean isWorkbench()
	{
		return X_AD_Menu.ACTION_Workbench.equals(m_imageIndicator);
	}	//	isWorkbench
	
	/**
	 * 	Is Workflow
	 *	@return true if Workflow
	 */
	public boolean isWorkFlow()
	{
		return X_AD_Menu.ACTION_WorkFlow.equals(m_imageIndicator);
	}	//	isWorkFlow

	/**
	 * 	Is Form
	 *	@return true if Form
	 */
	public boolean isForm()
	{
		return X_AD_Menu.ACTION_Form.equals(m_imageIndicator);
	}	//	isForm

	/**
	 * 	Is Task
	 *	@return true if Task
	 */
	public boolean isTask()
	{
		return X_AD_Menu.ACTION_Task.equals(m_imageIndicator);
	}	//	isTask

	/**
	 * 	Is Info
	 *	@return true if Info
	 */
	public boolean isInfo()
	{
		return X_AD_Menu.ACTION_Info.equals(m_imageIndicator);
	}	//	isTask
	
	/**
	 * 	Get Color
	 *	@return color or black if not set
	 */
	public Color getColor()
	{
		if (m_color != null)
			return m_color;
		return Color.black;
	}	//	getColor
	
	/*************************************************************************/

	/**	Last found ID				*/
	private int                 m_lastID = -1;
	/** Last found Node				*/
	private MTreeNode           m_lastNode = null;

	/**
	 *	Return the Node with ID in list of children
	 *  @param ID id
	 *  @return VTreeNode with ID or null
	 */
	public MTreeNode findNode (int ID)
	{
		if (m_node_ID == ID)
			return this;
		//
		if (ID == m_lastID && m_lastNode != null)
			return m_lastNode;
		//
		Enumeration<?> en = preorderEnumeration();
		while (en.hasMoreElements())
		{
			MTreeNode nd = (MTreeNode)en.nextElement();
			if (ID == nd.getNode_ID())
			{
				m_lastID = ID;
				m_lastNode = nd;
				return nd;
			}
		}
		return null;
	}   //  findNode

}   //  MTreeNode
