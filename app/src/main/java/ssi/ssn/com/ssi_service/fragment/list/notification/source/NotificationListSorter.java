package ssi.ssn.com.ssi_service.fragment.list.notification.source;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ssi.ssn.com.ssi_service.model.network.response.notification.objects.NotificationSeverity;
import ssi.ssn.com.ssi_service.model.network.response.notification.objects.ResponseNotification;

public class NotificationListSorter {

    public static List<ResponseNotification> sortNotificationBySeverity(List<ResponseNotification> list) {
        if(list == null || list.isEmpty()){
            return new ArrayList<>();
        }
        Map<NotificationSeverity, List<ResponseNotification>> severityMap = new HashMap<NotificationSeverity, List<ResponseNotification>>() {
            {
                put(NotificationSeverity.WARN, new ArrayList<ResponseNotification>());
                put(NotificationSeverity.INFO, new ArrayList<ResponseNotification>());
                put(NotificationSeverity.ERROR, new ArrayList<ResponseNotification>());
            }
        };
        for (NotificationSeverity severity : severityMap.keySet()) {
            for (ResponseNotification notification : list) {
                if (notification.getDefinition().getSeverity().equals(severity)) {
                    severityMap.get(severity).add(notification);
                }
            }
        }

        List<ResponseNotification> notifications = new ArrayList<>();
        notifications.addAll(sortNotificationByTime(severityMap.get(NotificationSeverity.ERROR)));
        notifications.addAll(sortNotificationByTime(severityMap.get(NotificationSeverity.WARN)));
        notifications.addAll(sortNotificationByTime(severityMap.get(NotificationSeverity.INFO)));
        return notifications;
    }

    public static List<ResponseNotification> sortNotificationByTime(List<ResponseNotification> list) {
        if(list.isEmpty()){
            return new ArrayList<>();
        }
        Collections.sort(list, new Comparator<ResponseNotification>() {
            @Override
            public int compare(ResponseNotification lNotification, ResponseNotification rNotification) {
                return lNotification.getStartTime() > rNotification.getStartTime() ? -1 : (lNotification.getStartTime() < rNotification.getStartTime()) ? 1 : 0;
            }
        });
        return list;
    }

    public static List<ResponseNotification> sortNotificationByNote(List<ResponseNotification> list) {
        Collections.sort(list, new Comparator<ResponseNotification>() {
            @Override
            public int compare(ResponseNotification lNotification, ResponseNotification rNotification) {
                String lNodePath = lNotification.getNodePath();
                String lNote = lNodePath == null ? "" : lNotification.getNodePath().substring(lNodePath.lastIndexOf(".") + 1, lNodePath.length());

                String rNotePath = rNotification.getNodePath();
                String rNote = rNotePath == null ? "" : rNotification.getNodePath().substring(rNotePath.lastIndexOf(".") + 1, rNotePath.length());

                return lNote.compareTo(rNote);
            }
        });
        return list;
    }
}
