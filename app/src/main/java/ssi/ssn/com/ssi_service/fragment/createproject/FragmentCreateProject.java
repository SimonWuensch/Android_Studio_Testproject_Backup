package ssi.ssn.com.ssi_service.fragment.createproject;


import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ssi.ssn.com.ssi_service.R;
import ssi.ssn.com.ssi_service.activity.MainActivity;
import ssi.ssn.com.ssi_service.model.data.ressource.Project;
import ssi.ssn.com.ssi_service.model.network.handler.RequestHandler;

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
    private Button bShowApplicationInfo;
    private Spinner spTimeInput;

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
            initializeViewComponents();
            fillInputsWithProject();
        }
        return rootView;
    }

    public void initializeViewComponents(){
        TextView tvHeadLine = (TextView) rootView.findViewById(R.id.default_action_bar_text_view_headline);
        tvHeadLine.setText(getActivity().getString(R.string.fragment_create_project_title));

        etProjectAddress = (EditText) rootView.findViewById(R.id.fragment_create_project_edit_text_project_address);
        etUserName = (EditText) rootView.findViewById(R.id.fragment_create_project_edit_text_user_name);
        etPassword = (EditText) rootView.findViewById(R.id.fragment_create_project_edit_text_password);

        bShowApplicationInfo = (Button) rootView.findViewById(R.id.fragment_create_project_button_show_project_application_info);
        bShowApplicationInfo.setOnClickListener(onClickShowApplicationInfo());

        bAddProject = (Button) rootView.findViewById(R.id.fragment_create_project_button_add_project);
        bAddProject.setOnClickListener(onClickProjectAdd());

        spTimeInput = (Spinner) rootView.findViewById(R.id.fragment_create_project_spinner_time_input);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.drop_down_box_time_input, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTimeInput.setAdapter(adapter);
        Log.d(TAG, "Fragment view components initialized.");
    }

    public void fillInputsWithProject(){
        if (project != null) {
            etProjectAddress.setText(project.getServerAddress());
            etUserName.setText(project.getUserName());
            etPassword.setText(project.getPassword());
        }
        Log.d(TAG, "Fragment view components filled with project [" + project + "].");
    }

    public View.OnClickListener onClickShowApplicationInfo(){
        return new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).showCustomListFragment();
            }
        };
    }

    public View.OnClickListener onClickProjectAdd(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final RequestHandler requestHandler = ((MainActivity) getActivity()).getRequestHandler();
                final ExecutorService executor = Executors.newSingleThreadExecutor();
                final Project project = new Project(
                        etProjectAddress.getText().toString(),
                        etUserName.getText().toString(),
                        etPassword.getText().toString());

                requestHandler.getRequestApplicationTask(project).executeOnExecutor(executor);

                new AsyncTask<Object, Void, Object>() {
                    @Override
                    protected Object doInBackground(Object[] objects) {
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Object o) {
                        if (project.getDefaultResponseApplication().getCode() == 200) {
                            requestHandler.getRequestLoginTask(project).executeOnExecutor(executor);

                            new AsyncTask<Object, Void, Object>() {
                                @Override
                                protected Object doInBackground(Object... objects) {
                                    return null;
                                }

                                @Override
                                protected void onPostExecute(Object o) {
                                    if (project.getDefaultResponseLogin().getCode() == 200) {
                                        Toast.makeText(getActivity(), "Serverdresse und Logindaten sind korrekt.", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(getActivity(), "FEHLER: Logindaten sind nicht korrekt.", Toast.LENGTH_LONG).show();
                                    }
                                }
                            }.executeOnExecutor(executor);
                        } else {
                            Toast.makeText(getActivity(), "FEHLER: Serveradresse ist nicht korrekt.", Toast.LENGTH_LONG).show();
                        }
                    }
                }.executeOnExecutor(executor);


            }
        };
    }

}
