package ssi.ssn.com.ssi_service.model.network.response.notification;

import java.util.List;

import ssi.ssn.com.ssi_service.model.network.response.AbstractResponse;
import ssi.ssn.com.ssi_service.model.network.response.notification.objects.ResponseNotification;


public class ResponseNotificationTable extends AbstractResponse {

    private long count;
    private List<ResponseNotification> data;

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
}
