
package eone.report.jasper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.util.PropertyResourceBundle;
import java.util.logging.Level;

import eone.base.IServiceReferenceHolder;
import eone.base.Service;
import eone.base.model.MAttachment;
import eone.base.model.MAttachmentEntry;
import eone.base.model.MProcess;
import eone.base.model.MSysConfig;
import eone.base.model.PrintInfo;
import eone.base.process.ClientProcess;
import eone.base.process.ProcessCall;
import eone.base.process.ProcessInfo;
import eone.base.process.ProcessInfoParameter;
import eone.exceptions.EONEException;
import eone.util.CLogger;
import eone.util.DB;
import eone.util.Env;
import eone.util.IProcessUI;
import eone.util.Ini;
import eone.util.Language;
import eone.util.Msg;
import eone.util.Trx;
import eone.util.Util;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JRPropertiesUtil;
import net.sf.jasperreports.engine.JRVariable;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperReportsContext;
import net.sf.jasperreports.engine.SimpleJasperReportsContext;
import net.sf.jasperreports.engine.fill.JRBaseFiller;
import net.sf.jasperreports.engine.fill.JRFiller;
import net.sf.jasperreports.engine.fill.JRSwapFileVirtualizer;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.util.JRSwapFile;

/**
 * Quynhnv.x8. Mod 25/09/2020
 */
public class ReportStarter implements ProcessCall, ClientProcess {
	private static final int DEFAULT_SWAP_MAX_PAGES = 100;
	/** Logger */
	private static final CLogger log = CLogger.getCLogger(ReportStarter.class);
	private static File REPORT_HOME = null;
	public static final JasperReportsContext jasperReportStartContext;

	static {
		String reportPath = System.getProperty("org.compiere.report.path");
		if (reportPath == null) {
			REPORT_HOME = new File(Ini.getEOneHome() + File.separator + "reports");
		} else {
			REPORT_HOME = new File(reportPath);
		}

		jasperReportStartContext = new SimpleJasperReportsContext();

		jasperReportStartContext.setProperty("org.eclipse.jdt.core.compiler.source", "1.5");

		JRPropertiesUtil.getInstance(jasperReportStartContext).setProperty("net.sf.jasperreports.awt.ignore.missing.font", "true");
		JRPropertiesUtil.getInstance(jasperReportStartContext).setProperty("net.sf.jasperreports.default.pdf.font.name", "Times New Roman");
		JRPropertiesUtil.getInstance(jasperReportStartContext).setProperty("net.sf.jasperreports.default.pdf.encoding", "UTF-8");
		JRPropertiesUtil.getInstance(jasperReportStartContext).setProperty("net.sf.jasperreports.default.pdf.embedded", "true");
		
	}

	private ProcessInfo processInfo;
	private MAttachment attachment;
	

	private File getRemoteFile(String reportLocation, String localPath) {
		try {
			URL reportURL = new URL(reportLocation);
			InputStream in = reportURL.openStream();

			File downloadedFile = new File(localPath);

			if (downloadedFile.exists()) {
				downloadedFile.delete();
			}

			FileOutputStream fout = new FileOutputStream(downloadedFile);

			byte buf[] = new byte[1024];
			int s = 0;
			while ((s = in.read(buf, 0, 1024)) > 0)
				fout.write(buf, 0, s);

			in.close();
			fout.flush();
			fout.close();
			return downloadedFile;
		} catch (FileNotFoundException e) {
			return null;
		} catch (IOException e) {
			throw new EONEException(
					"I/O error when trying to download (sub)report from server " + e.getLocalizedMessage());
		}
	}

	private File[] getHttpSubreports(String reportName, String reportPath, String fileExtension) {
		ArrayList<File> subreports = new ArrayList<File>();
		String remoteDir = reportPath.substring(0, reportPath.lastIndexOf("/"));

		for (int i = 1; i < 10; i++) {
			File subreport = httpDownloadedReport(remoteDir + "/" + reportName + i + fileExtension);
			if (subreport == null) // Subreport doesn't exist, abort further approaches
				break;

			subreports.add(subreport);
		}

		File[] subreportsTemp = new File[0];
		subreportsTemp = subreports.toArray(subreportsTemp);
		return subreportsTemp;
	}

