
package eone.webui.window;

import java.awt.Color;
import java.awt.Font;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Level;

import javax.servlet.http.HttpServletRequest;

import org.apache.ecs.XhtmlDocument;
import org.apache.ecs.xhtml.a;
import org.apache.ecs.xhtml.script;
import org.apache.ecs.xhtml.table;
import org.apache.ecs.xhtml.tbody;
import org.apache.ecs.xhtml.td;
import org.apache.ecs.xhtml.th;
import org.apache.ecs.xhtml.tr;
import org.zkoss.util.media.AMedia;
import org.zkoss.util.media.Media;
import org.zkoss.zk.au.out.AuScript;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.KeyEvent;
import org.zkoss.zk.ui.ext.render.DynamicMedia;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zss.ui.Spreadsheet;
import org.zkoss.zul.A;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Center;
import org.zkoss.zul.Div;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Iframe;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.North;
import org.zkoss.zul.Popup;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Toolbar;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.Vlayout;
import org.zkoss.zul.impl.Utils;
import org.zkoss.zul.impl.XulElement;

import eone.EOne;
import eone.base.impexp.PrintDataXLSXExporter;
import eone.base.model.MLanguage;
import eone.base.model.MQuery;
import eone.base.process.ProcessInfo;
import eone.exceptions.EONEException;
import eone.print.IHTMLExtension;
import eone.print.MPrintFormat;
import eone.print.MPrintFormatItem;
import eone.print.PrintDataItem;
import eone.print.ReportEngine;
import eone.print.ReportEngine.ColumnInfo;
import eone.util.CLogger;
import eone.util.ContextRunnable;
import eone.util.Env;
import eone.util.Ini;
import eone.util.Msg;
import eone.util.Util;
import eone.webui.ClientInfo;
import eone.webui.LayoutUtils;
import eone.webui.acct.WAcctViewer;
import eone.webui.apps.AEnv;
import eone.webui.apps.BusyDialog;
import eone.webui.apps.DesktopRunnable;
import eone.webui.component.ConfirmPanel;
import eone.webui.component.Label;
import eone.webui.component.ListItem;
import eone.webui.component.Listbox;
import eone.webui.component.Mask;
import eone.webui.component.Tabpanel;
import eone.webui.component.ToolBarButton;
import eone.webui.component.Window;
import eone.webui.desktop.IDesktop;
import eone.webui.event.ZoomEvent;
import eone.webui.panel.ITabOnCloseHandler;
import eone.webui.report.HTMLExtension;
import eone.webui.session.SessionManager;
import eone.webui.theme.ThemeManager;
import eone.webui.util.IServerPushCallback;
import eone.webui.util.ServerPushTemplate;
import eone.webui.util.ZKUpdateUtil;

/**
  * 
 * Quynhnv.x8. Mod 25/09/2020
 */
public class ZkReportViewer extends Window implements EventListener<Event>, ITabOnCloseHandler {
	/**
	 * 
	 */
	private static final long serialVersionUID = -424164233048709765L;

	/** Window No					*/
	private int                 m_WindowNo = -1;
	private long prevKeyEventTime = 0;
	private KeyEvent prevKeyEvent;
	/**	Print Context				*/
	private Properties			m_ctx;
	/**	Setting Values				*/
	private boolean				m_setting = false;
	/**	Report Engine				*/
	private ReportEngine 		m_reportEngine;
	
	private static HashMap<String, Object> m_params;
	/** Table ID					*/
	private boolean				m_isCanExport;
	
	private MQuery 		m_ddQ = null;
	private MQuery 		m_daQ = null;
	private Menuitem 	m_ddM = null;
	private Menuitem 	m_daM = null;

	/**	Logger			*/
	private static final CLogger log = CLogger.getCLogger(ZkReportViewer.class);

	//
	private Toolbar toolBar = new Toolbar();
	private ToolBarButton bExport = new ToolBarButton();
	//private WTableDirEditor wLanguage;
	private Listbox previewType = new Listbox();
	
	private Iframe iframe;
	
	private Window winExportFile = null;
	private ConfirmPanel confirmPanel = new ConfirmPanel(true);
	private Listbox cboType = new Listbox();
	

	private AMedia media;
	private int mediaVersion = 0;

	private A reportLink;

	private boolean init;
	
	private BusyDialog progressWindow;
	private Mask mask;

	private Future<?> future;
	
	private final static String ON_RENDER_REPORT_EVENT = "onRenderReport";
	
