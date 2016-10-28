package ssi.ssn.com.ssi_service.fragment.modulelist;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import ssi.ssn.com.ssi_service.R;
import ssi.ssn.com.ssi_service.fragment.launchboard.source.CardObjectModule;
import ssi.ssn.com.ssi_service.model.data.source.Status;
import ssi.ssn.com.ssi_service.model.helper.XMLHelper;


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

    protected void assignData(final XMLHelper.XMLObject object) {
        String tagName = object.getTagName();
        String moduleName = tagName.substring(0, 1).toUpperCase() + tagName.substring(1, tagName.indexOf("-"));
        tvName.setText(moduleName);

        String isEnabled;
        if (object.getAttributes().containsKey(CardObjectModule.XML_ATTRIBUTE_ENABLED)) {
            isEnabled = object.getAttributes().get(CardObjectModule.XML_ATTRIBUTE_ENABLED);
        } else {
            isEnabled = "true";
        }
        tvEnabled.setText(isEnabled);

        //Todo Module Status set
        String status = "NULL";
        tvStatus.setText(status);

        if (!Boolean.valueOf(isEnabled)) {
            rlNameBackground.setBackgroundColor(Status.NOT_AVAILABLE.getColor(activity));
        } else if (status.equals(Status.TEXT_RUNNING)) {
            rlNameBackground.setBackgroundColor(Status.OK.getColor(activity));
        } else if (status.equals(Status.TEXT_NOT_RUNNING)) {
            rlNameBackground.setBackgroundColor(Status.ERROR.getColor(activity));
        } else {
            rlNameBackground.setBackgroundColor(Status.NOT_AVAILABLE.getColor(activity));
        }

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, object.getTagName(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