	/**
	 * @author rlemeill
	 * @param reportLocation http string url ex:
	 *                       http://adempiereserver.domain.com/webApp/standalone.jrxml
	 * @return downloaded File (or already existing one)
	 */
	private File httpDownloadedReport(String reportLocation) {
		File reportFile = null;
		File downloadedFile = null;
		if (log.isLoggable(Level.INFO))
			log.info(" report deployed to " + reportLocation);
		try {

			String[] tmps = reportLocation.split("/");
			String cleanFile = tmps[tmps.length - 1];
			String localFile = System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") + cleanFile;
			String downloadedLocalFile = System.getProperty("java.io.tmpdir") + System.getProperty("file.separator")
					+ "TMP" + cleanFile;

			reportFile = new File(localFile);
			if (reportFile.exists()) {
				String remoteMD5Hash = getRemoteMD5(reportLocation);
				if (!Util.isEmpty(remoteMD5Hash, true)) {
					String localMD5hash = DigestOfFile.getMD5Hash(reportFile);
					if (log.isLoggable(Level.INFO))
						log.info("MD5 for local file is " + localMD5hash);
					if (localMD5hash.equals(remoteMD5Hash.trim())) {
						if (log.isLoggable(Level.INFO))
							log.info("MD5 match: local report file is up-to-date");
						return reportFile;
					} else {
						if (log.isLoggable(Level.INFO))
							log.info("MD5 is different, download and replace");
						downloadedFile = getRemoteFile(reportLocation, downloadedLocalFile);
						if (downloadedFile != null) {
							Path to = reportFile.toPath();
							Path from = downloadedFile.toPath();
							Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
							return to.toFile();
						} else {
							return null;
						}
					}
				} else {
					downloadedFile = getRemoteFile(reportLocation, downloadedLocalFile);
					if (downloadedFile == null)
						return null;

					// compare hash of existing and downloaded
					if (DigestOfFile.md5HashCompare(reportFile, downloadedFile)) {
						// nothing file are identical
						if (log.isLoggable(Level.INFO))
							log.info("MD5 match: local report file is up-to-date");
						return reportFile;
					} else {
						if (log.isLoggable(Level.INFO))
							log.info("MD5 is different, replace with downloaded file");
						Path to = reportFile.toPath();
						Path from = downloadedFile.toPath();
						Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
						return to.toFile();
					}
				}
			} else {
				reportFile = getRemoteFile(reportLocation, localFile);
				return reportFile;
			}

		} catch (Exception e) {
			throw new EONEException("Unknown exception: " + e.getLocalizedMessage());
		}
	}

	private String getRemoteMD5(String reportLocation) {
		try {
			String md5url = reportLocation + ".md5";
			URL reportURL = new URL(md5url);
			try (InputStream in = reportURL.openStream()) {
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				byte buf[] = new byte[1024];
				int s = 0;
				while ((s = in.read(buf, 0, 1024)) > 0)
					baos.write(buf, 0, s);

				String hash = new String(baos.toByteArray());
				int posSpace = hash.indexOf(" ");
				if (posSpace > 0)
					hash = hash.substring(0, posSpace);
				return hash;
			}
		} catch (IOException e) {
			if (log.isLoggable(Level.INFO))
				log.log(Level.INFO, "MD5 not available for " + reportLocation, e);
			return null;
		}
	}

	protected Connection getConnection() {
		return DB.getReportingConnectionRO();
	}

	public boolean startProcess(Properties ctx, ProcessInfo pi, Trx trx, HashMap<String, Object> params) {
		ClassLoader cl1 = Thread.currentThread().getContextClassLoader();
		ClassLoader cl2 = JasperReport.class.getClassLoader();
		try {
			if (!cl1.equals(cl2)) {
				Thread.currentThread().setContextClassLoader(cl2);
			}
			return startProcess0(ctx, pi, trx, params);
		} finally {
			if (!cl1.equals(Thread.currentThread().getContextClassLoader())) {
				Thread.currentThread().setContextClassLoader(cl1);
			}
		}
	}