	private Popup toolbarPopup;
	
	
	/**
	 * 	Static Layout
	 * 	@throws Exception
	 */
	public ZkReportViewer(ReportEngine re, String title) {		
		super();
		
		init = false;
		m_WindowNo = SessionManager.getAppDesktop().registerWindow(this);
		setAttribute(IDesktop.WINDOWNO_ATTRIBUTE, m_WindowNo);
		Env.setContext(re.getCtx(), m_WindowNo, "_WinInfo_IsReportViewer", "Y");
		m_reportEngine = re;
		m_params = re.getParams();
		String titleReport = m_params.get("ReportName").toString();
		m_isCanExport = true;
		//String export = Env.getContext(m_ctx, "#IsCanExport");
		//m_isCanExport =  "Y".equals(Env.getContext(m_ctx, "#IsCanExport")) ? true : false;
		setTitle(Util.cleanAmp(Msg.getMsg(Env.getCtx(), "Report") + ": " + titleReport));
		Spreadsheet sheet = new Spreadsheet();
		sheet.setSrc(null);
		sheet.setShowToolbar(true);
		addEventListener(ON_RENDER_REPORT_EVENT, this);
	}

	@Override
	public void onPageAttached(Page newpage, Page oldpage) {
		super.onPageAttached(newpage, oldpage);
		if (newpage != null && !init) {
			try
			{
				m_ctx = m_reportEngine.getCtx();
				init();
				SessionManager.getSessionApplication().getKeylistener().addEventListener(Events.ON_CTRL_KEY, this);
			}
			catch(Exception e)
			{
				log.log(Level.SEVERE, "", e);
				FDialog.error(m_WindowNo, this, "LoadError", e.getLocalizedMessage());
				this.onClose();
			}
		}
	}

	@Override
	public void onPageDetached(Page page) {
		super.onPageDetached(page);
		try {
			SessionManager.getSessionApplication().getKeylistener().removeEventListener(Events.ON_CTRL_KEY, this);
		} catch (Exception e) {}
	}

	private void init() {
		Borderlayout layout = new Borderlayout();
		layout.setStyle("position: absolute; height: 97%; width: 98%; border:none; padding:none; margin:none;");
		this.appendChild(layout);
		this.setStyle("width: 100%; height: 100%; position: absolute; border:none; padding:none; margin:none;");

		ZKUpdateUtil.setHeight(toolBar, "32px");
		
		ZKUpdateUtil.setWidth(toolBar, "100%");
		
		previewType.setMold("select");
		previewType.appendItem("PDF", "PDF");
		previewType.appendItem("HTML", "HTML");
		
		
		toolBar.appendChild(previewType);		
		previewType.addEventListener(Events.ON_SELECT, this);
		
		toolBar.appendChild(new Separator("vertical"));
		
		int pTypeIndex = 0;
		
		if (m_reportEngine.getReportType() != null)
		{
			if (m_reportEngine.getReportType().equals("PDF"))
				pTypeIndex = 0;
			else if (m_reportEngine.getReportType().equals("HTML"))
				pTypeIndex = 1;
		}
		else
		{
    		//set default type
    		String type = "PDF";
    			
    		if ("HTML".equals(type)) {
    			pTypeIndex = 1;
    		} else if ("PDF".equals(type)) {
    			pTypeIndex = 0;
    		} 
		}
		
		previewType.setSelectedIndex(pTypeIndex);
		
		Vlayout toolbarPopupLayout = null;
		if (ClientInfo.maxWidth(ClientInfo.SMALL_WIDTH-1))
		{
			toolbarPopup = new Popup();
			appendChild(toolbarPopup);	
			toolbarPopupLayout = new Vlayout();
			toolbarPopup.appendChild(toolbarPopupLayout);
		}
		
		
		if (toolbarPopup == null)
			toolBar.appendChild(new Separator("vertical"));
		
		if (toolbarPopup == null)
			toolBar.appendChild(new Separator("vertical"));
		
		//m_isCanExport = true;
		if ( m_isCanExport )
		{
			bExport.setName("Export");
			if (ThemeManager.isUseFontIconForImage())
				bExport.setIconSclass("z-icon-Export");
			else
				bExport.setImage(ThemeManager.getThemeResource("images/Export24.png"));
			bExport.setTooltiptext(Util.cleanAmp(Msg.getMsg(Env.getCtx(), "Export")));
			if (toolbarPopup != null)
			{
				toolbarPopupLayout.appendChild(bExport);
				bExport.setLabel(bExport.getTooltiptext());
			}
			else
				toolBar.appendChild(bExport);
			bExport.addEventListener(Events.ON_CLICK, this);
			if (ThemeManager.isUseFontIconForImage())
				LayoutUtils.addSclass("medium-toolbarbutton", bExport);
		}
		
		if (toolbarPopup == null)
			toolBar.appendChild(new Separator("vertical"));
		
		reportLink = new A();
		reportLink.setTarget("_blank");
		Div linkDiv = new Div();
		linkDiv.setStyle("width:100%; height: 40px; padding-top: 4px; padding-bottom: 4px; text-align: right;");
		linkDiv.appendChild(reportLink);
		toolBar.appendChild(linkDiv);
		
		if (toolbarPopup != null)
		{
			ToolBarButton more = new ToolBarButton();
			if (ThemeManager.isUseFontIconForImage())
				more.setIconSclass("z-icon-Expand");
			else
				more.setImage(ThemeManager.getThemeResource("images/expand-header.png"));
			toolBar.appendChild(more);
			LayoutUtils.addSclass("space-between-content", toolBar);
			more.addEventListener(Events.ON_CLICK, evt -> {
				toolbarPopup.open(more, "before_end");
			});
		}
		
		North north = new North();
		layout.appendChild(north);
		north.appendChild(toolBar);
		ZKUpdateUtil.setVflex(north, "min");
		
		Center center = new Center();
		layout.appendChild(center);
		iframe = new Iframe();
		ZKUpdateUtil.setWidth(iframe, "100%");
		ZKUpdateUtil.setHeight(iframe, "100%");
		iframe.setId("reportFrame");
		center.appendChild(iframe);
		
		
		postRenderReportEvent();
		//iframe.setAutohide(true);

		this.setBorder("normal");
		
		this.addEventListener("onZoom", new EventListener<Event>() {
			
			public void onEvent(Event event) throws Exception {
				if (event instanceof ZoomEvent) {
					Clients.clearBusy();
					ZoomEvent ze = (ZoomEvent) event;
					if (ze.getData() != null && ze.getData() instanceof MQuery) {
						MQuery query = (MQuery) ze.getData();
						String tableName = query.getTableName();
						if (tableName != null && !tableName.isEmpty() && "Fact_Acct".equalsIgnoreCase(tableName)) {
							int windowNo = (int)m_params.get("WindowNo");
							String zoomQuery = query.getZoomLogic();
							String codeZoom = query.getZoomValue().toString();
							if (zoomQuery.toLowerCase().contains("@value%@")) {
								zoomQuery = zoomQuery.replaceAll("@value%@", "'" + codeZoom + "%'");
							}
							if (zoomQuery.toLowerCase().contains("@value@")) {
								zoomQuery = zoomQuery.replaceAll("@value@", "'" + codeZoom + "'");
							}
							zoomQuery = Env.parseContext(Env.getCtx(), windowNo,  zoomQuery, false);
							new WAcctViewer(zoomQuery);
						}else
							AEnv.zoom((MQuery) ze.getData());
						
					}
				}
				
			}
		});
		
		
		init = true;
	}

	

