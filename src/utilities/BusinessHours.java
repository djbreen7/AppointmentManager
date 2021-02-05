package utilities;

import java.util.Calendar;
import java.util.TimeZone;

/**
 *
 * @author Daniel J Breen
 * @version 1.0
 * @since 1.0
 */
public class BusinessHours {
    private int startHour;
    private int endHour;

    public BusinessHours() {
        var startOfDay = Calendar.getInstance();
        var endOfDay = Calendar.getInstance();
        var curr = TimeZone.getTimeZone(startOfDay.getTimeZone().getID()).getOffset(startOfDay.getTimeInMillis());
        var est = TimeZone.getTimeZone("EST").getOffset(startOfDay.getTimeInMillis());

        startOfDay.set(Calendar.HOUR_OF_DAY, 9);
        startOfDay.setTimeInMillis(startOfDay.getTimeInMillis() + est - curr);

        endOfDay.set(Calendar.HOUR_OF_DAY, 17);
        endOfDay.setTimeInMillis(endOfDay.getTimeInMillis() + est - curr);

        this.startHour = startOfDay.get(Calendar.HOUR_OF_DAY);
        this.endHour = endOfDay.get(Calendar.HOUR_OF_DAY);
    }

    public int getStartHour() {
        return startHour;
    }

    public int getEndHour() {
        return endHour;
    }
}
