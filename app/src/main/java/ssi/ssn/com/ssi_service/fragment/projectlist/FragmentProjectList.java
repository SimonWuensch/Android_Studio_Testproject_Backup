package ssi.ssn.com.ssi_service.fragment.projectlist;

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

import java.util.List;

import ssi.ssn.com.ssi_service.R;
import ssi.ssn.com.ssi_service.activity.MainActivity;
import ssi.ssn.com.ssi_service.fragment.AbstractFragment;
import ssi.ssn.com.ssi_service.model.data.source.Project;
import ssi.ssn.com.ssi_service.model.data.source.cardobject.AbstractCardObject;
import ssi.ssn.com.ssi_service.model.helper.ObservationHelper;
import ssi.ssn.com.ssi_service.model.helper.SourceHelper;

public class FragmentProjectList extends AbstractFragment {

    public static String TAG = FragmentProjectList.class.getSimpleName();

    private static int FRAGMENT_LAYOUT = R.layout.fragment_project_list;
    private static int RECYCLERVIEW = R.id.fragment_project_list_recycler_view;
    private static int CARDVIEW = R.layout.fragment_project_list_card_view;

    private List<Project> projects;
    private FragmentProjectListAdapter mAdapter;

    private View rootView;
    public static FragmentProjectList newInstance() {
        FragmentProjectList fragment = new FragmentProjectList();
        return fragment;
    }

    public void initViewComponents() {
        TextView tvHeadLine = (TextView) rootView.findViewById(R.id.default_action_bar_text_view_headline);
        tvHeadLine.setText(SourceHelper.getString(getActivity(), R.string.fragment_project_list_title));

        ImageButton bReload = (ImageButton) rootView.findViewById(R.id.default_action_bar_button_reload);
        bReload.setVisibility(View.VISIBLE);
        bReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAdapter.reloadCardViews();
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(FRAGMENT_LAYOUT, container, false);
            Log.d(TAG, "Fragment inflated [" + getActivity().getResources().getResourceName(FRAGMENT_LAYOUT) + "].");

            projects = getSQLiteDB().project().getALL();
            mAdapter = new FragmentProjectListAdapter(CARDVIEW, this, projects);
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
}
