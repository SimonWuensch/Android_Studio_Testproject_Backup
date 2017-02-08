package ssi.ssn.com.ssi_service.model.database;

import android.content.ContentValues;

import ssi.ssn.com.ssi_service.model.data.source.cardobject.AbstractCardObject;
import ssi.ssn.com.ssi_service.model.data.source.cardobject.CardObjectModule;

public interface DBCardObject {

    void add(AbstractCardObject cardObject);
    long getCount(long projectID);
    AbstractCardObject getByProjectID(long id);

    boolean update(AbstractCardObject cardObject);
    boolean updateIsObservation(AbstractCardObject cardObject);
    boolean updateLastObservationTime(AbstractCardObject cardObject);
    boolean updateStatus(AbstractCardObject cardObject);
    boolean updateJson(AbstractCardObject cardObject);
    boolean updateValue(AbstractCardObject cardObject, ContentValues values);
    void delete(AbstractCardObject cardObject);
}