	private void renderReport() {
		media = null;
		Listitem selected = previewType.getSelectedItem();
		if (selected == null || "PDF".equals(selected.getValue())) {
			future = EOne.getThreadPoolExecutor().submit(new DesktopRunnable(new PDFRendererRunnable(this),getDesktop()));
		} else if ("HTML".equals(previewType.getSelectedItem().getValue())) {
			future = EOne.getThreadPoolExecutor().submit(new DesktopRunnable(new HTMLRendererRunnable(this),getDesktop()));
		}		
	}
	
	private void onPreviewReport() {
		try {
			if (future != null) {
				try {
					future.get();
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				} catch (ExecutionException e) {
					throw new RuntimeException(e.getCause());
				}
			}
			mediaVersion++;
			String url = Utils.getDynamicMediaURI(this, mediaVersion, media.getName(), media.getFormat());	
			String pdfJsUrl = "pdf.js/web/viewer.html?file="+url;
			HttpServletRequest request = (HttpServletRequest) Executions.getCurrent().getNativeRequest();
			if (url.startsWith(request.getContextPath() + "/"))
				url = url.substring((request.getContextPath() + "/").length());
			reportLink.setHref(url);
			reportLink.setLabel(media.getName());			
			
			if (ClientInfo.isMobile()) {
				Listitem selected = previewType.getSelectedItem();
				if (selected == null || "PDF".equals(selected.getValue())) {					
					iframe.setSrc(pdfJsUrl);
				} else if ("HTML".equals(previewType.getSelectedItem().getValue())) {
					iframe.setSrc(null);
					iframe.setContent(media);
				} else {
					iframe.setSrc(null);
					iframe.setContent(null);					
					String script = "zk.Widget.$('#" + reportLink.getUuid()+"').$n().click();";
					Clients.evalJavaScript(script);
				}
			} else {
				iframe.setSrc(null);
				iframe.setContent(media);
			}
			
		} finally {
			hideBusyDialog();
			future = null;
		}
	}

