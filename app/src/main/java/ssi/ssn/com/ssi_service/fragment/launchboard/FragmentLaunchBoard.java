package ssi.ssn.com.ssi_service.fragment.launchboard;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import ssi.ssn.com.ssi_service.R;
import ssi.ssn.com.ssi_service.activity.MainActivity;
import ssi.ssn.com.ssi_service.fragment.AbstractFragment;
import ssi.ssn.com.ssi_service.fragment.launchboard.source.AbstractCardObject;
import ssi.ssn.com.ssi_service.model.data.source.Project;
import ssi.ssn.com.ssi_service.model.helper.FormatHelper;
import ssi.ssn.com.ssi_service.model.helper.JsonHelper;
import ssi.ssn.com.ssi_service.model.helper.SourceHelper;
import ssi.ssn.com.ssi_service.model.network.handler.RequestHandler;
import ssi.ssn.com.ssi_service.model.network.response.application.ResponseApplication;

public class FragmentLaunchBoard extends AbstractFragment {

    public static String TAG = FragmentLaunchBoard.class.getSimpleName();

    private static int FRAGMENT_LAYOUT = R.layout.fragment_launch_board;
    private static int RECYCLERVIEW = R.id.fragment_launch_board_recycler_view;
    private static int CARDVIEW = R.layout.fragment_launch_board_card_view;

    private static String PROJECT_JSON = TAG + "PROJECT_JSON";

    private View rootView;
    private RelativeLayout rlProjectStateBackground;
    private TextView tvProjectStatus;
    private TextView tvProjectLifeTime;
    private TextView tvProjectVersion;
    private RelativeLayout rlLoadingView;
    private FragmentLaunchBoardAdapter mAdapter;

    private Project project;
    private List<AbstractCardObject> cardObjects;

    public static FragmentLaunchBoard newInstance(Project project) {
        if (project == null) {
            return new FragmentLaunchBoard();
        }

        FragmentLaunchBoard fragment = new FragmentLaunchBoard();
        Bundle bundle = new Bundle();
        bundle.putString(PROJECT_JSON, JsonHelper.toJson(project));
        fragment.setArguments(bundle);
        return fragment;
    }

