
package eone.db;

import java.sql.Connection;
import java.util.logging.Level;

import eone.exceptions.DBException;
import eone.util.CStatementVO;
import eone.util.Trx;

/**
 * Dynamic proxy for the CCallableStatement
 * @author Low Heng Sin
 */
public class CallableStatementProxy extends PreparedStatementProxy {

	public CallableStatementProxy(CStatementVO vo) {
		super(vo);
	}

	public CallableStatementProxy(int resultSetType, int resultSetConcurrency,
			String sql0, String trxName) {
		super(resultSetType, resultSetConcurrency, sql0, trxName);
	}
	
	/**
     * Initialise the prepared statement wrapper object 
     */
    protected void init()
    {
        try
        {
            Connection conn = null;
            Trx trx = p_vo.getTrxName() == null ? null : Trx.get(p_vo.getTrxName(), true);
            if (trx != null)
            {
                conn = trx.getConnection();
            }
            else
            {
            	m_conn = AutoCommitConnectionBroker.getConnection();
                conn = m_conn;
            }
            if (conn == null)
                throw new DBException("No Connection");
            p_stmt = conn.prepareCall(p_vo.getSql(), p_vo.getResultSetType(), p_vo.getResultSetConcurrency());
            return;
        }
        catch (Exception e)
        {
            log.log(Level.SEVERE, p_vo.getSql(), e);
            throw new DBException(e);
        }
    }
}
