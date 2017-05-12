package ssi.ssn.com.ssi_service.fragment.list.project;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import ssi.ssn.com.ssi_service.R;
import ssi.ssn.com.ssi_service.activity.MainActivity;
import ssi.ssn.com.ssi_service.fragment.AbstractFragment;
import ssi.ssn.com.ssi_service.model.data.source.Project;
import ssi.ssn.com.ssi_service.model.data.source.cardobject.AbstractCardObject;
import ssi.ssn.com.ssi_service.model.helper.JsonHelper;
import ssi.ssn.com.ssi_service.model.helper.SourceHelper;

public class FragmentProjectList extends AbstractFragment {

    public static String TAG = FragmentProjectList.class.getSimpleName();

    private static int FRAGMENT_LAYOUT = R.layout.fragment_list_project;
    private static int RECYCLERVIEW = R.id.fragment_project_list_recycler_view;
    private static int CARDVIEW = R.layout.fragment_list_project_card_view;

    private FragmentProjectListAdapter adapter;
    private RecyclerView recyclerView;

    private List<Project> projects;
    private View rootView;

    public static FragmentProjectList newInstance() {
        FragmentProjectList fragment = new FragmentProjectList();
        return fragment;
    }

    private void loadArguments() {
        projects = getSQLiteDB().project().getALL();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView != null) {
            return rootView;
        }
        rootView = inflater.inflate(FRAGMENT_LAYOUT, container, false);
        Log.d(TAG, "Fragment inflated [" + getActivity().getResources().getResourceName(FRAGMENT_LAYOUT) + "].");

        adapter = new FragmentProjectListAdapter(CARDVIEW, this, new ArrayList<Project>());
        Log.d(TAG, "Adapter [" + adapter.getClass().getSimpleName() + "] with CardView [" + getActivity().getResources().getResourceName(CARDVIEW) + "] initialized.");

        recyclerView = (RecyclerView) rootView.findViewById(RECYCLERVIEW);
        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        Log.d(TAG, "RecyclerView [" + getActivity().getResources().getResourceName(RECYCLERVIEW) + "] initialized.");

        initAdapter();
        initViewComponents();
        return rootView;
    }

    private void initAdapter() {
        new AsyncTask<Object, Void, RecyclerView.Adapter>() {
            @Override
            protected RecyclerView.Adapter doInBackground(Object... objects) {
                loadArguments();
                adapter = new FragmentProjectListAdapter(CARDVIEW, FragmentProjectList.this, projects);
                return adapter;
            }

            @Override
            protected void onPostExecute(RecyclerView.Adapter adapter) {
                recyclerView.setAdapter(adapter);
            }
        }.execute(adapter);
    }

    public void initViewComponents() {
        final TextView tvHeadLine = (TextView) rootView.findViewById(R.id.default_action_bar_text_view_headline);
        tvHeadLine.setText(SourceHelper.getString(getActivity(), R.string.fragment_project_list_title));

        ImageButton bReload = (ImageButton) rootView.findViewById(R.id.default_action_bar_button_reload);
        bReload.setVisibility(View.VISIBLE);
        bReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.reloadCardViews();
            }
        });

        ImageView ivAddProject = (ImageView) rootView.findViewById(R.id.fragment_project_list_image_add_project);
        ivAddProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).showCreateProjectFragment();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        if (adapter.clickedProject == null) {
            return;
        }

        new AsyncTask<Object, Void, Project>() {
            @Override
            protected Project doInBackground(Object... objects) {
                Project savedProject = (Project) JsonHelper.fromJsonGeneric(Project.class, adapter.clickedProjectJson);
                Project newProject = getSQLiteDB().project().getByID(savedProject.get_id());
                newProject.initCardObjects(getSQLiteDB());
                return newProject;
            }

            @Override
            protected void onPostExecute(Project newProject) {
                if (!adapter.clickedProjectJson.equals(JsonHelper.toJson(newProject))) {
                    adapter.reloadCardView(adapter.clickedProject, false);
                    return;
                }

                List<String> newCardObjectJsonList = new LinkedList<>();
                for (AbstractCardObject cardObject : newProject.getAllCardObjects()) {
                    newCardObjectJsonList.add(JsonHelper.toJson(cardObject));
                }

                for (int i = 0; i < adapter.cardObjectJsonList.size(); i++) {
                    String savedCardObject = adapter.cardObjectJsonList.get(i);
                    String newCardObject = newCardObjectJsonList.get(i);
                    if (!savedCardObject.equals(newCardObject)) {
                        adapter.reloadCardView(adapter.clickedProject, false);
                        return;
                    }
                }
            }
        }.execute();
    }
}
