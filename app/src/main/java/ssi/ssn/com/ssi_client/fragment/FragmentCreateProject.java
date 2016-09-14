package ssi.ssn.com.ssi_client.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ssi.ssn.com.ssi_client.R;

public class FragmentCreateProject extends Fragment {


    public static String TAG = FragmentCreateProject.class.getSimpleName();

    private static int FRAGMENT_LAYOUT = R.layout.fragment_create_project;

    private static String ARGS_PROJECT_ADDRESS = TAG + "_ARGS_PROJECT_ADDRESS";
    private static String ARGS_USERNAME = TAG + "_ARGS_USERNAME";
    private static String ARGS_PASSWORD = TAG + "_ARGS_PASSWORD";

    private String projectAddress;
    private String userName;
    private String password;

    private View rootView;

    public static FragmentCreateProject newInstance(String projectAddress, String userName, String password) {
        FragmentCreateProject fragment = new FragmentCreateProject();
        Bundle bundle = new Bundle();
        bundle.putString(ARGS_PROJECT_ADDRESS, projectAddress);
        bundle.putString(ARGS_USERNAME, userName);
        bundle.putString(ARGS_PASSWORD, password);
        fragment.setArguments(bundle);
        return fragment;
    }

    private Object loadArguments(){
        projectAddress= getArguments().getString(ARGS_PROJECT_ADDRESS);
        userName = getArguments().getString(ARGS_USERNAME);
        password = getArguments().getString(ARGS_PASSWORD);
        return null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(rootView == null) {
            rootView = inflater.inflate(FRAGMENT_LAYOUT, container, false);
            Log.d(TAG, "Fragment inflated [" + getActivity().getResources().getResourceName(FRAGMENT_LAYOUT) + "].");



            /*
            Space for Custom Views
            */
        }
        return rootView;
    }
}
