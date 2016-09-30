package ssi.ssn.com.ssi_service.fragment.customlist;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ssi.ssn.com.ssi_service.R;
import ssi.ssn.com.ssi_service.model.handler.JsonHelper;
import ssi.ssn.com.ssi_service.model.network.response.ResponseAbstract;
import ssi.ssn.com.ssi_service.model.network.response.ResponseApplication;

public class FragmentCustomList extends Fragment {

    public static String TAG = FragmentCustomList.class.getSimpleName();

    private static int FRAGMENT_LAYOUT = R.layout.fragment_custom_list;
    private static int RECYCLERVIEW = R.id.fragment_custom_list_recycler_view;
    private static int CARDVIEW = R.layout.fragment_custom_list_cardview;

    private static String RESPONSE_JSON = TAG + "_RESPONSE_JSON";
    private static String FRAGMENT_TYPE = TAG + "_FRAGMENT_TYPE";
    private ResponseAbstract responseAbstract;

    private View rootView;

    public static FragmentCustomList newInstance(Type type, String jsonResponse) {
        FragmentCustomList fragment = new FragmentCustomList();
        Bundle bundle = new Bundle();
        bundle.putString(RESPONSE_JSON, jsonResponse);
        bundle.putString(FRAGMENT_TYPE, JsonHelper.toJson(type));
        fragment.setArguments(bundle);
        return fragment;
    }

    private void loadArguments(){
        String jsonResponse = getArguments().getString(RESPONSE_JSON);
        Type type = (Type) JsonHelper.fromJsonGeneric(Type.class, getArguments().getString(FRAGMENT_TYPE));
        responseAbstract = type.deserialize(jsonResponse);
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

            RecyclerView.Adapter mAdapter = new FragmentCustomListAdapter(CARDVIEW, this, responseAbstract);
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

    public void initViewComponents(){
    }

    public enum Type{
        APPLICATION(ResponseApplication.class);

        public Class responseClass;
        Type(Class responseClass){
            this.responseClass = responseClass;
        }

        public ResponseAbstract deserialize(String json){
            return (ResponseApplication) JsonHelper.fromJsonGeneric(responseClass, json);
        }
    }
}
