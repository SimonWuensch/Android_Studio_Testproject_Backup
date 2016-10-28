package ssi.ssn.com.ssi_service.fragment.customlist;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ssi.ssn.com.ssi_service.R;
import ssi.ssn.com.ssi_service.fragment.customlist.source.CustomListObject;

class FragmentCustomListViewHolder extends RecyclerView.ViewHolder {

    private static String TAG = FragmentCustomListViewHolder.class.getSimpleName();

    private Activity activity;
    private View cardView;

    private RelativeLayout rlKeyValueFrame;
    private TextView tvHeadline;
    private TextView tvKey;
    private TextView tvValue;
    private View vHorizontalLine;

    FragmentCustomListViewHolder(Activity activity, View cardView) {
        super(cardView);
        this.activity = activity;
        this.cardView = cardView;
        initViewComponents();
    }

    private void initViewComponents() {
        rlKeyValueFrame = (RelativeLayout) cardView.findViewById(R.id.fragment_custom_list_relative_layout_key_value);
        tvHeadline = (TextView) cardView.findViewById(R.id.fragment_custom_list_text_view_headline);
        tvKey = (TextView) cardView.findViewById(R.id.fragment_custom_list_text_view_key);
        tvValue = (TextView) cardView.findViewById(R.id.fragment_custom_list_text_view_value);
        vHorizontalLine = cardView.findViewById(R.id.fragment_custom_list_view_horizontal_line);
    }

    void assignData(CustomListObject customListObject) {
        customListObject.reformatViewComponents(activity, rlKeyValueFrame, tvHeadline, tvKey, tvValue, vHorizontalLine);
        if (customListObject.getOnClickListener() != null) {
            cardView.setOnClickListener(customListObject.getOnClickListener());
        }
    }
}