	private boolean startProcess0(Properties ctx, ProcessInfo pi, Trx trx, HashMap<String, Object> params) {
		processInfo = pi;
		int nrows = 0;
		Object onrows = null;
		String Name = pi.getTitle();
		String AD_Session_ID = pi.getAD_Session_ID();
		int Record_ID = pi.getRecord_ID();

		if (log.isLoggable(Level.INFO))
			log.info("Name=" + Name + " Record_ID=" + Record_ID);

		String reportPath = pi.getJasperReport();
		
		if (Util.isEmpty(reportPath, true)) {
			pi.setSummary("Can not find report", true);
			return false;
		}
		if (reportPath.startsWith("@#LocalHttpAddr@")) {
			String localaddr = Env.getContext(Env.getCtx(), "#LocalHttpAddr");
			if (!Util.isEmpty(localaddr)) {
				reportPath = reportPath.replace("@#LocalHttpAddr@", localaddr);
			}
		}

		JasperData data = null;
		File reportFile = null;
		String fileExtension = "";

		reportFile = getReportFile(reportPath, (String) params.get("ReportType"));
		if (reportFile == null || reportFile.exists() == false) {
			log.severe("No report file found for given type, falling back to " + reportPath);
			reportFile = getReportFile(reportPath);
		}

		if (reportFile == null || reportFile.exists() == false) {
			String tmp = "Can not find report file at path - " + reportPath;
			log.severe(tmp);
			pi.setSummary(tmp, true);
		}

		if (reportFile != null) {
			data = processReport(reportFile);
			fileExtension = reportFile.getName().substring(reportFile.getName().lastIndexOf("."),
					reportFile.getName().length());
		} else {
			return false;
		}

		JasperReport jasperReport = data.getJasperReport();
		String jasperName = data.getJasperName();
		String name = jasperReport.getName();
		File reportDir = data.getReportDir();

		String resourcePath = reportDir.getAbsolutePath();
		if (!resourcePath.endsWith("/") && !resourcePath.endsWith("\\"))
			;
		{
			resourcePath = resourcePath + File.separator;
		}
		params.put("SUBREPORT_DIR", resourcePath);
		if (reportPath.startsWith("http://") || reportPath.startsWith("https://")) {
			int i = reportPath.lastIndexOf("/");
			String httpPath = reportPath.substring(0, i + 1);
			params.put("RESOURCE_DIR", httpPath);
		} else {
			params.put("RESOURCE_DIR", resourcePath);
		}

		if (jasperReport != null) {
			File[] subreports;

			// Subreports
			if (reportPath.startsWith("http://") || reportPath.startsWith("https://")) {
				// Locate and download subreports from remote webcontext
				subreports = getHttpSubreports(jasperName + "Subreport", reportPath, fileExtension);
			} else if (reportPath.startsWith("attachment:")) {
				subreports = getAttachmentSubreports(reportPath);
			} else if (reportPath.startsWith("resource:")) {
				String path = reportPath.substring(0, reportPath.length() + 1 - (name + "." + fileExtension).length());
				subreports = getResourceSubreports(name + "Subreport", path, fileExtension);
			} else {
				// Locate subreports from local/remote filesystem
				subreports = reportDir.listFiles(new FileFilter(jasperName + "Subreport", reportDir, fileExtension));
			}

			for (int i = 0; i < subreports.length; i++) {
				// @Trifon - begin
				if (subreports[i].getName().toLowerCase().endsWith(".jasper")
						|| subreports[i].getName().toLowerCase().endsWith(".jrxml")) {
					JasperData subData = processReport(subreports[i]);
					if (subData.getJasperReport() != null) {
						params.put(subData.getJasperName(), subData.getJasperFile().getAbsolutePath());
					}
				} // @Trifon - end
			}

			if (Record_ID > 0)
				params.put("RECORD_ID", Integer.valueOf(Record_ID));

			params.put("AD_SESSION_ID", AD_Session_ID);

			params.put("BASE_DIR", REPORT_HOME.getAbsolutePath());
			// params.put("HeaderLogo", reportPath);
			// params.put("LOGO", reportPath);

			Language currLang = Env.getLanguage(Env.getCtx());
			PrintInfo printInfo = null;
			ProcessInfoParameter[] pip = pi.getParameter();
			// Get print format and print info parameters
			if (pip != null) {
				for (int i = 0; i < pip.length; i++) {

					if ("PRINT_INFO".equalsIgnoreCase(pip[i].getParameterName())) {
						printInfo = (PrintInfo) pip[i].getParameter();
					}

				}
			}

			params.put("CURRENT_LANG", currLang.getAD_Language());
			params.put(JRParameter.REPORT_LOCALE, currLang.getLocale());

			// Resources
			File resFile = null;
			String bundleName = jasperReport.getResourceBundle();
			if (bundleName == null) {
				// If bundle name is not set, use the same name as the report file (legacy
				// behaviour)
				bundleName = jasperName;
			}
			if (reportPath.startsWith("attachment:") && attachment != null) {
				resFile = getAttachmentResourceFile(bundleName, currLang);
			} else if (reportPath.startsWith("resource:")) {
				resFile = getResourceResourceFile("resource:" + bundleName, currLang);
			} else if (reportPath.startsWith("http://") || reportPath.startsWith("https://")) {
				resFile = getHttpResourceFile(reportPath, bundleName, currLang);
			} else {
				resFile = getFileResourceFile(resourcePath, bundleName, currLang);
			}
			if (resFile != null) {
				try {
					PropertyResourceBundle res = new PropertyResourceBundle(new FileInputStream(resFile));
					params.put("RESOURCE", res);
					params.put(JRParameter.REPORT_RESOURCE_BUNDLE, res);
				} catch (IOException e) {
					;
				}
			}

			Connection conn = null;
			JRSwapFileVirtualizer virtualizer = null;
			int maxPages = MSysConfig.getIntValue(MSysConfig.JASPER_SWAP_MAX_PAGES, DEFAULT_SWAP_MAX_PAGES);
			try {
				if (trx != null)
					conn = trx.getConnection();
				else
					conn = getConnection();

				String swapPath = System.getProperty("java.io.tmpdir");
				JRSwapFile swapFile = new JRSwapFile(swapPath, 1024, 1024);
				virtualizer = new JRSwapFileVirtualizer(maxPages, swapFile, true);
				params.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);

				JRBaseFiller filler = JRFiller.createFiller(jasperReportStartContext, jasperReport);
				JasperPrint jasperPrint = filler.fill(params, conn);
				// params.clear();
				onrows = filler.getVariableValue(JRVariable.REPORT_COUNT);

				if (printInfo == null)
					printInfo = new PrintInfo(pi);
				
				if (log.isLoggable(Level.INFO))
					log.info("ReportStarter.startProcess run report -" + jasperPrint.getName());
				JRViewerProvider viewerLauncher = getViewerProvider();// Service.locator().locate(JRViewerProvider.class).getService();
				if (!Util.isEmpty(processInfo.getReportType())) {
					jasperPrint.setProperty("IDEMPIERE_REPORT_TYPE", processInfo.getReportType());
				}
				viewerLauncher.openViewer(jasperPrint, pi.getTitle(), printInfo);
				
			} catch (JRException e) {
				throw new EONEException(e.getLocalizedMessage()
						+ (e.getCause() != null ? " -> " + e.getCause().getLocalizedMessage() : ""));
			} finally {
				if (conn != null) {
					try {
						conn.close();
					} catch (SQLException e) {
					}
				}
			}
		}