	private String makePrefix(String name) {
		StringBuilder prefix = new StringBuilder();
		char[] nameArray = name.toCharArray();
		for (char ch : nameArray) {
			if (Character.isLetterOrDigit(ch)) {
				prefix.append(ch);
			} else {
				prefix.append("_");
			}
		}
		return prefix.toString();
	}
	
	
	/**
	 * 	Dispose
	 */
	public void onClose()
	{
		cleanUp();
		super.onClose();
	}	//	dispose

	@Override
	public void onClose(Tabpanel tabPanel) {
		Tab tab = tabPanel.getLinkedTab();
		tab.close();
		cleanUp();
	}
	
	
	@Override
	public void setParent(Component parent) {
		super.setParent(parent);
		if (parent != null) {
			if (parent instanceof Tabpanel) {
				Tabpanel tabPanel = (Tabpanel) parent;
				tabPanel.setOnCloseHandler(this);
			}
		}
	}

	private void cleanUp() {
		if (m_reportEngine != null || m_WindowNo >= 0)
		{
			SessionManager.getAppDesktop().unregisterWindow(m_WindowNo);
			m_reportEngine = null;
			m_ctx = null;
			m_WindowNo = -1;
		}
		if (future != null && !future.isDone())
		{
			future.cancel(true);
			future = null;
		}
			
	}
	
	public void onEvent(Event event) throws Exception {
		
		if(event.getTarget().getId().equals(ConfirmPanel.A_CANCEL))
			winExportFile.onClose();
		else if(event.getTarget() == bExport)			
			exportFile();
		else if(event.getName().equals(Events.ON_CLICK) || event.getName().equals(Events.ON_SELECT)) 
			actionPerformed(event);
			
		else if (event.getName().equals(ON_RENDER_REPORT_EVENT))
		{
			onRenderReportEvent();
        } else if (event.getName().equals(Events.ON_CTRL_KEY)) {
        	KeyEvent keyEvent = (KeyEvent) event;
        	if (LayoutUtils.isReallyVisible(this)) {
	        	long time = System.currentTimeMillis();
	        	if (prevKeyEvent != null && prevKeyEventTime > 0 &&
	        			prevKeyEvent.getKeyCode() == keyEvent.getKeyCode() &&
	    				prevKeyEvent.getTarget() == keyEvent.getTarget() &&
	    				prevKeyEvent.isAltKey() == keyEvent.isAltKey() &&
	    				prevKeyEvent.isCtrlKey() == keyEvent.isCtrlKey() &&
	    				prevKeyEvent.isShiftKey() == keyEvent.isShiftKey()) {
	        		if ((time - prevKeyEventTime) <= 300) {
	        			return;
	        		}
	        	}
	        	this.onCtrlKeyEvent(keyEvent);
        	}
		}
	}

	private void onCtrlKeyEvent(KeyEvent keyEvent) {
		if (keyEvent.isAltKey() && keyEvent.getKeyCode() == 0x58) { // Alt-X
			if (m_WindowNo > 0) {
				prevKeyEventTime = System.currentTimeMillis();
				prevKeyEvent = keyEvent;
				keyEvent.stopPropagation();
				SessionManager.getAppDesktop().closeWindow(m_WindowNo);
			}
		}
	}

	private void onRenderReportEvent() {
    	renderReport();
	}

	/**************************************************************************
	 * 	Action Listener
	 * 	@param e event
	 */
	public void actionPerformed (Event e)
	{
		if (m_setting)
			return;
		if (e.getTarget() == bExport)
			cmd_export();
		else if (e.getTarget() == previewType)
			cmd_render();
		else if (e.getTarget() == m_ddM)
			cmd_window(m_ddQ);
		else if (e.getTarget() == m_daM)
			cmd_window(m_daQ);
	}	//	actionPerformed
	
