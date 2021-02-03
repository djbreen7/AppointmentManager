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
}
