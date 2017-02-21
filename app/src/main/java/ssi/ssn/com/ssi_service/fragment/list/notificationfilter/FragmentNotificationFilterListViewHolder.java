package ssi.ssn.com.ssi_service.fragment.list.notificationfilter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import ssi.ssn.com.ssi_service.R;
import ssi.ssn.com.ssi_service.model.data.source.filter.FilterNotification;


public class FragmentNotificationFilterListViewHolder extends RecyclerView.ViewHolder {

    private static String TAG = FragmentNotificationFilterListViewHolder.class.getSimpleName();

    private Activity activity;
    private final TextView tvTextView;
    private View cardView;

    public FragmentNotificationFilterListViewHolder(Activity activity, View cardView) {
        super(cardView);
        this.activity = activity;
        this.cardView = cardView;
        tvTextView = ((TextView) cardView.findViewById(R.id.fragment_notification_filter_list_card_view_text_view_node_value));
    }

    protected void assignData(final FilterNotification filter) {
        tvTextView.setText(filter.getNote());

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, filter.getNote(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
