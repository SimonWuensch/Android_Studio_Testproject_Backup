package ssi.ssn.com.ssi_service.fragment.customlist.source.list;

import java.util.ArrayList;
import java.util.List;

import ssi.ssn.com.ssi_service.fragment.customlist.source.CustomListObject;
import ssi.ssn.com.ssi_service.model.network.response.ResponseAbstract;
import ssi.ssn.com.ssi_service.model.network.response.ResponseApplication;

public class CustomListCreator {

    public static List<CustomListObject> getCustomList(ResponseAbstract responseAbstract){
        if(responseAbstract instanceof ResponseApplication){
            return CustomListApplication.init((ResponseApplication) responseAbstract).getCustomList();
        }
        return new ArrayList<>();
    }




}
