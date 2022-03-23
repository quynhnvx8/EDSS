
package eone.webui.panel;

import static eone.base.model.SystemIDs.REFERENCE_IMPORT_MODE;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.zkoss.util.media.AMedia;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Space;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.Vlayout;

import eone.base.IGridTabImporter;
import eone.base.equinox.EquinoxExtensionLocator;
import eone.base.model.GridTab;
import eone.base.model.MLookup;
import eone.base.model.MLookupFactory;
import eone.base.model.MLookupInfo;
import eone.exceptions.EONEException;
import eone.util.Env;
import eone.util.Ini;
import eone.util.Msg;
import eone.util.Util;
import eone.webui.EONEWebUI;
import eone.webui.LayoutUtils;
import eone.webui.adwindow.AbstractADWindowContent;
import eone.webui.adwindow.IADTabbox;
import eone.webui.adwindow.IADTabpanel;
import eone.webui.component.Button;
import eone.webui.component.Column;
import eone.webui.component.Columns;
import eone.webui.component.ConfirmPanel;
import eone.webui.component.Grid;
import eone.webui.component.GridFactory;
import eone.webui.component.Label;
import eone.webui.component.ListItem;
import eone.webui.component.Listbox;
import eone.webui.component.Row;
import eone.webui.component.Rows;
import eone.webui.component.Window;
import eone.webui.editor.WTableDirEditor;
import eone.webui.event.DialogEvents;
import eone.webui.util.ReaderInputStream;
import eone.webui.util.ZKUpdateUtil;
import eone.webui.window.FDialog;

/**
 *
 * @author Quynhnv.x8 Modify 12/03/2022
 *
 */
public class ImportAction implements EventListener<Event>
{
	private AbstractADWindowContent panel;

	private Map<String, IGridTabImporter> importerMap = null;
	private Map<String, String> extensionMap = null;

	private Window winImportFile = null;
	private ConfirmPanel confirmPanel = new ConfirmPanel(true);
	private Listbox cboType = new Listbox();
	private Button bFile = new Button();
	private Listbox fCharset = new Listbox();
	private WTableDirEditor fImportMode;
	private InputStream m_file_istream = null;
	
	/**
	 * @param panel
	 */
	public ImportAction(AbstractADWindowContent panel)
	{
		this.panel = panel;
	}

	/**
	 * execute import action
	 */
	public void fileImport()
	{
		// charset
		Charset[] charsets = Ini.getAvailableCharsets();
		for (int i = 0; i < charsets.length; i++)
			fCharset.appendItem(charsets[i].displayName(), charsets[i]);
		Charset charset = Ini.getCharset();
		for (int i = 0; i < fCharset.getItemCount(); i++)
		{
			ListItem listitem = fCharset.getItemAtIndex(i);
			Charset compare = (Charset)listitem.getValue();
			
			if (charset == compare)
			{
				fCharset.setSelectedIndex(i);
				Executions.getCurrent().getDesktop().getWebApp().getConfiguration().setUploadCharset(compare.name());
				break;
			}
		}
		fCharset.addEventListener(Events.ON_SELECT, this);
		fCharset.setDisabled(true);

		MLookupInfo lookupInfo = MLookupFactory.getLookup_List(Env.getLanguage(Env.getCtx()), REFERENCE_IMPORT_MODE);
		MLookup lookup = new MLookup(lookupInfo, 0);
		fImportMode = new WTableDirEditor("ImportMode",true,true,true,lookup);
		fImportMode.setValue("M");
		
		importerMap = new HashMap<String, IGridTabImporter>();
		extensionMap = new HashMap<String, String>();
		List<IGridTabImporter> importerList = EquinoxExtensionLocator.instance().list(IGridTabImporter.class).getExtensions();
		for(IGridTabImporter importer : importerList)
		{
			String extension = importer.getFileExtension();
			if (!extensionMap.containsKey(extension))
			{
				extensionMap.put(extension, importer.getFileExtensionLabel());
				importerMap.put(extension, importer);
			}
		}

		if (winImportFile == null)
		{
			winImportFile = new Window();
			winImportFile.setTitle(Msg.getMsg(Env.getCtx(), "FileImport") + ": " + panel.getActiveGridTab().getName());
			ZKUpdateUtil.setWindowWidthX(winImportFile, 450);
			winImportFile.setClosable(true);
			winImportFile.setBorder("normal");
			winImportFile.setStyle("position:absolute");
			winImportFile.setWidgetAttribute(EONEWebUI.WIDGET_INSTANCE_NAME, "importAction");
			winImportFile.setSclass("popup-dialog");

			cboType.setMold("select");

			cboType.getItems().clear();
			for(Map.Entry<String, String> entry : extensionMap.entrySet())
			{
				cboType.appendItem(entry.getKey() + " - " + entry.getValue(), entry.getKey());
			}

			cboType.setSelectedIndex(0);
			cboType.setDisabled(true);

			Vbox vb = new Vbox();
			ZKUpdateUtil.setWidth(vb, "100%");
			winImportFile.appendChild(vb);

			Vlayout vlayout = new Vlayout();
			vlayout.setSclass("dialog-content");
			vb.appendChild(vlayout);
			
			Grid grid = GridFactory.newGridLayout();
			vlayout.appendChild(grid);
	        
	        Columns columns = new Columns();
	        Column column = new Column();
	        ZKUpdateUtil.setHflex(column, "min");
	        columns.appendChild(column);
	        column = new Column();
	        ZKUpdateUtil.setHflex(column, "1");
	        columns.appendChild(column);
	        grid.appendChild(columns);
	        
			Rows rows = new Rows();
			grid.appendChild(rows);
			
			Row row = new Row();
			rows.appendChild(row);
			row.appendChild(new Label(Msg.getMsg(Env.getCtx(), "FilesOfType", true)));
			row.appendChild(cboType);
			ZKUpdateUtil.setHflex(cboType, "1");
			
			row = new Row();
			rows.appendChild(row);
			row.appendChild(new Label(Msg.getMsg(Env.getCtx(), "Charset", true) + ": "));
			fCharset.setMold("select");
			fCharset.setRows(0);
			fCharset.setTooltiptext(Msg.getMsg(Env.getCtx(), "Charset", false));
			row.appendChild(fCharset);
			ZKUpdateUtil.setHflex(fCharset, "1");

			row = new Row();
			rows.appendChild(row);
			row.appendChild(new Label(Msg.getMsg(Env.getCtx(), "import.mode", true)));
			row.appendChild(fImportMode.getComponent());
			
			row = new Row();
			rows.appendChild(row);
			row.appendChild(new Space());
			bFile.setLabel(Msg.getMsg(Env.getCtx(), "FileImportFile"));
			bFile.setTooltiptext(Msg.getMsg(Env.getCtx(), "FileImportFileInfo"));
			bFile.setUpload(EONEWebUI.getUploadSetting());
			LayoutUtils.addSclass("txt-btn", bFile);
			bFile.addEventListener(Events.ON_UPLOAD, this);
			row.appendChild(bFile);

			LayoutUtils.addSclass("dialog-footer", confirmPanel);
			vb.appendChild(confirmPanel);
			confirmPanel.addActionListener(this);
		}
		
		panel.getComponent().getParent().appendChild(winImportFile);
		panel.showBusyMask(winImportFile);
		LayoutUtils.openOverlappedWindow(panel.getComponent(), winImportFile, "middle_center");
		winImportFile.addEventListener(DialogEvents.ON_WINDOW_CLOSE, this);
	}