		if (onrows != null && onrows instanceof Integer) {
			nrows = (Integer) onrows;
			processInfo.setRowCount(nrows);
		}
		pi.setSummary(Msg.getMsg(Env.getCtx(), "Success"), false);
		return true;
	}

	private File getAttachmentResourceFile(String bundleName, Language currLang) {
		String resname = bundleName + "_" + currLang.getLocale().getLanguage() + "_" + currLang.getLocale().getCountry()
				+ ".properties";
		File resFile = getAttachmentEntryFile(resname);
		if (resFile == null) {
			resname = bundleName + "_" + currLang.getLocale().getLanguage() + ".properties";
			resFile = getAttachmentEntryFile(resname);
			if (resFile == null) {
				resname = bundleName + ".properties";
				resFile = getAttachmentEntryFile(resname);
			}
		}
		return resFile;
	}

	private File getAttachmentEntryFile(String resname) {
		File fileattach = null;
		MAttachmentEntry[] entries = attachment.getEntries();
		for (int i = 0; i < entries.length; i++) {
			if (entries[i].getName().equals(resname)) {
				fileattach = getAttachmentEntryFile(entries[i]);
				break;
			}
		}
		return fileattach;
	}

	/**
	 * Get .property resource file from resources
	 * 
	 * @param bundleName
	 * @param currLang
	 * @return File
	 */
	private File getResourceResourceFile(String bundleName, Language currLang) {
		String resname = bundleName + "_" + currLang.getLocale().getLanguage() + "_" + currLang.getLocale().getCountry()
				+ ".properties";
		File resFile = null;
		try {
			resFile = getFileAsResource(resname);
		} catch (Exception e) {
			// ignore exception - file couldn't exist
		}
		if (resFile == null) {
			resname = bundleName + "_" + currLang.getLocale().getLanguage() + ".properties";
			try {
				resFile = getFileAsResource(resname);
			} catch (Exception e) {
				// ignore exception - file couldn't exist
			}
			if (resFile == null) {
				resname = bundleName + ".properties";
				try {
					resFile = getFileAsResource(resname);
				} catch (Exception e) {
					// ignore exception - file couldn't exist
				}
			}
		}
		return resFile;
	}

	/**
	 * Get .property resource file from http URL
	 * 
	 * @param reportPath
	 * @param bundleName
	 * @param currLang
	 * @return File
	 */
	private File getHttpResourceFile(String reportPath, String bundleName, Language currLang) {
		String remoteDir = reportPath.substring(0, reportPath.lastIndexOf("/"));
		String resname = bundleName + "_" + currLang.getLocale().getLanguage() + "_" + currLang.getLocale().getCountry()
				+ ".properties";
		File resFile = httpDownloadedReport(remoteDir + "/" + resname);
		if (resFile == null) {
			resname = bundleName + "_" + currLang.getLocale().getLanguage() + ".properties";
			resFile = httpDownloadedReport(remoteDir + "/" + resname);
			if (resFile == null) {
				resname = bundleName + ".properties";
				resFile = httpDownloadedReport(remoteDir + "/" + resname);
			}
		}
		return resFile;
	}

	/**
	 * Get .property resource file from file://
	 * 
	 * @param resourcePath
	 * @param bundleName
	 * @param currLang
	 * @return File
	 */
	private File getFileResourceFile(String resourcePath, String bundleName, Language currLang) {
		String resname = bundleName + "_" + currLang.getLocale().getLanguage() + "_" + currLang.getLocale().getCountry()
				+ ".properties";
		File resFile = new File(resourcePath, resname);
		if (!resFile.exists()) {
			resname = bundleName + "_" + currLang.getLocale().getLanguage() + ".properties";
			resFile = new File(resourcePath, resname);
			if (!resFile.exists()) {
				resname = bundleName + ".properties";
				resFile = new File(resourcePath, resname);
				if (!resFile.exists()) {
					resFile = null;
				}
			}
		}
		return resFile;
	}

	/**
	 * Get subreports from attachment. Assume all other jasper attachment is
	 * subreport.
	 * 
	 * @param reportPath
	 * @return File[]
	 */
	private File[] getAttachmentSubreports(String reportPath) {
		String name = reportPath.substring("attachment:".length()).trim();
		ArrayList<File> subreports = new ArrayList<File>();
		MAttachmentEntry[] entries = attachment.getEntries();
		for (int i = 0; i < entries.length; i++) {
			// @Trifon
			if (!entries[i].getName().equals(name)) {
				File reportFile = getAttachmentEntryFile(entries[i]);
				if (reportFile != null) {
					if (entries[i].getName().toLowerCase().endsWith(".jrxml")
							|| entries[i].getName().toLowerCase().endsWith(".jasper")) {
						subreports.add(reportFile);
					}
				}
			}
		}
		File[] subreportsTemp = new File[0];
		subreportsTemp = subreports.toArray(subreportsTemp);
		return subreportsTemp;
	}

	/**
	 * Search for additional subreports deployed as resources
	 * 
	 * @param reportName    The original report name
	 * @param reportPath    The full path to the parent report
	 * @param fileExtension The file extension of the parent report
	 * @return An Array of File objects referencing to the downloaded subreports
	 */
	private File[] getResourceSubreports(String reportName, String reportPath, String fileExtension) {
		ArrayList<File> subreports = new ArrayList<File>();
		// Currently check hardcoded for max. 10 subreports
		for (int i = 1; i < 10; i++) {
			// Check if subreport number i exists
			File subreport = null;
			try {
				subreport = getFileAsResource(reportPath + reportName + i + fileExtension);
			} catch (Exception e) {
				// just ignore it
			}
			if (subreport == null) // Subreport doesn't exist, abort further approaches
				break;

			subreports.add(subreport);
		}

		File[] subreportsTemp = new File[subreports.size()];
		subreportsTemp = subreports.toArray(subreportsTemp);
		return subreportsTemp;
	}

	/**
	 * @author alinv
	 * @param reportPath
	 * @param reportType
	 * @return the abstract file corresponding to typed report
	 */
	protected File getReportFile(String reportPath, String reportType) {

		if (reportType != null) {
			int cpos = reportPath.lastIndexOf('.');
			reportPath = reportPath.substring(0, cpos) + "_" + reportType
					+ reportPath.substring(cpos, reportPath.length());
		}

		return getReportFile(reportPath);
	}

	/**
	 * @author alinv
	 * @param reportPath
	 * @return the abstract file corresponding to report
	 */
	protected File getReportFile(String reportPath) {
		File reportFile = null;

		// Reports deployment on web server Thanks to Alin Vaida
		if (reportPath.startsWith("http://") || reportPath.startsWith("https://")) {
			reportFile = httpDownloadedReport(reportPath);
		} else if (reportPath.startsWith("attachment:")) {
			// report file from process attachment
			reportFile = downloadAttachment(reportPath);
		} else if (reportPath.startsWith("/")) {
			reportFile = new File(reportPath);
		} else if (reportPath.startsWith("file:/")) {
			try {
				reportFile = new File(new URI(reportPath));
			} catch (URISyntaxException e) {
				log.warning(e.getLocalizedMessage());
				reportFile = null;
			}
		} else if (reportPath.startsWith("resource:")) {
			try {
				reportFile = getFileAsResource(reportPath);
			} catch (Exception e) {
				log.warning(e.getLocalizedMessage());
				reportFile = null;
			}
		} else {
			reportFile = new File(REPORT_HOME, reportPath);
		}

		return reportFile;
	}

	/**
	 * @param reportPath
	 * @return
	 * @throws Exception
	 */
	private File getFileAsResource(String reportPath) throws Exception {
		File reportFile;
		String name = reportPath.substring("resource:".length()).trim();
		String localName = name.replace('/', '_');
		if (log.isLoggable(Level.INFO)) {
			log.info("reportPath = " + reportPath);
			log.info("getting resource from = " + getClass().getClassLoader().getResource(name));
		}
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(name);
		String localFile = System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") + localName;
		if (log.isLoggable(Level.INFO))
			log.info("localFile = " + localFile);
		reportFile = new File(localFile);

		boolean empty = true;
		OutputStream out = null;
		try {
			out = new FileOutputStream(reportFile);
			if (out != null) {
				byte buf[] = new byte[1024];
				int len;
				while ((len = inputStream.read(buf)) > 0) {
					empty = false;
					out.write(buf, 0, len);
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (out != null)
				out.close();
			if (inputStream != null)
				inputStream.close();
		}

		if (empty)
			return null;
		else
			return reportFile;
	}

	/**
	 * Download db attachment
	 * 
	 * @param reportPath must of syntax attachment:filename
	 * @return File
	 */
	private File downloadAttachment(String reportPath) {
		File reportFile = null;
		String name = reportPath.substring("attachment:".length()).trim();
		MProcess process = new MProcess(Env.getCtx(), processInfo.getAD_Process_ID(), processInfo.getTransactionName());
		attachment = process.getAttachment();
		if (attachment != null) {
			MAttachmentEntry[] entries = attachment.getEntries();
			MAttachmentEntry entry = null;
			for (int i = 0; i < entries.length; i++) {
				if (entries[i].getName().equals(name)) {
					entry = entries[i];
					break;
				}
			}
			if (entry != null) {
				reportFile = getAttachmentEntryFile(entry);
			}
		}
		return reportFile;
	}

	/**
	 * Download db attachment to local file
	 * 
	 * @param entry
	 * @return File
	 */
	private File getAttachmentEntryFile(MAttachmentEntry entry) {
		String localFile = System.getProperty("java.io.tmpdir") + System.getProperty("file.separator")
				+ entry.getName();
		String downloadedLocalFile = System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") + "TMP"
				+ entry.getName();
		File reportFile = new File(localFile);
		if (reportFile.exists()) {
			String localMD5hash = DigestOfFile.getMD5Hash(reportFile);
			String entryMD5hash = DigestOfFile.getMD5Hash(entry.getData());
			if (localMD5hash.equals(entryMD5hash)) {
				log.info(" no need to download: local report is up-to-date");
			} else {
				log.info(" report on server is different that local one, download and replace");
				File downloadedFile = new File(downloadedLocalFile);
				entry.getFile(downloadedFile);
				if (!reportFile.delete()) {
					throw new EONEException("Cannot delete temporary file " + reportFile.toString());
				}
				if (!downloadedFile.renameTo(reportFile)) {
					throw new EONEException("Cannot rename temporary file " + downloadedFile.toString() + " to "
							+ reportFile.toString());
				}
			}
		} else {
			entry.getFile(reportFile);
		}
		return reportFile;
	}

	protected JasperData processReport(File reportFile) {
		if (log.isLoggable(Level.INFO))
			log.info("reportFile.getAbsolutePath() = " + reportFile.getAbsolutePath());
		JasperReport jasperReport = null;

		String jasperName = reportFile.getName();
		int pos = jasperName.indexOf('.');
		if (pos != -1)
			jasperName = jasperName.substring(0, pos);
		File reportDir = reportFile.getParentFile();

		// test if the compiled report exists
		File jasperFile = new File(reportDir.getAbsolutePath(), jasperName + ".jasper");
		if (jasperFile.exists()) { // test time
			if (reportFile.lastModified() == jasperFile.lastModified()) {
				if (log.isLoggable(Level.INFO))
					log.info(" no need to compile use " + jasperFile.getAbsolutePath());
				try {
					jasperReport = (JasperReport) JRLoader.loadObjectFromFile(jasperFile.getAbsolutePath());
				} catch (JRException e) {
					jasperReport = null;
					log.severe("Can not load report - " + e.getMessage());
				}
			} else {
				jasperReport = compileReport(reportFile, jasperFile);
			}
		} else { // create new jasper file
			jasperReport = compileReport(reportFile, jasperFile);
		}

		return new JasperData(jasperReport, reportDir, jasperName, jasperFile);
	}

	/**
	 * @author rlemeill
	 * @param reportFile
	 * @param jasperFile
	 * @return compiled JasperReport
	 */
	private JasperReport compileReport(File reportFile, File jasperFile) {
		JasperReport compiledJasperReport = null;
		try {
			JasperCompileManager manager = JasperCompileManager.getInstance(jasperReportStartContext);
			manager.compileToFile(reportFile.getAbsolutePath(), jasperFile.getAbsolutePath());
			jasperFile.setLastModified(reportFile.lastModified()); // Synchronize Dates
			compiledJasperReport = (JasperReport) JRLoader.loadObject(jasperFile);
		} catch (JRException e) {
			throw new EONEException(e);
		}
		return compiledJasperReport;
	}

	public static class JasperData implements Serializable {
		/**
		 *
		 */
		private static final long serialVersionUID = 4375195020654531202L;
		private JasperReport jasperReport;
		private File reportDir;
		private String jasperName;
		private File jasperFile;

		public JasperData(JasperReport jasperReport, File reportDir, String jasperName, File jasperFile) {
			this.jasperReport = jasperReport;
			this.reportDir = reportDir;
			this.jasperName = jasperName;
			this.jasperFile = jasperFile;
		}

		public JasperReport getJasperReport() {
			return jasperReport;
		}

		public File getReportDir() {
			return reportDir;
		}

		public String getJasperName() {
			return jasperName;
		}

		public File getJasperFile() {
			return jasperFile;
		}
	}

	static class FileFilter implements FilenameFilter {
		private String reportStart;
		private File directory;
		private String extension;

		public FileFilter(String reportStart, File directory, String extension) {
			this.reportStart = reportStart;
			this.directory = directory;
			this.extension = extension;
		}

		public boolean accept(File file, String name) {
			if (file.equals(directory)) {
				if (name.startsWith(reportStart)) {
					int pos = name.lastIndexOf(extension);
					if ((pos != -1) && (pos == (name.length() - extension.length())))
						return true;
				}
			}
			return false;
		}
	}

	@Override
	public void setProcessUI(IProcessUI processUI) {
		//
	}

	private static IServiceReferenceHolder<JRViewerProvider> s_viewerProviderReference = null;

	public static synchronized JRViewerProvider getViewerProvider() {
		JRViewerProvider viewerLauncher = null;
		if (s_viewerProviderReference != null) {
			viewerLauncher = s_viewerProviderReference.getService();
			if (viewerLauncher != null)
				return viewerLauncher;
		}
		IServiceReferenceHolder<JRViewerProvider> viewerReference = Service.locator().locate(JRViewerProvider.class).getServiceReference();
		if (viewerReference != null) {
			viewerLauncher = viewerReference.getService();
			s_viewerProviderReference = viewerReference;
		}
		return viewerLauncher;
	}

}