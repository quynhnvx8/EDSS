package eone.base.db;

import java.util.ArrayList;

import eone.db.Convert;
import eone.util.CLogger;

public class Convert_Oracle extends Convert {

	/** Logger */
	private static final CLogger log = CLogger.getCLogger(Convert_Oracle.class);

	public Convert_Oracle() {}
	
	@Override
	protected ArrayList<String> convertStatement(String sqlStatement) {
		ArrayList<String> result = new ArrayList<String>();
		result.add(sqlStatement);
		if ("true".equals(System.getProperty("org.idempiere.db.oracle.debug"))) {
			log.warning("Oracle -> " + sqlStatement);
		}
		return result;
	}

	@Override
	public boolean isOracle() {
		return true;
	}

}
