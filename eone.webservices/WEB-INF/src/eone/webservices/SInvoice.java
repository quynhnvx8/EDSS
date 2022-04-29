package eone.webservices;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.springframework.web.bind.annotation.RestController;

@RestController
@WebService (serviceName = "SInvoice", targetNamespace = "http://dssvn.com")
public class SInvoice {
	
	@WebMethod(operationName = "getData")
	public String getData(String username, String password) {
		return username + password;
	}
}
