
package eone.webui.panel;

import java.sql.Timestamp;
import java.util.Properties;

import org.zkoss.zk.au.out.AuFocus;
import org.zkoss.zk.au.out.AuScript;
import org.zkoss.zk.ui.event.Deferrable;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Div;

import eone.base.model.MSysConfig;
import eone.base.model.MUser;
import eone.util.Env;
import eone.util.ItemMandatory;
import eone.util.KeyNamePair;
import eone.util.Language;
import eone.util.Login;
import eone.util.Msg;
import eone.util.TimeUtil;
import eone.webui.EONEIdGenerator;
import eone.webui.LayoutUtils;
import eone.webui.component.ComboItem;
import eone.webui.component.Combobox;
import eone.webui.component.ConfirmPanel;
import eone.webui.component.Label;
import eone.webui.component.Window;
import eone.webui.session.SessionManager;
import eone.webui.theme.ITheme;
import eone.webui.util.UserPreference;
import eone.webui.util.ZKUpdateUtil;
import eone.webui.window.FDialog;
import eone.webui.window.LoginWindow;

/**
 *
 * @author Qunhnv.x8
 * @date 28/10/2020
 */
public class RolePanel extends Window implements EventListener<Event>, Deferrable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4486118071892173802L;

	protected LoginWindow wndLogin;
	protected Login login;

	protected Combobox lstClient;
	protected Label lblClient;

	/** Context */
	protected Properties m_ctx;
	/** Username */
	protected String m_userName;
	/** Password */
	protected KeyNamePair[] m_clientKNPairs;

	protected UserPreference m_userpreference = null;

	protected boolean m_show = true;

	private RolePanel component;
	protected Button btnOk, btnCancel;

	private boolean isChangeRole = false;

	public boolean isChangeRole() {
		return isChangeRole;
	}

	public void setChangeRole(boolean isChangeRole) {
		this.isChangeRole = isChangeRole;
	}

	private static final String ON_DEFER_LOGOUT = "onDeferLogout";

	public RolePanel(Properties ctx, LoginWindow loginWindow, String userName, boolean show,
			KeyNamePair[] clientsKNPairs) {
		this.wndLogin = loginWindow;
		m_ctx = ctx;
		m_userName = userName;
		login = new Login(ctx);
		m_show = show;
		m_clientKNPairs = clientsKNPairs;

		if (m_clientKNPairs.length == 1 && !m_show) {
			Env.setContext(m_ctx, "#AD_Client_ID", (String) m_clientKNPairs[0].getID());
			MUser user = MUser.get(m_ctx, m_userName);
			m_userpreference = new UserPreference();
			m_userpreference.loadPreference(user.get_ID());
		}

		initComponents();
		init();
		this.setId("rolePanel");
		this.setSclass("login-box");

		if (!m_show) {
			// check if all mandatory fields are ok to not show
			if (lstClient.getSelectedItem() == null || lstClient.getSelectedItem().getValue() == null) {
				m_show = true;
			}
		}
		if (m_show) {
			AuFocus auf = null;
			if (lstClient.getItemCount() > 1) {
				auf = new AuFocus(lstClient);
			}
			Clients.response(auf);
		} else {
			validateRoles();
		}
	}

	private void init() {
		Clients.response(new AuScript("zAu.cmd0.clearBusy()"));
		createUI();
	}

	protected Label lblTextBegin = new Label(ITheme.ZK_COPYRIGHT);

	protected void createUI() {
		Div divLogin = new Div();
		divLogin.setSclass("login-div");

		this.appendChild(divLogin);

		// Image
		Div divImageSologan = new Div();
		divImageSologan.setSclass("sologan-div");
		divLogin.appendChild(divImageSologan);

		// Box login
		Div divBox = new Div();
		divBox.setSclass("log-three-box");

		// Client
		Div divClient = new Div();
		divClient.setSclass("login-common");
		Div divLabelClient = new Div();
		divLabelClient.setSclass("role-label-up-div");
		lblClient.setZclass("role-text");
		divLabelClient.appendChild(lblClient);

		Div divLstClient = new Div();
		divLstClient.setSclass("login-txt");
		divLstClient.appendChild(lstClient);
		divClient.appendChild(divLabelClient);
		divClient.appendChild(divLstClient);

		divBox.appendChild(divClient);
		
		divLogin.appendChild(divBox);

		ConfirmPanel pnlButtons = new ConfirmPanel(true, false, false, false, false, false, true);
		pnlButtons.addActionListener(this);
		Button okBtn = pnlButtons.getButton(ConfirmPanel.A_OK);
		okBtn.setWidgetListener("onClick", "zAu.cmd0.showBusy(null)");

		LayoutUtils.addSclass(ITheme.LOGIN_BOX_FOOTER_PANEL_CLASS, pnlButtons);
		pnlButtons.setWidth(null);
		pnlButtons.getButton(ConfirmPanel.A_OK).setSclass(ITheme.LOGIN_BUTTON_CLASS);
		pnlButtons.getButton(ConfirmPanel.A_CANCEL).setSclass(ITheme.LOGIN_BUTTON_CLASS);

		btnOk.setSclass("login-button");
		btnCancel.setSclass("login-button");

		// Button
		Div divButton = new Div();
		divButton.setSclass("role-button");
		divButton.appendChild(btnOk);
		divButton.appendChild(btnCancel);

		divBox.appendChild(divButton);

		Div divFooter = new Div();
		divFooter.setSclass("login-footer");

		// image
		Div divImage = new Div();
		divImage.setSclass("login-image-footer");

		divFooter.appendChild(divImage);

		// Text
		Div divText = new Div();
		divText.setSclass("login-text-footer");

		lblTextBegin.setZclass("login-text-comon-login");
		divText.appendChild(lblTextBegin);

		divFooter.appendChild(divText);

		divLogin.appendChild(divFooter);
	}

	private void initComponents() {
		Language language = Env.getLanguage(m_ctx);

		lblClient = new Label();
		lblClient.setId("lblClient");
		lblClient.setValue(Msg.getMsg(language, "Client"));
		
		lstClient = new Combobox();
		lstClient.setAutocomplete(true);
		lstClient.setAutodrop(true);
		lstClient.setId("lstClient");

		lstClient.addEventListener(Events.ON_SELECT, this);
		ZKUpdateUtil.setWidth(lstClient, "220px");

		btnOk = new Button();
        btnOk.setId("btnOk");
        btnOk.setLabel("OK");
        btnOk.addEventListener("onClick", this);

        btnCancel = new Button();
        btnCancel.setId("btnCancel");
        btnCancel.setLabel("CANCEL");
        btnCancel.addEventListener("onClick", this);
		
		UserPreference userPreference = SessionManager.getSessionApplication().getUserPreference();
		String initDefault = userPreference.getProperty(UserPreference.P_CLIENT);
		if (initDefault.length() == 0 && !m_show && m_userpreference != null) {
			initDefault = m_userpreference.getProperty(UserPreference.P_CLIENT);
		}
		if (m_clientKNPairs != null && m_clientKNPairs.length > 0) {
			for (int i = 0; i < m_clientKNPairs.length; i++) {
				ComboItem ci = new ComboItem(m_clientKNPairs[i].getName(), m_clientKNPairs[i].getID());
				String id = EONEIdGenerator.escapeId(ci.getLabel());
				if (lstClient.getFellowIfAny(id) == null)
					ci.setId(id);
				lstClient.appendChild(ci);
				if (m_clientKNPairs[i].getID().equals(initDefault))
					lstClient.setSelectedItem(ci);
			}
			if (lstClient.getSelectedIndex() == -1 && lstClient.getItemCount() > 0) {
				m_show = true; 
				lstClient.setSelectedIndex(0);
			}
		}
		//

		if (m_clientKNPairs != null && m_clientKNPairs.length == 1) {
			// disable client if is just one
			lstClient.setSelectedIndex(0);
			lstClient.setEnabled(false);
		} else {
			lstClient.setEnabled(true);
		}

		
		this.component = this;
		component.addEventListener(ON_DEFER_LOGOUT, this);
	}


	public void onEvent(Event event) {
		
		if (event.getTarget().getId().equals(ConfirmPanel.A_OK) || event.getTarget().getId().equals(btnOk.getId())) {
	        
			//Set lại client khi được chọn.
			int AD_Client_ID = Integer.parseInt((String) lstClient.getSelectedItem().getValue());
			Env.setContext(m_ctx, "#AD_Client_ID", AD_Client_ID);
			if (AD_Client_ID == 0) {
				Env.setContext(m_ctx, "#AD_Org_ID", 0);
			}
	    	
	    	validateRoles();			
		} else if (event.getTarget().getId().equals(ConfirmPanel.A_CANCEL) || event.getTarget().getId().equals(btnCancel.getId())) {
			SessionManager.logoutSession();
            wndLogin.loginCancelled();
		} else if (ON_DEFER_LOGOUT.equals(event.getName())) {
			SessionManager.logoutSession();
			wndLogin.loginCancelled();
		}
		
	}

	

	/**
	 * validate Roles
	 *
	 **/
	public void validateRoles() {
		Clients.clearBusy();
		//Comboitem lstItemRole = lstRole.getSelectedItem();
		Comboitem lstItemClient = lstClient.getSelectedItem();
		//Comboitem lstItemOrg = lstOrganisation.getSelectedItem();
		
		/*
		if (lstItemRole == null || lstItemRole.getValue() == null) {
			throw new WrongValueException(lstRole, Msg.getMsg(m_ctx, "FillMandatory") + lblRole.getValue());
		} else if (lstItemClient == null || lstItemClient.getValue() == null) {
			throw new WrongValueException(lstClient, Msg.getMsg(m_ctx, "FillMandatory") + lblClient.getValue());
		} else if (lstItemOrg == null || lstItemOrg.getValue() == null) {
			throw new WrongValueException(lstOrganisation,
					Msg.getMsg(m_ctx, "FillMandatory") + lblOrganisation.getValue());
		}
		int orgId = 0;
		orgId = Integer.parseInt((String) lstItemOrg.getValue());
		KeyNamePair orgKNPair = new KeyNamePair(orgId, lstItemOrg.getLabel());
		


		String msg = login.loadPreferences(orgKNPair);
		if (Util.isEmpty(msg)) {

			Session currSess = Executions.getCurrent().getDesktop().getSession();
			HttpSession httpSess = (HttpSession) currSess.getNativeSession();
			int timeout = MSysConfig.getIntValue(MSysConfig.ZK_SESSION_TIMEOUT_IN_SECONDS, -2,
					Env.getAD_Client_ID(Env.getCtx()), Env.getAD_Org_ID(Env.getCtx()));
			if (timeout != -2) // default to -2 meaning not set
				httpSess.setMaxInactiveInterval(timeout);

			msg = login.validateLogin(orgKNPair);
		}
		if (!Util.isEmpty(msg)) {
			Env.getCtx().clear();
			FDialog.error(0, this, "Error", msg, new Callback<Integer>() {
				@Override
				public void onCallback(Integer result) {
					Events.echoEvent(new Event(ON_DEFER_LOGOUT, component));
				}
			});
			return;
		}
		 */
		int notifyDay = MSysConfig.getIntValue(MSysConfig.USER_LOCKING_PASSWORD_NOTIFY_DAY, 0);
		int pwdAgeDay = MSysConfig.getIntValue(MSysConfig.USER_LOCKING_MAX_PASSWORD_AGE_DAY, 0);
		if (notifyDay > 0 && pwdAgeDay > 0) {
			Timestamp limit = TimeUtil.addDays(MUser.get(Env.getCtx()).getDatePasswordChanged(), pwdAgeDay);
			Timestamp notifyAfter = TimeUtil.addDays(limit, -notifyDay);
			Timestamp now = TimeUtil.getDay(null);

			if (now.after(notifyAfter))
				FDialog.warn(0, null, "", Msg.getMsg(Env.getCtx(), "YourPasswordWillExpireInDays",
						new Object[] { TimeUtil.getDaysBetween(now, limit) }));
		}

		wndLogin.loginCompleted();

		// Elaine 2009/02/06 save preference to AD_Preference
		UserPreference userPreference = SessionManager.getSessionApplication().getUserPreference();
		userPreference.setProperty(UserPreference.P_LANGUAGE, Env.getContext(m_ctx, UserPreference.LANGUAGE_NAME));
		//userPreference.setProperty(UserPreference.P_ROLE, (String) lstItemRole.getValue());
		userPreference.setProperty(UserPreference.P_CLIENT, (String) lstItemClient.getValue());
		//userPreference.setProperty(UserPreference.P_ORG, (String) lstItemOrg.getValue());
		userPreference.savePreference();
		//
	}

	public boolean isDeferrable() {
		return false;
	}
	
	
	public void setContextExtend(ItemMandatory itemMan) {
		
		//MandatoryLogic
		
		Env.setContext(m_ctx, "#BankAccount", 		itemMan.getBankAccount());
		Env.setContext(m_ctx, "#Asset", 			itemMan.getAsset());
		Env.setContext(m_ctx, "#BPartner", 			itemMan.getBpartner());
		Env.setContext(m_ctx, "#Product", 			itemMan.getProduct());
		Env.setContext(m_ctx, "#Project", 			itemMan.getProject());
		Env.setContext(m_ctx, "#ProjectLine", 		itemMan.getProjectLine());
		Env.setContext(m_ctx, "#Contract", 			itemMan.getContract());
		Env.setContext(m_ctx, "#ContractLine", 		itemMan.getContractLine());
		Env.setContext(m_ctx, "#Construction", 		itemMan.getConstruction());
		Env.setContext(m_ctx, "#ConstructionLine",  itemMan.getConstructionLine());
		Env.setContext(m_ctx, "#Warehouse", 		itemMan.getWarehouse());
		Env.setContext(m_ctx, "#TypeCost", 			itemMan.getTypeCost());
		Env.setContext(m_ctx, "#TypeRevenue", 		itemMan.getTypeRevenue());
		
	}
	
}
