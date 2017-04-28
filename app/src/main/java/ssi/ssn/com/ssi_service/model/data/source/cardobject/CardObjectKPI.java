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
import ssi.ssn.com.ssi_service.model.data.source.filter.FilterKPI;
import ssi.ssn.com.ssi_service.model.database.DBCardObject;
import ssi.ssn.com.ssi_service.model.database.DBCardObjectKPI;
import ssi.ssn.com.ssi_service.model.database.SQLiteDB;
import ssi.ssn.com.ssi_service.model.detector.DetectorCardObjectKPI;
import ssi.ssn.com.ssi_service.model.helper.SourceHelper;
import ssi.ssn.com.ssi_service.model.network.handler.RequestHandler;
import ssi.ssn.com.ssi_service.model.network.response.kpi.definitions.ResponseKPIDefinitionList;
import ssi.ssn.com.ssi_service.notification_android.AbstractAndroidNotification;

public class CardObjectKPI extends AbstractCardObject {

    private static String TAG = CardObjectKPI.class.getSimpleName();

    private static int NOTIFICATION_ID = 4;

    private ResponseKPIDefinitionList definitions;
    private List<FilterKPI> kpiFilters = new LinkedList<>();
    private int filterCount = 0;

    public CardObjectKPI(Project project) {
        super(project);
        this.title = R.string.fragment_launch_board_card_kpi;
        this.icon = R.drawable.icon_kpi;
    }

    public CardObjectKPI() {
    }

    public static void init(SQLiteDB sqLiteDB, Project project) {
        DBCardObjectKPI dbCardObject = sqLiteDB.cardObjectKPI();
        if (dbCardObject.getCount(project.get_id()) == 0) {
            CardObjectKPI cardObject = new CardObjectKPI(project);
            project.setCardObjectKPI(cardObject);
            dbCardObject.add(cardObject);
        } else {
            project.setCardObjectKPI(dbCardObject.getByProjectID(project.get_id()));
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

    public ResponseKPIDefinitionList getDefinitions() {
        return definitions;
    }

    public void setDefinitions(ResponseKPIDefinitionList definitions) {
        this.definitions = definitions;
    }

    public List<FilterKPI> getKpiFilters() {
        return kpiFilters;
    }

    public void setKpiFilters(List<FilterKPI> kpiFilters) {
        this.kpiFilters = kpiFilters;
    }

    public FilterKPI getFilterByID(int filterID) {
        for (FilterKPI filter : kpiFilters) {
            if (filter.getId() == filterID) {
                return filter;
            }
        }
        throw new NullPointerException("No filter found with the id [" + filterID + "]");
    }

    public void removeFilterByID(int filterID) {
        for (FilterKPI filter : kpiFilters) {
            if (filter.getId() == filterID) {
                kpiFilters.remove(filter);
                return;
            }
        }
        throw new NullPointerException("No KPI filter found with the id [" + filterID + "]");
    }

    public boolean addKPIFilter(SQLiteDB sqLiteDB, FilterKPI newFilter) {
        if (!isFilterExists(newFilter)) {
            newFilter.setId(filterCount);
            filterCount++;
            kpiFilters.add(newFilter);
            return sqLiteDB.cardObjectKPI().update(this);
        }
        return false;
    }

    public boolean updateKPIFilter(SQLiteDB sqLiteDB, FilterKPI filter) {
        if (isFilterExists(filter)) {
            return false;

        }
        FilterKPI oldFilter = kpiFilters.get(filter.getId());
        //TODO CARD Object KPI -> Update Filter
        /*oldFilter.setNote(filter.getNote());
        oldFilter.setActiveTime(filter.getActiveTime());
        oldFilter.setSeverity(filter.getSeverity());
        oldFilter.setText(filter.getText());
        */
        sqLiteDB.cardObjectKPI().update(this);
        return true;
    }

    public boolean removeNotificationFilter(SQLiteDB sqLiteDB, FilterKPI filter) {
        if (!isFilterExists(filter)) {
            return false;
        }
        removeFilterByID(filter.getId());
        return sqLiteDB.cardObjectKPI().update(this);
    }

    private boolean isFilterExists(FilterKPI newFilter) {
        for (FilterKPI oldFilter : kpiFilters) {
            //TODO CARD Object KPI Filter -> Is Filter Exists if clause
            if (false) {
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
            activity.showKPIFilterListFragment(project.get_id());
            if (getKpiFilters().isEmpty()) {
                activity.showKpiDefinitionList(project.get_id());
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
