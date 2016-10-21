package ssi.ssn.com.ssi_service.model.helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;

public class AlertDialogHelper {

    public static AlertDialog init(Activity activity, String message, String positiveButtonText, final AsyncTask positiveButtonTask, String negativeButtonText, final AsyncTask negativeButtonTask) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setCancelable(true);

        alertDialogBuilder.setPositiveButton(
                positiveButtonText,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (positiveButtonTask != null) {
                            positiveButtonTask.execute();
                        } else {
                            dialog.cancel();
                        }
                    }
                });

        alertDialogBuilder.setNegativeButton(
                negativeButtonText,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (negativeButtonTask != null) {
                            negativeButtonTask.execute();
                        } else {
                            dialog.cancel();
                        }
                    }
                });
        return alertDialogBuilder.create();
    }
}
