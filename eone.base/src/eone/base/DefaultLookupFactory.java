
package eone.base;

import static eone.util.DisplayType.Account;
import static eone.util.DisplayType.Location;
import static eone.util.DisplayType.Locator;
import static eone.util.DisplayType.PAttribute;
import static eone.util.DisplayType.Payment;

import eone.base.model.GridFieldVO;
import eone.base.model.Lookup;
import eone.base.model.MAccountLookup;
import eone.base.model.MLookup;
import eone.base.model.MPAttributeLookup;
import eone.base.model.MPaymentLookup;
import eone.util.DisplayType; 

/**
 * @author Jan Thielemann - jan.thielemann@evenos.de
 * @author hengsin
 *
 */
public class DefaultLookupFactory implements ILookupFactory{

	@Override
	public Lookup getLookup(GridFieldVO gridFieldVO) {
		Lookup lookup = null;
		if (gridFieldVO.lookupInfo == null && DisplayType.isLookup(gridFieldVO.displayType)) // IDEMPIERE-913
			gridFieldVO.loadLookupInfo();
		if (gridFieldVO.displayType == Account)    //  not cached
		{
			lookup = new MAccountLookup (gridFieldVO.ctx, gridFieldVO.WindowNo);
		}
		else if (gridFieldVO.displayType == PAttribute)    //  not cached
		{
			lookup = new MPAttributeLookup (gridFieldVO.ctx, gridFieldVO.WindowNo);
		}
		else if (gridFieldVO.displayType == Payment)
		{
			lookup = new MPaymentLookup (gridFieldVO.ctx, gridFieldVO.WindowNo, gridFieldVO.ValidationCode);
		}
		else if (DisplayType.isLookup(gridFieldVO.displayType) && gridFieldVO.lookupInfo != null)
		{
			lookup = new MLookup (gridFieldVO.lookupInfo, gridFieldVO.TabNo);
		}
		return lookup;
	}

	@Override
	public boolean isLookup(GridFieldVO gridFieldVO) {
		if (gridFieldVO.displayType == Location
			|| gridFieldVO.displayType == Locator
			|| gridFieldVO.displayType == Account
			|| gridFieldVO.displayType == PAttribute
			|| gridFieldVO.displayType == Payment
			|| DisplayType.isLookup(gridFieldVO.displayType))
			return true;
				
		return false;
	}

}
