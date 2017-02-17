package ssi.ssn.com.ssi_service.fragment.notificatonlist;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import ssi.ssn.com.ssi_service.R;
import ssi.ssn.com.ssi_service.model.network.response.notification.objects.ResponseNotification;


public class FragmentNotificationListViewHolder extends RecyclerView.ViewHolder {

    private static String TAG = FragmentNotificationListViewHolder.class.getSimpleName();

    private Activity activity;
    private View vStatusColor;
    private TextView tvNumber;
    private TextView tvTime;
    private TextView tvDate;
    private TextView tvNodePath;
    private TextView tvText;

    private View cardView;

    public FragmentNotificationListViewHolder(Activity activity, View cardView) {
        super(cardView);
        this.activity = activity;
        this.cardView = cardView;
        vStatusColor = cardView.findViewById(R.id.fragment_notification_list_card_view_view_status_color);
        tvNumber = (TextView) cardView.findViewById(R.id.fragment_notification_list_card_view_text_view_number);
        tvTime = (TextView) cardView.findViewById(R.id.fragment_notification_list_card_view_text_view_time);
        tvDate = (TextView) cardView.findViewById(R.id.fragment_notification_list_card_view_text_view_date);
        tvNodePath = (TextView) cardView.findViewById(R.id.fragment_notification_list_card_view_text_view_node_path);
        tvText = (TextView) cardView.findViewById(R.id.fragment_notification_list_card_view_text_view_text);
    }

    protected void assignData(final ResponseNotification notification) {

        tvText.setText(notification.getText());

    }
}
