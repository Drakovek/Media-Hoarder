package drakovek.hoarder.processing;

import java.time.LocalDateTime;

import drakovek.hoarder.file.DSettings;
import drakovek.hoarder.file.language.DefaultLanguage;

/**
 * Contains methods for getting converting times and dates into strings.
 * 
 * @author Drakovek
 * @version 2.0
 */
public class TimeMethods
{
	/**
	 * Value indicating short date format (DD/MM/YYYY, etc.)
	 */
	public static final int DATE_SHORT = 0;
	
	/**
	 * Value indicating long date format (DD Month YYYY, etc.)
	 */
	public static final int DATE_LONG = 1;
	
	/**
	 * Value indicating Month-Day-Year date format.
	 */
	public static final int DATE_MDY = 0;
	
	/**
	 * Value indicating Day-Month-Year date format.
	 */
	public static final int DATE_DMY = 1;
	
	/**
	 * Value indicating Year-Month-Day date format.
	 */
	public static final int DATE_YMD = 2;
	
	/**
	 * Value indicating a 12 hour clock format.
	 */
	public static final int HOUR_12 = 0;
	
	/**
	 * Value indicating a 24 hour clock format.
	 */
	public static final int HOUR_24 = 1;
	
	/**
	 * Returns a String representation of the current date/time using the program's default date and time format.
	 * 
	 * @param settings Program Settings
	 * @param dateLengthFormat Whether to use short or long date format
	 * @return String for date/time
	 */
	public static String getCurrentTimeString(DSettings settings, final int dateLengthFormat)
	{
		return getCurrentTimeString(settings, dateLengthFormat, settings.getDateFormat(), settings.getClockFormat());
		
	}//METHOD
	
	/**
	 * Returns a String representation of the current date/time.
	 * 
	 * @param settings Program Settings
	 * @param dateLengthFormat Whether to use short or long date format
	 * @param dateFormat Format for the date
	 * @param clockFormat Format for the clock time
	 * @return String for date/time
	 */
	public static String getCurrentTimeString(DSettings settings, final int dateLengthFormat, final int dateFormat, final int clockFormat)
	{
		LocalDateTime ldt = LocalDateTime.now();
		return getFullTimeString(settings, dateLengthFormat, dateFormat, clockFormat, ldt.getYear(), ldt.getMonthValue(), ldt.getDayOfMonth(), ldt.getHour(), ldt.getMinute(), ldt.getSecond());
		
	}//METHOD
	
	/**
	 * Returns a String representation of a given date/time.
	 * 
	 * @param settings Program Settings
	 * @param dateLengthFormat Whether to use short or long date format
	 * @param dateFormat Format for the date
	 * @param clockFormat Format for the clock time
	 * @param year Year
	 * @param month Month
	 * @param day Day of Month
	 * @param hour Hour
	 * @param minute Minute
	 * @param second Second of Minute
	 * @return String for date/time
	 */
	public static String getFullTimeString(DSettings settings, final int dateLengthFormat, final int dateFormat, final int clockFormat, final int year, final int month, final int day, final int hour, final int minute, final int second)
	{
		StringBuilder timeString = new StringBuilder();
		timeString.append(getDateString(settings, dateLengthFormat, dateFormat, year, month, day));
		timeString.append(" | "); //$NON-NLS-1$
		timeString.append(getTimeString(settings, clockFormat, hour, minute, second));
		return timeString.toString();
		
	}//METHOD
	
	/**
	 * Returns a String representation of a given time of day.
	 * 
	 * @param settings Program Settings
	 * @param time Long value of date and time
	 * @return Time String	 */
	public static String getTimeString(DSettings settings, final long time)
	{
		return getTimeString(settings, settings.getClockFormat(), time);
		
	}//METHOD
	
	/**
	 * Returns a String representation of a given time of day.
	 * 
	 * @param settings Program Settings
	 * @param clockFormat Format for the clock time
	 * @param time Long value of date and time
	 * @return Time String
	 */
	public static String getTimeString(DSettings settings, final int clockFormat, final long time)
	{
		try
		{
			String timeString = StringMethods.extendNumberString(Long.toString(time), 12);
			int hour = Integer.parseInt(timeString.substring(8, 10));
			int minute = Integer.parseInt(timeString.substring(10, 12));
			
			return getTimeString(settings, clockFormat, hour, minute, -1);
			
		}//TRY
		catch(Exception e)
		{
			return new String();
			
		}//CATCH
		
	}//METHOD
	
