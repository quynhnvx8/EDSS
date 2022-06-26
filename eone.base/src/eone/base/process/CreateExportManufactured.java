
package eone.base.process;

/**
 *	Copy Order and optionally close
 *	
 */
public class CreateExportManufactured extends SvrProcess
{
	
	protected void prepare()
	{
		
	}	//	prepare

	
	protected String doIt() throws Exception
	{
		
		return "@C_Order_ID@ @Created@";
	}	//	doIt

}