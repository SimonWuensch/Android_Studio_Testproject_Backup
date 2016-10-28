package ssi.ssn.com.ssi_service.fragment.projectlist;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ssi.ssn.com.ssi_service.R;
import ssi.ssn.com.ssi_service.activity.MainActivity;
import ssi.ssn.com.ssi_service.model.data.source.Project;
import ssi.ssn.com.ssi_service.model.network.handler.RequestHandler;


class FragmentProjectListViewHolder extends RecyclerView.ViewHolder {

    private static String TAG = FragmentProjectListViewHolder.class.getSimpleName();

    private Activity activity;
    private View cardView;

    private CheckBox cbObserveProject;
    private TextView tvProjectName;
    private TextView tvProjectLocation;
    private TextView tvProjectOrderNr;
    private ImageView ivProjectSettings;
    private View vProjectState;

    FragmentProjectListViewHolder(Activity activity, View cardView) {
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
        ivProjectSettings = (ImageView) cardView.findViewById(R.id.fragment_project_list_card_view_image_settings);
        vProjectState = cardView.findViewById(R.id.fragment_project_list_card_view_view_project_status);
    }

    protected void assignData(final Project project) {
        String projectName = project.getProjectName();
        String projectLocation = project.getProjectLocation();
        String projectOrderNr = project.getProjectOrderNr();

        if (projectName == null || projectLocation == null || projectOrderNr == null) {
            tvProjectName.setText(project.getServerAddress());
            tvProjectLocation.setText(project.getUserName());
            tvProjectOrderNr.setText("");
        } else {
            tvProjectName.setText(projectName);
            tvProjectLocation.setText(projectLocation);
            tvProjectOrderNr.setText(projectOrderNr);
        }

        cbObserveProject.setChecked(project.isProjectObservation());
        ivProjectSettings.setOnClickListener(onClickCardViewProjectSettings(project));

        if (project.isProjectObservation()) {
            //TODO
            // Passe Status View vProjectState an
        }

        RequestHandler requestHandler = ((MainActivity) activity).getRequestHandler();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        requestHandler.getRequestApplicationTask(project).executeOnExecutor(executor);
        new AsyncTask<Object, Void, Object>() {
            @Override
            protected Object doInBackground(Object... objects) {
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                if (project.getDefaultResponseApplication().getCode() == 200) {
                    vProjectState.setBackgroundColor(ssi.ssn.com.ssi_service.model.data.source.Status.OK.getColor(activity));
                    cardView.setOnClickListener(onClickCardView(project));
                } else {
                    vProjectState.setBackgroundColor(ssi.ssn.com.ssi_service.model.data.source.Status.ERROR.getColor(activity));
                    //TODO TEST REMOVE
                    cardView.setOnClickListener(onClickCardView(project));
                }
            }
        }.executeOnExecutor(executor);
        executor.shutdown();
    }

    private View.OnClickListener onClickCardViewProjectSettings(final Project project) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) activity).showCreateProjectFragment(project);
            }
        };
    }

    private View.OnClickListener onClickCardView(final Project project) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) activity).showLaunchBoardFragment(project);
            }
        };
    }
}
