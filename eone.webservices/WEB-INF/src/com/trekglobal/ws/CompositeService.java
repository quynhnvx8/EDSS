package com.trekglobal.ws;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.idempiere.adInterface.x10.CompositeRequestDocument;
import org.idempiere.adInterface.x10.CompositeResponsesDocument;
import org.springframework.web.bind.annotation.RestController;

@Path("/composite_service/")
@Consumes("application/json") 
@Produces("application/json")
@WebService(targetNamespace="http://dssvn.com")
//@SOAPBinding(style=Style.RPC,use=Use.LITERAL,parameterStyle=ParameterStyle.WRAPPED)
@RestController
public interface CompositeService {

	/**
	 * 
	 * @param reqs
	 * @return CompositeResponsesDocument 
	 */
    @POST
	@Path("/composite_operation") 
	public CompositeResponsesDocument compositeOperation(CompositeRequestDocument reqs);
}
