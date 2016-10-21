package ssi.ssn.com.ssi_service.fragment.launchboard;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import ssi.ssn.com.ssi_service.R;
import ssi.ssn.com.ssi_service.fragment.launchboard.source.AbstractCardObject;
import ssi.ssn.com.ssi_service.model.data.source.Project;
import ssi.ssn.com.ssi_service.model.data.source.Status;


public class FragmentLaunchBoardViewHolder extends RecyclerView.ViewHolder {

    private static String TAG = FragmentLaunchBoardViewHolder.class.getSimpleName();

    private Activity activity;
    private View cardView;

    private ImageView image;
    private TextView tvTitle;
    private CheckBox cbObservation;
    private View vStatus;


    public FragmentLaunchBoardViewHolder(Activity activity, View cardView) {
        super(cardView);
        this.activity = activity;
        this.cardView = cardView;

        image = (ImageView) cardView.findViewById(R.id.fragment_launch_board_card_view_image);
        tvTitle = (TextView) cardView.findViewById(R.id.fragment_launch_board_card_view_title);
        cbObservation = (CheckBox) cardView.findViewById(R.id.fragment_launch_board_card_view_check_box_observation);
        vStatus = (View) cardView.findViewById(R.id.fragment_launch_board_card_view_view_status);
    }

    protected void assignData(final AbstractCardObject cardObject, final Project project) {
        image.setImageResource(cardObject.getIcon());
        tvTitle.setText(activity.getString(cardObject.getTitle()));
        cbObservation.setChecked(cardObject.isObservation());
        cardObject.setStatusView(vStatus);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardObject.onClick(activity, project);
            }
        });
    }
}
