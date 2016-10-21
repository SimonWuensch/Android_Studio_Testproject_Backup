package ssi.ssn.com.ssi_service.model.extended;

import android.os.AsyncTask;


public class ExtendedAsyncTask<Params, Progress, Result> extends AsyncTask {

    private String returnValue;

    public String getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(String returnValue) {
        this.returnValue = returnValue;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
    }


}
