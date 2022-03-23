/******************************************************************************
 * Copyright (C) 2012 Elaine Tan                                              *
 * Copyright (C) 2012 Trek Global
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
package eone.util;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParsePosition;

/**
 * 
 * @author Elaine
 *
 */
public class PaymentUtil {

	
	public static String encrpytCreditCard(String value) {
		if (value == null)
			return "";
		else

		if (value.length() <= 4)
			return value;

		Integer valueLength = value.length();

		StringBuilder encryptedCC = new StringBuilder();

		for (int i = 0; i < (valueLength - 4); i++) {
			encryptedCC.append("0");
		}

		encryptedCC.append(value.substring(valueLength - 4, valueLength));

		return encryptedCC.toString();
	}

	public static String encrpytCvv(String creditCardVV) {
		if (creditCardVV == null)
			return "";
		else {
			Integer valueLength = creditCardVV.length();

			StringBuilder encryptedCC = new StringBuilder();

			for (int i = 0; i < valueLength; i++) {
				encryptedCC.append("0");
			}
			return encryptedCC.toString();
		}
	}

	public static boolean isNumeric(String str) {
		if (str != null && str.length() > 0) {
			NumberFormat formatter = NumberFormat.getInstance();
			ParsePosition pos = new ParsePosition(0);

			formatter.parse(str, pos);
			return str.length() == pos.getIndex();
		}
		return true;
	}
	
	public static int getPayAmtInCents(BigDecimal payAmt)
	{
		if (payAmt == null)
			return 0;
		
		BigDecimal bd = payAmt.multiply(Env.ONEHUNDRED);
		return bd.intValue();
	}
	
	public static String getCreditCardExp(int creditCardExpMM, int creditCardExpYY, String delimiter)
	{
		String mm = String.valueOf(creditCardExpMM);
		String yy = String.valueOf(creditCardExpYY);

		StringBuilder retValue = new StringBuilder();
		if (mm.length() == 1)
			retValue.append("0");
		retValue.append(mm);
		//
		if (delimiter != null)
			retValue.append(delimiter);
		//
		if (yy.length() == 1)
			retValue.append("0");
		retValue.append(yy);
		//
		return (retValue.toString());
	}
}
