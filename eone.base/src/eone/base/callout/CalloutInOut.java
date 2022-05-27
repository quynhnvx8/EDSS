
package eone.base.callout;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.Properties;

import eone.base.model.CalloutEngine;
import eone.base.model.GridField;
import eone.base.model.GridTab;
import eone.base.model.MDocType;
import eone.base.model.MInOut;
import eone.base.model.MInOutLine;
import eone.base.model.MPrice;
import eone.base.model.MProduct;
import eone.base.model.MStorage;
import eone.base.model.MTax;
import eone.base.model.X_M_InOut;
import eone.util.Env;

//eone.base.callout.CalloutInOut.fillAmount_QtyPrice
//eone.base.callout.CalloutInOut.fillAmount_Discount
//eone.base.callout.CalloutInOut.getPricePOTable
//eone.base.callout.CalloutInOut.getPriceSOTable

public class CalloutInOut extends CalloutEngine
{

	/*
	 * Dùng để lấy giá trong bảng giá
	 */
	public String getPricePOTable (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		if (isCalloutActive())	
			return "";
		if (value == null)
			return "";
		Integer m_M_Product_ID = Integer.valueOf(value.toString());
		MProduct product = MProduct.get(ctx, m_M_Product_ID.intValue());
		if (product != null && product.getPricePO() != null) {
			if (mTab.getValue("PricePO") != null)
				mTab.setValue("PricePO", product.getPricePO());
			if (mTab.getValue("Price") != null)
				mTab.setValue("Price", product.getPricePO());
			if (mTab.getValue("Price") == null && mTab.getValue("PricePO") == null)
				mTab.setValue("Amount", product.getPricePO());
		} else {
			mTab.setValue("PricePO", Env.ZERO);
			mTab.setValue("Price", Env.ZERO);
			mTab.setValue("Amount", Env.ZERO);
		}
		return "";
	}
	
	public String getPriceSOTable (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		if (isCalloutActive())	
			return "";
		if (value == null)
			return "";
		Integer m_M_Product_ID = Integer.valueOf(value.toString());
		MProduct product = MProduct.get(ctx, m_M_Product_ID.intValue());
		if (product != null && product.getPriceSO() != null) {
			if (mTab.getValue("PriceSO") != null)
				mTab.setValue("PriceSO", product.getPriceSO());
			if (mTab.getValue("Price") != null)
				mTab.setValue("Price", product.getPriceSO());
			if (mTab.getValue("Price") == null && mTab.getValue("PriceSO") == null)
				mTab.setValue("Amount", product.getPriceSO());
		} else {
			mTab.setValue("PricePO", Env.ZERO);
			mTab.setValue("Price", Env.ZERO);
			mTab.setValue("Amount", Env.ZERO);
		}
		return "";
	}

