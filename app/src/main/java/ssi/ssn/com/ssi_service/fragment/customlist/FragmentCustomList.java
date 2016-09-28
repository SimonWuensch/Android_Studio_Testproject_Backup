package ssi.ssn.com.ssi_service.fragment.customlist;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import ssi.ssn.com.ssi_service.R;

public class FragmentCustomList extends Fragment {

    public static String TAG = FragmentCustomList.class.getSimpleName();

    private static int FRAGMENT_LAYOUT = R.layout.fragment_custom_list;
    private static int RECYCLERVIEW = R.id.fragment_custom_list_recycler_view;
    private static int CARDVIEW = R.layout.fragment_custom_list_cardview;

    private static String RESPONSE = TAG + "_RESPONSE";
    private String response;

    private View rootView;

    public static FragmentCustomList newInstance(String response) {
        FragmentCustomList fragment = new FragmentCustomList();
        Bundle bundle = new Bundle();
        bundle.putString(RESPONSE, response);
        fragment.setArguments(bundle);
        return fragment;
    }

    private Object loadArguments(){
        response = getArguments().getString(RESPONSE);
        return null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadArguments();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(rootView == null) {
            rootView = inflater.inflate(FRAGMENT_LAYOUT, container, false);
            Log.d(TAG, "Fragment inflated [" + getActivity().getResources().getResourceName(FRAGMENT_LAYOUT) + "].");

            RecyclerView.Adapter mAdapter = new FragmentCustomListAdapter(CARDVIEW, this, response);
            Log.d(TAG, "Adapter [" + mAdapter.getClass().getSimpleName() + "] with CardView [" + getActivity().getResources().getResourceName(CARDVIEW) + "] initialized.");

            RecyclerView mRecyclerView = (RecyclerView) rootView.findViewById(RECYCLERVIEW);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setAdapter(mAdapter);
            Log.d(TAG, "RecyclerView [" + getActivity().getResources().getResourceName(RECYCLERVIEW) + "] initialized.");

            initializeViewComponents();
        }
        return rootView;
    }

    public void initializeViewComponents(){
    }
}
