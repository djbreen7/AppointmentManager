package utilities;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * A tool for handling date manipulation with Calendar.
 *
 * @author Daniel J Breen
 * @version 1.0
 * @since 1.0
 */
public class CalendarUtils {

    /**
     * Produces a Calendar from LocalDateTime.
     *
     * @param dateTime The date to be converted to Calendar.
     * @return A Calender in the UTC Time Zone.
     */
    public static Calendar fromLocalDateTime(LocalDateTime dateTime) {
        var zdt = ZonedDateTime.of(dateTime, TimeZone.getTimeZone("UTC").toZoneId());
        var cal = GregorianCalendar.from(zdt);

        return cal;
    }


    /**
     * Produces a Calendar from date fragments.
     *
     * @param year Year
     * @param month Month
     * @param day Day
     * @param hour Hour
     * @param minute Minute
     * @return A Calendar object.
     */
    public static Calendar fromValues(int year, int month, int day, int hour, int minute) {
        var cal = Calendar.getInstance();
        var indexedMonth = month - 1;

        cal.set(year, indexedMonth, day, hour, minute);
        return cal;
    }


    /**
     * Reverse the offset of the provided Calendar to convert the time to UTC.
     *
     * @param cal The Calendar date to be adjusted.
     * @return A Calender in the UTC Time Zone.
     */
    public static Calendar toUtc(Calendar cal) {
        var offset = TimeZone.getTimeZone(cal.getTimeZone().getID()).getOffset(cal.getTimeInMillis());

        cal.setTimeInMillis(cal.getTimeInMillis() - offset);
        return cal;
    }
}
