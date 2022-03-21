package eone.base.model;

import java.sql.Timestamp;
import java.util.Properties;

import eone.exceptions.EONEException;

public interface EONEProcessor
{
	public int getAD_Client_ID();	
	
	public String getName();

	public String getDescription();

	public Properties getCtx();
	
	public String getFrequencyType();

	public String getScheduleType();

	public String getCronPattern();

	public int getFrequency();

	public String getServerID();

	public Timestamp getDateNextRun (boolean requery);

	public void setDateNextRun(Timestamp dateNextWork);

	public Timestamp getDateLastRun ();

	public void setDateLastRun(Timestamp dateLastRun);

	public boolean save();

	public void saveEx() throws EONEException;
	
	public EONEProcessorLog[] getLogs();
	
}
