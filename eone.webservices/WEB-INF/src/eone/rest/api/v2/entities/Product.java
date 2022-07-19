package eone.rest.api.v2.entities;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Product implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6839297626524090053L;
	public Product() {
	}
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty("product_id")
	public Integer getProductId() {
		return productId;
	}

	@JsonProperty("product_id")
	public void setProductId(Integer productId) {
		this.productId = productId;
	}


	private Integer productId;
	private String  value;
	private String 	name;

}

