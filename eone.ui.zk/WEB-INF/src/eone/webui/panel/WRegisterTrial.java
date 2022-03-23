
package eone.webui.panel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

import eone.base.model.MClient;
import eone.base.model.MDepartment;
import eone.base.model.MEmployee;
import eone.base.model.MOrg;
import eone.base.model.MRegister;
import eone.base.model.MUser;
import eone.base.model.MUserRoles;
import eone.base.model.Query;
import eone.base.model.X_AD_Client;
import eone.base.model.X_AD_Department;
import eone.base.model.X_AD_Org;
import eone.base.model.X_AD_Register;
import eone.base.model.X_AD_User;
import eone.base.model.X_AD_User_Roles;
import eone.base.model.X_HR_Employee;
import eone.util.DB;
import eone.util.Env;
import eone.util.Msg;
import eone.util.TimeUtil;
import eone.webui.component.Borderlayout;
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
	private ConfirmPanel confirmPanel;

	private String domain;

	private Borderlayout mainPanel = new Borderlayout();

	public WRegisterTrial() {

		super();

		initComponent();

		setTitle(Msg.getMsg(Env.getCtx(), "Registertrial"));
		setAttribute(Window.MODE_KEY, Window.MODE_HIGHLIGHTED);
		ZKUpdateUtil.setWindowWidthX(this, 400);
		ZKUpdateUtil.setWindowHeightX(this, 460);
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

		if (!Pattern.matches("\\D*", txtDomain.getValue())) {
			FDialog.error(0, "Tên miền không hợp lệ");
			return false;
		}

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
		
		//Insert Register
		//Luu thong tin vao file Properties
		/*
		String listDomain = Ini.getProperty(Ini.LIST_DOMAIN);
		if (listDomain.length() == 0) {
			listDomain = txtDomain.getValue().toUpperCase();
		} else {
			listDomain = listDomain + ";"+txtDomain.getValue().toUpperCase();
		}
		Ini.setProperty(Ini.LIST_DOMAIN, listDomain);
		Ini.saveProperties(false);
		*/
		//Ket thuc luu vao file
		
		MRegister  reg = isExistsReg(ctx, txtTaxCode.getValue(), trxName); 
		if (reg == null) {
			reg = new MRegister(Env.getCtx(), -1, null);
			reg.setDomain(txtDomain.getValue());
			reg.setName(txtCompany.getValue());
			reg.setAddress(txtAddress.getValue());
			reg.setPhone(txtPhone.getValue());
			reg.setEMail(txtEmail.getValue());
			reg.setTaxID(txtTaxCode.getValue());
			reg.setStartDate(new Timestamp(new Date().getTime()));
			reg.setEndDate(TimeUtil.addDays(reg.getStartDate(), 90));
			reg.setAD_Client_ID(DB.getNextID(Env.getCtx(), X_AD_Register.Table_Name, null));
			if (!reg.save())
				return "Đăng ký mới bị lỗi !";
		}
		
		//Insert Client
		
		MClient  client = isExistsClient(ctx, txtDomain.getValue(), trxName); 
		
		if (client == null)
		{
			client = new MClient(ctx, -1, trxName);
			client.setValue(txtDomain.getValue());
			client.setName(txtCompany.getValue());
			client.setAddress(txtAddress.getValue());
			client.setAD_Client_ID(DB.getNextID(ctx, X_AD_Client.Table_Name, trxName));
			client.set_ValueNoCheck("Created", new Timestamp(new Date().getTime()));
			client.set_ValueNoCheck("Updated", new Timestamp(new Date().getTime()));
			client.set_ValueNoCheck("CreatedBy", 100);
			client.set_ValueNoCheck("UpdatedBy", 100);
			client.set_ValueNoCheck("IsActive", true);
			client.setAD_Language("vi_VN");
			client.setRoundUnitPrice(4);
			client.setRoundCalculate(2);
			client.setRoundFinal(0);
			client.setIsCheckMaterial(true);
			client.setIsGroup(false);
			client.setIsMultiCurrency(false);
			client.setC_Currency_ID(234);
			client.setMMPolicy("A");
			client.setAutoArchive("N");
			client.setAD_Org_ID(0);
			if (!client.save()) {
				return "Tạo công ty mới bị lỗi !";
			}
		}
		
		//Insert Org
		
		MOrg org = isExistsOrg(ctx, txtDomain.getValue(), trxName); 
		if (org == null) {
			org = new MOrg(ctx, 0, trxName);
			org.setAD_Client_ID(client.getAD_Client_ID());
			org.setValue(client.getValue());
			org.setName(client.getName());
			org.setAddress(client.getAddress());
			org.setTaxID(txtTaxCode.getValue());
			org.setPhone(txtPhone.getValue());
			org.setIsCreateWarehouse(true);
			org.setAD_Org_ID(DB.getNextID(ctx, X_AD_Org.Table_Name, trxName));
			if (!org.save()) {
				return "Tạo đơn vị mới bị lỗi !";
			}			
		}
		
		//Insert Department
		MDepartment dept = isExistsDept(ctx, txtDomain.getValue(), trxName); 
		if (dept == null) {
			dept = new MDepartment(ctx, 0, trxName);
			dept.setAD_Client_ID(org.getAD_Client_ID());
			dept.setAD_Org_ID(org.getAD_Org_ID());
			dept.setValue(org.getValue());
			dept.setName(org.getName());
			dept.setIsCreateBPartner(false);
			dept.setAD_Department_ID(DB.getNextID(ctx, X_AD_Department.Table_Name, trxName));
			if (!dept.save()) {
				return "Tạo phòng ban mới bị lỗi !";
			}
		}
		//Insert Emplooyee
		MEmployee emp = isExistsEmp(ctx, txtEmail.getValue(), trxName); 
		if (emp == null) {
			emp = new MEmployee(ctx, 0, trxName);
			emp.setCode("");
			emp.setName(txtEmail.getValue());
			emp.setAD_Client_ID(org.getAD_Client_ID());
			emp.setAD_Org_ID(org.getAD_Org_ID());
			emp.setAD_Department_ID(dept.getAD_Department_ID());
			emp.setStartDate(new Timestamp(new Date().getTime()));
			emp.setHR_Employee_ID(DB.getNextID(ctx, X_HR_Employee.Table_Name, trxName));
			if (!emp.save()) {
				return "Tạo nhân sự bị lỗi !";
			}
		}
		
		//Insert User
		MUser u = isExistsUser(ctx, txtEmail.getValue(), trxName); 
		if (u == null) {
			u = new MUser(ctx, 0, trxName);
			u.setValue(txtEmail.getValue().substring(0, txtEmail.getValue().indexOf("@")));
			u.setEMail(txtEmail.getValue());
			u.setName(u.getValue());
			u.setHR_Employee_ID(emp.getHR_Employee_ID());
			u.setNotificationType(X_AD_User.NOTIFICATIONTYPE_None);
			u.setPassword("1");
			u.setAD_Client_ID(org.getAD_Client_ID());
			u.setAD_Org_ID(org.getAD_Org_ID());
			u.setAD_User_ID(DB.getNextID(ctx, X_AD_User.Table_Name, trxName));
			if (!u.save()) {
				return "Tạo account bị lỗi !";
			}
		}
		//AD_Role_ID = 1050017: DEMO
		MUserRoles ur = isExistsUserRole(ctx, u.getAD_User_ID(), 1050017, trxName);
		if (ur == null) {
			ur = new MUserRoles(ctx, u.getAD_User_ID(), 1050017, trxName);
			ur.setAD_Client_ID(client.getAD_Client_ID());
			ur.setAD_Org_ID(org.getAD_Org_ID());
			ur.save();
		}
		
		//createElement(ctx, trxName, org.getAD_Org_ID(), org.getAD_Client_ID());
		
		//createConfig(ctx, trxName, org.getAD_Org_ID(), org.getAD_Client_ID());
		
		return "";
	}
	
	private MClient isExistsClient(Properties ctx, String domain, String trxName) {
		MClient item = new Query(ctx, X_AD_Client.Table_Name, "Value = ?", trxName)
				.setParameters(domain)
				.first();
		return item;
	}
	
	private MRegister isExistsReg(Properties ctx, String domain, String trxName) {
		MRegister item = new Query(ctx, X_AD_Register.Table_Name, "TaxID = ?", trxName)
				.setParameters(domain)
				.first();
		return item;
	}
	
	private MOrg isExistsOrg(Properties ctx, String domain, String trxName) {
		MOrg item = new Query(ctx, X_AD_Org.Table_Name, "Value = ?", trxName)
				.setParameters(domain)
				.first();
		return item;
	}
	
	private MDepartment isExistsDept(Properties ctx, String domain, String trxName) {
		MDepartment item = new Query(ctx, X_AD_Department.Table_Name, "Value = ?", trxName)
				.setParameters(domain)
				.first();
		return item;
	}
	
	private MEmployee isExistsEmp(Properties ctx, String domain, String trxName) {
		MEmployee item = new Query(ctx, X_HR_Employee.Table_Name, "Name = ?", trxName)
				.setParameters(domain)
				.first();
		return item;
	}
	
	private MUser isExistsUser(Properties ctx, String domain, String trxName) {
		MUser item = new Query(ctx, X_AD_User.Table_Name, "Email = ?", trxName)
				.setParameters(domain)
				.first();
		return item;
	}
	
	private MUserRoles isExistsUserRole(Properties ctx, int AD_User_ID, int AD_Role_ID, String trxName) {
		MUserRoles item = new Query(ctx, X_AD_User_Roles.Table_Name, "AD_User_ID = ? AND AD_Role_ID = ?", trxName)
				.setParameters(AD_User_ID, AD_Role_ID)
				.first();
		return item;
	}
	
	/*
	private static int 			BATCH_SIZE = Env.getBatchSize(Env.getCtx());;
	Map<Integer, Integer> mapConfig = new HashMap<Integer, Integer>();
	
	private void createElement(Properties ctx, String trxName, int AD_Org_ID, int AD_Client_ID) {
		List<MElementValue> arr = new Query(ctx, X_C_ElementValue.Table_Name, "AD_Client_ID = 11 AND IsActive = 'Y'", trxName)
				.list();
		String sql = PO.getSqlInsert(X_C_ElementValue.Table_ID, trxName);
		
		MElementValue e = null;
		List<List<Object>> values = new ArrayList<List<Object>>();
		for(int i = 0; i < arr.size(); i++) {
			int C_ElementValue_ID = DB.getNextID(ctx, X_C_ElementValue.Table_Name, trxName);
			mapConfig.put(arr.get(i).getC_ElementValue_ID(), C_ElementValue_ID);
			e = new MElementValue(ctx, 0, trxName);
			e.setAD_Org_ID(AD_Org_ID);
			e.setAD_Client_ID(AD_Client_ID);
			e.setC_ElementValue_ID(C_ElementValue_ID);
			e.setValue(arr.get(i).getValue());
			e.setName(arr.get(i).getName());
			e.setAccountType(arr.get(i).getAccountType());
			e.setIsBankAccount(arr.get(i).isBankAccount());
			e.setIsDetailAsset(arr.get(i).isDetailAsset());
			e.setIsDetailBPartner(arr.get(i).isDetailBPartner());
			e.setIsDetailConstruction(arr.get(i).isDetailConstruction());
			e.setIsDetailConstructionPharse(arr.get(i).isDetailConstructionPharse());
			e.setIsDetailContract(arr.get(i).isDetailContract());
			e.setIsDetailContractSchedule(arr.get(i).isDetailContractSchedule());
			e.setIsDetailProduct(arr.get(i).isDetailProduct());
			e.setIsDetailProject(arr.get(i).isDetailProject());
			e.setIsDetailProjectPharse(arr.get(i).isDetailProjectPharse());
			e.setIsDetailTypeCost(arr.get(i).isDetailTypeCost());
			e.setIsDetailTypeRevenue(arr.get(i).isDetailTypeRevenue());
			e.setIsDetailWarehouse(arr.get(i).isDetailWarehouse());
			int parent_id = 0;
			if (arr.get(i).getParent_ID() > 0) {
				parent_id = mapConfig.get(arr.get(i).getParent_ID());
			}
			e.setParent_ID(parent_id);
			List<Object> param = PO.getBatchValueList(e, X_C_ElementValue.Table_ID, trxName, C_ElementValue_ID);
			values.add(param);
			if (values.size() >= BATCH_SIZE) {
				DB.excuteBatch(sql, values, trxName);
				values.clear();
			}
		}
		if (values.size() > 0) {
			DB.excuteBatch(sql, values, trxName);
			values.clear();
		}
	}
	
	private void createConfig(Properties ctx, String trxName, int AD_Org_ID, int AD_Client_ID) {
		List<MAccount> arr = new Query(ctx, X_C_Account.Table_Name, "AD_Client_ID = 11 AND IsActive = 'Y'", trxName)
				.list();
		String sql = PO.getSqlInsert(X_C_Account.Table_ID, trxName);
		
		MAccount e = null;
		List<List<Object>> values = new ArrayList<List<Object>>();
		for(int i = 0; i < arr.size(); i++) {
			int C_Account_ID = DB.getNextID(ctx, X_C_Account.Table_Name, trxName);
			int Account_ID = mapConfig.get(arr.get(i).getAccount_ID());
			e = new MAccount(ctx, 0, trxName);
			e.setAD_Org_ID(AD_Org_ID);
			e.setAD_Client_ID(AD_Client_ID);
			e.setC_Account_ID(C_Account_ID);
			e.setAccount_ID(Account_ID);
			e.setC_DocType_ID(arr.get(i).getC_DocType_ID());
			e.setC_DocTypeSub_ID(arr.get(i).getC_DocTypeSub_ID());
			e.setTypeAccount(arr.get(i).getTypeAccount());
			
			List<Object> param = PO.getBatchValueList(e, X_C_Account.Table_ID, trxName, C_Account_ID);
			values.add(param);
			if (values.size() >= BATCH_SIZE) {
				DB.excuteBatch(sql, values, trxName);
				values.clear();
			}
		}
		if (values.size() > 0) {
			DB.excuteBatch(sql, values, trxName);
			values.clear();
		}
	}
	*/
}
