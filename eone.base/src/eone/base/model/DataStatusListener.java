
package eone.base.model;

import java.util.EventListener;

/**
 *  Data Status Interface
 *
 *  @author 	Jorg Janke
 *  @version 	$Id: DataStatusListener.java,v 1.3 2006/07/30 00:51:05 jjanke Exp $
 */
public interface DataStatusListener extends EventListener
{
	/**
	 *  Data Changed
	 *  @param e event
	 */
	public void dataStatusChanged(DataStatusEvent e);
}	//	DataStatusListener