	private void cmd_render() {
		postRenderReportEvent();		
	}

	
	private void cmd_window (MQuery query)
	{
		if (query == null)
			return;
		AEnv.zoom(query);
	}	//	cmd_window
	
	
	private void cmd_export()
	{		
		log.config("");
		if (!m_isCanExport)
		{
			FDialog.error(m_WindowNo, this, "AccessCannotExport", getTitle());
			return;
		}
		
		if(winExportFile == null)
		{
			winExportFile = new Window();
			winExportFile.setTitle(Msg.getMsg(Env.getCtx(), "Export") + ": " + getTitle());
			ZKUpdateUtil.setWindowWidthX(winExportFile, 450);
			ZKUpdateUtil.setWindowHeightX(winExportFile, 150);
			winExportFile.setClosable(true);
			winExportFile.setBorder("normal");
			winExportFile.setSclass("popup-dialog");
			winExportFile.setStyle("position:absolute");

			cboType.setMold("select");
			
			cboType.getItems().clear();			
			ListItem li = cboType.appendItem("XLSX", "XLSX");
			cboType.appendItem("HTML", "HTML");
			cboType.appendItem("PDF", "PDF");
			cboType.setSelectedItem(li);
			cboType.setSelectedIndex(0);
			
			Hbox hb = new Hbox();
			hb.setSclass("dialog-content");			
			hb.setAlign("center");
			hb.setPack("start");
			Div div = new Div();
			div.setStyle("text-align: right;");
			div.appendChild(new Label(Msg.getMsg(Env.getCtx(), "FilesOfType")));
			hb.appendChild(div);
			hb.appendChild(cboType);
			ZKUpdateUtil.setWidth(cboType, "100%");

			Vbox vb = new Vbox();
			ZKUpdateUtil.setWidth(vb, "100%");
			winExportFile.appendChild(vb);
			vb.appendChild(hb);
			vb.appendChild(confirmPanel);
			LayoutUtils.addSclass("dialog-footer", confirmPanel);
			confirmPanel.addActionListener(this);
		}
		
		winExportFile.setAttribute(Window.MODE_KEY, Window.MODE_HIGHLIGHTED);
		AEnv.showWindow(winExportFile);
	}	//	cmd_export
		
