package ssi.ssn.com.ssi_service.test;

import android.os.AsyncTask;

public class MyThread extends AsyncTask<Object, Void, Object> {
	
	private String myName;
	private int count;
	private final long timeSleep;

	public MyThread(String name, int newcount, long newtimeSleep) {
		this.myName = name;
	    this.count = newcount;
	    this.timeSleep = newtimeSleep;
	}

	@Override
	protected Object doInBackground(Object... objects) {
		int sum = 0;
		for (int i = 1; i <= this.count; i++) {
			sum = sum + i;
		}
		System.out.println(myName + " thread has sum = " + sum +
				" and is going to sleep for " + timeSleep);
		try {
			Thread.sleep(this.timeSleep);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	@Override
	protected void onPostExecute(Object object) {
		super.onPostExecute(object);
	}
}
