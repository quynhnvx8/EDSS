

package eone.webui.panel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.logging.Level;

import org.zkoss.lang.Strings;
import org.zkoss.util.Locales;
import org.zkoss.web.Attributes;
import org.zkoss.zhtml.Div;
import org.zkoss.zk.au.out.AuFocus;
import org.zkoss.zk.au.out.AuScript;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.A;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Comboitem;

import eone.base.model.MClient;
import eone.base.model.MSysConfig;
import eone.base.model.MUser;
import eone.base.model.Query;
import eone.util.CLogger;
import eone.util.Env;
import eone.util.KeyNamePair;
import eone.util.Language;
import eone.util.LogAuthFailure;
import eone.util.Login;
import eone.util.Msg;
import eone.util.Util;
import eone.webui.IWebClient;
import eone.webui.apps.AEnv;
import eone.webui.component.Button;
import eone.webui.component.Combobox;
import eone.webui.component.ConfirmPanel;
import eone.webui.component.Label;
import eone.webui.component.Textbox;
import eone.webui.component.Window;
import eone.webui.theme.ITheme;
import eone.webui.util.BrowserToken;
import eone.webui.util.UserPreference;
import eone.webui.window.LoginWindow;


public class LoginPanel extends Window implements EventListener<Event>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6130436148212949636L;

	public static final String ROLE_TYPES_WEBUI = "NULL,ZK,SS"; 

	private static LogAuthFailure logAuthFailure = new LogAuthFailure();

	private static final String ON_LOAD_TOKEN = "onLoadToken";
    private static final CLogger logger = CLogger.getCLogger(LoginPanel.class);

    protected Properties ctx;
    protected Label lblUserId;
    protected Label lblPassword;
    protected Label lblLanguage;
    protected Label lblLogin;    
    protected Textbox txtUserId;
    protected Textbox txtPassword;
    protected Combobox lstLanguage;
    protected LoginWindow wndLogin;
    protected Checkbox chkRememberMe;
    protected A btnResetPassword;
    protected ConfirmPanel pnlButtons; 
    protected boolean email_login = MSysConfig.getBooleanValue(MSysConfig.USE_EMAIL_FOR_LOGIN, false);
    protected String validLstLanguage = null;
    
    protected Button btnLogin;
    protected Button btnRegister;
    protected Label lblTextBegin = new Label(ITheme.ZK_COPYRIGHT);
    
    protected IWebClient app;
    
    public LoginPanel(Properties ctx, LoginWindow loginWindow, IWebClient app)
    {
        this.ctx = ctx;
        this.wndLogin = loginWindow;
        this.app = app;
        initComponents();
        init();
        this.setId("loginPanel");
        this.setSclass("login-box");

        txtUserId.setEnabled(false);
        txtUserId.setPlaceholder(Msg.getMsg(Env.getCtx(), "AccountPlaceholder"));
        txtPassword.setPlaceholder(Msg.getMsg(Env.getCtx(), "PasswordPlaceholder"));
        txtPassword.setEnabled(false);
        lstLanguage.setEnabled(false);
        Events.echoEvent(ON_LOAD_TOKEN, this, null);
        this.addEventListener(ON_LOAD_TOKEN, this);
    }

    private void init()
    {
    	createUI();
        List<String> browserLanguages = browserLanguages(Executions.getCurrent().getHeader("accept-language"));
        String defaultSystemLanguage = MClient.get(ctx, 0).getAD_Language();
        if (!browserLanguages.contains(defaultSystemLanguage))
        	browserLanguages.add(defaultSystemLanguage);
        boolean found = false;
        for (String browserLanguage : browserLanguages) {
            for (int i = 0; i < lstLanguage.getItemCount(); i++) {
            	Comboitem li = lstLanguage.getItemAtIndex(i);
            	String lang = li.getValue();
            	if (lang.startsWith(browserLanguage)) {
            		lstLanguage.setSelectedIndex(i);
            		languageChanged(li.getLabel());
            		found = true;
            		break;
            	}
            }
            if (found)
            	break;
        }
        
        txtUserId.removeEventListener(Events.ON_FOCUS, txtUserId);
        txtPassword.removeEventListener(Events.ON_FOCUS, txtPassword);
    }

	protected void createUI() {
		Div divLogin = new Div();
		divLogin.setSclass("login-div");		
		
		this.appendChild(divLogin);
		
		//Image
		Div divImageSologan = new Div();
		divImageSologan.setSclass("sologan-div");		
		divLogin.appendChild(divImageSologan);
		
		//Box login	
		Div divBox = new Div();
		divBox.setSclass("log-three-box");
		//User
		Div divUser = new Div();
		divUser.setSclass("login-common");
		Div divLabelUser = new Div();
		divLabelUser.setSclass("role-label-up-div");
		lblUserId.setZclass("role-text");
		divLabelUser.appendChild(lblUserId);
		
		Div divTxtUser = new Div();
		divTxtUser.setSclass("login-txt");
		divTxtUser.appendChild(txtUserId);
		divUser.appendChild(divLabelUser);
		divUser.appendChild(divTxtUser);
		
		divBox.appendChild(divUser);
		//Password
		Div divPassword = new Div();
		divPassword.setSclass("login-common");
		Div divLabelPassword = new Div();
		divLabelPassword.setSclass("role-label-up-div");
		lblPassword.setZclass("role-text");
		divLabelPassword.appendChild(lblPassword);
		
		Div divTxtPassword = new Div();
		divTxtPassword.setSclass("login-txt");
		divTxtPassword.appendChild(txtPassword);
		divPassword.appendChild(divLabelPassword);
		divPassword.appendChild(divTxtPassword);
		
		divBox.appendChild(divPassword);
			
		//Language
		Div divLanguage = new Div();
		divLanguage.setSclass("login-common");
		Div divLabelLanguage = new Div();
		divLabelLanguage.setSclass("role-label-up-div");
		lblLanguage.setZclass("role-text");
		divLabelLanguage.appendChild(lblLanguage);
		
		Div divTxtLanguage = new Div();
		divTxtLanguage.setSclass("login-common");
		divTxtLanguage.appendChild(lstLanguage);
		divLanguage.appendChild(divLabelLanguage);
		divLanguage.appendChild(divTxtLanguage);
		
		divBox.appendChild(divLanguage);
		
		divLogin.appendChild(divBox);
		
    	btnLogin = new Button("LOGIN");
        btnLogin.setId("btnLogin");
        btnLogin.addEventListener(Events.ON_CLICK, this);
        btnLogin.setSclass("login-button");
        
        
        Div divButton = new Div();
        divButton.setSclass("login-common");
        
        divButton.appendChild(btnLogin);
        
        divBox.appendChild(divButton);
        
        Div divFooter = new Div();
        divFooter.setSclass("login-footer");
        
    	//image
    	Div divImage = new Div();
    	divImage.setSclass("login-image-footer");
    	
    	divFooter.appendChild(divImage);
    	
    	//Text
    	Div divText = new Div();
    	divText.setSclass("login-text-footer"); 
    	btnRegister = new Button("Register");
    	btnRegister.setId("btnRegister");
    	btnRegister.addEventListener(Events.ON_CLICK, this);
    	lblTextBegin.setZclass("login-text-comon-login");
    	divText.appendChild(btnRegister);
        divText.appendChild(lblTextBegin);
    	
    	divFooter.appendChild(divText);
        
        divLogin.appendChild(divFooter);
	}

    private void initComponents()
    {
    	lblUserId = new Label();
    	lblUserId.setId("lblUserId");
    	lblUserId.setValue("Username");

    	lblPassword = new Label();
    	lblPassword.setId("lblPassword");
    	lblPassword.setValue("Password");
    	
    	lblLanguage = new Label();
    	lblLanguage.setId("lblPassword");
    	lblLanguage.setValue("Language");
    	
    	txtUserId = new Textbox();
        txtUserId.setId("txtUserId");
        txtUserId.setCols(25);
        txtUserId.setMaxlength(40);
        txtUserId.setWidth("220px");//220 ca 3
        txtUserId.addEventListener(Events.ON_CHANGE, this); 

        txtPassword = new Textbox();
        txtPassword.setId("txtPassword");
        txtPassword.setType("password");
        
        txtPassword.setCols(25);
        txtPassword.setWidth("220px");

        lstLanguage = new Combobox();
        lstLanguage.setAutocomplete(true);
        lstLanguage.setAutodrop(true);
        lstLanguage.setId("lstLanguage");
        lstLanguage.addEventListener(Events.ON_SELECT, this);
        lstLanguage.setWidth("220px");

        // Update Language List
        lstLanguage.getItems().clear();
        ArrayList<String> supported = Env.getLoginLanguages();
        String[] availableLanguages = Language.getNames();
        for (String langName : availableLanguages) {
    		Language language = Language.getLanguage(langName);
   			if (!supported.contains(language.getAD_Language()))
   				continue;
			lstLanguage.appendItem(langName, language.getAD_Language());
		}

        chkRememberMe = new Checkbox(Msg.getMsg(Language.getBaseAD_Language(), "RememberMe"));
        chkRememberMe.setId("chkRememberMe");
           
   }

    public void onEvent(Event event)
    {
        Component eventComp = event.getTarget();

        if (event.getTarget().getId().equals(ConfirmPanel.A_OK) || event.getTarget().getId().equals("btnLogin"))
        {
            validateLogin();
        }
        
        else if (event.getName().equals(Events.ON_CLICK) && event.getTarget().getId().equals("btnRegister"))
        {
        	WRegisterTrial win = new WRegisterTrial ();	
        	AEnv.showCenterScreen(win);
        }
        
        else if (event.getName().equals(Events.ON_SELECT))
        {
            if(eventComp.getId().equals(lstLanguage.getId())) {            	            	
            	if (lstLanguage.getSelectedItem() == null){
            		lstLanguage.setValue(validLstLanguage);
            	}else{
            		validLstLanguage = lstLanguage.getSelectedItem().getLabel();
            	}
            	           	
            	languageChanged(validLstLanguage);
            }
        }
        else if (event.getTarget() == btnResetPassword)
        {
        	btnResetPasswordClicked();
        }
        
        else if (event.getName().equals(ON_LOAD_TOKEN)) 
        {
        	BrowserToken.load(txtUserId);
        	
        	txtUserId.setEnabled(true);
            txtPassword.setEnabled(true);
            lstLanguage.setEnabled(true);
            
        	AuFocus auf = new AuFocus(txtUserId);
            Clients.response(auf);
        }
        //
    }

	
	
    private void languageChanged(String langName)
    {
    	Language language = findLanguage(langName);

		Locale loc = language.getLocale();
		Locale.setDefault(loc);
		
    	chkRememberMe.setLabel(Msg.getMsg(language, "RememberMe"));
    	lblUserId.setValue(Msg.getMsg(language, "Username"));
    	lblPassword.setValue(Msg.getMsg(language, "Password"));
    	lblLanguage.setValue(Msg.getMsg(language, "Language"));
    }

	private Language findLanguage(String langName) {
		Language tmp = Language.getLanguage(langName);
    	Language language = new Language(tmp.getName(), tmp.getAD_Language(), tmp.getLocale(), tmp.isDecimalPoint(),
    			tmp.getDateFormat().toPattern(), tmp.getMediaSize());
    	Env.verifyLanguage(ctx, language);
    	Env.setContext(ctx, Env.LANGUAGE, language.getAD_Language());
    	Env.setContext(ctx, AEnv.LOCALE, language.getLocale().toString());
		return language;
	}
    /**
     *  validates user name and password when logging in
     *
    **/
	
	protected Login login;
	
    public void validateLogin()
    {
        Login login = new Login(ctx);
        String userId = txtUserId.getValue();
        String userPassword = txtPassword.getValue();
   
        Session currSess = Executions.getCurrent().getDesktop().getSession();
        
        KeyNamePair clientsKNPairs[] = login.getClients(userId, userPassword, ROLE_TYPES_WEBUI);
        
        if (clientsKNPairs == null || clientsKNPairs.length == 0)
        {
        	String loginErrMsg = login.getLoginErrMsg();
        	if (Util.isEmpty(loginErrMsg))
        		loginErrMsg = Msg.getMsg(ctx,"FailedLogin", true);

            String x_Forward_IP = Executions.getCurrent().getHeader("X-Forwarded-For");
            if (x_Forward_IP == null) {
            	 x_Forward_IP = Executions.getCurrent().getRemoteAddr();
            }
        	logAuthFailure.log(x_Forward_IP, "/webui", userId, loginErrMsg);

        	Clients.clearBusy();
       		throw new WrongValueException(loginErrMsg);
        }
        else
        {
        	String langName = null;
        	if ( lstLanguage.getSelectedItem() != null )
        		langName = (String) lstLanguage.getSelectedItem().getLabel();
        	else
        		langName = Language.getBaseLanguage().getName();
        	Language language = findLanguage(langName);
            Env.setContext(ctx, UserPreference.LANGUAGE_NAME, language.getName()); // Elaine 2009/02/06


            login = new Login(ctx);
            login.loadAllContext(clientsKNPairs[0].getKey(), userId);
            
            if (login.isPasswordExpired())
            	wndLogin.changePassword(userId, userPassword, clientsKNPairs);
            else
            	wndLogin.loginOk(userId, clientsKNPairs);

            Locale locale = language.getLocale();
            currSess.setAttribute(Attributes.PREFERRED_LOCALE, locale);
            try {
				Clients.reloadMessages(locale);
			} catch (IOException e) {
				logger.log(Level.WARNING, e.getLocalizedMessage(), e);
			}
            Locales.setThreadLocal(locale);

            String timeoutText = getUpdateTimeoutTextScript();
            if (!Strings.isEmpty(timeoutText))
            	Clients.response("browserTimeoutScript", new AuScript(null, timeoutText));
        }

        currSess.setAttribute("Check_AD_User_ID", Env.getAD_User_ID(ctx));
		
    }

	private String getUpdateTimeoutTextScript() {
		String msg = Msg.getMsg(Env.getCtx(), "SessionTimeoutText").trim();  
		String continueNsg = Msg.getMsg(Env.getCtx(), "continue").trim();   
		if (msg == null || msg.equals("SessionTimeoutText")) {
			return null;
		}
		msg = Strings.escape(msg, "\"");
		String s = "eone.set(\"zkTimeoutText\", \"" + msg + "\");";
		s = s + " eone.set(\"zkContinueText\", \"" + continueNsg + "\");"; 
		return s;
	}
	
	private void btnResetPasswordClicked()
	{
		String userId = txtUserId.getValue();
		if (Util.isEmpty(userId))
    		throw new IllegalArgumentException(Msg.getMsg(ctx, "FillMandatory") + " " + lblUserId.getValue());
		
		boolean email_login = MSysConfig.getBooleanValue(MSysConfig.USE_EMAIL_FOR_LOGIN, false);
    	StringBuilder whereClause = new StringBuilder("Password IS NOT NULL AND ");
		if (email_login)
			whereClause.append("EMail=?");
		else
			whereClause.append("COALESCE(LDAPUser,Name)=?");
		whereClause.append(" AND")
				.append(" EXISTS (SELECT * FROM AD_User_Roles ur")
				.append("         INNER JOIN AD_Role r ON (ur.AD_Role_ID=r.AD_Role_ID)")
				.append("         WHERE ur.AD_User_ID=AD_User.AD_User_ID AND ur.IsActive='Y' AND r.IsActive='Y') AND ")
				.append(" EXISTS (SELECT * FROM AD_Client c")
				.append("         WHERE c.AD_Client_ID=AD_User.AD_Client_ID")
				.append("         AND c.IsActive='Y') AND ")
				.append(" AD_User.IsActive='Y'");
		
		List<MUser> users = new Query(ctx, MUser.Table_Name, whereClause.toString(), null)
			.setParameters(userId)
			.setOrderBy(MUser.COLUMNNAME_AD_User_ID)
			.list();
		
		wndLogin.resetPassword(userId, users.size() == 0);
	}

	/** get default languages from the browser */
	private List<String> browserLanguages(String header) {
		List<String> arrstr = new ArrayList<String>();
		if (header == null)
			return arrstr;

		for (String str : header.split(",")){
			String[] arr = str.trim().replace("-", "_").split(";");

			for (String s : arr){
				s = s.trim();
				if (!s.startsWith("q=")) {
					if (s.contains("_") && s.length() == 5) {
						String baselang = s.substring(0, 2).toLowerCase();
						StringBuffer lang = new StringBuffer(baselang).append("_").append(s.substring(3).toUpperCase());
						if (!arrstr.contains(lang.toString()))
							arrstr.add(lang.toString());
						if (!arrstr.contains(baselang))
							arrstr.add(baselang);
					} else {
						if (s.length() == 2 && !arrstr.contains(s.toLowerCase()))
							arrstr.add(s.toLowerCase());
					}
				}
			}
		}
		return arrstr;
	}
	
}
