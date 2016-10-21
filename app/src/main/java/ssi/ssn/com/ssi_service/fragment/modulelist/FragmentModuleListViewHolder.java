package ssi.ssn.com.ssi_service.fragment.modulelist;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;


public class FragmentModuleListViewHolder extends RecyclerView.ViewHolder {

    private static String TAG = FragmentModuleListViewHolder.class.getSimpleName();

    private Activity activity;
    private View cardView;

    public FragmentModuleListViewHolder(Activity activity, View cardView) {
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
