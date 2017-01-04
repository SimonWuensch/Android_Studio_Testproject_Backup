package ssi.ssn.com.ssi_service.fragment.modulelist;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ssi.ssn.com.ssi_service.R;
import ssi.ssn.com.ssi_service.model.data.source.Status;
import ssi.ssn.com.ssi_service.model.network.response.module.ResponseModule;


class FragmentModuleListViewHolder extends RecyclerView.ViewHolder {

    private static String TAG = FragmentModuleListViewHolder.class.getSimpleName();

    private Activity activity;
    private View cardView;

    private TextView tvName;
    private TextView tvEnabled;
    private TextView tvStatus;
    private RelativeLayout rlNameBackground;

    FragmentModuleListViewHolder(Activity activity, View cardView) {
        super(cardView);
        this.activity = activity;
        this.cardView = cardView;

        tvName = (TextView) cardView.findViewById(R.id.fragment_module_list_name);
        tvEnabled = (TextView) cardView.findViewById(R.id.fragment_module_list_enabled_value);
        tvStatus = (TextView) cardView.findViewById(R.id.fragment_module_list_status_value);
        rlNameBackground = (RelativeLayout) cardView.findViewById(R.id.fragment_module_list_relative_layout_name_background);
    }

    protected void assignData(final ResponseModule responseModule) {
        tvName.setText(responseModule.getName());
        tvStatus.setText(responseModule.getStatus());
        tvEnabled.setText(responseModule.getEnabled());

        if (!Boolean.valueOf(responseModule.getEnabled())) {
            rlNameBackground.setBackgroundColor(Status.NOT_AVAILABLE.getColor(activity));
        } else if (responseModule.getStatus().equals(Status.TEXT_RUNNING)) {
            rlNameBackground.setBackgroundColor(Status.OK.getColor(activity));
        } else if (!responseModule.getStatus().equals(Status.TEXT_RUNNING)) {
            rlNameBackground.setBackgroundColor(Status.ERROR.getColor(activity));
        } else {
            rlNameBackground.setBackgroundColor(Status.NOT_AVAILABLE.getColor(activity));
        }
    }
}
