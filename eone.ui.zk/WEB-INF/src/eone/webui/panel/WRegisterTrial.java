
package eone.webui.panel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Center;
import org.zkoss.zul.South;

import eone.base.model.MRegister;
import eone.base.model.Query;
import eone.base.model.X_AD_Register;
import eone.util.DB;
import eone.util.Env;
import eone.util.Msg;
import eone.util.TimeUtil;
import eone.webui.component.Borderlayout;
import eone.webui.component.Combobox;
import eone.webui.component.ConfirmPanel;
import eone.webui.component.Grid;
import eone.webui.component.GridFactory;
import eone.webui.component.Label;
import eone.webui.component.Row;
import eone.webui.component.Rows;
import eone.webui.component.Textbox;
import eone.webui.component.Window;
import eone.webui.util.ZKUpdateUtil;
import eone.webui.window.FDialog;

/**
 * 
 * @author Quynhnv.x8
 *
 */
public class WRegisterTrial extends Window implements EventListener<Event> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7757368164776005797L;

	private Textbox txtCompany, txtAddress, txtTaxCode, txtPhone, txtEmail, txtDomain;
	private Label lblCompany, lblAddress, lblTaxCode, lblPhone, lblEmail, lblDomain;
	private Label lblModel, lblCalPrice, lblRulesAccount;
	private Combobox cboModel, cboCalPrice, cboRulesAccount;
	
	private ConfirmPanel confirmPanel;

	private String domain;

	private Borderlayout mainPanel = new Borderlayout();

	public WRegisterTrial() {

		super();

		initComponent();

		setTitle(Msg.getMsg(Env.getCtx(), "Registertrial"));
		setAttribute(Window.MODE_KEY, Window.MODE_HIGHLIGHTED);
		ZKUpdateUtil.setWindowWidthX(this, 400);
		ZKUpdateUtil.setWindowHeightX(this, 650);
		this.setSclass("popup-dialog request-dialog");
		this.setBorder("normal");
		this.setShadow(true);
		this.setClosable(true);

		this.appendChild(mainPanel);

		Grid grid = GridFactory.newGridLayout();

		Rows rows = new Rows();
		grid.appendChild(rows);

		Row row = new Row();
		row.appendChild(lblCompany);
		rows.appendChild(row);

		row = new Row();
		row.appendChild(txtCompany);
		rows.appendChild(row);

		row = new Row();
		row.appendChild(lblAddress);
		rows.appendChild(row);

		row = new Row();
		row.appendChild(txtAddress);
		rows.appendChild(row);

		row = new Row();
		row.appendChild(lblTaxCode);
		rows.appendChild(row);

		row = new Row();
		row.appendChild(txtTaxCode);
		rows.appendChild(row);

		row = new Row();
		row.appendChild(lblPhone);
		rows.appendChild(row);

		row = new Row();
		row.appendChild(txtPhone);
		rows.appendChild(row);

		row = new Row();
		row.appendChild(lblEmail);
		rows.appendChild(row);

		row = new Row();
		row.appendChild(txtEmail);
		rows.appendChild(row);

		row = new Row();
		row.appendChild(lblDomain);
		rows.appendChild(row);

		row = new Row();
		row.appendChild(txtDomain);
		rows.appendChild(row);
		
		row = new Row();
		row.appendChild(lblModel);
		rows.appendChild(row);

		row = new Row();
		row.appendChild(cboModel);
		rows.appendChild(row);
		
		row = new Row();
		row.appendChild(lblRulesAccount);
		rows.appendChild(row);

		row = new Row();
		row.appendChild(cboRulesAccount);
		rows.appendChild(row);
		
		row = new Row();
		row.appendChild(lblCalPrice);
		rows.appendChild(row);

		row = new Row();
		row.appendChild(cboCalPrice);
		rows.appendChild(row);

		ZKUpdateUtil.setHflex(mainPanel, "1");
		ZKUpdateUtil.setVflex(mainPanel, "1");
		
		Center centerPane = new Center();
		centerPane.setSclass("dialog-content");
		centerPane.setAutoscroll(true);
		mainPanel.appendChild(centerPane);

		centerPane.appendChild(grid);
		ZKUpdateUtil.setVflex(grid, "min");
		ZKUpdateUtil.setHflex(grid, "1");
		ZKUpdateUtil.setVflex(centerPane, "min");
		
		ZKUpdateUtil.setWidth(grid, "100%");
		ZKUpdateUtil.setHeight(grid, "100%");

		South southPane = new South();
		southPane.setSclass("dialog-footer");
		mainPanel.appendChild(southPane);
		southPane.appendChild(confirmPanel);

	}

	private void initComponent() {

		confirmPanel = new ConfirmPanel(true);
		confirmPanel.addActionListener(this);

		txtCompany = new Textbox();
		ZKUpdateUtil.setWidth(txtCompany, "100%");
		lblCompany = new Label(Msg.getMsg(Env.getCtx(), "Trial_Company"));

		txtAddress = new Textbox();
		ZKUpdateUtil.setWidth(txtAddress, "100%");
		lblAddress = new Label(Msg.getMsg(Env.getCtx(), "Trial_Address"));

		txtTaxCode = new Textbox();
		ZKUpdateUtil.setWidth(txtTaxCode, "100%");
		lblTaxCode = new Label(Msg.getMsg(Env.getCtx(), "Trial_TaxCode"));

		txtPhone = new Textbox();
		ZKUpdateUtil.setWidth(txtPhone, "100%");
		lblPhone = new Label(Msg.getMsg(Env.getCtx(), "Trial_Phone"));

		txtEmail = new Textbox();
		ZKUpdateUtil.setWidth(txtEmail, "100%");
		lblEmail = new Label(Msg.getMsg(Env.getCtx(), "Trial_Email"));

		txtDomain = new Textbox();
		ZKUpdateUtil.setWidth(txtDomain, "100%");
		lblDomain = new Label(Msg.getMsg(Env.getCtx(), "Trial_Domain"));
		
		cboModel = new Combobox();
		ZKUpdateUtil.setWidth(cboModel, "100%");
		lblModel = new Label(Msg.getMsg(Env.getCtx(), "Trial_Model"));
		fillComboModel();
		
		cboRulesAccount = new Combobox();
		ZKUpdateUtil.setWidth(cboRulesAccount, "100%");
		lblRulesAccount = new Label(Msg.getMsg(Env.getCtx(), "Trial_RulesAccount"));
		fillComboRulesAccountance();
		
		cboCalPrice = new Combobox();
		cboCalPrice.appendItem(Msg.getMsg(Env.getCtx(), "Price_Average"), "A");
		cboCalPrice.appendItem(Msg.getMsg(Env.getCtx(), "Price_FiFo"), "F");
		cboCalPrice.appendItem(Msg.getMsg(Env.getCtx(), "Price_LiFo"), "L");
		cboCalPrice.appendItem(Msg.getMsg(Env.getCtx(), "Price_None"), "N");
		cboCalPrice.setSelectedIndex(0);
		
		ZKUpdateUtil.setWidth(cboCalPrice, "100%");
		lblCalPrice = new Label(Msg.getMsg(Env.getCtx(), "Trial_CalPrice"));
	}

	public void onEvent(Event e) throws Exception {
		if (e.getTarget() == confirmPanel.getButton(ConfirmPanel.A_OK)) {
			
			 if(!validateInput()) 
				 return;
			
			String error = createData(Env.getCtx(), null);
			if (!error.isEmpty()) {
				FDialog.error(0, error);
				return;
			}
			/*
			if (!processDatabase()) {
				FDialog.error(0, "Không xử lý được database dùng riêng !");
				return;
			}
			*/
			FDialog.error(0, "Bạn đã đăng ký thành công. Bạn có 90 ngày dùng thử. Tài khoản đăng nhập của bạn là " + 
					txtEmail.getValue().substring(0, txtEmail.getValue().indexOf("@")) + ", mật khẩu là 1");
			//this.detach();
		} else if (e.getTarget() == confirmPanel.getButton(ConfirmPanel.A_CANCEL))
			this.detach();
	}

	private boolean validateInput() {
		if (txtCompany.getValue() == null || txtCompany.getValue().isEmpty()) {
			FDialog.error(0, "Tên công ty bắt buộc nhập");
			return false;
		}

		if (txtAddress.getValue() == null || txtAddress.getValue().isEmpty()) {
			FDialog.error(0, "Địa chỉ công ty bắt buộc nhập");
			return false;
		}

		if (txtTaxCode.getValue() == null || txtTaxCode.getValue().isEmpty()) {
			FDialog.error(0, "Mã số thuế công ty bắt buộc nhập");
			return false;
		}

		if (!Pattern.matches("\\d*", txtTaxCode.getValue())) {
			FDialog.error(0, "Mã số thuế không hợp lệ");
			return false;
		}

		if (txtPhone.getValue() == null || txtPhone.getValue().isEmpty()) {
			FDialog.error(0, "Điện thoại liên hệ bắt buộc nhập");
			return false;
		}

		if (!Pattern.matches("\\d*", txtPhone.getValue())) {
			FDialog.error(0, "Điện thoại không hợp lệ");
			return false;
		}

		if (txtEmail.getValue() == null || txtEmail.getValue().isEmpty()) {
			FDialog.error(0, "Thư điện tử bắt buộc nhập");
			return false;
		}

		if (!txtEmail.getValue().contains("@") || !txtEmail.getValue().contains(".com")) {
			FDialog.error(0, "Email không hợp lệ");
			return false;
		}

		if (txtDomain.getValue() == null || txtDomain.getValue().isEmpty()) {
			FDialog.error(0, "Tên miền bắt buộc nhập");
			return false;
		}

		if (txtDomain.getValue().length() > 20) {
			FDialog.error(0, "Tên miền chỉ tối đa 20 ký tự viết liền nhau");
			return false;
		}
		
		/*
		if (!Pattern.matches("\\D*", txtDomain.getValue())) {
			FDialog.error(0, "Tên miền không hợp lệ");
			return false;
		}
		*/
		return true;
	}

	public boolean processDatabase() throws IOException, InterruptedException, SQLException {
		/*Dung truong hợp tạo Schame riêng.
		if (!createDB())
			 return false;
		 
		backupDB();

		restoreDB();
		*/
		return true;
	}

	public boolean createDB() throws SQLException {
		domain = DB.TO_STRING(txtDomain.getValue().toUpperCase()).replaceAll("'", "");
		String sql = "CREATE DATABASE " + domain;
		Statement smtp = DB.createStatement();
		int no = smtp.executeUpdate(sql);
		if (no < 0)
			return false;
		return true;
	}

	public void restoreDB() throws IOException {
		Runtime.getRuntime();
		Process p;
		ProcessBuilder pb;
		pb = new ProcessBuilder("C:\\Program Files\\PostgreSQL\\13\\bin\\pg_restore.exe", "--host", "103.74.121.159",
				"--port=5432", "--username=xerp", "--dbname=ctch", "--role=xerp", "--no-password",
				"--verbose", "D:\\Working\\EONE\\DBEONE\\xerp.backup");
		pb.redirectErrorStream(true);
		final Map<String, String> env = pb.environment();
		env.put("PGPASSWORD", "Dss123@");
		p = pb.start();
		InputStream is = p.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		String ll;
		while ((ll = br.readLine()) != null) {
			System.out.println(ll);
		}
	}

	public void backupDB() throws IOException, InterruptedException {
		Runtime.getRuntime();
		Process p;
		ProcessBuilder pb;
		pb = new ProcessBuilder("C:\\Program Files\\PostgreSQL\\13\\bin\\pg_dump.exe", "--host", "103.74.121.159",
				"--port", "5432", "--username", "xerp", "--no-password", "--format", "custom", "--blobs", "--verbose",
				"--file", "D:\\Working\\EONE\\DBEONE\\xerp.backup", "xerp");
		try {
			final Map<String, String> env = pb.environment();
			env.put("PGPASSWORD", "Dss123@");
			p = pb.start();
			final BufferedReader r = new BufferedReader(new InputStreamReader(p.getErrorStream()));
			String line = r.readLine();
			while (line != null) {
				System.err.println(line);
				line = r.readLine();
			}
			r.close();
			p.waitFor();
			System.out.println(p.exitValue());

		} catch (IOException | InterruptedException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public String createData(Properties ctx, String trxName) {
		
		MRegister  reg = isExistsReg(ctx, txtTaxCode.getValue(), trxName); 
		if (reg == null) {
			reg = new MRegister(Env.getCtx(), -1, null);
			reg.setName(txtCompany.getValue());
			reg.setAddress(txtAddress.getValue());
			reg.setPhone(txtPhone.getValue());
			reg.setEMail(txtEmail.getValue());
			reg.setTaxID(txtTaxCode.getValue());
			reg.setDomain(txtDomain.getValue());
			//reg.setAD_PackagePrice_ID();
			reg.setRegisterType(X_AD_Register.REGISTERTYPE_Trial);
			reg.setStartDate(new Timestamp(new Date().getTime()));
			reg.setEndDate(TimeUtil.addDays(reg.getStartDate(), 90));
			reg.setAD_Client_ID(DB.getNextID(Env.getCtx(), X_AD_Register.Table_Name, null));
			reg.setC_Element_ID(cboRulesAccount.getSelectedItem().getValue());
			reg.setMMPolicy(cboCalPrice.getSelectedItem().getValue());
			reg.setAD_ModelClient_ID(cboModel.getSelectedItem().getValue());
			if (!reg.save())
				return "Đăng ký mới bị lỗi !";
		} else {
			return "Mã số thuế này đăng ký rồi !";
		}
		
		return "";
	}
	
	
	
	private MRegister isExistsReg(Properties ctx, String domain, String trxName) {
		MRegister item = new Query(ctx, X_AD_Register.Table_Name, "TaxID = ?", trxName)
				.setParameters(domain)
				.first();
		return item;
	}
	
	
	private void fillComboRulesAccountance() {
		String sql = "SELECT * FROM C_Element WHERE IsActive = 'Y' ORDER BY IsDefault DESC";
		PreparedStatement ps = DB.prepareCall(sql);
		ResultSet rs = null;
		int i = 0;
		try {
			rs = ps.executeQuery();
			while (rs.next()) {
				cboRulesAccount.appendItem(rs.getString("Name"), rs.getInt("C_Element_ID"));
				if ("Y".equals(rs.getString("IsDefault")))
					cboRulesAccount.setSelectedIndex(i);
				i++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DB.close(rs, ps);
		}	
				
	}
	
	private void fillComboModel() {
		String sql = "SELECT * FROM AD_ModelClient WHERE IsActive = 'Y' ORDER BY IsDefault DESC";
		PreparedStatement ps = DB.prepareCall(sql);
		ResultSet rs = null;
		int i = 0;
		try {
			rs = ps.executeQuery();
			while (rs.next()) {
				cboModel.appendItem(rs.getString("Name"), rs.getInt("AD_ModelClient_ID"));
				if ("Y".equals(rs.getString("IsDefault")))
					cboModel.setSelectedIndex(i);
				i++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DB.close(rs, ps);
		}	
				
	}
	
}
