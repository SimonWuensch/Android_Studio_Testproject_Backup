package ssi.ssn.com.ssi_service.fragment.overview.launchboard;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import ssi.ssn.com.ssi_service.R;
import ssi.ssn.com.ssi_service.activity.MainActivity;
import ssi.ssn.com.ssi_service.model.data.source.Project;
import ssi.ssn.com.ssi_service.model.data.source.cardobject.AbstractCardObject;
import ssi.ssn.com.ssi_service.model.helper.ObservationHelper;
import ssi.ssn.com.ssi_service.model.helper.SourceHelper;


class FragmentLaunchBoardViewHolder extends RecyclerView.ViewHolder {

    private static String TAG = FragmentLaunchBoardViewHolder.class.getSimpleName();

    private MainActivity activity;
    private View cardView;

    private ImageView image;
    private TextView tvTitle;
    private CheckBox cbObservation;
    private View vStatus;
    private View loadingView;

    FragmentLaunchBoardViewHolder(MainActivity activity, View cardView) {
        super(cardView);
        this.activity = activity;
        this.cardView = cardView;

        image = (ImageView) cardView.findViewById(R.id.fragment_launch_board_card_view_image);
        tvTitle = (TextView) cardView.findViewById(R.id.fragment_launch_board_card_view_title);
        cbObservation = (CheckBox) cardView.findViewById(R.id.fragment_launch_board_card_view_check_box_observation);
        vStatus = cardView.findViewById(R.id.fragment_launch_board_card_view_view_status);
        loadingView = cardView.findViewById(R.id.fragment_launch_board_card_view_view_loading_view);
    }

    protected void assignData(final Project project, final AbstractCardObject cardObject) {
        image.setImageResource(cardObject.getIcon());
        tvTitle.setText(SourceHelper.getString(activity, cardObject.getTitle()));
        cbObservation.setChecked(cardObject.isObservation());
        cbObservation.setOnCheckedChangeListener(onCheckedChangeListener(project, cardObject));
        vStatus.setBackgroundColor(SourceHelper.getColor(activity, R.color.lightGray));
        loadingView.setVisibility(View.GONE);

        if (cardObject.isObservation()) {
            if (ObservationHelper.isCardObjectOutOfDate(project, cardObject)) {
                detectCardObjectStatus(project, cardObject);
            }else{
                vStatus.setBackgroundColor(cardObject.getStatus().getColor(activity));
            }
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (loadingView.getVisibility() != View.VISIBLE) {
                        cardObject.onClick(activity, project);
                    }
                }
            });
        }
    }

    public void detectCardObjectStatus(final Project project, final AbstractCardObject cardObject) {
        loadingView.setVisibility(View.VISIBLE);
        new AsyncTask<Object, Void, Object>() {
            @Override
            protected Object doInBackground(Object... objects) {
                project.detectProjectStatus(activity.getSQLiteDB(), activity.getRequestHandler());
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                loadingView.setVisibility(View.GONE);
                vStatus.setBackgroundColor(cardObject.getStatus().getColor(activity));
            }
        }.execute();
    }

    private CompoundButton.OnCheckedChangeListener onCheckedChangeListener(final Project project, final AbstractCardObject cardObject) {
        return new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    cardView.setOnClickListener(null);
                    vStatus.setBackgroundColor(SourceHelper.getColor(activity, R.color.lightGray));

                    cardObject.setObservation(false);
                    cardObject.getDBSQLiteCardObject(activity.getSQLiteDB()).updateIsObservation(cardObject);
                    return;
                }
                cardObject.setObservation(true);
                cardObject.getDBSQLiteCardObject(activity.getSQLiteDB()).updateIsObservation(cardObject);
                assignData(project, cardObject);
            }
        };
    }
}
