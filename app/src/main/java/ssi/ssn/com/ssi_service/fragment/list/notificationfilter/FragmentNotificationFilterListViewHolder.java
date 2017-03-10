package ssi.ssn.com.ssi_service.fragment.list.notificationfilter;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import ssi.ssn.com.ssi_service.R;
import ssi.ssn.com.ssi_service.activity.MainActivity;
import ssi.ssn.com.ssi_service.fragment.overview.launchboard.source.DetectorCardObjectNotification;
import ssi.ssn.com.ssi_service.model.data.source.Project;
import ssi.ssn.com.ssi_service.model.data.source.filter.FilterNotification;
import ssi.ssn.com.ssi_service.model.helper.FormatHelper;
import ssi.ssn.com.ssi_service.model.helper.ObservationHelper;
import ssi.ssn.com.ssi_service.model.helper.SourceHelper;


class FragmentNotificationFilterListViewHolder extends RecyclerView.ViewHolder {

    private static String TAG = FragmentNotificationFilterListViewHolder.class.getSimpleName();

    private MainActivity activity;
    private RelativeLayout rlHeadLine;
    private TextView tvCount;
    private TextView tvNode;
    private TextView tvActiveTime;
    private TextView tvSeverity;
    private TextView tvText;
    private ImageView ivSettings;

    private FragmentNotificationFilterListAdapter adapter;
    private View cardView;

    protected FragmentNotificationFilterListViewHolder(MainActivity activity, FragmentNotificationFilterListAdapter adapter, View cardView) {
        super(cardView);
        this.activity = activity;
        this.adapter = adapter;
        this.cardView = cardView;
        rlHeadLine = (RelativeLayout) cardView.findViewById(R.id.fragment_notification_filter_list_card_view_relative_layout_headline);
        tvCount = (TextView) cardView.findViewById(R.id.fragment_notification_filter_list_card_view_text_view_count);
        tvNode = (TextView) cardView.findViewById(R.id.fragment_notification_filter_list_card_view_text_view_node_value);
        tvActiveTime = (TextView) cardView.findViewById(R.id.fragment_notification_filter_list_card_view_text_view_active_time_value);
        tvSeverity = (TextView) cardView.findViewById(R.id.fragment_notification_filter_list_card_view_text_view_severity_value);
        tvText = (TextView) cardView.findViewById(R.id.fragment_notification_filter_list_card_view_text_view_text_value);
        ivSettings = (ImageView) cardView.findViewById(R.id.fragment_notification_filter_list_card_view_image_settings);
    }

    protected void assignData(final Project project, final FilterNotification filter) {
        tvNode.setText(filter.getNote());
        tvSeverity.setText(filter.getSeverity().name());
        tvText.setText(filter.getText());

        final String activeTime;
        String timeKind;
        if (FormatHelper.formatMillisecondsToMinutes(filter.getActiveTime()) % 60 != 0) {
            long minutes = FormatHelper.formatMillisecondsToMinutes(filter.getActiveTime());
            activeTime = String.valueOf(minutes);
            timeKind = SourceHelper.getString(activity,
                    minutes > 1 ? R.string.minutes : R.string.minute);
        } else {
            long hours = FormatHelper.formatMillisecondsToHours(filter.getActiveTime());
            activeTime = String.valueOf(hours);
            timeKind = SourceHelper.getString(activity,
                    hours > 1 ? R.string.hours : R.string.hour);
        }
        tvActiveTime.setText(activeTime + " " + timeKind);

        if (ObservationHelper.isCardObjectOutOfDate(project, project.getCardObjectNotification()) || filter.getNotificationTable() == null) {
            new AsyncTask<Object, Void, Object>() {
                @Override
                protected Object doInBackground(Object... objects) {
                    DetectorCardObjectNotification.loadAllNotificationsByFilter(activity.getRequestHandler(), project, filter.getId());
                    project.getCardObjectNotification().setLastObservationTime(new Date().getTime());
                    activity.getSQLiteDB().cardObjectNotification().update(project.getCardObjectNotification());
                    return null;
                }

                @Override
                protected void onPostExecute(Object o) {
                    updateHeadline(filter);
                }
            }.execute();
        } else {
            updateHeadline(filter);
        }
        cardView.setOnClickListener(onCardViewClicked(project, filter));
        ivSettings.setOnClickListener(onSettingsClicked(project, filter));
    }

    private void updateHeadline(FilterNotification filter) {
        tvCount.setText(String.valueOf(filter.getNotificationTable().getCount()));
        if (filter.getActiveTimeReachedNotificationTable().getCount() > 0) {
            rlHeadLine.setBackgroundColor(SourceHelper.getColor(activity, R.color.ERROR));
        }else{
            rlHeadLine.setBackgroundColor(SourceHelper.getColor(activity, R.color.colorWhite));
        }
    }

    private View.OnClickListener onCardViewClicked(final Project project, final FilterNotification filter) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (filter.getNotificationTable().getCount() != 0) {
                    activity.showNotificationListFragment(project.get_id(), filter.getId());
                    return;
                }
                Toast.makeText(activity, R.string.fragment_notification_filter_list_alert_no_notifications_found_for_this_filter, Toast.LENGTH_SHORT).show();
            }
        };
    }

    private View.OnClickListener onSettingsClicked(final Project project, final FilterNotification filter) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.showCreateNotificationFilterFragment(project.get_id(), filter);
                adapter.clickedFilter = filter;
            }
        };
    }
}
