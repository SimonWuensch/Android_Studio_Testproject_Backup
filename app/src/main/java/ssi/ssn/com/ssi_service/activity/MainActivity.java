package ssi.ssn.com.ssi_service.activity;

import android.os.AsyncTask;
import android.os.Bundle;

import java.util.List;

import ssi.ssn.com.ssi_service.R;
import ssi.ssn.com.ssi_service.model.data.source.Project;
import ssi.ssn.com.ssi_service.model.database.DBApplicationStatus;
import ssi.ssn.com.ssi_service.model.helper.AlarmHelper;
import ssi.ssn.com.ssi_service.model.helper.JsonHelper;
import ssi.ssn.com.ssi_service.notification_android.AndroidNotificationHelper;

public class MainActivity extends AbstractActivity {

    private static String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String fragmentTag = getIntent().getStringExtra(AndroidNotificationHelper.TAG_FRAGMENT);
        if (fragmentTag != null) {
            androidNotificationHelper.handleNotificationClick(this, getIntent());
        } else if (sqliteDB.project().getCount() == 0) {
            showCreateProjectFragment();
        } else {
            showProjectListFragment();
        }

        loadingView = findViewById(R.id.activity_main_view_loading);
        setLoadingViewVisible(false);
        //addTestProjects();
    }

    @Override
    protected void onResume() {
        super.onResume();
        new AsyncTask<Object, Void, List<Project>>() {
            @Override
            protected List<Project> doInBackground(Object... params) {
                sqliteDB.application().updateApplicationStatus(DBApplicationStatus.ApplicationStatus.FOREGROUND);
                return null;
            }
        }.execute();
    }

    @Override
    protected void onStop() {
        super.onStop();
        new AsyncTask<Object, Void, List<Project>>(){
            @Override
            protected List<Project> doInBackground(Object... params) {
                sqliteDB.application().updateApplicationStatus(DBApplicationStatus.ApplicationStatus.BACKGROUND);
                return sqliteDB.project().getALL();
            }

            @Override
            protected void onPostExecute(List<Project> projects) {
                AlarmHelper.startAlertPerString(MainActivity.this, projects);
                super.onPostExecute(projects);
            }
        }.execute();
    }

    private void addTestProjects() {
        Project one = (Project) JsonHelper.fromJsonGeneric(Project.class, "{\"_id\":1,\"defaultResponseApplication\":{\"additional\":{},\"code\":200,\"result\":\"{\\\"state\\\":{\\\"since\\\":1482487804669,\\\"status\\\":\\\"RUNNING\\\"},\\\"enabledModules\\\":[\\\"Scada\\\",\\\"Kpi.UserCharts\\\",\\\"Kpi.UserDashboards\\\"],\\\"project\\\":{\\\"name\\\":\\\"TZ\\\",\\\"location\\\":\\\"Giebelstadt\\\",\\\"orderNr\\\":\\\"2x0\\\"},\\\"build\\\":{\\\"version\\\":\\\"2.0.9.0\\\",\\\"number\\\":\\\"8439\\\",\\\"builtBy\\\":\\\"WU-LIGHTHOUSE$\\\",\\\"builtOn\\\":1482487531909},\\\"time\\\":{\\\"stamp\\\":1483533997191,\\\"offset\\\":3600000}}\"},\"lastObservationTime\":0,\"observationInterval\":60000,\"outOfDate\":true,\"password\":\"admin\",\"projectLocation\":\"Maidbronn\",\"projectName\":\"Gamma\",\"projectObservation\":true,\"projectOrderNr\":\"2x0\",\"serverAddress\":\"172.26.26.16:8180\",\"userName\":\"admin\"}");
        //Project two = (Project) JsonHelper.fromJsonGeneric(Project.class, "{\"_id\":1,\"defaultResponseApplication\":{\"additional\":{},\"code\":200,\"result\":\"{\\\"state\\\":{\\\"since\\\":1482487804669,\\\"status\\\":\\\"RUNNING\\\"},\\\"enabledModules\\\":[\\\"Scada\\\",\\\"Kpi.UserCharts\\\",\\\"Kpi.UserDashboards\\\"],\\\"project\\\":{\\\"name\\\":\\\"TZ\\\",\\\"location\\\":\\\"Giebelstadt\\\",\\\"orderNr\\\":\\\"2x0\\\"},\\\"build\\\":{\\\"version\\\":\\\"2.0.9.0\\\",\\\"number\\\":\\\"8439\\\",\\\"builtBy\\\":\\\"WU-LIGHTHOUSE$\\\",\\\"builtOn\\\":1482487531909},\\\"time\\\":{\\\"stamp\\\":1483533997191,\\\"offset\\\":3600000}}\"},\"lastObservationTime\":0,\"observationInterval\":60000,\"outOfDate\":true,\"password\":\"admin\",\"projectLocation\":\"Kirchheim\",\"projectName\":\"ZETA\",\"projectObservation\":true,\"projectOrderNr\":\"2x0\",\"serverAddress\":\"172.26.26.16:8180\",\"userName\":\"admin\"}");
        //Project three = (Project) JsonHelper.fromJsonGeneric(Project.class, "{\"_id\":1,\"defaultResponseApplication\":{\"additional\":{},\"code\":200,\"result\":\"{\\\"state\\\":{\\\"since\\\":1482487804669,\\\"status\\\":\\\"RUNNING\\\"},\\\"enabledModules\\\":[\\\"Scada\\\",\\\"Kpi.UserCharts\\\",\\\"Kpi.UserDashboards\\\"],\\\"project\\\":{\\\"name\\\":\\\"TZ\\\",\\\"location\\\":\\\"Giebelstadt\\\",\\\"orderNr\\\":\\\"2x0\\\"},\\\"build\\\":{\\\"version\\\":\\\"2.0.9.0\\\",\\\"number\\\":\\\"8439\\\",\\\"builtBy\\\":\\\"WU-LIGHTHOUSE$\\\",\\\"builtOn\\\":1482487531909},\\\"time\\\":{\\\"stamp\\\":1483533997191,\\\"offset\\\":3600000}}\"},\"lastObservationTime\":0,\"observationInterval\":60000,\"outOfDate\":true,\"password\":\"admin\",\"projectLocation\":\"Rimpar\",\"projectName\":\"Beta\",\"projectObservation\":true,\"projectOrderNr\":\"2x0\",\"serverAddress\":\"172.26.26.16:8180\",\"userName\":\"admin\"}");
        //Project four = (Project) JsonHelper.fromJsonGeneric(Project.class, "{\"_id\":1,\"defaultResponseApplication\":{\"additional\":{},\"code\":200,\"result\":\"{\\\"state\\\":{\\\"since\\\":1482487804669,\\\"status\\\":\\\"RUNNING\\\"},\\\"enabledModules\\\":[\\\"Scada\\\",\\\"Kpi.UserCharts\\\",\\\"Kpi.UserDashboards\\\"],\\\"project\\\":{\\\"name\\\":\\\"TZ\\\",\\\"location\\\":\\\"Giebelstadt\\\",\\\"orderNr\\\":\\\"2x0\\\"},\\\"build\\\":{\\\"version\\\":\\\"2.0.9.0\\\",\\\"number\\\":\\\"8439\\\",\\\"builtBy\\\":\\\"WU-LIGHTHOUSE$\\\",\\\"builtOn\\\":1482487531909},\\\"time\\\":{\\\"stamp\\\":1483533997191,\\\"offset\\\":3600000}}\"},\"lastObservationTime\":0,\"observationInterval\":60000,\"outOfDate\":true,\"password\":\"admin\",\"projectLocation\":\"TestLocation\",\"projectName\":\"Juhu\",\"projectObservation\":true,\"projectOrderNr\":\"2x0\",\"serverAddress\":\"172.26.26.16:8180\",\"userName\":\"admin\"}");
        //Project five = (Project) JsonHelper.fromJsonGeneric(Project.class, "{\"_id\":1,\"defaultResponseApplication\":{\"additional\":{},\"code\":200,\"result\":\"{\\\"state\\\":{\\\"since\\\":1482487804669,\\\"status\\\":\\\"RUNNING\\\"},\\\"enabledModules\\\":[\\\"Scada\\\",\\\"Kpi.UserCharts\\\",\\\"Kpi.UserDashboards\\\"],\\\"project\\\":{\\\"name\\\":\\\"TZ\\\",\\\"location\\\":\\\"Giebelstadt\\\",\\\"orderNr\\\":\\\"2x0\\\"},\\\"build\\\":{\\\"version\\\":\\\"2.0.9.0\\\",\\\"number\\\":\\\"8439\\\",\\\"builtBy\\\":\\\"WU-LIGHTHOUSE$\\\",\\\"builtOn\\\":1482487531909},\\\"time\\\":{\\\"stamp\\\":1483533997191,\\\"offset\\\":3600000}}\"},\"lastObservationTime\":0,\"observationInterval\":60000,\"outOfDate\":true,\"password\":\"admin\",\"projectLocation\":\"ABC Dorf\",\"projectName\":\"Alpha\",\"projectObservation\":true,\"projectOrderNr\":\"2x0\",\"serverAddress\":\"172.26.26.16:8180\",\"userName\":\"admin\"}");
        sqliteDB.project().add(one);
        /*sqliteDB.add(two);
        sqliteDB.add(three);
        sqliteDB.add(four);
        sqliteDB.add(five);
        */
    }
}
