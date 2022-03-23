

package eone.webui.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;

import eone.base.process.ProcessInfoParameter;
import eone.base.process.SvrProcess;
import eone.broadcast.BroadCastMsg;
import eone.broadcast.BroadCastUtil;
import eone.broadcast.BroadcastMsgUtil;
import eone.util.CLogger;
import eone.util.DB;

/**
 * 
 * @author Deepak Pansheriya
 *
 */
public class KillAllSession extends SvrProcess {
	private static final CLogger logger = CLogger
			.getCLogger(KillAllSession.class);
	private int scndTimeout = 0;


	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++) {
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("TimeOutInSeconds"))
				scndTimeout = para[i].getParameterAsInt();
		}
	}

	@Override
	protected String doIt() throws Exception {


		String sql = "SELECT servername FROM ad_session WHERE ad_session_id=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, getRecord_ID());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				BroadCastMsg msg = new BroadCastMsg();
				msg.setEventId(BroadCastUtil.EVENT_SESSION_ONNODE_TIMEOUT);
				msg.setIntData(scndTimeout);
				msg.setTarget(rs.getString("servername"));
				BroadcastMsgUtil.pushToQueue(msg,false);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "servername could not be retrieved", e);
			throw new IllegalStateException(e.getMessage());
		} finally {
			DB.close(rs, pstmt);
		}
		
		return "Session Time Out Initiated";
	}
}
