
package org.compiere.util;


public final class KeyNamePair extends NamePair
{

	private static final long serialVersionUID = 6347385376010388473L;
	
	public static final KeyNamePair EMPTY = new KeyNamePair(-1, "");


	public KeyNamePair(int key, String name)
	{
		super(name);
		m_key = key;
	}   //  KeyNamePair

	/** The Key         */
	private int 	m_key = 0;

	/**
	 *	Get Key
	 *  @return key
	 */
	public int getKey()
	{
		return m_key;
	}	//	getKey

	/**
	 *	Get ID (key as String)
	 *
	 *  @return String value of key or null if -1
	 */
	public String getID()
	{
		if (m_key == -1)
			return null;
		return String.valueOf(m_key);
	}	//	getID


	/**
	 *	Equals
	 *  @param obj object
	 *  @return true if equal
	 */
	public boolean equals(Object obj)
	{
		if (obj instanceof KeyNamePair)
		{
			KeyNamePair pp = (KeyNamePair)obj;
			if (pp.getKey() == m_key
				&& pp.getName() != null
				&& pp.getName().equals(getName()))
				return true;
			return false;
		}
		return false;
	}	//	equals

	/**
	 *  Return Hashcode of key
	 *  @return hascode
	 */
	public int hashCode()
	{
		return m_key;
	}   //  hashCode

}	//	KeyNamePair
