package ssi.ssn.com.ssi_service.fragment.overview.launchboard.source;

import android.util.Log;

import ssi.ssn.com.ssi_service.model.data.source.Project;
import ssi.ssn.com.ssi_service.model.data.source.cardobject.CardObjectComponent;
import ssi.ssn.com.ssi_service.model.data.source.cardobject.CardObjectNotification;
import ssi.ssn.com.ssi_service.model.data.source.filter.FilterNotification;
import ssi.ssn.com.ssi_service.model.database.SQLiteDB;
import ssi.ssn.com.ssi_service.model.helper.JsonHelper;
import ssi.ssn.com.ssi_service.model.network.handler.RequestHandler;
import ssi.ssn.com.ssi_service.model.network.response.notification.ResponseNotificationTable;

public class DetectorCardObjectNotification {

    private static String TAG = DetectorCardObjectNotification.class.getSimpleName();

    public static void loadAllActiveNotificationsFromNetwork(RequestHandler requestHandler, Project project) {
        CardObjectNotification cardObject = project.getCardObjectNotification();
        Log.d(TAG + " Project ID: " + cardObject.get_id_project(), cardObject.getClass().getSimpleName() + " start load card object notification information from network...");
        requestHandler.sendRequestLogin(project);
        requestHandler.sendRequestNotificationTableAll(project);

        if (project.getDefaultResponseNotification().getCode() != 200) {
            return;
        }

        ResponseNotificationTable notificationTable = (ResponseNotificationTable) JsonHelper.fromJsonGeneric(ResponseNotificationTable.class, project.getDefaultResponseNotification().getResult());
        cardObject.setNotificationTable(notificationTable);
        Log.d(TAG + " Project ID: " + cardObject.get_id_project(), "Response notification table size is [" + cardObject.getNotificationTable().getCount() + "], [" + cardObject.getNotificationTable().getData() + "]");
    }

    public static void loadAllNotificationsByAllFilter(RequestHandler requestHandler, Project project){
        CardObjectNotification cardObject = project.getCardObjectNotification();
        for(FilterNotification filter : cardObject.getNotificationFilters().values()){
            LoadAllNotificationsByFilter(requestHandler, project, filter.getId());
        }
    }

    public static void LoadAllNotificationsByFilter(RequestHandler requestHandler, Project project, int filterID) {
        CardObjectNotification cardObject = project.getCardObjectNotification();
        Log.d(TAG + " Project ID: " + cardObject.get_id_project(), cardObject.getClass().getSimpleName() + " start load card object notification information from network...");
        requestHandler.sendRequestLogin(project);
        FilterNotification filter = cardObject.getNotificationFilters().get(filterID);
        requestHandler.sendRequestNotification(project, filter);

        if (project.getDefaultResponseNotification().getCode() != 200) {
            return;
        }

        ResponseNotificationTable notificationTable = (ResponseNotificationTable) JsonHelper.fromJsonGeneric(ResponseNotificationTable.class, project.getDefaultResponseNotification().getResult());
        filter.setNotificationTable(notificationTable);
        Log.d(TAG + " Project ID: " + cardObject.get_id_project() + " Filter: " + filter.identity(), "Response notification table size is [" + filter.getNotificationTable().getCount() + "], [" + filter.getNotificationTable().getData() + "]");
    }

    public static void detectCardStatus(SQLiteDB sqLiteDB, CardObjectComponent cardObject) {
        /*Log.d(TAG + " Project ID: " + cardObject.get_id_project(), cardObject.getClass().getSimpleName() + " start detecting card object component status...");
        Status overAllState = Status.OK;
        if (cardObject.getResponseComponentList().isEmpty()) {
            overAllState = Status.NOT_AVAILABLE;
        } else {
            for (ResponseComponent responseComponent : cardObject.getResponseComponentList()) {
                String componentStatus = responseComponent.getState().getStatus();
                if (!componentStatus.equals(Status.TEXT_ONLINE) &&
                        !componentStatus.equals(Status.TEXT_UNKNOWN)) {
                    overAllState = Status.ERROR;
                }
            }
        }
        cardObject.setStatus(overAllState);
        boolean isSuccessful = cardObject.getDBSQLiteCardObject(sqLiteDB).update(cardObject);
        if (!isSuccessful) {
            isSuccessful = cardObject.getDBSQLiteCardObject(sqLiteDB).update(cardObject);
            if (!isSuccessful) {
                throw new NullPointerException("Can not update " + cardObject.getClass().getSimpleName() + " [" + cardObject + "]");
            }
        }
        Log.i(TAG + " Project ID: " + cardObject.get_id_project(), cardObject.getClass().getSimpleName() + " status: " + cardObject.getStatus());
        */
    }
}
