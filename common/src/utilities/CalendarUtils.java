package utilities;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 *
 * @author Daniel J Breen
 * @version 1.0
 * @since 1.0
 */
public class CalendarUtils {
    public static Calendar fromLocalDateTime(LocalDateTime dateTime) {
        var zdt = ZonedDateTime.of(dateTime, TimeZone.getTimeZone("UTC").toZoneId());
        var cal = GregorianCalendar.from(zdt);

        return cal;
    }

    public static Calendar fromValues(int year, int month, int day, int hour, int minute) {
        var cal = Calendar.getInstance();
        var indexedMonth = month - 1;

        cal.set(year, indexedMonth, day, hour, minute);
        return cal;
    }

    public static Calendar toUtc(Calendar cal) {
        var offset = TimeZone.getTimeZone(cal.getTimeZone().getID()).getOffset(cal.getTimeInMillis());

        cal.setTimeInMillis(cal.getTimeInMillis() - offset);
        return cal;
    }
}
