package org.idempiere.webservices;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SInvoice {
	
	@GetMapping(value = "/sinvoice")
	public String getData(@RequestBody String data) {
		data = "Test";
		return data;
	}
}
