package ssi.ssn.com.ssi_service.model.detector;

import android.util.Log;

import java.util.ArrayList;

import ssi.ssn.com.ssi_service.model.data.source.Project;
import ssi.ssn.com.ssi_service.model.data.source.Status;
import ssi.ssn.com.ssi_service.model.data.source.cardobject.CardObjectKpi;
import ssi.ssn.com.ssi_service.model.data.source.filter.kpi.FilterKpi;
import ssi.ssn.com.ssi_service.model.database.SQLiteDB;
import ssi.ssn.com.ssi_service.model.helper.JsonHelper;
import ssi.ssn.com.ssi_service.model.network.DefaultResponse;
import ssi.ssn.com.ssi_service.model.network.handler.RequestHandler;
import ssi.ssn.com.ssi_service.model.network.response.kpi.definitions.ResponseKpiDefinitionList;
import ssi.ssn.com.ssi_service.model.network.response.kpi.measurements.ResponseKpiMeasurement;

public class DetectorCardObjectKPI {

    private static String TAG = DetectorCardObjectKPI.class.getSimpleName();

    public static void loadKpiDefinitionsFromNetwork(RequestHandler requestHandler, Project project) {
        CardObjectKpi cardObject = project.getCardObjectKpi();
        Log.d(TAG + " Project ID: " + cardObject.get_id_project(), cardObject.getClass().getSimpleName() + " start load card object kpi definitions information from network...");
        requestHandler.sendRequestLogin(project);
        requestHandler.sendRequestKPIDefinitions(project);

        if (project.getDefaultResponseKPIDefinitionList().getCode() != 200) {
            cardObject.setDefinitions(null);
            return;
        }

        ResponseKpiDefinitionList definitions = (ResponseKpiDefinitionList) JsonHelper.fromJsonGeneric(ResponseKpiDefinitionList.class, project.getDefaultResponseKPIDefinitionList().getResult());
        cardObject.setDefinitions(definitions);
        Log.d(TAG + " Project ID: " + cardObject.get_id_project(), "Response kpi definition list size is [" + cardObject.getDefinitions().getDefinitions().size() + "], [" + cardObject.getDefinitions().getDefinitions() + "]");
    }

    public static void loadAllKpiMeasurementsByAllFilters(RequestHandler requestHandler, Project project) {
        CardObjectKpi cardObject = project.getCardObjectKpi();
        for (FilterKpi filter : cardObject.getKpiFilters()) {
            loadKpiMeasurementByFilter(requestHandler, project, filter);
        }
    }

    public static void loadKpiMeasurementByFilter(RequestHandler requestHandler, Project project, FilterKpi filter) {
        CardObjectKpi cardObject = project.getCardObjectKpi();
        Log.d(TAG + " Project ID: " + cardObject.get_id_project(), cardObject.getClass().getSimpleName() + " start load card object kpi filter information from network...");
        requestHandler.sendRequestLogin(project);
        requestHandler.sendRequestKPIMeasurements(project, filter);

        DefaultResponse defaultResponse = project.getDefaultResponseKPIMeasurementMap().get(filter.getDefinition().getKey());
        if (defaultResponse.getCode() != 200) {
            filter.setMeasurements(new ArrayList<ResponseKpiMeasurement>());
            Log.e(TAG + " Project ID: " + cardObject.get_id_project() + " Filter: " + filter.identity(), "No filter found. ID [" + filter.getDefinition().getKey() + "]");
            return;
        }

        if (!defaultResponse.getResult().equals("[]")) {
            filter.setMeasurements(new ArrayList<ResponseKpiMeasurement>());
            Log.i(TAG + " Project ID: " + cardObject.get_id_project() + " Filter: " + filter.identity(), "No measurements found. ID [" + filter.getDefinition().getKey() + "]");
            return;
        }

        if (!defaultResponse.getResult().startsWith("[")) {
            ResponseKpiMeasurement[] measurementArray = (ResponseKpiMeasurement[]) JsonHelper.fromJsonGeneric(ResponseKpiMeasurement[].class, defaultResponse.getResult());
            for (ResponseKpiMeasurement measurement : measurementArray) {
                filter.getMeasurements().add(measurement);
            }
        } else {
            ResponseKpiMeasurement measurement = (ResponseKpiMeasurement) JsonHelper.fromJsonGeneric(ResponseKpiMeasurement.class, defaultResponse.getResult());
            filter.getMeasurements().add(measurement);
        }
        Log.d(TAG + " Project ID: " + cardObject.get_id_project() + " Filter: " + filter.identity(), "Measurement size [" + filter.getMeasurements().size() + "], [" + filter.getMeasurements() + "]");
    }

    public static void detectCardStatus(SQLiteDB sqLiteDB, CardObjectKpi cardObject) {
        Log.d(TAG + " Project ID: " + cardObject.get_id_project(), cardObject.getClass().getSimpleName() + " start detecting card object kpi status...");
        Status overAllState = Status.OK;
        if (cardObject.getDefinitions() == null) {
            overAllState = Status.NOT_AVAILABLE;
        } else if (cardObject.getKpiFilters().isEmpty()) {
            overAllState = Status.OK;
        } else {
            for (FilterKpi filter : cardObject.getKpiFilters()) {
                for (ResponseKpiMeasurement measurement : filter.getMeasurements()) {
                    if (!filter.getKpiType().check(measurement)){
                        overAllState = Status.ERROR;
                    }
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

    }
}
