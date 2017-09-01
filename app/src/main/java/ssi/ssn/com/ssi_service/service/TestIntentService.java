package ssi.ssn.com.ssi_service.service;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class TestIntentService extends IntentService {
    private static final String ACTION = "sim.ssn.com.servicealarmclockapp.action.FOO";

    private static final String EXTRA_PARAM1 = "sim.ssn.com.servicealarmclockapp.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "sim.ssn.com.servicealarmclockapp.extra.PARAM2";

    public TestIntentService() {
        super("TestIntentService");
    }

    public static void start(Context context) {
        context.startService(getStartIntent(context));
        Log.d(TestIntentService.class.getSimpleName(), "Service started...");
    }

    public static void stop(Context context) {
        stopRepeating(context);
        Log.d(TestIntentService.class.getSimpleName(), "Service stopped...");
    }

    private static void stopRepeating(Context context){
        repeatAlarmManager(context, getStartIntent(context), -1);
    }
    private static void startRepeating(Context context, int interval){
        repeatAlarmManager(context, getStartIntent(context), interval);
    }

    private static void repeatAlarmManager(Context context, Intent intent, int interval){
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
        if(interval > 0){
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);
        }
    }

    private static Intent getStartIntent(Context context){
        Intent intent = new Intent(context, TestIntentService.class);
        intent.setAction(ACTION);
        return intent;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(this.getClass().getSimpleName(), "Handle Intent");
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION.equals(action)) {
                doIT();
                startRepeating(this, 5000);
            }
        }
    }

    private void doIT() {
        Log.d(this.getClass().getSimpleName(), "do something");
    }
}