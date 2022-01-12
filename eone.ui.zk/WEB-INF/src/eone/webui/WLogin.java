/******************************************************************************
 * Copyright (C) 2008 Low Heng Sin                                            *
 * Copyright (C) 2009 Idalica Corporation                                     *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/

package eone.webui;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.metainfo.PageDefinition;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Center;
import org.zkoss.zul.Image;
import org.zkoss.zul.Window;

import eone.webui.part.AbstractUIPart;
import eone.webui.theme.ITheme;
import eone.webui.theme.ThemeManager;
import eone.webui.window.LoginWindow;

/**
 *
 * @author  <a href="mailto:agramdass@gmail.com">Ashley G Ramdass</a>
 * @author  Low Heng Sin
 * @date    Mar 3, 2007
 * @version $Revision: 0.10 $
 */
public class WLogin extends AbstractUIPart
{

	private IWebClient app;
	private Borderlayout layout;
	private Window browserWarningWindow;
	private LoginWindow loginWindow;

    public WLogin(IWebClient app)
    {
        this.app = app;
    }

    private Image bgDesktop;
    protected Component doCreatePart(Component parent)
    {
    	PageDefinition pageDefintion = Executions.getCurrent().getPageDefinition(ThemeManager.getThemeResource("zul/login/login.zul"));
    	Component loginPage = Executions.createComponents(pageDefintion, parent, null);
    	
        layout = (Borderlayout) loginPage.getFellow("layout");

        loginWindow = (LoginWindow) loginPage.getFellow("loginWindow");
        loginWindow.init(app);

        Center windowArea = layout.getCenter();
        bgDesktop = new Image();
        bgDesktop.setId("bglogin");
        bgDesktop.setSrc(ITheme.BG_LOGIN);
        bgDesktop.setSclass("login-desktop");
        windowArea.appendChild(bgDesktop);
        
        /* IDEMPIERE-1022 - deprecated message
        if (!AEnv.isBrowserSupported())
        {
        	//TODO: localization
        	String msg = "You might experience slow performance and user interface anomalies using your current browser to access the application. We recommend the use of Firefox, Google Chrome or Apple Safari.";
        	browserWarningWindow = new Window();
        	Div div = new Div();
        	div.setStyle("font-size: 9pt");
        	div.appendChild(new Text(msg));
        	browserWarningWindow.appendChild(div);
        	browserWarningWindow.setPosition("top,right");
        	ZKUpdateUtil.setWidth(browserWarningWindow, "550px");
        	browserWarningWindow.setPage(page);
        	browserWarningWindow.doOverlapped();
        }
        */
        /*
        boolean mobile = false;        
		if (Executions.getCurrent().getBrowser("mobile") !=null) {
			mobile = true;
		} else {
			String ua = Servlets.getUserAgent((ServletRequest) Executions.getCurrent().getNativeRequest());
			ua = ua.toLowerCase();
			if (ua.contains("ipad") || ua.contains("iphone") || ua.contains("android"))
				mobile = true;
		}
    	
        West west = layout.getWest();
        if (west.getFirstChild() != null && west.getFirstChild().getFirstChild() != null) {
    		west.setCollapsible(true);
    		west.setSplittable(true);
        	if (mobile) {    		
        		west.setOpen(false);
        	}
        } else {
        	west.setVisible(false);
        }
        
        East east = layout.getEast();
        if (east.getFirstChild() != null && east.getFirstChild().getFirstChild() != null) {
        	if (mobile) {    		
        		east.setCollapsible(true);
        		east.setOpen(false);
        	}
        } else {
        	east.setVisible(false);
        }
        
        North north = layout.getNorth();
        if (north.getFirstChild() == null || north.getFirstChild().getFirstChild() == null) {
        	north.setVisible(false);
        }
        
        South south = layout.getSouth();
        if (south.getFirstChild() == null || south.getFirstChild().getFirstChild() == null) {
        	south.setVisible(false);
        }
		*/
        return layout;
    }

	public void detach() {
		layout.detach();
		layout = null;
		if (browserWarningWindow != null)
			browserWarningWindow.detach();
	}

	public Component getComponent() {
		return layout;
	}

	
}
