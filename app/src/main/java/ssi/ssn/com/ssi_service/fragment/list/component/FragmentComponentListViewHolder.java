package ssi.ssn.com.ssi_service.fragment.list.component;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ssi.ssn.com.ssi_service.R;
import ssi.ssn.com.ssi_service.model.data.source.Status;
import ssi.ssn.com.ssi_service.model.network.response.component.ResponseComponent;
import ssi.ssn.com.ssi_service.model.network.response.component.objects.ResponseState;


class FragmentComponentListViewHolder extends RecyclerView.ViewHolder {

    private static String TAG = FragmentComponentListViewHolder.class.getSimpleName();

    private Activity activity;
    private View cardView;

    private TextView tvName;
    private TextView tvManage;
    private TextView tvStatus;
    private TextView tvEnabled;
    private RelativeLayout rlName;
    private LinearLayout llEnabled;

    FragmentComponentListViewHolder(Activity activity, View cardView) {
        super(cardView);
        this.activity = activity;
        this.cardView = cardView;

        tvName = (TextView) cardView.findViewById(R.id.fragment_component_list_name);
        tvManage = (TextView) cardView.findViewById(R.id.fragment_component_list_manage_value);
        tvStatus = (TextView) cardView.findViewById(R.id.fragment_component_list_status_value);
        tvEnabled = (TextView) cardView.findViewById(R.id.fragment_component_list_enabled_value);
        rlName = (RelativeLayout) cardView.findViewById(R.id.fragment_component_list_relative_layout_name_background);
        llEnabled = (LinearLayout) cardView.findViewById(R.id.fragment_component_list_linear_layout_enabled);
    }

    protected void assignData(final ResponseComponent responseComponent) {
        final ResponseState state = responseComponent.getState();
        tvName.setText(state.getName());
        tvManage.setText(state.getManaged());
        tvStatus.setText(state.getStatus());

        llEnabled.setVisibility(View.GONE);
        if (!state.isEnabled()) {
            rlName.setBackgroundColor(Status.NOT_AVAILABLE.getColor(activity));
            llEnabled.setVisibility(View.VISIBLE);
            tvEnabled.setText(String.valueOf(state.isEnabled()));
        } else if (state.getStatus().equals(Status.TEXT_ONLINE)) {
            rlName.setBackgroundColor(Status.OK.getColor(activity));
        } else if (state.getStatus().equals(Status.TEXT_UNKNOWN)) {
            rlName.setBackgroundColor(Status.NOT_AVAILABLE.getColor(activity));
        } else {
            rlName.setBackgroundColor(Status.ERROR.getColor(activity));
        }
    }
}