	/**
	 * Returns a String representation of a given time of day.
	 * 
	 * @param settings Program Settings
	 * @param clockFormat Format for the clock time
	 * @param hour Hour
	 * @param minute Minute
	 * @param second Second of Minute
	 * @return Time String
	 */
	public static String getTimeString(DSettings settings, final int clockFormat, final int hour, final int minute, final int second)
	{
		StringBuilder timeString = new StringBuilder();
		
		if(clockFormat == HOUR_24)
		{
			timeString.append(StringMethods.extendNumberString(hour, 2));
			timeString.append(':');
			timeString.append(StringMethods.extendNumberString(minute, 2));
			
			if(second > -1)
			{
				timeString.append(':');
				timeString.append(StringMethods.extendNumberString(second, 2));
				
			}//IF
			
		}//IF
		else
		{
			String ampm = settings.getLanguageText(DefaultLanguage.PM);
			if(hour < 12)
			{
				ampm = settings.getLanguageText(DefaultLanguage.AM);
				
			}//IF
			
			int newHour = hour;
			if(newHour == 0)
			{
				newHour = 12;
				
			}//IF
			else if(newHour > 12)
			{
				newHour -= 12;
			}//ELSE IF
			
			timeString.append(StringMethods.extendNumberString(newHour, 2));
			timeString.append(':');
			timeString.append(StringMethods.extendNumberString(minute, 2));
			timeString.append(' ');
			timeString.append(ampm);
			
			if(second > -1)
			{
				timeString.append(", "); //$NON-NLS-1$
				timeString.append(StringMethods.extendNumberString(second, 2));
				timeString.append(settings.getLanguageText(DefaultLanguage.SECONDS_SUFFIX));
			
			}//IF
			
		}//ELSE
		
		return timeString.toString();
		
	}//METHOD
	
	/**
	 * Returns a String representation of a given date.
	 * 
	 * @param settings Program Settings
	 * @param dateLengthFormat Whether to use short or long date format
	 * @param time Long value of date and time
	 * @return Date String
	 */
	public static String getDateString(DSettings settings, final int dateLengthFormat, final long time)
	{
		return getDateString(settings, dateLengthFormat, settings.getDateFormat(), time);

	}//METHOD
	
	/**
	 * Returns a String representation of a given date.
	 * 
	 * @param settings Program Settings
	 * @param dateLengthFormat Whether to use short or long date format
	 * @param dateFormat Format for the date
	 * @param time Long value of date and time
	 * @return Date String
	 */
	public static String getDateString(DSettings settings, final int dateLengthFormat, final int dateFormat, final long time)
	{
		try
		{
			String timeString = StringMethods.extendNumberString(Long.toString(time), 12);
			int year = Integer.parseInt(timeString.substring(0, 4));
			int month = Integer.parseInt(timeString.substring(4, 6));
			int day = Integer.parseInt(timeString.substring(6, 8));
			
			return getDateString(settings, dateLengthFormat, dateFormat, year, month, day);
			
		}//TRY
		catch(Exception e)
		{
			return new String();
			
		}//CATCH
		
	}//METHOD
	
	/**
	 * Returns a String representation of a given date.
	 * 
	 * @param settings Program Settings
	 * @param dateLengthFormat Whether to use short or long date format
	 * @param dateFormat Format for the date
	 * @param year Year
	 * @param month Month
	 * @param day Day of Month
	 * @return Date String
	 */
	public static String getDateString(DSettings settings, final int dateLengthFormat, final int dateFormat, final int year, final int month, final int day)
	{
		StringBuilder dateString = new StringBuilder();
		
		if(dateLengthFormat == DATE_SHORT)
		{
			switch(dateFormat)
			{
				case DATE_MDY:
					dateString.append(StringMethods.extendNumberString(month, 2));
					dateString.append('/');
					dateString.append(StringMethods.extendNumberString(day, 2));
					dateString.append('/');
					dateString.append(StringMethods.extendNumberString(year, 2));
					break;
				case DATE_DMY:
					dateString.append(StringMethods.extendNumberString(day, 2));
					dateString.append('/');
					dateString.append(StringMethods.extendNumberString(month, 2));
					dateString.append('/');
					dateString.append(StringMethods.extendNumberString(year, 2));
					break;
				case DATE_YMD:
					dateString.append(StringMethods.extendNumberString(year, 2));
					dateString.append('/');
					dateString.append(StringMethods.extendNumberString(month, 2));
					dateString.append('/');
					dateString.append(StringMethods.extendNumberString(day, 2));
					break;
					
			}//SWITCH
			
		}//IF
		else if(month > 0 && month <= DefaultLanguage.MONTHS.length)
		{
			switch(dateFormat)
			{
				case DATE_MDY:
					dateString.append(settings.getLanguageText(DefaultLanguage.MONTHS[month - 1]));
					dateString.append(' ');
					dateString.append(StringMethods.extendNumberString(day, 2));
					dateString.append(',');
					dateString.append(' ');
					dateString.append(StringMethods.extendNumberString(year, 2));
					break;
				case DATE_DMY:
					dateString.append(StringMethods.extendNumberString(day, 2));
					dateString.append(' ');
					dateString.append(settings.getLanguageText(DefaultLanguage.MONTHS[month - 1]));
					dateString.append(',');
					dateString.append(' ');
					dateString.append(StringMethods.extendNumberString(year, 2));
					break;
				case DATE_YMD:
					dateString.append(StringMethods.extendNumberString(year, 2));
					dateString.append(',');
					dateString.append(' ');
					dateString.append(settings.getLanguageText(DefaultLanguage.MONTHS[month - 1]));
					dateString.append(' ');
					dateString.append(StringMethods.extendNumberString(day, 2));
					break;
					
			}//SWITCH
			
		}//ELSE
		
		return dateString.toString();
		
	}//METHOD
	
}//CLASS
