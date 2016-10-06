package ssi.ssn.com.ssi_service.model.network.response;

import android.app.Activity;
import android.util.Log;

import java.util.List;

import ssi.ssn.com.ssi_service.fragment.customlist.source.CustomListObject;

public class AbstractResponse {

    public List<CustomListObject> getCustomList(final Activity activity){
        Log.e(getClass().getSimpleName(), "This response type has no custom list implementation.");
        throw new NullPointerException(getClass().getSimpleName() + ": This response type has no custom list implementation.");
    }

}
