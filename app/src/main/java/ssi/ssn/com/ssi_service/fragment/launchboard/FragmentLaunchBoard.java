package ssi.ssn.com.ssi_service.fragment.launchboard;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import ssi.ssn.com.ssi_service.R;
import ssi.ssn.com.ssi_service.activity.MainActivity;
import ssi.ssn.com.ssi_service.fragment.AbstractFragment;
import ssi.ssn.com.ssi_service.model.data.source.Project;
import ssi.ssn.com.ssi_service.model.helper.FormatHelper;
import ssi.ssn.com.ssi_service.model.helper.JsonHelper;
import ssi.ssn.com.ssi_service.model.network.DefaultResponse;
import ssi.ssn.com.ssi_service.model.network.handler.RequestHandler;
import ssi.ssn.com.ssi_service.model.network.response.ResponseApplication;

public class FragmentLaunchBoard extends AbstractFragment {

    public static String TAG = FragmentLaunchBoard.class.getSimpleName();

    private static int FRAGMENT_LAYOUT = R.layout.fragment_launch_board;
    private static int RECYCLERVIEW = R.id.fragment_launch_board_recycler_view;
    private static int CARDVIEW = R.layout.fragment_launch_board_card_view;

    private static String PROJECT_JSON = TAG + "PROJECT_JSON";
    private Project project;

    private View rootView;
    private RelativeLayout rlProjectStateBackground;
    private TextView tvProjectStatus;
    private TextView tvProjectLifeTime;
    private TextView tvProjectVersion;

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

            RecyclerView.Adapter mAdapter = new FragmentLaunchBoardAdapter(CARDVIEW, this);
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
        rlProjectStateBackground = (RelativeLayout) rootView.findViewById(R.id.fragment_launch_board_relative_layout_project_state_background);
        tvProjectStatus = (TextView) rootView.findViewById(R.id.fragment_launch_board_project_text_view_project_status);
        tvProjectLifeTime = (TextView) rootView.findViewById(R.id.fragment_launch_board_project_text_view_project_life_time);
        tvProjectVersion = (TextView) rootView.findViewById(R.id.fragment_launch_board_project_text_view_project_version);

        updateProjectStateView();
    }

    public void updateProjectStateView() {
        final RequestHandler requestHandler = ((MainActivity) getActivity()).getRequestHandler();
        final ExecutorService executor = Executors.newSingleThreadExecutor();
        requestHandler.getRequestApplicationTask(project).executeOnExecutor(executor);
        setLoadingViewVisible(true);
        new AsyncTask<Object, Void, Object>() {
            @Override
            protected Object doInBackground(Object[] objects) {
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                DefaultResponse defaultResponseApplication = project.getDefaultResponseApplication();
                if (defaultResponseApplication.getCode() == 200) {
                    ResponseApplication responseApplication = (ResponseApplication) JsonHelper.fromJsonGeneric(ResponseApplication.class, defaultResponseApplication.getResult());
                    String projectStatus = responseApplication.getState().getStatus();
                    tvProjectStatus.setText(projectStatus);
                    if (projectStatus.equals("RUNNING")) {
                        rlProjectStateBackground.setBackgroundColor(ssi.ssn.com.ssi_service.model.data.source.Status.OK.getColor(getActivity()));

                        Date since = new Date(responseApplication.getState().getSince());
                        String dateString = FormatHelper.formatDate(since);
                        long lifeTimeHour = new Date().getTime() - since.getTime();
                        int days = (int) TimeUnit.MILLISECONDS.toDays(lifeTimeHour);
                        long hours = TimeUnit.MILLISECONDS.toHours(lifeTimeHour) - TimeUnit.DAYS.toHours(days);
                        String lifeTime = days + " " + getActivity().getString(R.string.days) + " " + hours + " " + getActivity().getString(R.string.hours);
                        tvProjectLifeTime.setText(dateString + " (" + lifeTime + ")");

                        tvProjectVersion.setText(responseApplication.getBuild().getVersion());
                    } else {
                        rlProjectStateBackground.setBackgroundColor(ssi.ssn.com.ssi_service.model.data.source.Status.ERROR.getColor(getActivity()));
                    }
                } else {
                    tvProjectStatus.setText(getActivity().getString(R.string.fragment_launch_board_state_not_available));
                    rlProjectStateBackground.setBackgroundColor(ssi.ssn.com.ssi_service.model.data.source.Status.NOT_AVAILABLE.getColor(getActivity()));
                    tvProjectLifeTime.setText(getActivity().getString(R.string.fragment_launch_board_server_address_not_available));
                    tvProjectVersion.setText("-");
                }
                setLoadingViewVisible(false);
            }
        }.executeOnExecutor(executor);
    }
}
