package drakovek.hoarder.processing;

import java.time.LocalDateTime;

import drakovek.hoarder.file.DSettings;
import drakovek.hoarder.file.language.DefaultLanguage;

/**
 * Contains methods for getting converting times and dates into strings.
 * 
 * @author Drakovek
 * @version 2.0
 * @since 2.0
 */
public class TimeMethods
{
	/**
	 * Value indicating Month-Day-Year date format.
	 * 
	 * @since 2.0
	 */
	public static final int DATE_MDY = 0;
	
	/**
	 * Value indicating Day-Month-Year date format.
	 * 
	 * @since 2.0
	 */
	public static final int DATE_DMY = 1;
	
	/**
	 * Value indicating Year-Month-Day date format.
	 * 
	 * @since 2.0
	 */
	public static final int DATE_YMD = 2;
	
	/**
	 * Value indicating a 12 hour clock format.
	 * 
	 * @since 2.0
	 */
	public static final int HOUR_12 = 0;
	
	/**
	 * Value indicating a 24 hour clock format.
	 * 
	 * @since 2.0
	 */
	public static final int HOUR_24 = 1;
	
	/**
	 * Returns a String representation of the current date/time using the program's default date and time format.
	 * 
	 * @param settings Program Settings
	 * @return String for date/time
	 * @since 2.0
	 */
	public static String getCurrentTimeString(DSettings settings)
	{
		return getCurrentTimeString(settings, settings.getDateFormat(), settings.getClockFormat());
		
	}//METHOD
	
	/**
	 * Returns a String representation of the current date/time.
	 * 
	 * @param settings Program Settings
	 * @param dateFormat Format for the date
	 * @param clockFormat Format for the clock time
	 * @return String for date/time
	 * @since 2.0
	 */
	public static String getCurrentTimeString(DSettings settings, final int dateFormat, final int clockFormat)
	{
		LocalDateTime ldt = LocalDateTime.now();
		return getTimeString(settings, dateFormat, clockFormat, ldt.getYear(), ldt.getMonthValue(), ldt.getDayOfMonth(), ldt.getHour(), ldt.getMinute(), ldt.getSecond());
		
	}//METHOD
	
	/**
	 * Returns a String representation of a given date/time.
	 * 
	 * @param settings Program Settings
	 * @param dateFormat Format for the date
	 * @param clockFormat Format for the clock time
	 * @param year Year
	 * @param month Month
	 * @param day Day of Month
	 * @param hour Hour
	 * @param minute Minute
	 * @param second Second of Minute
	 * @return String for date/time
	 * @since 2.0
	 */
	public static String getTimeString(DSettings settings, final int dateFormat, final int clockFormat, final int year, final int month, final int day, final int hour, final int minute, final int second)
	{
		StringBuilder timeString = new StringBuilder();
		
		switch(dateFormat)
		{
			case DATE_MDY:
				timeString.append(StringMethods.extendNumberString(month, 2));
				timeString.append('/');
				timeString.append(StringMethods.extendNumberString(day, 2));
				timeString.append('/');
				timeString.append(StringMethods.extendNumberString(year, 2));
				break;
			case DATE_DMY:
				timeString.append(StringMethods.extendNumberString(day, 2));
				timeString.append('/');
				timeString.append(StringMethods.extendNumberString(month, 2));
				timeString.append('/');
				timeString.append(StringMethods.extendNumberString(year, 2));
				break;
			case DATE_YMD:
				timeString.append(StringMethods.extendNumberString(year, 2));
				timeString.append('/');
				timeString.append(StringMethods.extendNumberString(month, 2));
				timeString.append('/');
				timeString.append(StringMethods.extendNumberString(day, 2));
				break;
				
		}//SWITCH
		
		timeString.append(" | "); //$NON-NLS-1$
		
		if(clockFormat == HOUR_24)
		{
			timeString.append(StringMethods.extendNumberString(hour, 2));
			timeString.append(':');
			timeString.append(StringMethods.extendNumberString(minute, 2));
			timeString.append(':');
			timeString.append(StringMethods.extendNumberString(second, 2));
			
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
			timeString.append(", "); //$NON-NLS-1$
			timeString.append(StringMethods.extendNumberString(second, 2));
			timeString.append(settings.getLanguageText(DefaultLanguage.SECONDS_SUFFIX));
			
		}//ELSE
		
		return timeString.toString();
		
	}//METHOD
	
}//CLASS
