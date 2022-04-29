package eone.adinterface;


import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.idempiere.adInterface.x10.ModelCRUDRequestDocument;
import org.idempiere.adInterface.x10.ModelGetListRequestDocument;
import org.idempiere.adInterface.x10.StandardResponseDocument;
import org.idempiere.adInterface.x10.WindowTabDataDocument;

@Path("/model_adservice/")
@Consumes({"application/xml", "application/json"}) 
@Produces({"application/xml", "application/json"})
@WebService(targetNamespace="http://dssvn.com")
@SOAPBinding(style=Style.RPC,use=Use.LITERAL,parameterStyle=ParameterStyle.WRAPPED)
public interface ModelADService {

    
    @POST
	@Path("/get_list") 
    public WindowTabDataDocument getList(ModelGetListRequestDocument req);

    @POST
	@Path("/create_data") 
    public StandardResponseDocument createData(ModelCRUDRequestDocument req);

    @POST
	@Path("/update_data") 
    public StandardResponseDocument updateData(ModelCRUDRequestDocument req);

    @POST
 	@Path("/delete_data") 
    public StandardResponseDocument deleteData(ModelCRUDRequestDocument req);

	@POST
	@Path("/read_data") 
    public WindowTabDataDocument readData(ModelCRUDRequestDocument req);
    
	@POST
	@Path("/query_data") 
    public WindowTabDataDocument queryData(ModelCRUDRequestDocument req);
    
	@POST
	@Path("/create_update_data") 
	public StandardResponseDocument createUpdateData(ModelCRUDRequestDocument req);

}
