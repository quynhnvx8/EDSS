package eone.base.process;

import java.util.Properties;

import eone.base.model.MBPartner;
import eone.base.model.MDepartment;
import eone.base.model.MOrg;
import eone.base.model.MWarehouse;

public class CreateOrUpdateRelatedOrg  {
	
	public static String processData(int AD_Org_ID, Properties ctx, String trxName) {
		MOrg org = MOrg.get(ctx, AD_Org_ID);
		MDepartment dept = MDepartment.get(ctx, AD_Org_ID);
		if (dept == null) {
			dept = new MDepartment(ctx, 0, trxName);
			dept.setAD_Department_ID(AD_Org_ID);//Lay Department_DI = AD_Org_ID luon. Da xu ly Sequence dai khac			
		}
		if (dept.getValue() != org.getValue())
			dept.setValue(org.getValue());
		if (dept.getName() != org.getName())
			dept.setName(org.getName());
		dept.setDescription("This record have been created from Org. Don't delete !");
		dept.setIsAutoCreate(true);
		//dept.setIsCreateBPartner(true);
		dept.setAD_Org_ID(AD_Org_ID);
		if(!dept.save())
			return "Update Department false !";
		
		MWarehouse wh = MWarehouse.get(ctx, AD_Org_ID);
		if (wh == null) {
			wh = new MWarehouse(ctx, 0, trxName);
			wh.setM_Warehouse_ID(AD_Org_ID);//Tuong tu nhu Department.
		}
		wh.setName(org.getName());
		wh.setDescription("This record have been created from Org. Don't delete !");
		wh.setIsAutoCreate(true);
		wh.setValue(org.getValue());
		wh.setAD_Department_ID(dept.getAD_Department_ID());
		wh.setIsDefault(true);
		wh.setAD_Org_ID(AD_Org_ID);
		if (!wh.save())
			return "Update warehouse false!";
		
		MBPartner bp = MBPartner.get(ctx, AD_Org_ID);
		if (bp == null) {
			bp = new MBPartner(ctx, 0, trxName);
			bp.setGroupType(MBPartner.GROUPTYPE_Org);
		}
		bp.setValue(org.getValue());
		bp.setName(org.getName());
		bp.setName2(org.getName());
		bp.setAD_Department_ID(dept.getAD_Department_ID());
		bp.setIsAutoCreate(true);
		bp.setAD_Org_ID(AD_Org_ID);
		
		if (!bp.save())
			return "Update BPartner false!";
		
		return "Update Department, Warehouse, BPartner Success!";
	}

}
