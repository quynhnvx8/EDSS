package eone.webui.window;

import java.util.List;

import eone.base.model.PrintInfo;
import eone.report.jasper.JRViewerProvider;
import eone.webui.apps.AEnv;
import eone.webui.component.Window;
import eone.webui.part.WindowContainer;
import eone.webui.session.SessionManager;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;

public class ZkJRViewerProvider implements JRViewerProvider {

	public void openViewer(final JasperPrint jasperPrint, final String title, final PrintInfo printInfo)
			throws JRException {
		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				Window viewer = new ZkJRViewer(jasperPrint, title, printInfo);

				viewer.setAttribute(Window.MODE_KEY, Window.MODE_EMBEDDED);
				viewer.setAttribute(Window.INSERT_POSITION_KEY, Window.INSERT_NEXT);
				viewer.setAttribute(WindowContainer.DEFER_SET_SELECTED_TAB, Boolean.TRUE);
				SessionManager.getAppDesktop().showWindow(viewer);
			}
		};
		AEnv.executeAsyncDesktopTask(runnable);
	}

	public void openViewer(final List<JasperPrint> jasperPrintList, final String title , final PrintInfo printInfo)
			throws JRException {
		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				Window viewer = new ZkJRViewer(jasperPrintList, title, printInfo);

				viewer.setAttribute(Window.MODE_KEY, Window.MODE_EMBEDDED);
				viewer.setAttribute(Window.INSERT_POSITION_KEY, Window.INSERT_NEXT);
				viewer.setAttribute(WindowContainer.DEFER_SET_SELECTED_TAB, Boolean.TRUE);
				SessionManager.getAppDesktop().showWindow(viewer);
			}
		};
		AEnv.executeAsyncDesktopTask(runnable);
	}

}
