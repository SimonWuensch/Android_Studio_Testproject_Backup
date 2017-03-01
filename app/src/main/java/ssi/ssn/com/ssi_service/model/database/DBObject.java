package ssi.ssn.com.ssi_service.model.database;

import ssi.ssn.com.ssi_service.model.data.source.cardobject.CardObjectNotification;

public interface DBObject {

    boolean isObjectDataChanged(String jsonObject);
}
