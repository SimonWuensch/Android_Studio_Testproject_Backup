package ssi.ssn.com.ssi_service.test;

import android.os.AsyncTask;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class TaskExecutorHandler {

	public static void executeTasks(AsyncTask... tasks) throws InterruptedException, ExecutionException{
		ExecutorService executor = Executors.newSingleThreadExecutor();
		for(AsyncTask task : tasks){
			task.executeOnExecutor(executor);
		}
	    executor.shutdown();
	    executor.awaitTermination(1, TimeUnit.SECONDS);
	}
}
