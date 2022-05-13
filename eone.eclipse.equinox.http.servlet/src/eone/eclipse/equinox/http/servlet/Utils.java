package eone.eclipse.equinox.http.servlet;

public class Utils {
	public static String TO_STRING (String txt)
	{
		return TO_STRING (txt, 0);
	}   //  TO_STRING

	private static final char QUOTE = '\'';
	
	public static String TO_STRING (String txt, int maxLength)
	{
		if (txt == null || txt.length() == 0)
			return "NULL";

		//  Length
		String text = txt;
		if (maxLength != 0 && text.length() > maxLength)
			text = txt.substring(0, maxLength);

		//  copy characters		(we need to look through anyway)
		StringBuilder out = new StringBuilder();
		out.append(QUOTE);		//	'
		for (int i = 0; i < text.length(); i++)
		{
			char c = text.charAt(i);
			if (c == QUOTE)
				out.append("''");
			else
				out.append(c);
		}
		out.append(QUOTE);		//	'
		//
		return out.toString();
	}

}
