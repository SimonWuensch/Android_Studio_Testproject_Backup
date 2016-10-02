package ssi.ssn.com.ssi_service.fragment.projectlist;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import ssi.ssn.com.ssi_service.R;
import ssi.ssn.com.ssi_service.model.data.ressource.Project;


public class FragmentProjectListViewHolder extends RecyclerView.ViewHolder {

    private static String TAG = FragmentProjectListViewHolder.class.getSimpleName();

    private Activity activity;
    private View cardView;

    private CheckBox cbObserveProject;
    private TextView tvProjectName;
    private TextView tvProjectLocation;
    private TextView tvProjectOrderNr;
    private ImageView ivDropDown;
    private View vProjectState;


    public FragmentProjectListViewHolder(Activity activity, View cardView) {
        super(cardView);
        this.activity = activity;
        this.cardView = cardView;
        initViewComponents();
    }

    private void initViewComponents() {
        cbObserveProject = (CheckBox) cardView.findViewById(R.id.fragment_project_list_card_view_check_box_project_observation);
        tvProjectName = (TextView) cardView.findViewById(R.id.fragment_project_list_card_view_text_view_project_name);
        tvProjectLocation = (TextView) cardView.findViewById(R.id.fragment_project_list_card_view_text_view_project_location);
        tvProjectOrderNr = (TextView) cardView.findViewById(R.id.fragment_project_list_card_view_text_view_project_order_nr);
        ivDropDown = (ImageView) cardView.findViewById(R.id.fragment_project_list_card_view_image_drop_down);
        vProjectState = cardView.findViewById(R.id.fragment_project_list_card_view_view_project_status);
    }

    protected void assignData(final Project project) {

        tvProjectName.setText(project.getProjectName());
        tvProjectLocation.setText(project.getProjectLocation());
        tvProjectOrderNr.setText(project.getProjectOrderNr());
        cbObserveProject.setSelected(project.isProjectObservation());
        Log.e("TEST: ", "Name: " + project.getProjectName());

        if (project.isProjectObservation()) {
            //TODO
            // Passe Status View vProjectState an
        }

        ivDropDown.setOnClickListener(onClickCardViewMenuButton(project));
        cardView.setOnClickListener(onClickCardView(project));
    }

    private View.OnClickListener onClickCardViewMenuButton(final Project project) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO
                // Show Dropdown - items: Edit Project, Delete Project
            }
        };
    }

    private View.OnClickListener onClickCardView(final Project project) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, project.toString(), Toast.LENGTH_SHORT).show();
            }
        };
    }
}
