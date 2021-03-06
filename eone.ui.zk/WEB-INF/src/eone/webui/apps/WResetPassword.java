/******************************************************************************
 * Copyright (C) 2012 Elaine Tan                                              *
 * Copyright (C) 2012 Trek Global                                             *
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

package eone.webui.apps;

import java.util.ArrayList;
import java.util.logging.Level;

import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Center;
import org.zkoss.zul.Columns;
import org.zkoss.zul.South;

import eone.base.model.MLookup;
import eone.base.model.MLookupFactory;
import eone.base.model.MPasswordHistory;
import eone.base.model.MPasswordRule;
import eone.base.model.MSysConfig;
import eone.base.model.MUser;
import eone.exceptions.EONEException;
import eone.util.CLogger;
import eone.util.DisplayType;
import eone.util.Env;
import eone.util.Msg;
import eone.util.Util;
import eone.webui.component.Checkbox;
import eone.webui.component.Column;
import eone.webui.component.ConfirmPanel;
import eone.webui.component.Grid;
import eone.webui.component.GridFactory;
import eone.webui.component.Label;
import eone.webui.component.Row;
import eone.webui.component.Rows;
import eone.webui.component.Textbox;
import eone.webui.editor.WSearchEditor;
import eone.webui.event.ValueChangeEvent;
import eone.webui.event.ValueChangeListener;
import eone.webui.panel.ADForm;
import eone.webui.panel.CustomForm;
import eone.webui.panel.IFormController;
import eone.webui.session.SessionManager;
import eone.webui.util.ZKUpdateUtil;
import eone.webui.window.FDialog;

/**
 * Reset Password Form
 * @author Elaine
 * @date September 18, 2012
 */
public class WResetPassword implements IFormController, EventListener<Event>, ValueChangeListener {

	private static final CLogger log = CLogger.getCLogger(WResetPassword.class);

	private CustomForm form;
	private Grid gridPanel;
    private ConfirmPanel confirmPanel;
    
    private Label lblUser;
	private Label lblOldPassword;
    private Label lblNewPassword;
    private Label lblRetypeNewPassword;
    
    private WSearchEditor fUser;
    private Textbox txtOldPassword;
    private Textbox txtNewPassword;
    private Textbox txtRetypeNewPassword;
    private Checkbox cbForceChangeNextLogin;
    private Checkbox cbReset;
    
    public WResetPassword()
    {
    	form = new CustomForm();
    	
    	try
		{
			dynInit();
			zkInit();
			
			Borderlayout contentPane = new Borderlayout();
			form.appendChild(contentPane);
			ZKUpdateUtil.setWidth(contentPane, "99%");
			ZKUpdateUtil.setHeight(contentPane, "100%");
			Center center = new Center();
			center.setStyle("border: none");
			contentPane.appendChild(center);
			ZKUpdateUtil.setHflex(gridPanel, "true");
			ZKUpdateUtil.setVflex(gridPanel, "true");
			center.appendChild(gridPanel);
			South south = new South();
			south.setStyle("border: none");
			contentPane.appendChild(south);
			south.appendChild(confirmPanel);
			confirmPanel.addActionListener(this);
		}
		catch(Exception ex)
		{
			log.log(Level.SEVERE, "init", ex);
		}
    }
	
