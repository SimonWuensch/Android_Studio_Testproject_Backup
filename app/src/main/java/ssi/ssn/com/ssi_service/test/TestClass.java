package ssi.ssn.com.ssi_service.test;

import android.util.Log;

import org.apache.commons.io.IOUtils;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.util.ArrayList;
import java.util.List;

import ssi.ssn.com.ssi_service.R;
import ssi.ssn.com.ssi_service.activity.MainActivity;
import ssi.ssn.com.ssi_service.fragment.componentlist.source.ComponentListObject;
import ssi.ssn.com.ssi_service.fragment.customlist.FragmentCustomList;

public class TestClass {

	private static String restApplication = "{\"state\":{\"since\":1474986112465,\"status\":\"RUNNING\"},\"enabledModules\":[\"Scada\",\"Kpi.UserCharts\",\"Kpi.UserDashboards\"],\"project\":{\"name\":\"AntSimDemo\",\"location\":\"Giebelstadt\",\"orderNr\":\"2x0\"},\"build\":{\"version\":\"2.0.0.0-DEV\",\"number\":\"7755\",\"builtBy\":\"scott.hady\",\"builtOn\":1474880244488},\"time\":{\"stamp\":1475065035240,\"offset\":7200000}}";

	public static void test(MainActivity activity){
		activity.showCustomListFragment(R.string.fragment_custom_list_application_info_title, FragmentCustomList.Type.APPLICATION, restApplication);
	}

	public static void xmlParser() {

		String startEndTag = "components-module";
		boolean startTagReached = false;

		List<ComponentListObject> componentListObjects = new ArrayList<>();
		XmlPullParser myParser = null;
		try {
			XmlPullParserFactory xmlFactoryObject = XmlPullParserFactory.newInstance();
			myParser = xmlFactoryObject.newPullParser();
			myParser.setInput(IOUtils.toInputStream(RESTResponseTEST.restApplicationConfig), null);

			int event = myParser.getEventType();
			while (event != XmlPullParser.END_DOCUMENT) {
				String name = myParser.getName();
				switch (event) {

					case XmlPullParser.START_TAG:
						if (name.equals(startEndTag)) {
							startTagReached = true;
							Log.d("TEST", "START_TAG: " + name);
						}
						if (startTagReached && name.contains("server")) {
							Log.d("TEST", "Componente: " + name);
							String manage = myParser.getAttributeValue(null, "manage");
							ComponentListObject object = new ComponentListObject(name);
							object.setManaged(manage);
							componentListObjects.add(object);
							break;
						}

					case XmlPullParser.END_TAG:
						if (name.equals(startEndTag)) {
							startTagReached = false;
							Log.d("TEST", "END_TAG: " + name);
						}
						break;
				}
				event = myParser.next();
			}

			for (ComponentListObject object : componentListObjects) {
				Log.d("TEST", object.getName() + ": Managed " + object.getManaged());
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

}
