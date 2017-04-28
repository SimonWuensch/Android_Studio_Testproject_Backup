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
import ssi.ssn.com.ssi_service.model.data.source.filter.FilterNotification;
import ssi.ssn.com.ssi_service.model.database.DBCardObject;
import ssi.ssn.com.ssi_service.model.database.DBCardObjectNotification;
import ssi.ssn.com.ssi_service.model.database.SQLiteDB;
import ssi.ssn.com.ssi_service.model.detector.DetectorCardObjectNotification;
import ssi.ssn.com.ssi_service.model.helper.SourceHelper;
import ssi.ssn.com.ssi_service.model.network.handler.RequestHandler;
import ssi.ssn.com.ssi_service.model.network.response.notification.ResponseNotificationTable;
import ssi.ssn.com.ssi_service.notification_android.AbstractAndroidNotification;

public class CardObjectNotification extends AbstractCardObject {

    private static String TAG = CardObjectNotification.class.getSimpleName();

    private static int NOTIFICATION_ID = 3;

    private ResponseNotificationTable notificationTable;
    private List<FilterNotification> notificationFilters = new LinkedList<>();
    private int filterCount = 0;

    public CardObjectNotification(Project project) {
        super(project);
        this.title = R.string.fragment_launch_board_card_notification;
        this.icon = R.drawable.icon_notification;
    }

    public CardObjectNotification() {
    }

    public static void init(SQLiteDB sqLiteDB, Project project) {
        DBCardObjectNotification dbCardObject = sqLiteDB.cardObjectNotification();
        if (dbCardObject.getCount(project.get_id()) == 0) {
            CardObjectNotification cardObject = new CardObjectNotification(project);
            project.setCardObjectNotification(cardObject);
            dbCardObject.add(cardObject);
        } else {
            project.setCardObjectNotification(dbCardObject.getByProjectID(project.get_id()));
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

    public ResponseNotificationTable getNotificationTable() {
        return notificationTable;
    }

    public void setNotificationTable(ResponseNotificationTable notificationTable) {
        this.notificationTable = notificationTable;
    }

    public List<FilterNotification> getNotificationFilters() {
        return notificationFilters;
    }

    public void setNotificationFilters(List<FilterNotification> notificationFilters) {
        this.notificationFilters = notificationFilters;
    }

    public FilterNotification getFilterByID(int filterID) {
        for (FilterNotification filter : notificationFilters) {
            if (filter.getId() == filterID) {
                return filter;
            }
        }
        throw new NullPointerException("No filter found with the id [" + filterID + "]");
    }

    public void removeFilterByID(int filterID) {
        for (FilterNotification filter : notificationFilters) {
            if (filter.getId() == filterID) {
                notificationFilters.remove(filter);
                return;
            }
        }
        throw new NullPointerException("No Notification filter found with the id [" + filterID + "]");
    }

    public boolean addNotificationFilter(SQLiteDB sqLiteDB, FilterNotification newFilter) {
        if (!isFilterExists(newFilter)) {
            newFilter.setId(filterCount);
            filterCount++;
            notificationFilters.add(newFilter);
            return sqLiteDB.cardObjectNotification().update(this);
        }
        return false;
    }

    public boolean updateNotificationFilter(SQLiteDB sqLiteDB, FilterNotification filter) {
        if (isFilterExists(filter)) {
            return false;

        }
        FilterNotification oldFilter = notificationFilters.get(filter.getId());
        oldFilter.setNote(filter.getNote());
        oldFilter.setActiveTime(filter.getActiveTime());
        oldFilter.setSeverity(filter.getSeverity());
        oldFilter.setText(filter.getText());
        sqLiteDB.cardObjectNotification().update(this);
        return true;
    }

    public boolean removeNotificationFilter(SQLiteDB sqLiteDB, FilterNotification filter) {
        if (!isFilterExists(filter)) {
            return false;
        }
        removeFilterByID(filter.getId());
        return sqLiteDB.cardObjectNotification().update(this);
    }

    private boolean isFilterExists(FilterNotification newFilter) {
        for (FilterNotification oldFilter : notificationFilters) {
            if (oldFilter.getNote().equals(newFilter.getNote()) &&
                    oldFilter.getActiveTime() == newFilter.getActiveTime() &&
                    oldFilter.getText().equals(newFilter.getText()) &&
                    oldFilter.getSeverity().equals(newFilter.getSeverity())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public DBCardObject getDBSQLiteCardObject(SQLiteDB sqLiteDB) {
        return sqLiteDB.cardObjectNotification();
    }

    @Override
    public void loadFromNetwork(RequestHandler requestHandler, Project project) {
        DetectorCardObjectNotification.loadAllActiveNotificationsFromNetwork(requestHandler, project);
        DetectorCardObjectNotification.loadAllNotificationsByAllFilter(requestHandler, project);
    }

    @Override
    public void detectCardStatus(SQLiteDB sqLiteDB) {
        DetectorCardObjectNotification.detectCardStatus(sqLiteDB, this);
    }

    @Override
    public void onClick(final MainActivity activity, final Project project) {
        if (getStatus().equals(Status.NOT_AVAILABLE)) {
            Toast.makeText(activity, SourceHelper.getString(activity, R.string.fragment_launch_board_error_notification), Toast.LENGTH_SHORT).show();
        } else {
            activity.showNotificationFilterListFragment(project.get_id());
            if (getNotificationFilters().isEmpty()) {
                activity.showCreateNotificationFilterFragment(project.get_id());
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