	private void dynInit() throws Exception
	{
		lblUser = new Label(Msg.translate(Env.getCtx(), "AD_User_ID"));
    	lblOldPassword = new Label(Msg.getMsg(Env.getCtx(), "Old Password"));
    	lblNewPassword = new Label(Msg.getMsg(Env.getCtx(), "New Password"));
    	lblRetypeNewPassword = new Label(Msg.getMsg(Env.getCtx(), "New Password Confirm"));
    	
		//	AD_User.AD_User_ID
		MLookup userLkp = MLookupFactory.get(Env.getCtx(), form.getWindowNo(), 0, 212, DisplayType.Search);
		fUser = new WSearchEditor("AD_User_ID", false, false, true, userLkp);
		ZKUpdateUtil.setWidth(fUser.getComponent(), "220px");
		
		txtOldPassword = new Textbox();
        txtOldPassword.setId("txtOldPassword");
        txtOldPassword.setType("password");
        txtOldPassword.setCols(25);
        ZKUpdateUtil.setWidth(txtOldPassword, "220px");

        txtNewPassword = new Textbox();
        txtNewPassword.setId("txtNewPassword");
        txtNewPassword.setType("password");
        txtNewPassword.setCols(25);
        txtNewPassword.addEventListener(Events.ON_BLUR, this);
        ZKUpdateUtil.setWidth(txtNewPassword, "220px");
        
        txtRetypeNewPassword = new Textbox();
        txtRetypeNewPassword.setId("txtRetypeNewPassword");
        txtRetypeNewPassword.setType("password");
        txtRetypeNewPassword.setCols(25);
        txtRetypeNewPassword.addEventListener(Events.ON_BLUR, this);
        ZKUpdateUtil.setWidth(txtRetypeNewPassword, "220px");

        cbForceChangeNextLogin = new Checkbox();
        cbForceChangeNextLogin.setLabel(Msg.getMsg(Env.getCtx(), "ForceChangeOnNextLogin"));
        cbForceChangeNextLogin.setChecked(false);
        
        cbReset = new Checkbox();
        cbReset.setLabel(Msg.getMsg(Env.getCtx(), "ResetPassword"));
        cbReset.setChecked(false);
        
		confirmPanel = new ConfirmPanel(true);
	}
    
    private void zkInit() throws Exception
	{
    	gridPanel = GridFactory.newGridLayout();
    	
    	Columns columns = new Columns();
    	gridPanel.appendChild(columns);
    	
    	Column column = new Column();
    	columns.appendChild(column);
    	ZKUpdateUtil.setWidth(column, "40%");
    	
    	column = new Column();
    	columns.appendChild(column);
    	ZKUpdateUtil.setWidth(column, "60%");
    	
    	Rows rows = new Rows();
    	gridPanel.appendChild(rows);

		Row row = new Row();
		rows.appendChild(row);
		row.appendChild(lblUser.rightAlign());
		row.appendChild(fUser.getComponent());
		fUser.addValueChangeListener(this);
				
		row = new Row();
		rows.appendChild(row);
		row.appendChild(new Label());
		row.appendChild(cbReset);
		cbReset.addActionListener(this);
		
		row = new Row();
		rows.appendChild(row);
		row.appendChild(lblOldPassword.rightAlign());
		row.appendChild(txtOldPassword);
		
		row = new Row();
		rows.appendChild(row);
		row.appendChild(lblNewPassword.rightAlign());
		row.appendChild(txtNewPassword);

		row = new Row();
		rows.appendChild(row);
		row.appendChild(lblRetypeNewPassword.rightAlign());
		row.appendChild(txtRetypeNewPassword);
		
		row = new Row();
		rows.appendChild(row);
		row.appendChild(new Label());
		row.appendChild(cbForceChangeNextLogin);
		
	}
    
	@Override
	public void valueChange(ValueChangeEvent e) {
		log.info(e.getPropertyName() + "=" + e.getNewValue());
		if (e.getPropertyName().equals("AD_User_ID"))
			fUser.setValue(e.getNewValue());
	}

	@Override
	public void onEvent(Event e) throws Exception 
	{
		if (e.getTarget().getId().equals(ConfirmPanel.A_CANCEL))
		{
			SessionManager.getAppDesktop().closeActiveWindow();
			return;
		}
		else if (e.getTarget() == cbReset) {
			if (cbReset.isChecked()) {
				lblOldPassword.setVisible(false);
				txtOldPassword.setVisible(false);
			} else {
				lblOldPassword.setVisible(true);
				txtOldPassword.setVisible(true);
			}
		}
		else if (e.getTarget().getId().equals(ConfirmPanel.A_OK))
		{
			validateChangePassword();
		}
        else if (e.getTarget() == txtNewPassword) {
        	MPasswordRule pwdrule = MPasswordRule.getRules(Env.getCtx(), null);
        	String userName = "";
        	int userID = -1;
    		if (fUser.getValue() != null) {
    			userID = Integer.parseInt(fUser.getValue().toString());
    		}
			if (userID < 0)
				throw new WrongValueException(fUser.getComponent(), Msg.getMsg(Env.getCtx(), "UserMandatory"));
			userName = MUser.getNameOfUser(userID);
			if (pwdrule != null) {
				try {
					pwdrule.validate(userName, txtNewPassword.getValue(), new ArrayList<MPasswordHistory>());
				}
				catch (Exception ex) {
					throw new WrongValueException(txtNewPassword, ex.getMessage());
				}
			}
        }
        else if (e.getTarget() == txtRetypeNewPassword) {
        	if (!txtNewPassword.getValue().equals(txtRetypeNewPassword.getValue()))
        		throw new WrongValueException(txtRetypeNewPassword, Msg.getMsg(Env.getCtx(), "PasswordNotMatch"));
        }
	}
		
