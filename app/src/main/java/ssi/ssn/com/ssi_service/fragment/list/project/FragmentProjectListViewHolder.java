package ssi.ssn.com.ssi_service.fragment.list.project;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;

import ssi.ssn.com.ssi_service.R;
import ssi.ssn.com.ssi_service.activity.MainActivity;
import ssi.ssn.com.ssi_service.model.data.source.Project;
import ssi.ssn.com.ssi_service.model.data.source.Status;
import ssi.ssn.com.ssi_service.model.data.source.cardobject.AbstractCardObject;
import ssi.ssn.com.ssi_service.model.helper.JsonHelper;
import ssi.ssn.com.ssi_service.model.helper.SourceHelper;


class FragmentProjectListViewHolder extends RecyclerView.ViewHolder {

    private static String TAG = FragmentProjectListViewHolder.class.getSimpleName();

    private MainActivity activity;
    private FragmentProjectListAdapter adapter;
    private View cardView;

    private CheckBox cbObserveProject;
    private TextView tvProjectAlias;
    private TextView tvProjectIdentity;
    private ImageView ivProjectSettings;
    private View vProjectStatus;
    private View loadingView;

    FragmentProjectListViewHolder(MainActivity activity, FragmentProjectListAdapter adapter, View cardView) {
        super(cardView);
        this.activity = activity;
        this.adapter = adapter;
        this.cardView = cardView;
        initViewComponents();
    }

    private void initViewComponents() {
        cbObserveProject = (CheckBox) cardView.findViewById(R.id.fragment_project_list_card_view_check_box_project_observation);
        tvProjectAlias = (TextView) cardView.findViewById(R.id.fragment_project_list_card_view_text_view_project_name);
        tvProjectIdentity = (TextView) cardView.findViewById(R.id.fragment_project_list_card_view_text_view_project_identity);
        ivProjectSettings = (ImageView) cardView.findViewById(R.id.fragment_project_list_card_view_image_settings);
        vProjectStatus = cardView.findViewById(R.id.fragment_project_list_card_view_view_project_status);
        loadingView = cardView.findViewById(R.id.fragment_project_list_card_view_view_loading_view);
    }

    protected void assignData(final Project project, boolean isLast) {
        String identity;
        if (project.getProjectName() == null || project.getProjectLocation() == null || project.getProjectOrderNr() == null) {
            identity = project.getServerAddress() + " " + project.getUserName();
        } else {
            identity = project.getProjectName() + " " + project.getProjectLocation() + " " + project.getProjectOrderNr();
        }

        vProjectStatus.setBackgroundColor(Status.NOT_OBSERVATION.getColor(activity));
        tvProjectAlias.setText(project.getAlias() != null && !project.getAlias().isEmpty() ? project.getAlias() : identity);
        tvProjectIdentity.setText(identity);

        cbObserveProject.setChecked(project.isProjectObservation());
        cbObserveProject.setOnCheckedChangeListener(onCheckedChangeListener(project));
        ivProjectSettings.setOnClickListener(onClickCardViewProjectSettings(project));

        if (project.isProjectObservation()) {
            detectProjectStatus(project, isLast);
        }
    }

    public void detectProjectStatus(final Project project, final boolean isLast) {
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
                vProjectStatus.setBackgroundColor(project.getStatus().getColor(activity));
                cardView.setOnClickListener(onClickCardView(project));
                if (isLast) {
                    adapter.sort();
                }
            }
        }.execute();
    }

    private View.OnClickListener onClickCardViewProjectSettings(final Project project) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.showCreateProjectFragment(project.get_id());
            }
        };
    }

    private View.OnClickListener onClickCardView(final Project project) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loadingView.getVisibility() == View.GONE) {
                    saveDataForBackStack(project);
                    if (!project.getStatus().equals(ssi.ssn.com.ssi_service.model.data.source.Status.NOT_AVAILABLE)) {
                        activity.showLaunchBoardFragment(project.get_id());
                    } else {
                        Toast.makeText(activity, SourceHelper.getString(activity, R.string.fragment_project_list_error_project_not_available), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        };
    }

    private void saveDataForBackStack(Project project) {
        adapter.clickedProject = project;
        adapter.clickedProjectJson = JsonHelper.toJson(project);
        adapter.clickedProject.initCardObjects(activity.getSQLiteDB());
        adapter.cardObjectJsonList = new LinkedList<>();
        for (AbstractCardObject cardObject : project.getAllCardObjects()) {
            adapter.cardObjectJsonList.add(JsonHelper.toJson(cardObject));
        }
    }

    private CompoundButton.OnCheckedChangeListener onCheckedChangeListener(final Project project) {
        return new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    cardView.setOnClickListener(null);
                    vProjectStatus.setBackgroundColor(SourceHelper.getColor(activity, R.color.lightGray));

                    project.setProjectObservation(false);
                    activity.getSQLiteDB().project().updateIsObservation(project);

                    int from = getAdapterPosition();
                    int to = adapter.getItemCount() - 1;
                    adapter.notifyItemMoved(from, to);
                    return;
                }
                project.setProjectObservation(true);
                activity.getSQLiteDB().project().updateIsObservation(project);
                assignData(project, true);

            }
        };
    }
}
