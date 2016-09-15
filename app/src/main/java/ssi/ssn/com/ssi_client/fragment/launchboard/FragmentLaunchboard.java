package ssi.ssn.com.ssi_client.fragment.launchboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ssi.ssn.com.ssi_client.R;

public class FragmentLaunchBoard extends android.app.Fragment {

    public static String TAG = FragmentLaunchBoard.class.getSimpleName();

    private static int FRAGMENT_LAYOUT = R.layout.fragment_launch_board;

    private static String ARGS_PROJECT_ADDRESS = TAG + "_ARGS_PROJECT_ADDRESS";
    private static String ARGS_USERNAME = TAG + "_ARGS_USERNAME";
    private static String ARGS_PASSWORD = TAG + "_ARGS_PASSWORD";

    private String projectAddress;
    private String userName;
    private String password;


    private View rootView;

    public static FragmentLaunchBoard newInstance() {
        return new FragmentLaunchBoard();
    }

    public static FragmentLaunchBoard newInstance(String projectAddress, String userName, String password) {
        FragmentLaunchBoard fragment = new FragmentLaunchBoard();
        Bundle bundle = new Bundle();
        bundle.putString(ARGS_PROJECT_ADDRESS, projectAddress);
        bundle.putString(ARGS_USERNAME, userName);
        bundle.putString(ARGS_PASSWORD, password);
        fragment.setArguments(bundle);
        return fragment;
    }

    private Object loadArguments() {
        projectAddress = getArguments().getString(ARGS_PROJECT_ADDRESS);
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
        if (rootView == null) {
            rootView = inflater.inflate(FRAGMENT_LAYOUT, container, false);
            Log.d(TAG, "Fragment inflated [" + getActivity().getResources().getResourceName(FRAGMENT_LAYOUT) + "].");



        }
        return rootView;
    }
}