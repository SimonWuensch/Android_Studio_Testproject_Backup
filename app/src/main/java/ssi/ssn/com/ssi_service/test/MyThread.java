package ssi.ssn.com.ssi_service.test;

import android.icu.text.DateFormat;
import android.icu.text.MessageFormat;
import android.os.AsyncTask;

import com.owlike.genson.Genson;

import java.lang.reflect.Field;

import ssi.ssn.com.ssi_service.model.handler.JsonHelper;
import ssi.ssn.com.ssi_service.model.network.response.ResponseApplication;

public class MyThread{

	private static String restApplication = "{\"state\":{\"since\":1474986112465,\"status\":\"RUNNING\"},\"enabledModules\":[\"Scada\",\"Kpi.UserCharts\",\"Kpi.UserDashboards\"],\"project\":{\"name\":\"AntSimDemo\",\"location\":\"Giebelstadt\",\"orderNr\":\"2x0\"},\"build\":{\"version\":\"2.0.0.0-DEV\",\"number\":\"7755\",\"builtBy\":\"scott.hady\",\"builtOn\":1474880244488},\"time\":{\"stamp\":1475065035240,\"offset\":7200000}}";

	public static void test(){
		ResponseApplication responseApplication = (ResponseApplication) JsonHelper.fromJsonGeneric(ResponseApplication.class, restApplication);

		for(Field field: responseApplication.getClass().getFields()){
			System.out.println("TEST: " + field.getName());
			if(field.getClass().getFields() != null || field.getClass().getFields().length > 0){
				for(Field fieldSub : field.getClass().getFields()){
					System.out.println("\tTEST: " + fieldSub.getName());
				}
			}
		}
	}

}
