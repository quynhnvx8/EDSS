package eone.webui.session;

import java.lang.ref.WeakReference;
import java.util.Properties;

import org.zkoss.zk.ui.Desktop;

import eone.base.model.MUser;
import eone.util.Env;
import eone.webui.IWebClient;
import eone.webui.apps.AEnv;
import eone.webui.desktop.IDesktop;


public class SessionManager
{
    public static final String SESSION_APPLICATION = "SessionApplication";
    
    public static boolean isUserLoggedIn(Properties ctx)
    {
        String adUserId = Env.getContext(ctx, "#AD_User_ID");
        String adClientId = Env.getContext(ctx, "#AD_Client_ID");
        String adOrgId = Env.getContext(ctx, "#AD_Org_ID");

        return (!"".equals(adUserId) && !"".equals(adClientId) && !"".equals(adOrgId));
    }
    
    public static void setSessionApplication(IWebClient app)
    {
    	Desktop desktop = AEnv.getDesktop();
    	if (desktop != null)
    		desktop.setAttribute(SESSION_APPLICATION, new WeakReference<IWebClient>(app));
    }
    
    public static IDesktop getAppDesktop()
    {
    	IWebClient webClient = getSessionApplication();
    	return webClient != null ? webClient.getAppDeskop() : null;
    }
    
    public static IWebClient getSessionApplication()
    {
    	Desktop desktop = AEnv.getDesktop();
        IWebClient app = null;
        if (desktop != null) 
        {
        	@SuppressWarnings("unchecked")
			WeakReference<IWebClient> wref = (WeakReference<IWebClient>) desktop.getAttribute(SESSION_APPLICATION); 
        	app = wref != null ? wref.get() : null;
        }
        return app;
    }
    
    public static void logoutSession()
    {
    	IWebClient app = getSessionApplication();
    	if (app != null)
    		app.logout();
    }

    public static void logoutSessionAfterBrowserDestroyed()
    {
    	IWebClient app = getSessionApplication();
    	if (app != null)
    		app.logoutAfterTabDestroyed();
    }
    
    public static void changeRole(MUser user){
    	IWebClient app = getSessionApplication();
    	if (app != null)
    		app.changeRole(user);
    }
}
