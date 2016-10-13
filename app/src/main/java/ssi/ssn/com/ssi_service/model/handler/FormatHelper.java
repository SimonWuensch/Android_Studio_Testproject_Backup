package ssi.ssn.com.ssi_service.model.handler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class FormatHelper {

    public static String formatDate(long millis) {
        return formatDate(new Date(millis));
    }

    public static String formatDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy - hh:mm:ss");
        return format.format(date);
    }

    public static long formatHoursToMilliseconds(String hour) {
        return TimeUnit.HOURS.toMillis(Integer.parseInt(hour));
    }

    public static long formatMinutesToMilliseconds(String minutes) {
        return TimeUnit.MINUTES.toMillis(Integer.parseInt(minutes));
    }

    public static long formatMillisecondsToHours(long millis) {
        return TimeUnit.MILLISECONDS.toHours(millis);
    }

    public static long formatMillisecondsToMinutes(long millis) {
        return TimeUnit.MILLISECONDS.toMinutes(millis);
    }
}
