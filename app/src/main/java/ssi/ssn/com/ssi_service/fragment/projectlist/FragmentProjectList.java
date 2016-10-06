package ssi.ssn.com.ssi_service.fragment.projectlist;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ssi.ssn.com.ssi_service.R;
import ssi.ssn.com.ssi_service.activity.MainActivity;
import ssi.ssn.com.ssi_service.fragment.AbstractFragment;

public class FragmentProjectList extends AbstractFragment {

    public static String TAG = FragmentProjectList.class.getSimpleName();

    private static int FRAGMENT_LAYOUT = R.layout.fragment_project_list;
    private static int RECYCLERVIEW = R.id.fragment_project_list_recycler_view;
    private static int CARDVIEW = R.layout.fragment_project_list_card_view;

    private static String OBJECTONE = "OBJECTONE";
    private static String OBJECTTWO = "OBJECTTWO";

    private View rootView;

    public static FragmentProjectList newInstance() {
        FragmentProjectList fragment = new FragmentProjectList();
        Bundle bundle = new Bundle();
        bundle.putString(OBJECTONE, "TESTONE");
        bundle.putString(OBJECTTWO, "TESTTWO");
        fragment.setArguments(bundle);
        return fragment;
    }

    private void loadArguments() {
        String objectOne = getArguments().getString(OBJECTONE);
        String objectTwo = getArguments().getString(OBJECTTWO);
    }

    public void initViewComponents() {
        TextView tvHeadLine = (TextView) rootView.findViewById(R.id.default_action_bar_text_view_headline);
        tvHeadLine.setText(getActivity().getString(R.string.fragment_project_list_title));

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

            RecyclerView.Adapter mAdapter = new FragmentProjectListAdapter(CARDVIEW, this);
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
