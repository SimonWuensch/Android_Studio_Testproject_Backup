package ssi.ssn.com.ssi_service.fragment.customlist;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ssi.ssn.com.ssi_service.R;
import ssi.ssn.com.ssi_service.fragment.AbstractFragment;
import ssi.ssn.com.ssi_service.model.helper.JsonHelper;
import ssi.ssn.com.ssi_service.model.network.response.AbstractResponse;
import ssi.ssn.com.ssi_service.model.network.response.ResponseApplication;

public class FragmentCustomList extends AbstractFragment {

    public static String TAG = FragmentCustomList.class.getSimpleName();

    private static int FRAGMENT_LAYOUT = R.layout.fragment_custom_list;
    private static int RECYCLERVIEW = R.id.fragment_custom_list_recycler_view;
    private static int CARDVIEW = R.layout.fragment_custom_list_card_view;

    private static String HEADLINE_STRING_ID = TAG + "HEADLINE_STRING_ID";
    private static String RESPONSE_JSON = TAG + "_RESPONSE_JSON";
    private static String FRAGMENT_TYPE = TAG + "_FRAGMENT_TYPE";

    private int headlineStringID;
    private AbstractResponse responseAbstract;


    private View rootView;

    public static FragmentCustomList newInstance(int headlineString, Type type, String jsonResponse) {
        FragmentCustomList fragment = new FragmentCustomList();
        Bundle bundle = new Bundle();
        bundle.putInt(HEADLINE_STRING_ID, headlineString);
        bundle.putString(RESPONSE_JSON, jsonResponse);
        bundle.putString(FRAGMENT_TYPE, JsonHelper.toJson(type));
        fragment.setArguments(bundle);
        return fragment;
    }

    private void loadArguments() {
        if (getArguments() != null) {
            headlineStringID = getArguments().getInt(HEADLINE_STRING_ID);
            String jsonResponse = getArguments().getString(RESPONSE_JSON);
            Type type = (Type) JsonHelper.fromJsonGeneric(Type.class, getArguments().getString(FRAGMENT_TYPE));
            responseAbstract = type.deserialize(jsonResponse);
        }
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

    public void initViewComponents() {
        TextView tvHeadLine = (TextView) rootView.findViewById(R.id.default_action_bar_text_view_headline);
        tvHeadLine.setText(getActivity().getString(headlineStringID));
    }

    public enum Type {
        APPLICATION(ResponseApplication.class);

        public Class responseClass;

        Type(Class responseClass) {
            this.responseClass = responseClass;
        }

        public AbstractResponse deserialize(String json) {
            return (ResponseApplication) JsonHelper.fromJsonGeneric(responseClass, json);
        }
    }
}