	private void validateChangePassword()
    {
		int p_AD_User_ID = -1;
		if (fUser.getValue() != null)
			p_AD_User_ID = Integer.parseInt(fUser.getValue().toString());
		if (p_AD_User_ID < 0)
			throw new IllegalArgumentException(Msg.getMsg(Env.getCtx(), "UserMandatory"));
		
		String p_OldPassword = txtOldPassword.getValue();
		String p_NewPassword = txtNewPassword.getValue();
		String p_NewPasswordConfirm = txtRetypeNewPassword.getValue();
				
		MUser user = MUser.get(Env.getCtx(), p_AD_User_ID);		
		if (log.isLoggable(Level.FINE)) log.fine("User=" + user);
				
		//	Do we need a password ?
		if (Util.isEmpty(p_OldPassword) && !cbReset.isChecked())		//	Password required
		{
			MUser operator = MUser.get(Env.getCtx(), Env.getAD_User_ID(Env.getCtx()));
			if (log.isLoggable(Level.FINE)) log.fine("Operator=" + operator);
			
			if (p_AD_User_ID == 0			//	change of System
					|| p_AD_User_ID == 100		//	change of SuperUser
					|| !operator.isAdministrator())
				throw new IllegalArgumentException(Msg.getMsg(Env.getCtx(), "OldPasswordMandatory"));
		} else {
			//	is entered Password correct ?
			boolean hash_password = MSysConfig.getBooleanValue(MSysConfig.USER_PASSWORD_HASH, false);
			if (hash_password) {
				if (!user.authenticateHash(p_OldPassword, user.getName()) && !cbReset.isChecked())
					throw new IllegalArgumentException(Msg.getMsg(Env.getCtx(), "OldPasswordNoMatch"));
			} else {
				if (!p_OldPassword.equals(user.getPassword()) && !cbReset.isChecked())
					throw new IllegalArgumentException(Msg.getMsg(Env.getCtx(), "OldPasswordNoMatch"));
			}
	    	if (MSysConfig.getBooleanValue(MSysConfig.CHANGE_PASSWORD_MUST_DIFFER, true))
	    	{
	    		if (p_OldPassword.equals(p_NewPassword)) {
	        		throw new IllegalArgumentException(Msg.getMsg(Env.getCtx(), "NewPasswordMustDiffer"));
	    		}
	    	}
		}
		
		// new password confirm
		if (!Util.isEmpty(p_NewPassword)) {
			if (Util.isEmpty(p_NewPasswordConfirm)) {
				throw new IllegalArgumentException(Msg.getMsg(Env.getCtx(), "NewPasswordConfirmMandatory"));
			} else {
				if (!p_NewPassword.equals(p_NewPasswordConfirm)) {
					throw new IllegalArgumentException(Msg.getMsg(Env.getCtx(), "PasswordNotMatch"));
				} 
			}
		}
		
		
		if (!Util.isEmpty(p_NewPassword))
			user.set_ValueOfColumn("Password", p_NewPassword); // will be hashed and validate on saveEx
		if (cbForceChangeNextLogin.isChecked())
			user.setIsExpired(true);
		
		try {
			user.saveEx();
		}
		catch(EONEException e)
		{
			user.load(user.get_TrxName());
			throw e;
		}
		clearForm();
		FDialog.info(form.getWindowNo(), form, "RecordSaved");
		return;
    }
	
	private void clearForm()
	{
		fUser.setValue(null);
		txtOldPassword.setValue(null);
	    txtNewPassword.setValue(null);
	    txtRetypeNewPassword.setValue(null);
	}

	@Override
	public ADForm getForm() 
	{
		return form;
	}
}
