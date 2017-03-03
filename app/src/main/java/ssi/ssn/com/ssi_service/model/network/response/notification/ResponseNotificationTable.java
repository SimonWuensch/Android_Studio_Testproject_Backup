package ssi.ssn.com.ssi_service.model.network.response.notification;

import com.owlike.genson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

import ssi.ssn.com.ssi_service.model.network.response.AbstractResponse;
import ssi.ssn.com.ssi_service.model.network.response.notification.objects.ResponseNotification;


public class ResponseNotificationTable extends AbstractResponse {

    private long count;
    private List<ResponseNotification> data = new ArrayList<>();

    public ResponseNotificationTable() {
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public List<ResponseNotification> getData() {
        return data;
    }

    public void setData(List<ResponseNotification> data) {
        this.data = data;
    }

    @JsonIgnore
    public void addNotification(ResponseNotification notification){
        count++;
        data.add(notification);
    }
}
