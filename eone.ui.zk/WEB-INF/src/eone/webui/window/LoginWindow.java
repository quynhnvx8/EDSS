
package eone.webui.window;

import java.util.Properties;

import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.Clients;

import eone.webui.IWebClient;
import eone.webui.component.FWindow;
import eone.webui.panel.ChangePasswordPanel;
import eone.webui.panel.LoginPanel;
import eone.webui.panel.ResetPasswordPanel;
import eone.webui.panel.RolePanel;
import eone.webui.session.SessionManager;
import eone.webui.util.UserPreference;


public class LoginWindow extends FWindow implements EventListener<Event>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5169830531440825871L;

	protected IWebClient app;
    protected Properties ctx;
    protected LoginPanel pnlLogin;
    protected ResetPasswordPanel pnlResetPassword;
    protected ChangePasswordPanel pnlChangePassword;
    protected RolePanel pnlRole;

    public LoginWindow() {}

    public void init(IWebClient app)
    {
    	this.ctx = Env.getCtx();
        this.app = app;
        initComponents();
        this.appendChild(pnlLogin);
        this.setStyle("background-color: transparent");
        // add listener on 'ENTER' key for the login window
        addEventListener(Events.ON_OK,this);
        setWidgetListener("onOK", "zAu.cmd0.showBusy(null)");
    }

    private void initComponents()
    {
        createLoginPanel();
    }

	protected void createLoginPanel() {
		pnlLogin = new LoginPanel(ctx, this, app);
	}

    public void loginOk(String userName, KeyNamePair[] clientsKNPairs)
    {
    	
    	loginCompleted();
    	UserPreference userPreference = SessionManager.getSessionApplication().getUserPreference();
		userPreference.setProperty(UserPreference.P_LANGUAGE, Env.getContext(ctx, UserPreference.LANGUAGE_NAME));
		userPreference.setProperty(UserPreference.P_CLIENT, Env.getAD_Client_ID(Env.getCtx()));    	
    }

    public void changePassword(String userName, String userPassword, KeyNamePair[] clientsKNPairs)
    {
    	Clients.clearBusy();
		createChangePasswordPanel(userName, userPassword, clientsKNPairs);
        this.getChildren().clear();
        this.appendChild(pnlChangePassword);
    }

	protected void createChangePasswordPanel(String userName, String userPassword, KeyNamePair[] clientsKNPairs) {
		pnlChangePassword = new ChangePasswordPanel(ctx, this, userName, userPassword, clientsKNPairs);
	}
    
    public void resetPassword(String userName, boolean noSecurityQuestion)
    {
    	createResetPasswordPanel(userName, noSecurityQuestion);
        this.getChildren().clear();
        this.appendChild(pnlResetPassword);
    }

	protected void createResetPasswordPanel(String userName,
			boolean noSecurityQuestion) {
		pnlResetPassword = new ResetPasswordPanel(ctx, this, userName, noSecurityQuestion);
	}

    public void loginCompleted()
    {
        app.loginCompleted();
    }

    public void loginCancelled()
    {
        createLoginPanel();
        this.getChildren().clear();
        this.appendChild(pnlLogin);
    }

    public void onEvent(Event event)
    {
       // check that 'ENTER' key is pressed
       if (Events.ON_OK.equals(event.getName()))
       {
           RolePanel rolePanel = (RolePanel)this.getFellowIfAny("rolePanel");
           if (rolePanel != null)
           {
               rolePanel.validateRoles();
               return;
           }
           
           LoginPanel loginPanel = (LoginPanel)this.getFellowIfAny("loginPanel");
           if (loginPanel != null)
           {
               loginPanel.validateLogin();
               return;
           }
           
           ChangePasswordPanel changePasswordPanel = (ChangePasswordPanel)this.getFellowIfAny("changePasswordPanel");
           if (changePasswordPanel != null){
        	   changePasswordPanel.validateChangePassword();
        	   return;
           }
           
           ResetPasswordPanel resetPasswordPanel = (ResetPasswordPanel)this.getFellowIfAny("resetPasswordPanel");
           if (resetPasswordPanel != null){
        	   resetPasswordPanel.validate();
        	   return;
           }
       }
    }
   
}