	public String fillAmount_QtyPrice (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		if (isCalloutActive())	
			return "";

		Object objQty = mTab.getValue(MInOutLine.COLUMNNAME_Qty);
		Object objPrice = mTab.getValue(MInOutLine.COLUMNNAME_Price);
		
		Object objTax = mTab.getValue("C_Tax_ID");
		Object objInOut = mTab.getValue("M_InOut_ID");
		int p_M_InOut_ID = 0;
		if (objInOut != null) {
			p_M_InOut_ID = Integer.parseInt(objInOut.toString());
		}
		MInOut inout = MInOut.get(ctx, p_M_InOut_ID);
		MDocType mDocType = MDocType.get(ctx, inout.getC_DocType_ID());
		
		BigDecimal qty = Env.ZERO;
		BigDecimal price = Env.ZERO;
		BigDecimal p_Amount = Env.ZERO;
		BigDecimal p_TaxAmt = Env.ZERO;
		BigDecimal p_TaxBase = Env.ZERO;
		BigDecimal taxRate = Env.ZERO;
		
		if (objQty == null) {
			objQty = "0";			
		}
		if (objPrice == null) {
			objPrice = "0";
		}
		
		if (objTax == null) {
			objTax = 0;
		}
		
		//Lấy số lượng trên form
		qty = new BigDecimal(objQty.toString());
		
		//Lấy lại số lượng khi thay đổi cột số lượng
		if (MInOutLine.COLUMNNAME_Qty.equalsIgnoreCase(mField.getColumnName())) {
			objQty = mTab.getValue(MInOutLine.COLUMNNAME_Qty);
			if (objQty == null)
				return "";	
			qty = new BigDecimal(objQty.toString());
		}
		//Cot gia xoa ve 0
		if (MInOutLine.COLUMNNAME_Price.equalsIgnoreCase(mField.getColumnName())) {
			objPrice = mTab.getValue(MInOutLine.COLUMNNAME_Price);
			if (objPrice == null)
				return "";	
			price = new BigDecimal(objPrice.toString());
		}
		
		//Nhap don gia hoac so luong
		BigDecimal pricePO = Env.ZERO;
		if (MDocType.DOCTYPE_Output.equals(mDocType.getDocType())) {
			
			//Lay kho xuat (Kho co)
			int M_Warehouse_ID = inout.getM_Warehouse_Cr_ID();
			Timestamp dateAcct = inout.getDateAcct();
			//Lay Product
			Object objProduct_ID = mTab.getValue(MInOutLine.COLUMNNAME_M_Product_ID);
			if (objProduct_ID == null)
				return "";
			int M_Product_ID = Integer.parseInt(objProduct_ID.toString());
			
			//Lấy giá xuất vật tư hàng hóa trong bảng M_Storage
			price = MStorage.getPriceStorage(M_Product_ID, M_Warehouse_ID, dateAcct, null, qty);
			pricePO = price;
			
			//Nếu là bán hàng
			if (MDocType.DOCBASETYPE_ExportWarehouseForSell.equals(mDocType.getDocBaseType())) {
				
				MPrice priceSell = MPrice.getPriceByDate(ctx, null, M_Product_ID, dateAcct);
				if (priceSell != null) {
					price = priceSell.getPriceSO();
				}
			} 
			
			if (price.compareTo(Env.ZERO) > 0) {
				
				p_Amount = qty.multiply(price);
				p_Amount = p_Amount.setScale(Env.getScaleFinal(), RoundingMode.HALF_UP);
			
				mTab.setValue(MInOutLine.COLUMNNAME_Price, price);
				mTab.setValue(MInOutLine.COLUMNNAME_PricePO, pricePO);
				mTab.setValue(MInOutLine.COLUMNNAME_Amount, p_Amount);
				
			} 
			else
			{//trong hop khong co data tu m_storage
				objQty = mTab.getValue(MInOutLine.COLUMNNAME_Qty);
				objPrice = mTab.getValue(MInOutLine.COLUMNNAME_Price);
				if (objQty == null) {
					objQty = "0";
				}
				if (objPrice == null) {
					objPrice = "0";
				}
				qty = new BigDecimal(objQty.toString());
				price = new BigDecimal(objPrice.toString());
				mTab.setValue(MInOutLine.COLUMNNAME_Amount, qty.multiply(price));
				
			}//end truong hop ko co data
			
		} else {
			//Cac type nhap khac
			if (MInOutLine.COLUMNNAME_Qty.equalsIgnoreCase(mField.getColumnName()) 
					|| MInOutLine.COLUMNNAME_Price.equalsIgnoreCase(mField.getColumnName())
					|| "C_Tax_ID".equalsIgnoreCase(mField.getColumnName())
					) {
				
	 			objQty = mTab.getValue(MInOutLine.COLUMNNAME_Qty);
				objPrice = mTab.getValue(MInOutLine.COLUMNNAME_Price);
				if (objQty == null) {
					objQty = "0";
				}
				if (objPrice == null) {
					objPrice = "0";
				}
				qty = new BigDecimal(objQty.toString());
				price = new BigDecimal(objPrice.toString());
											
			}
		}//End Type nhap khac
		
		
		if(!X_M_InOut.INCLUDETAXTAB_NONE.equalsIgnoreCase(inout.getIncludeTaxTab())) {
			MTax tax = MTax.get(Env.getCtx(), inout.getC_Tax_ID());
			if (tax != null) 
				taxRate = tax.getRate();
		} else {
			taxRate = Env.ZERO;
		}
		
		p_Amount = qty.multiply(price);
		//Tinh lai Amount va AmountConvert neu co thue
		if (taxRate.compareTo(Env.ZERO) != 0) {
			p_TaxBase = p_Amount.multiply(Env.ONE.add(taxRate));
			p_TaxAmt = p_TaxBase.subtract(p_Amount);
			mTab.setValue("TaxAmt", p_TaxAmt);
			mTab.setValue("TaxBaseAmt", p_TaxBase);
		}
		mTab.setValue("Amount", p_Amount);
		return "";
	}

	public String fillAmount_Discount (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		if (isCalloutActive())	
			return "";

		Object objDiscountType = mTab.getValue(MInOutLine.COLUMNNAME_DiscountType);
		Object objAmount = mTab.getValue(MInOutLine.COLUMNNAME_Amount);
		Object objBaseAmt = mTab.getValue(MInOutLine.COLUMNNAME_TaxBaseAmt);
		
		BigDecimal p_Amount = Env.ZERO;
		
		if (objAmount != null) {
			
		}
		p_Amount = new BigDecimal(objBaseAmt.toString());
		
		if (objDiscountType != null) {
			if (!MInOutLine.DISCOUNTTYPE_NONE.equals(objDiscountType.toString())) {
				mTab.setValue("DiscountAmt", p_Amount);
			} else {
				mTab.setValue("DiscountAmt", Env.ZERO);
			}
		}
		
		return "";
	}
	
}	//	CalloutInOut