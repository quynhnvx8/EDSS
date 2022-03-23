

package com.akunagroup.uk.postcode;

public class Postcode implements AddressInterface
{
	
	// required implementation by interface
	private String Street1;
	private String Street2;
	private String Street3;
	private String Street4;
	private String Street5;
	private String Comments;
	private String City;
	private String Region;
	private String Postcode;
	private String Country;

	// UK Postcode specific
	private String Addr;	// Full address in one variable
	private String CountryCode;	// Two Letter ISO Country Code
	private String TradCounty;	// Traditional County (Region)
	private String AdminCounty;	// Administrative County
	private String LonLocation;	// London Location
	
	public int size()
	{
		return 1;
	}
		
	public String getAddr()
	{
		return Addr;
	}
	
	public void setAddr(String newAddr)
	{
		Addr = newAddr;
	}
	
	public String getStreet1()
	{
		return Street1;
	}
	
	public void setStreet1(String newStreet1)
	{
		Street1 = newStreet1;
	}
	public String getStreet2()
	{
		return Street2;
	}
	
	public void setStreet2(String newStreet2)
	{
		Street2 = newStreet2;
	}
	public String getStreet3()
	{
		return Street3;
	}
	
	public void setStreet3(String newStreet3)
	{
		Street3 = newStreet3;
	}
	public String getStreet4()
	{
		return Street4;
	}
	
	public void setStreet4(String newStreet4)
	{
		Street4 = newStreet4;
	}
	public String getStreet5()
	{
		return Street5;
	}
	
	public void setStreet5(String newStreet5)
	{
		Street4 = newStreet5;
	}
	public String getComments()
	{
		return Comments;
	}
	
	public void setComments(String newComments)
	{
		Street4 = newComments;
	}
	
	public String getCity()
	{
		return City;
	}
	
	public void setCity(String newCity)
	{
		City = newCity;
	}

	public String getRegion()
	{
		return Region;
	}
	
	public void setRegion(String newRegion)
	{
		Region = newRegion;
	}
	public String getPostcode()
	{
		return Postcode;
	}
	
	public void setPostcode(String newPostcode)
	{
		Postcode = newPostcode;
	}
	public String getCountry()
	{
		return Country;
	}
	
	public void setCountry(String newCountry)
	{
		Country = newCountry;
	}
	public String getCountryCode()
	{
		return CountryCode;
	}
	
	public void setCountryCode(String newCountryCode)
	{
		CountryCode = newCountryCode;
	}
	public String getTradCounty()
	{
		return TradCounty;
	}
	
	public void setTradCounty(String newTradCounty)
	{
		TradCounty = newTradCounty;
	}
	public String getAdminCounty()
	{
		return AdminCounty;
	}
	
	public void setAdminCounty(String newAdminCounty)
	{
		AdminCounty = newAdminCounty;
	}
	public String getLonLocation()
	{
		return LonLocation;
	}
	
	public void setLonLocation(String newLonLocation)
	{
		LonLocation = newLonLocation;
	}

}
