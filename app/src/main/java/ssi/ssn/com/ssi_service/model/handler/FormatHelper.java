package ssi.ssn.com.ssi_service.model.handler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class FormatHelper {

    public static String formatDate(long millis){
        return formatDate(new Date(millis));
    }

    public static String formatDate(Date date){
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
        return format.format(date);
    }

    public static String formatLongToTime(long millis){
        return formatLongToTime(millis, "%d Stunden");
    }

    private static String formatLongToTime(long millis, String format){
        return String.format(Locale.getDefault(), format,
                TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis))
        );
    }
}
