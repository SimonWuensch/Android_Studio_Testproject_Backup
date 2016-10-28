package ssi.ssn.com.ssi_service.fragment.componentlist;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import ssi.ssn.com.ssi_service.R;
import ssi.ssn.com.ssi_service.fragment.launchboard.source.CardObjectComponent;
import ssi.ssn.com.ssi_service.model.data.source.Status;
import ssi.ssn.com.ssi_service.model.helper.XMLHelper;


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

    protected void assignData(final XMLHelper.XMLObject object) {
        llEnabled.setVisibility(View.GONE);

        String tagName = object.getTagName();
        String componentName = tagName.substring(0, 1).toUpperCase() + tagName.substring(1, tagName.indexOf("-"));
        tvName.setText(componentName);

        String isManaged = object.getAttributes().get(CardObjectComponent.XML_ATTRIBUTE_MANAGE);
        tvManage.setText(isManaged);

        //Todo Component Status set
        String status = "NULL";
        tvStatus.setText(status);

        String isEnabled = "true";
        if (object.getAttributes().containsKey(CardObjectComponent.XML_ATTRIBUTE_ENABLED)) {
            llEnabled.setVisibility(View.VISIBLE);
            isEnabled = object.getAttributes().get(CardObjectComponent.XML_ATTRIBUTE_ENABLED);
            tvEnabled.setText(isEnabled);
        }

        if (!Boolean.valueOf(isEnabled)) {
            rlName.setBackgroundColor(Status.NOT_AVAILABLE.getColor(activity));
        } else if (status.equals(Status.TEXT_RUNNING)) {
            rlName.setBackgroundColor(Status.OK.getColor(activity));
        } else if (status.equals(Status.TEXT_NOT_RUNNING)) {
            rlName.setBackgroundColor(Status.ERROR.getColor(activity));
        } else {
            rlName.setBackgroundColor(Status.NOT_AVAILABLE.getColor(activity));
        }

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, object.getTagName(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
