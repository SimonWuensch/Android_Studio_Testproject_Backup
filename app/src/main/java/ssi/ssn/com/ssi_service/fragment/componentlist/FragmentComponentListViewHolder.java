package ssi.ssn.com.ssi_service.fragment.componentlist;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import ssi.ssn.com.ssi_service.R;


public class FragmentComponentListViewHolder extends RecyclerView.ViewHolder {

    private static String TAG = FragmentComponentListViewHolder.class.getSimpleName();

    private Activity activity;
    private View cardView;

    public FragmentComponentListViewHolder(Activity activity, View cardView) {
        super(cardView);
        this.activity = activity;
        this.cardView = cardView;
    }

    protected void assignData(final String string) {
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, string, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
