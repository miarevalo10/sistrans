package dateUtilities;

import java.util.Date;
import java.util.Locale;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.*;

public class DateParser {
	
	/* 
	 * String string = "January 2, 2010";
DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
Date date = format.parse(string);
System.out.println(date); // Sat Jan 02 00:00:00 GMT 2010
	 * */
	public static Date fromStringToDate (String stringDate) throws ParseException{
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		Date date = format.parse(stringDate);
		return date;
	}
	
	// falta comparar dos fechas
}