	private void exportFile()
	{
		try
		{

			File inputFile = null;
			inputFile = File.createTempFile("Export", ".xlsx");							
			//m_reportEngine.createXLSX(inputFile);
			PrintDataXLSXExporter exp = new PrintDataXLSXExporter(m_params, m_reportEngine.m_printFormat);
			exp.export(inputFile, true);
			
			AMedia media = new AMedia(m_reportEngine.getName(), "xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", inputFile, true);
			Filedownload.save(media, m_reportEngine.getName() + ".xlsx");
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, "Failed to export content.", e);
		}
	}
	
	

	protected void setLanguage (){
		MLanguage language = MLanguage.get(m_ctx, Env.getAD_Language(m_ctx));
		m_reportEngine.setLanguageID(language.getAD_Language_ID());
	}
	
	private void postRenderReportEvent() {
		showBusyDialog();
		setLanguage();
		Events.echoEvent(ON_RENDER_REPORT_EVENT, this, null);
	}



	//-- ComponentCtrl --//
	public Object getExtraCtrl() {
		return new ExtraCtrl();
	}
	/** A utility class to implement {@link #getExtraCtrl}.
	 * It is used only by component developers.
	 */
	protected class ExtraCtrl extends XulElement.ExtraCtrl
	implements DynamicMedia {
		//-- DynamicMedia --//
		public Media getMedia(String pathInfo) {
			return media;
		}
	}
	

	private void showBusyDialog() {		
		progressWindow = new BusyDialog();
		progressWindow.setStyle("position: absolute;");
		this.appendChild(progressWindow);
		showBusyMask(progressWindow);
		LayoutUtils.openOverlappedWindow(this, progressWindow, "middle_center");
	}
	
	private Div getMask() {
		if (mask == null) {
			mask = new Mask();
		}
		return mask;
	}
	
	private void showBusyMask(Window window) {
		getParent().appendChild(getMask());
		StringBuilder script = new StringBuilder("var w=zk.Widget.$('#");
		script.append(getParent().getUuid()).append("');");
		if (window != null) {
			script.append("var d=zk.Widget.$('#").append(window.getUuid()).append("');w.busy=d;");
		} else {
			script.append("w.busy=true;");
		}
		Clients.response(new AuScript(script.toString()));
	}
	
	public void hideBusyMask() {
		if (mask != null && mask.getParent() != null) {
			mask.detach();
			StringBuilder script = new StringBuilder("var w=zk.Widget.$('#");
			script.append(getParent().getUuid()).append("');w.busy=false;");
			Clients.response(new AuScript(script.toString()));
		}
	}
	
	private void hideBusyDialog() {
		hideBusyMask();
		if (progressWindow != null) {
			progressWindow.dispose();
			progressWindow = null;
		}
	}
	
	static class PDFRendererRunnable extends ContextRunnable implements IServerPushCallback {

		private ZkReportViewer viewer;

		public PDFRendererRunnable(ZkReportViewer viewer) {
			super();
			this.viewer = viewer;
		}

		@Override
		protected void doRun() {
			try {
				String path = System.getProperty("java.io.tmpdir");
				String prefix = viewer.makePrefix(viewer.m_reportEngine.getName());
				if (prefix.length() < 3)
					prefix += "_".repeat(3-prefix.length());
				if (log.isLoggable(Level.FINE)) log.log(Level.FINE, "Path="+path + " Prefix="+prefix);
				File file = File.createTempFile(prefix, ".pdf", new File(path));
				viewer.m_reportEngine.createPDF(file);
				viewer.media = new AMedia(file.getName(), "pdf", "application/pdf", file, true);
			} catch (Exception e) {
				if (e instanceof RuntimeException)
					throw (RuntimeException)e;
				else
					throw new RuntimeException(e);
			} finally {		
				Desktop desktop = AEnv.getDesktop();
				if (desktop != null && desktop.isAlive()) {
					new ServerPushTemplate(desktop).executeAsync(this);
				}
			}
		}

		@Override
		public void updateUI() {
			viewer.onPreviewReport();
		}
		
	}
	
	static class HTMLRendererRunnable extends ContextRunnable implements IServerPushCallback {

		private ZkReportViewer viewer;
		private String contextPath;
		private MPrintFormat m_printFormat;
		private int windowNo;
		public HTMLRendererRunnable(ZkReportViewer viewer) {
			super();
			this.viewer = viewer;
			contextPath = Executions.getCurrent().getContextPath();
		}
		
		@Override
		protected void doRun() {
			try {
				m_printFormat = viewer.m_reportEngine.m_printFormat;
				String path = System.getProperty("java.io.tmpdir");
				String prefix = viewer.makePrefix(viewer.m_reportEngine.getName());
				if (prefix.length() < 3)
					prefix += "_".repeat(3-prefix.length());
				if (log.isLoggable(Level.FINE)) log.log(Level.FINE, "Path="+path + " Prefix="+prefix);
				File file = File.createTempFile(prefix, ".html", new File(path));
				Writer fw = new OutputStreamWriter(new FileOutputStream(file, false), Ini.getCharset()); 
				createHTML (new BufferedWriter(fw), false, new HTMLExtension(contextPath, "rp", viewer.getUuid()), false);
				
				viewer.media = new AMedia(file.getName(), "html", "text/html", file, false);
			} catch (Exception e) {
				if (e instanceof RuntimeException)
					throw (RuntimeException)e;
				else
					throw new RuntimeException(e);
			} finally {
				Desktop desktop = AEnv.getDesktop();
				if (desktop != null && desktop.isAlive()) {
					new ServerPushTemplate(desktop).executeAsync(this);
				}
			}
		}

		@Override
		public void updateUI() {						
			
			viewer.onPreviewReport();
		}		
		
		
		
		public boolean createHTML (Writer writer, boolean onlyTable, IHTMLExtension extension, boolean isExport)
		{
			try
			{
				String cssPrefix = extension != null ? extension.getClassPrefix() : null;
				if (cssPrefix != null && cssPrefix.trim().length() == 0)
					cssPrefix = null;
				
				
				table table = new table();
				if (cssPrefix != null)
					table.setClass(cssPrefix + "-table");
				//
				//
				table.setNeedClosingTag(false);
				PrintWriter w = new PrintWriter(writer);
				XhtmlDocument doc = null;
						
				if (onlyTable)
					table.output(w);
				else
				{
					doc = new XhtmlDocument();
					doc.getHtml().setNeedClosingTag(false);
					doc.getBody().setNeedClosingTag(false);
					doc.appendHead("<meta charset=\"UTF-8\" />");
					
					if (extension != null && extension.getStyleURL() != null)
					{
						// maybe cache style content with key is path
						String pathStyleFile = extension.getFullPathStyle(); // creates a temp file - delete below
						Path path = Paths.get(pathStyleFile);
					    List<String> styleLines = Files.readAllLines(path, Ini.getCharset());
					    Files.delete(path); // delete temp file
					    StringBuilder styleBuild = new StringBuilder();
					    for (String styleLine : styleLines){
					    	styleBuild.append(styleLine); //.append("\n");
					    }
					    appendInlineCss (doc, styleBuild);
					}
					if (extension != null && extension.getScriptURL() != null && !isExport)
					{
						script jslink = new script();
						jslink.setLanguage("javascript");
						jslink.setSrc(extension.getScriptURL());
						doc.appendHead(jslink);
					}
					
					if (extension != null && !isExport){
						extension.setWebAttribute(doc.getBody());
					}				
				}
				
				if (doc != null)
				{
					mapCssInfo.clear();
					MPrintFormatItem item = null;
					int printColIndex = -1;
					for(int col = 0; col < m_printFormat.getItemCount(); col++)
					{
						item = m_printFormat.getItem(col);
						if(item.isPrinted())
						{
							printColIndex++;
							addCssInfo(item, printColIndex);
						}
					}
					appendInlineCss(doc);
					
					StringBuilder styleBuild = new StringBuilder();
										
					//MPrintFont printFont = MPrintFont.get(m_printFormat.getAD_PrintFont_ID());
					Font base = Font.decode("sansserif-PLAIN-10"); //printFont.getFont(); //sansserif-PLAIN-10
					Font newFont = new Font(base.getName(), Font.PLAIN, base.getSize()-1);
					CSSInfo cssInfo = new CSSInfo(newFont, null);
					styleBuild.append(".tr-level-1 td").append(cssInfo.getCssRule());
					
					newFont = new Font(base.getName(), Font.PLAIN, base.getSize()-2);
					cssInfo = new CSSInfo(newFont, null);
					styleBuild.append(".tr-level-2 td").append(cssInfo.getCssRule());
					
					styleBuild = new StringBuilder(styleBuild.toString().replaceAll(";", "!important;"));
					appendInlineCss (doc, styleBuild);
								
					doc.output(w);
					
					table.output(w);
				}
				
				tbody tbody = new tbody();
				tbody.setNeedClosingTag(false);
				
				ProcessInfo pi = (ProcessInfo)m_params.get("ProcessInfo");
				windowNo = (int)m_params.get("WindowNo");
				int countRow = pi.getRowCountQuery();
				List<ArrayList<PrintDataItem>> dataQuery = pi.getDataQueryC();
				int maxRow = pi.getMaxRow();
				//Add Header
				tr tr = new tr();
				MPrintFormatItem [] items = m_printFormat.getItemContent();
	            
				for(int r = 1; r <= maxRow; r++) {
		    		for(int c = 0; c < items.length; c++) {
		            	MPrintFormatItem item = items[c];
		            	int rowOrder = Integer.parseInt(item.getOrderRowHeader());
		            	if (rowOrder == r && item.isPrinted()) {
		            		th th = new th();
		    				tr.addElement(th);
		    				th.addElement(Util.maskHTML(item.getName(m_printFormat.getLanguage(), windowNo)));
		            		th.setColSpan(item.getColumnSpan());
		            		th.setRowSpan(item.getNumLines());		
		            		th.setWidth(item.getMaxWidth());
		            	}
		            }  
		    		tr.output(w);
		    		tr = new tr();
		    	}
					            
	            tr.output(w);
				
	           				
				for (int row = 0; row < countRow; row ++) {

					tr = new tr();
					
					ArrayList<PrintDataItem> arrItem = dataQuery.get(row);
	            	for(int c = 0; c < arrItem.size(); c++) {  
	            		PrintDataItem item = arrItem.get(c);

						td td = new td();
						if (item.getColSpan() > 1)
							td.setColSpan(item.getColSpan());
						td.setStyle("font-size: 9pt;");
						if (item.isNumeric()) {
							td.setStyle("font-size: 9pt; text-align: right;");
						}
						tr.addElement(td);
						Object value = item.getValueDisplay(Env.getLanguage(Env.getCtx()));
						if (value == null){
							td.addElement("&nbsp;");
						}else if(item.isZoomLogic()) {            			
							a href = new a("javascript:void(0)");									
							href.setID(item.getColumnName() + "_" + row + "_a");									
							td.addElement(href);
							href.addElement(Util.maskHTML(value.toString()));
							if (cssPrefix != null)
								href.setClass(cssPrefix + "-href");
							extension.extendIDColumn(row, td, href, item);
						
						}else {
							td.addElement(Util.maskHTML(value.toString()));
						}
					}	//	for all columns
					
					tr.output(w);					
				
				}
				
				w.println();
				w.println("</tbody>");
				w.println("</table>");
				if (!onlyTable)
				{
					w.println("</body>");
					w.println("</html>");
				}
				w.flush();
				w.close();
			}
			catch (Exception e)
			{
				log.log(Level.SEVERE, "(w)", e);
				throw new EONEException(e);
			}
			return true;
		}
		
		public void appendInlineCss (XhtmlDocument doc){
			StringBuilder buildCssInline = new StringBuilder();
			
			// each entry is a css class
			for (Entry<CSSInfo, List<ColumnInfo>> cssClassInfo : mapCssInfo.entrySet()){
				// each column is a css name.
				for (int i = 0; i < cssClassInfo.getValue().size(); i++){
					if (i > 0)
						buildCssInline.append (",");
					
					buildCssInline.append(cssClassInfo.getValue().get(i).getCssSelector());
				}
				
				buildCssInline.append(cssClassInfo.getKey().getCssRule());
				buildCssInline.append("\n");
			}
			
			appendInlineCss (doc, buildCssInline);
		}
		
		public void appendInlineCss (XhtmlDocument doc, StringBuilder buildCssInline){
			if (buildCssInline.length() > 0){
				buildCssInline.insert(0, "<style>");
				buildCssInline.append("</style>");
				doc.appendHead(buildCssInline.toString());
			}
		}
		
		private Map<CSSInfo, List<ColumnInfo>> mapCssInfo = new HashMap<CSSInfo, List<ColumnInfo>>();
		
		public void addCssInfo (MPrintFormatItem formatItem, int index){
			CSSInfo cadidateCss = new CSSInfo(formatItem);
			if (mapCssInfo.containsKey(cadidateCss)){
				mapCssInfo.get(cadidateCss).add(new ColumnInfo(index, formatItem));
			}else{
				List<ColumnInfo> newColumnList = new ArrayList<ColumnInfo>();
				newColumnList.add(new ColumnInfo(index, formatItem));
				mapCssInfo.put(cadidateCss, newColumnList);
			}
		}
		

		public class CSSInfo {
			private Font font;		
			private Color color;
			private String cssStr;
			public CSSInfo (MPrintFormatItem item){
				
			}
			
			public CSSInfo (Font font, Color color) {
				this.font = font;
				this.color = color;
			}
			
			@Override
			public int hashCode() {
				return (color == null ? 0 : color.hashCode()) + (font == null ? 0 : font.hashCode());
			}
			
			public String getCssRule (){
				if (cssStr != null)
					return cssStr;
				
				StringBuilder cssBuild = new StringBuilder();
				cssBuild.append ("{");
				
				if (font != null){
					
					String fontFamily = font.getFamily();
					fontFamily = getCSSFontFamily(fontFamily);
					if (fontFamily != null){
						addCssRule(cssBuild, "font-family", fontFamily);
					}
					
					if (font.isBold())
					{
						addCssRule(cssBuild, "font-weight", "bold");					
					}
					
					if (font.isItalic())
					{
						addCssRule(cssBuild, "font-style", "italic");
					}									
					
					int size = font.getSize();
					addCssRule(cssBuild, "font-size", size + "pt");
				}
				
				if (color != null)
				{
					cssBuild.append("color:rgb(");
					cssBuild.append(color.getRed()); 
					cssBuild.append(",");
					cssBuild.append(color.getGreen());
					cssBuild.append(",");
					cssBuild.append(color.getBlue());
					cssBuild.append(");");
				}
				cssBuild.append ("}");
				cssStr = cssBuild.toString();
				
				return cssStr;
			}
			
			protected void addCssRule(StringBuilder cssBuild, String ruleName, String ruleValue) {
				cssBuild.append (ruleName);
				cssBuild.append (":");
				cssBuild.append (ruleValue);
				cssBuild.append (";");
			}
			
			private String getCSSFontFamily(String fontFamily) {
				if ("Dialog".equals(fontFamily) || "DialogInput".equals(fontFamily) || 	"Monospaced".equals(fontFamily))
				{
					return "monospace";
				} else if ("SansSerif".equals(fontFamily))
				{
					return "sans-serif";
				} else if ("Serif".equals(fontFamily))
				{
					return "serif";
				}
				return null;
			}
		}
	}
	
	

	static class XLSXRendererRunnable extends ContextRunnable implements IServerPushCallback
	{

		private ZkReportViewer viewer;

		public XLSXRendererRunnable(ZkReportViewer viewer)
		{
			super();
			this.viewer = viewer;
		}

		@Override
		protected void doRun()
		{
			try
			{
				String path = System.getProperty("java.io.tmpdir");
				String prefix = viewer.makePrefix(viewer.m_reportEngine.getName());
				if (log.isLoggable(Level.FINE))
				{
					log.log(Level.FINE, "Path=" + path + " Prefix=" + prefix);
				}
				File file = File.createTempFile(prefix, ".xlsx", new File(path));
				PrintDataXLSXExporter exp = new PrintDataXLSXExporter(m_params, viewer.m_reportEngine.getPrintFormat());
				exp.export(file, true);
				viewer.media = new AMedia(file.getName(), "xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", file, true);
			}
			catch (Exception e)
			{
				if (e instanceof RuntimeException)
					throw (RuntimeException) e;
				else
					throw new RuntimeException(e);
			}
			finally
			{
				Desktop desktop = AEnv.getDesktop();
				if (desktop != null && desktop.isAlive())
				{
					new ServerPushTemplate(desktop).executeAsync(this);
				}
			}
		}

		@Override
		public void updateUI()
		{
			viewer.onPreviewReport();
		}

	}
	
}