	@Override
	public void onEvent(Event event) throws Exception {
		if (event instanceof UploadEvent) 
		{
			UploadEvent ue = (UploadEvent) event;
			processUploadMedia(ue.getMedia());
		} else if (event.getTarget().getId().equals(ConfirmPanel.A_CANCEL)) {
			winImportFile.onClose();
		} else if (event.getTarget() == fCharset) {
			if (m_file_istream != null) {
				m_file_istream.close();
				m_file_istream = null;
			}
			ListItem listitem = fCharset.getSelectedItem();
			if (listitem == null)
				return;
			Charset charset = (Charset)listitem.getValue();
			Executions.getCurrent().getDesktop().getWebApp().getConfiguration().setUploadCharset(charset.name());
			bFile.setLabel(Msg.getMsg(Env.getCtx(), "FileImportFile"));
		} else if (event.getTarget().getId().equals(ConfirmPanel.A_OK)) {
			if (m_file_istream == null || fCharset.getSelectedItem() == null || Util.isEmpty((String)fImportMode.getValue()))
				return;
			importFile();
		} else if (event.getName().equals(DialogEvents.ON_WINDOW_CLOSE)) {
			panel.hideBusyMask();
		}
	}

	private void processUploadMedia(Media media) {
		if (media == null)
			return;

		if (media.isBinary()) {
			m_file_istream = media.getStreamData();
		}
		else {
			ListItem listitem = fCharset.getSelectedItem();
			if (listitem == null) {
				m_file_istream = new ReaderInputStream(media.getReaderData());
			} else {
				Charset charset = (Charset)listitem.getValue();
				m_file_istream = new ReaderInputStream(media.getReaderData(), charset.name());
			}
		}
		
		bFile.setLabel(media.getName());
	}
	
	private void importFile() {
		try {
			ListItem li = cboType.getSelectedItem();
			if(li == null || li.getValue() == null)
			{
				FDialog.error(0, winImportFile, "FileInvalidExtension");
				return;
			}

			String ext = li.getValue().toString();
			IGridTabImporter importer = importerMap.get(ext);
			if (importer == null)
			{
				FDialog.error(0, winImportFile, "FileInvalidExtension");
				return;
			}

			IADTabbox adTab = panel.getADTab();
			int selected = adTab.getSelectedIndex();
			int tabLevel = panel.getActiveGridTab().getTabLevel();
			Set<String> tables = new HashSet<String>();
			List<GridTab> childs = new ArrayList<GridTab>();
			List<GridTab> includedList = panel.getActiveGridTab().getIncludedTabs();
			for(GridTab included : includedList)
			{
				String tableName = included.getTableName();
				if (tables.contains(tableName))
					continue;
				tables.add(tableName);
				childs.add(included);
			}
			for(int i = selected+1; i < adTab.getTabCount(); i++)
			{
				IADTabpanel adTabPanel = adTab.getADTabpanel(i);
				if (adTabPanel.getGridTab().getTabLevel() <= tabLevel)
					break;
				String tableName = adTabPanel.getGridTab().getTableName();
				if (tables.contains(tableName))
					continue;
				tables.add(tableName);
				childs.add(adTabPanel.getGridTab());
			}

			ListItem listitem = fCharset.getSelectedItem();
			Charset charset = null;
			if (listitem == null)
				return;
			charset = (Charset)listitem.getValue();
			
			String iMode = (String) fImportMode.getValue();
			File outFile = importer.fileImport(panel.getActiveGridTab(), childs, m_file_istream, charset,iMode);
			winImportFile.onClose();
			winImportFile = null;

			AMedia media = null;
			media = new AMedia(importer.getSuggestedFileName(panel.getActiveGridTab()), null, importer.getContentType(), outFile, charset.name());
			Filedownload.save(media);

		} catch (Exception e) {
			throw new EONEException(e);
		} finally {
			if (winImportFile != null)
				winImportFile.onClose();
		}
	}
}
