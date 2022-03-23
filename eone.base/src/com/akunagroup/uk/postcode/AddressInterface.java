
package com.akunagroup.uk.postcode;


/**
 * Interface for Address Lookup Web Service.
 * http://sourceforge.net/tracker/index.php?func=detail&aid=1741222&group_id=176962&atid=879335
 * The Address Structure
 */
public interface AddressInterface
{
	public int size();

	public String getStreet1();
	public void setStreet1(String newStreet1);
	public String getStreet2();
	public void setStreet2(String newStreet2);
	public String getStreet3();
	public void setStreet3(String newStreet3);
	public String getStreet4();
	public void setStreet4(String newStreet4);
	public String getStreet5();
	public void setStreet5(String newStreet5);
	public String getComments();
	public void setComments(String newComments);
	public String getCity();
	public void setCity(String newCity);
	public String getRegion();
	public void setRegion(String newRegion);
	public String getPostcode();
	public void setPostcode(String newPostcode);
	public String getCountry();
	public void setCountry(String newCountry);
	public String getCountryCode();
	public void setCountryCode(String newCountryCode);
}