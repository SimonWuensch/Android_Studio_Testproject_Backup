package ssi.ssn.com.ssi_service.fragment.customlist.source.list;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import ssi.ssn.com.ssi_service.fragment.customlist.source.CustomListObject;
import ssi.ssn.com.ssi_service.model.network.response.ResponseAbstract;
import ssi.ssn.com.ssi_service.model.network.response.ResponseApplication;

public class CustomListApplication {

    private ResponseApplication responseApplication;

    private CustomListApplication(ResponseApplication responseApplication){
        this.responseApplication = responseApplication;
    }

    public static CustomListApplication init(ResponseApplication responseApplication){
        return new CustomListApplication(responseApplication);
    }

    public List<CustomListObject> getCustomList(){
        return new LinkedList<CustomListObject>(){
            {
                add(new CustomListObject.HeadLine("state"));
                add(new CustomListObject.Key_Value("since", responseApplication.getState().getSince() + ""));
                add(new CustomListObject.Key_Value("status", responseApplication.getState().getStatus() + ""));
                add(new CustomListObject.Horizontal_Line());

                add(new CustomListObject.HeadLine("enabledModules"));

                String[] enabledModules = responseApplication.getEnabledModules();
                for(String module : enabledModules){
                add(new CustomListObject.Only_Value(module));
                }
                add(new CustomListObject.Horizontal_Line());

                add(new CustomListObject.HeadLine("project"));
                add(new CustomListObject.Key_Value("name", responseApplication.getRestProject().getName() + ""));
                add(new CustomListObject.Key_Value("location", responseApplication.getRestProject().getLocation() + ""));
                add(new CustomListObject.Key_Value("orderNr", responseApplication.getRestProject().getOrderNr() + ""));
                add(new CustomListObject.Horizontal_Line());

                add(new CustomListObject.HeadLine("build"));
                add(new CustomListObject.Key_Value("version", responseApplication.getBuild().getVersion()));
                add(new CustomListObject.Key_Value("number", responseApplication.getBuild().getNumber() + ""));
                add(new CustomListObject.Key_Value("builtBy", responseApplication.getBuild().getBuiltBy()));
                add(new CustomListObject.Key_Value("builtOn", responseApplication.getBuild().getBuiltOn() + ""));
                add(new CustomListObject.Horizontal_Line());

                add(new CustomListObject.HeadLine("time"));
                add(new CustomListObject.Key_Value("stamp", responseApplication.getTime().getStamp() + ""));
                add(new CustomListObject.Key_Value("offset", responseApplication.getTime().getOffset() + ""));
            }
        };
    }

}