    private void loadArguments() {
        if (getArguments() == null) {
            return;
        }

        String projectJson = getArguments().getString(PROJECT_JSON);
        project = (Project) JsonHelper.fromJsonGeneric(Project.class, projectJson);
        cardObjects = ((MainActivity)getActivity()).getCardObjects(project);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadArguments();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(FRAGMENT_LAYOUT, container, false);
            Log.d(TAG, "Fragment inflated [" + getActivity().getResources().getResourceName(FRAGMENT_LAYOUT) + "].");

            mAdapter = new FragmentLaunchBoardAdapter(CARDVIEW, this, project, cardObjects);
            Log.d(TAG, "Adapter [" + mAdapter.getClass().getSimpleName() + "] with CardView [" + getActivity().getResources().getResourceName(CARDVIEW) + "] initialized.");

            RecyclerView mRecyclerView = (RecyclerView) rootView.findViewById(RECYCLERVIEW);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setAdapter(mAdapter);
            Log.d(TAG, "RecyclerView [" + getActivity().getResources().getResourceName(RECYCLERVIEW) + "] initialized.");

            initViewComponents();
        }
        return rootView;
    }

    public void initViewComponents() {
        ImageButton bReload = (ImageButton) rootView.findViewById(R.id.default_action_bar_button_reload);
        bReload.setVisibility(View.VISIBLE);
        bReload.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        checkProjectState();
                        boolean isUpdating = false;
                        for (AbstractCardObject cardObject : cardObjects) {
                            if (cardObject.getLoadingView().getVisibility() == View.VISIBLE) {
                                isUpdating = true;
                            }
                        }
                        Log.d(TAG, "Card objects still updating status...");

                        if (!isUpdating) {
                            for (AbstractCardObject cardObject : cardObjects) {
                                cardObject.reloadStatus(getActivity(), project);
                            }
                        }
                    }
                }
        );

        TextView tvHeadLine = (TextView) rootView.findViewById(R.id.default_action_bar_text_view_headline);
        String headLineText = project.getProjectName() + " " + project.getProjectLocation() + " " + project.getProjectOrderNr();
        tvHeadLine.setText(headLineText);

        rlProjectStateBackground = (RelativeLayout) rootView.findViewById(R.id.fragment_launch_board_relative_layout_project_state_background);
        tvProjectStatus = (TextView) rootView.findViewById(R.id.fragment_launch_board_project_state_text_view_project_status);
        tvProjectLifeTime = (TextView) rootView.findViewById(R.id.fragment_launch_board_project_state_text_view_project_life_time);
        tvProjectVersion = (TextView) rootView.findViewById(R.id.fragment_launch_board_project_state_text_view_project_version);
        rlLoadingView = (RelativeLayout) rootView.findViewById(R.id.fragment_launch_board_project_state_loading_view);
        rlLoadingView.setVisibility(View.GONE);
        checkProjectState();
    }

    public void checkProjectState() {
        final RequestHandler requestHandler = ((MainActivity) getActivity()).getRequestHandler();

        new AsyncTask<Object, Void, Object>(){
            @Override
            protected Object doInBackground(Object... objects) {
                boolean isOutOfTime = new Date().getTime() - project.getLastObservationTime() > project.getObservationInterval();
                if (project.getDefaultResponseApplication() == null || isOutOfTime) {
                    requestHandler.sendRequestApplication(project);
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                rlLoadingView.setVisibility(View.VISIBLE);

                if (project.getDefaultResponseApplication().getCode() == 200) {
                    ResponseApplication responseApplication = (ResponseApplication) JsonHelper.fromJsonGeneric(ResponseApplication.class, project.getDefaultResponseApplication().getResult());
                    String projectStatus = responseApplication.getState().getStatus();
                    tvProjectStatus.setText(projectStatus);
                    if (projectStatus.equals(ssi.ssn.com.ssi_service.model.data.source.Status.TEXT_RUNNING)) {
                        rlProjectStateBackground.setBackgroundColor(ssi.ssn.com.ssi_service.model.data.source.Status.OK.getColor(getActivity()));

                        Date since = new Date(responseApplication.getState().getSince());
                        String dateString = FormatHelper.formatDate(since);
                        long lifeTimeHour = new Date().getTime() - since.getTime();
                        int days = (int) TimeUnit.MILLISECONDS.toDays(lifeTimeHour);
                        long hours = TimeUnit.MILLISECONDS.toHours(lifeTimeHour) - TimeUnit.DAYS.toHours(days);
                        String lifeTime = days + " " + SourceHelper.getString(getActivity(), R.string.days) + " " + hours + " " + SourceHelper.getString(getActivity(), R.string.hours);
                        tvProjectLifeTime.setText(dateString + " (" + lifeTime + ")");

                        tvProjectVersion.setText(responseApplication.getBuild().getVersion());
                    } else {
                        rlProjectStateBackground.setBackgroundColor(ssi.ssn.com.ssi_service.model.data.source.Status.ERROR.getColor(getActivity()));
                    }
                } else {
                    tvProjectStatus.setText(SourceHelper.getString(getActivity(), R.string.fragment_launch_board_state_not_available));
                    rlProjectStateBackground.setBackgroundColor(ssi.ssn.com.ssi_service.model.data.source.Status.NOT_AVAILABLE.getColor(getActivity()));
                    tvProjectLifeTime.setText(SourceHelper.getString(getActivity(), R.string.fragment_launch_board_server_address_not_available));
                    tvProjectVersion.setText("-");
                }
                rlLoadingView.setVisibility(View.GONE);
            }
        }.execute();
    }

    @Override
    public void onResume() {
        super.onResume();
        for (AbstractCardObject cardObject : cardObjects) {
            cardObject.detectCardStatus(getActivity(), project);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
