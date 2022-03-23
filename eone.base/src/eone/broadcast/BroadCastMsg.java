
package eone.broadcast;

import java.io.Serializable;

/**
 * 
 * @author Deepak Pansheriya
 *
 */
public class BroadCastMsg implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7669279373526944539L;

	private String src;
	private int intData;
	private String target;
	private int eventId;
	
	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String targetNode) {
		this.target = targetNode;
	}

	public String getSrc() {
		return src;
	}
	
	public void setSrc(String src) {
		this.src = src;
	}

	public int getIntData() {
		return intData;
	}
	
	public void setIntData(int messageId) {
		this.intData = messageId;
	}
	
	
}
