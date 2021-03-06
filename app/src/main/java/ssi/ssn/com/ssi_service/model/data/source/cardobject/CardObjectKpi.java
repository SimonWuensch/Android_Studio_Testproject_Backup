package ssi.ssn.com.ssi_service.model.data.source.cardobject;

import android.content.Context;
import android.widget.Toast;

import com.owlike.genson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import ssi.ssn.com.ssi_service.R;
import ssi.ssn.com.ssi_service.activity.MainActivity;
import ssi.ssn.com.ssi_service.fragment.list.notification.FragmentNotificationListNotification;
import ssi.ssn.com.ssi_service.model.data.source.Project;
import ssi.ssn.com.ssi_service.model.data.source.Status;
import ssi.ssn.com.ssi_service.model.data.source.filter.kpi.FilterKpi;
import ssi.ssn.com.ssi_service.model.database.DBCardObject;
import ssi.ssn.com.ssi_service.model.database.DBCardObjectKPI;
import ssi.ssn.com.ssi_service.model.database.SQLiteDB;
import ssi.ssn.com.ssi_service.model.detector.DetectorCardObjectKPI;
import ssi.ssn.com.ssi_service.model.helper.JsonHelper;
import ssi.ssn.com.ssi_service.model.helper.SourceHelper;
import ssi.ssn.com.ssi_service.model.network.handler.RequestHandler;
import ssi.ssn.com.ssi_service.model.network.response.kpi.definitions.ResponseKpiDefinitionList;
import ssi.ssn.com.ssi_service.model.network.response.kpi.measurements.ResponseKPIMeasurementList;
import ssi.ssn.com.ssi_service.notification_android.AbstractAndroidNotification;

public class CardObjectKpi extends AbstractCardObject {

    private static String TAG = CardObjectKpi.class.getSimpleName();

    private static int NOTIFICATION_ID = 4;

    private ResponseKpiDefinitionList definitions;
    private ResponseKPIMeasurementList measurements;
    private List<FilterKpi> kpiFilters = new LinkedList<>();
    private int filterCount = 0;

    public CardObjectKpi(Project project) {
        super(project);
        this.title = R.string.fragment_launch_board_card_kpi;
        this.icon = R.drawable.icon_kpi;
    }

    public CardObjectKpi() {
    }

    public static void init(SQLiteDB sqLiteDB, Project project) {
        DBCardObjectKPI dbCardObject = sqLiteDB.cardObjectKPI();
        if (dbCardObject.getCount(project.get_id()) == 0) {
            CardObjectKpi cardObject = new CardObjectKpi(project);
            project.setCardObjectKpi(cardObject);
            dbCardObject.add(cardObject);
        } else {
            project.setCardObjectKpi(dbCardObject.getByProjectID(project.get_id()));
        }
    }

    public static int getNotificationId() {
        return NOTIFICATION_ID;
    }

    public static void setNotificationId(int notificationId) {
        NOTIFICATION_ID = notificationId;
    }

    public int getFilterCount() {
        return filterCount;
    }

    public void setFilterCount(int filterCount) {
        this.filterCount = filterCount;
    }

    public ResponseKpiDefinitionList getDefinitions() {
        return definitions;
    }

    public void setDefinitions(ResponseKpiDefinitionList definitions) {
        this.definitions = definitions;
    }

    public ResponseKPIMeasurementList getMeasurements() {
        return measurements;
    }

    public void setMeasurements(ResponseKPIMeasurementList measurements) {
        this.measurements = measurements;
    }

    public List<FilterKpi> getKpiFilters() {
        return kpiFilters;
    }

    public void setKpiFilters(List<FilterKpi> kpiFilters) {
        this.kpiFilters = kpiFilters;
    }

    public FilterKpi getFilterByID(int filterID) {
        for (FilterKpi filter : kpiFilters) {
            if (filter.getId() == filterID) {
                return filter;
            }
        }
        throw new NullPointerException("No filter found with the id [" + filterID + "]");
    }

    public void removeFilterByID(int filterID) {
        for (FilterKpi filter : kpiFilters) {
            if (filter.getId() == filterID) {
                kpiFilters.remove(filter);
                return;
            }
        }
        throw new NullPointerException("No KPI filter found with the id [" + filterID + "]");
    }

    public boolean addKpiFilter(SQLiteDB sqLiteDB, FilterKpi newFilter) {
        if (!isFilterExisting(newFilter)) {
            newFilter.setId(filterCount);
            filterCount++;
            kpiFilters.add(newFilter);
            return sqLiteDB.cardObjectKPI().update(this);
        }
        return false;
    }

    public boolean updateKpiFilter(SQLiteDB sqLiteDB, FilterKpi filter) {
        if (!isFilterExistingByID(filter)) {
            return false;

        }
        FilterKpi oldFilter = getFilterByID(filter.getId());
        oldFilter.setSignification(filter.getSignification());
        oldFilter.setKpiType(filter.getKpiType());
        sqLiteDB.cardObjectKPI().update(this);
        return true;
    }

    public boolean removeKpiFilter(SQLiteDB sqLiteDB, FilterKpi filter) {
        if (!isFilterExistingByID(filter)) {
            return false;
        }
        removeFilterByID(filter.getId());
        return sqLiteDB.cardObjectKPI().update(this);
    }

    private boolean isFilterExisting(FilterKpi newFilter) {
        for (FilterKpi oldFilter : kpiFilters) {
            String oldKpiTypeJson = JsonHelper.toJson(oldFilter.getKpiType());
            String newKpiTypeJson = JsonHelper.toJson(newFilter.getKpiType());
            if (oldKpiTypeJson.equals(newKpiTypeJson)) {
                return true;
            }
        }
        return false;
    }

    private boolean isFilterExistingByID(FilterKpi newFilter) {
        for (FilterKpi oldFilter : kpiFilters) {
            if (oldFilter.getId() == newFilter.getId()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public DBCardObject getDBSQLiteCardObject(SQLiteDB sqLiteDB) {
        return sqLiteDB.cardObjectKPI();
    }

    @Override
    public void loadFromNetwork(RequestHandler requestHandler, Project project) {
        DetectorCardObjectKPI.loadKpiDefinitionsFromNetwork(requestHandler, project);
        DetectorCardObjectKPI.loadAllKpiMeasurementsByAllFilters(requestHandler, project);
    }

    @Override
    public void detectCardStatus(SQLiteDB sqLiteDB) {
        DetectorCardObjectKPI.detectCardStatus(sqLiteDB, this);
    }

    @Override
    public void onClick(final MainActivity activity, final Project project) {
        if (getStatus().equals(Status.NOT_AVAILABLE)) {
            Toast.makeText(activity, SourceHelper.getString(activity, R.string.fragment_launch_board_error_kpi), Toast.LENGTH_SHORT).show();
        } else {
            if (getKpiFilters().isEmpty()) {
                activity.showKpiDefinitionList(project.get_id());
            }else{
                activity.showKpiFilterListFragment(project.get_id());
            }
        }
    }

    // ** Notification settings ***************************************************************** //
    @Override
    @JsonIgnore
    public int getNotificationID() {
        return NOTIFICATION_ID;
    }

    @Override
    @JsonIgnore
    public AbstractAndroidNotification getNotificationClass() {
        return new FragmentNotificationListNotification();
    }

    @Override
    @JsonIgnore
    public List<String> getNotificationMessages(Context context) {
        return new ArrayList<>();
    }

}
