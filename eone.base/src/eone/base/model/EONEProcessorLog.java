package eone.base.model;

import java.sql.Timestamp;

public interface EONEProcessorLog
{
	public Timestamp getCreated();
	
	public String getSummary ();

	public String getDescription ();

	public boolean isError ();

	public String getReference ();

	public String getTextMsg (); 
	
}
