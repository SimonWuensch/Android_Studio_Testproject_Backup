package ssi.ssn.com.ssi_service.fragment.createproject;


import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import ssi.ssn.com.ssi_service.R;
import ssi.ssn.com.ssi_service.model.data.ressource.Project;
import ssi.ssn.com.ssi_service.test.ExecutorServiceTest;

public class FragmentCreateProject extends Fragment {


    public static String TAG = FragmentCreateProject.class.getSimpleName();

    private static int FRAGMENT_LAYOUT = R.layout.fragment_create_project;

    private static String ARGS_PROJECT_ADDRESS = TAG + "_ARGS_PROJECT_ADDRESS";
    private static String ARGS_USERNAME = TAG + "_ARGS_USERNAME";
    private static String ARGS_PASSWORD = TAG + "_ARGS_PASSWORD";

    private Project project;

    private EditText etProjectAddress;
    private EditText etUserName;
    private EditText etPassword;
    private Button bAddProject;

    private View rootView;

    public static FragmentCreateProject newInstance() {
        return new FragmentCreateProject();
    }

    public static FragmentCreateProject newInstance(Project project) {
        FragmentCreateProject fragment = new FragmentCreateProject();
        Bundle bundle = new Bundle();
        bundle.putString(ARGS_PROJECT_ADDRESS, project.getServerAddress());
        bundle.putString(ARGS_USERNAME, project.getUserName());
        bundle.putString(ARGS_PASSWORD, project.getPassword());
        fragment.setArguments(bundle);
        return fragment;
    }

    private void loadArguments() {
        String projectAddress = getArguments().getString(ARGS_PROJECT_ADDRESS);
        String userName = getArguments().getString(ARGS_USERNAME);
        String password = getArguments().getString(ARGS_PASSWORD);
        this.project = new Project(projectAddress, userName, password);
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

            etProjectAddress = (EditText) rootView.findViewById(R.id.fragment_create_project_edit_text_project_address);
            etUserName = (EditText) rootView.findViewById(R.id.fragment_create_project_edit_text_user_name);
            etPassword = (EditText) rootView.findViewById(R.id.fragment_create_project_edit_text_password);
            bAddProject = (Button) rootView.findViewById(R.id.fragment_create_project_button_add_project);
            TextView tvHeadLine = (TextView) rootView.findViewById(R.id.default_action_bar_text_view_headline);


            if(project != null) {
                etProjectAddress.setText(project.getServerAddress());
                etUserName.setText(project.getUserName());
                etPassword.setText(project.getPassword());
            }
            tvHeadLine.setText(getActivity().getString(R.string.fragment_create_project_title));

            bAddProject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /*Toast.makeText(getActivity(), "Project add clicked... [" +
                            etProjectAddress.getText().toString() + ", " +
                            etUserName.getText().toString() + ", " +
                            etPassword.getText().toString() + "]", Toast.LENGTH_SHORT).show();
                    */
                    //Project project = new Project("172.26.78.235:8180", "admin", "admin");
                    //((MainActivity)getActivity()).getRequestHandler().doNotificationRequest(project, NotificationRequest.Path.TABLE_ALL);

                    try {
                        ExecutorServiceTest.runIt();
                    }catch(Throwable t){
                        t.printStackTrace();
                    }
                }
            });
        }
        return rootView;
    }

}
