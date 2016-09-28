package ssi.ssn.com.ssi_service.fragment.customlist;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import ssi.ssn.com.ssi_service.R;
import ssi.ssn.com.ssi_service.fragment.customlist.source.CustomListObject;

public class FragmentCustomListViewHolder extends RecyclerView.ViewHolder{

    private static String TAG = FragmentCustomListViewHolder.class.getSimpleName();

    private Activity activity;
    private View cardView;

    private LinearLayout llKeyValue;
    private TextView tvKey;
    private TextView tvValue;
    private View vHorizontalLine;

    public FragmentCustomListViewHolder(Activity activity, View cardView) {
        super(cardView);
        this.activity = activity;
        this.cardView = cardView;
        initializeViewComponents();
    }

    public void initializeViewComponents(){
        llKeyValue = (LinearLayout) cardView.findViewById(R.id.fragment_custom_list_linear_layout_key_value);
        tvKey = (TextView) cardView.findViewById(R.id.fragment_custom_list_text_view_key);
        tvValue = (TextView) cardView.findViewById(R.id.fragment_custom_list_text_view_value);
        vHorizontalLine = (View) cardView.findViewById(R.id.fragment_custom_list_view_horizontal_line);
    }

    protected void assignData(CustomListObject customListObject){
        customListObject.reformatViewComponents(activity, llKeyValue, tvKey, tvValue, vHorizontalLine);
        if(customListObject.getOnClickListener() != null){
            cardView.setOnClickListener(customListObject.getOnClickListener());
        }
    }
}
