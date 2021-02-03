package utilities;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class CalendarUtils {
    public static Calendar fromLocalDateTime(LocalDateTime dateTime) {
        var zoneId = ZonedDateTime.now().getZone();
        var zdt = ZonedDateTime.of(dateTime, TimeZone.getTimeZone("UTC").toZoneId());

        GregorianCalendar cal = GregorianCalendar.from(zdt);
        cal.setTimeZone(TimeZone.getTimeZone(zoneId));
        System.out.println("Time: " + cal.getTime());
        return cal;
    }

    public static Calendar doStuff(int year, int month, int day, int hour, int minute) {
        var cal = Calendar.getInstance();
        var offset = TimeZone.getTimeZone(cal.getTimeZone().getID()).getOffset(cal.getTimeInMillis());
        var indexedMonth = month - 1;

        cal.set(year, indexedMonth, day, hour, minute);
        cal.setTimeInMillis(cal.getTimeInMillis() - offset);

        return cal;
    }
}
