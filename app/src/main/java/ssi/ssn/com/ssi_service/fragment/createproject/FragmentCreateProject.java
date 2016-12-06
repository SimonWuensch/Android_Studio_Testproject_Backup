package ssi.ssn.com.ssi_service.fragment.createproject;


import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;
import java.util.concurrent.ExecutorService;

import ssi.ssn.com.ssi_service.R;
import ssi.ssn.com.ssi_service.activity.MainActivity;
import ssi.ssn.com.ssi_service.fragment.AbstractFragment;
import ssi.ssn.com.ssi_service.fragment.customlist.FragmentCustomList;
import ssi.ssn.com.ssi_service.model.data.source.Project;
import ssi.ssn.com.ssi_service.model.extended.ExtendedAsyncTask;
import ssi.ssn.com.ssi_service.model.helper.AlertDialogHelper;
import ssi.ssn.com.ssi_service.model.helper.FormatHelper;
import ssi.ssn.com.ssi_service.model.helper.JsonHelper;
import ssi.ssn.com.ssi_service.model.helper.SourceHelper;
import ssi.ssn.com.ssi_service.model.network.communication.HttpAddressExists;
import ssi.ssn.com.ssi_service.model.network.handler.RequestHandler;
import ssi.ssn.com.ssi_service.model.network.response.application.ResponseApplication;

public class FragmentCreateProject extends AbstractFragment {

    public static String TAG = FragmentCreateProject.class.getSimpleName();
    private static int FRAGMENT_LAYOUT = R.layout.fragment_create_project;
    private static String PROJECT_JSON = TAG + "PROJECT_JSON";

    private Status fragmentStatus;
    private EditText etServerAddress;
    private EditText etUserName;
    private EditText etPassword;
    private EditText etObservationInterval;
    private Button bFinal;
    private Button bShowApplicationInfo;
    private Spinner spTimeInput;
    private View rootView;
    private Project project;

    private RequestHandler requestHandler;

    public static FragmentCreateProject newInstance(Project project) {
        if (project == null) {
            return new FragmentCreateProject();
        }

        FragmentCreateProject fragment = new FragmentCreateProject();
        Bundle bundle = new Bundle();
        bundle.putString(PROJECT_JSON, JsonHelper.toJson(project));
        fragment.setArguments(bundle);
        return fragment;
    }

    private void loadArguments() {
        if (getArguments() == null) {
            fragmentStatus = Status.ADD;
            return;
        }

        fragmentStatus = Status.DELETE;
        String projectJson = getArguments().getString(PROJECT_JSON);
        project = (Project) JsonHelper.fromJsonGeneric(Project.class, projectJson);
        Log.d(TAG, "Fragment status [" + fragmentStatus + "].");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestHandler = ((MainActivity) getActivity()).getRequestHandler();
        loadArguments();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(FRAGMENT_LAYOUT, container, false);
            Log.d(TAG, "Fragment inflated [" + getActivity().getResources().getResourceName(FRAGMENT_LAYOUT) + "].");

            initViewComponents();
            if (!fragmentStatus.equals(Status.ADD)) {
                fillViewComponentsWithProjectInfo();
            }
        }
        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    public void initViewComponents() {
        TextView tvHeadLine = (TextView) rootView.findViewById(R.id.default_action_bar_text_view_headline);
        tvHeadLine.setText(SourceHelper.getString(getActivity(), R.string.fragment_create_project_title));

        etServerAddress = (EditText) rootView.findViewById(R.id.fragment_create_project_edit_text_project_address);
        etUserName = (EditText) rootView.findViewById(R.id.fragment_create_project_edit_text_user_name);
        etPassword = (EditText) rootView.findViewById(R.id.fragment_create_project_edit_text_password);
        etObservationInterval = (EditText) rootView.findViewById(R.id.fragment_create_project_edit_text_update_interval);

        bShowApplicationInfo = (Button) rootView.findViewById(R.id.fragment_create_project_button_show_project_application_info);
        bShowApplicationInfo.setOnClickListener(onClickShowApplicationInfo());

        spTimeInput = (Spinner) rootView.findViewById(R.id.fragment_create_project_spinner_time_input);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.fragment_create_project_drop_down_box_time_input, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_drop_down_item);
        spTimeInput.setAdapter(adapter);

        bFinal = (Button) rootView.findViewById(R.id.fragment_create_project_button_final);
        bFinal.setOnClickListener(onFinalButtonClick());
        Log.d(TAG, "Fragment view components initialized.");
    }

    public void fillViewComponentsWithProjectInfo() {
        if (project != null) {
            etServerAddress.setText(project.getServerAddress());
            etUserName.setText(project.getUserName());
            etPassword.setText(project.getPassword());

            long millis = project.getObservationInterval();
            String observationInterval;
            if (FormatHelper.formatMillisecondsToMinutes(millis) % 60 != 0) {
                observationInterval = String.valueOf(FormatHelper.formatMillisecondsToMinutes(millis));
                spTimeInput.setSelection(0);
            } else {
                observationInterval = String.valueOf(FormatHelper.formatMillisecondsToHours(millis));
                spTimeInput.setSelection(1);
            }
            etObservationInterval.setText(observationInterval);

            switch (fragmentStatus) {
                case ADD:
                    bFinal.setText(SourceHelper.getString(getActivity(), R.string.fragment_create_project_button_add_project));
                    break;
                case DELETE:
                    bFinal.setText(SourceHelper.getString(getActivity(), R.string.fragment_create_project_button_delete_project));
                    super.onTextChangeListener(etServerAddress);
                    super.onTextChangeListener(etUserName);
                    super.onTextChangeListener(etPassword);
                    super.onTextChangeListener(etObservationInterval);
                    spTimeInput.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                            doAfterChanged();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parentView) {
                        }
                    });
                    break;
            }
            Log.d(TAG, "Fragment view components filled with project [" + project + "].");
        }
    }

    @Override
    public void doAfterChanged() {
        String initialServerAddress = project.getServerAddress();
        String initialUserName = project.getUserName();
        String initialPassword = project.getPassword();

        long millis = project.getObservationInterval();
        String initialObservationInterval;
        int timeInputSelection;
        if (FormatHelper.formatMillisecondsToMinutes(millis) % 60 != 0) {
            initialObservationInterval = String.valueOf(FormatHelper.formatMillisecondsToMinutes(millis));
            timeInputSelection = 0;
        } else {
            initialObservationInterval = String.valueOf(FormatHelper.formatMillisecondsToHours(millis));
            timeInputSelection = 1;
        }

        boolean isChangedServerAddress = !initialServerAddress.equals(etServerAddress.getText().toString());
        boolean isChangedUsername = !initialUserName.equals(etUserName.getText().toString());
        boolean isChangedPassword = !initialPassword.equals(etPassword.getText().toString());
        boolean isChangedObservationInterval = !initialObservationInterval.equals(etObservationInterval.getText().toString());
        boolean isChangedTimeInput = timeInputSelection != spTimeInput.getSelectedItemPosition();

        if (isChangedServerAddress || isChangedUsername || isChangedPassword || isChangedObservationInterval || isChangedTimeInput) {
            fragmentStatus = Status.UPDATE;
        } else {
            fragmentStatus = Status.DELETE;
        }
        updateFinalButtonText();
    }

    public void updateFinalButtonText() {
        switch (fragmentStatus) {
            case ADD:
                bFinal.setText(SourceHelper.getString(getActivity(), R.string.fragment_create_project_button_add_project));
                break;
            case UPDATE:
                bFinal.setText(SourceHelper.getString(getActivity(), R.string.fragment_create_project_button_update_project));
                break;
            case DELETE:
                bFinal.setText(SourceHelper.getString(getActivity(), R.string.fragment_create_project_button_delete_project));
                break;
        }
    }

    public Project getProjectWithViewComponents() {
        if (project == null) {
            project = new Project();
        }
        project.setServerAddress(etServerAddress.getText().toString());
        project.setUserName(etUserName.getText().toString());
        project.setPassword(etPassword.getText().toString());
        project.setObservationInterval(getObservationInterval());
        return project;
    }

    private long getObservationInterval() {
        String selectedItem = spTimeInput.getSelectedItem().toString();
        String[] spinnerItems = getActivity().getResources().getStringArray(R.array.fragment_create_project_drop_down_box_time_input);

        if (selectedItem.equals(spinnerItems[0])) {
            return FormatHelper.formatMinutesToMilliseconds(etObservationInterval.getText().toString());
        }
        if (selectedItem.equals(spinnerItems[1])) {
            return FormatHelper.formatHoursToMilliseconds(etObservationInterval.getText().toString());
        }
        return 0;
    }

    private AlertDialog getAlertDialog() {
        String message = SourceHelper.getString(getActivity(), fragmentStatus.equals(Status.ADD) ?
                R.string.fragment_create_project_message_server_address_not_correct_anyhow_add :
                R.string.fragment_create_project_message_server_address_not_correct_anyhow_update);
        String positiveButtonText = SourceHelper.getString(getActivity(), R.string.yes);
        String negativeButtonText = SourceHelper.getString(getActivity(), R.string.no);

        project.setProjectName(null);
        project.setProjectLocation(null);
        project.setProjectOrderNr(null);
        AsyncTask positiveButtonTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                switch (fragmentStatus) {
                    case ADD:
                        getSQLiteHelper().addProject(project);
                        break;
                    case UPDATE:
                        getSQLiteHelper().updateProject(project);
                        break;
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                ((MainActivity) getActivity()).showProjectListFragment();
            }
        };
        return AlertDialogHelper.init(getActivity(), message, positiveButtonText, positiveButtonTask, negativeButtonText, null);
    }

    public View.OnClickListener onClickShowApplicationInfo() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Project project = getProjectWithViewComponents();
                bShowApplicationInfo.setEnabled(false);
                setLoadingViewVisible(true);

                new AsyncTask<Object, Void, Long>() {
                    @Override
                    protected Long doInBackground(Object... objects) {
                        return HttpAddressExists.exists(project.getServerAddress());
                    }

                    @Override
                    protected void onPostExecute(Long responseCode) {
                        if (responseCode != 200) {
                            Toast.makeText(getActivity(), SourceHelper.getString(getActivity(), R.string.fragment_create_project_message_server_address_not_correct), Toast.LENGTH_SHORT).show();
                            bShowApplicationInfo.setEnabled(true);
                            setLoadingViewVisible(false);
                            return;
                        }

                        new AsyncTask<Object, Void, Object>(){
                            @Override
                            protected Object doInBackground(Object... objects) {
                                requestHandler.sendRequestApplication(project);
                                return null;
                            }

                            @Override
                            protected void onPostExecute(Object o) {
                                if (project.getDefaultResponseApplication() != null && project.getDefaultResponseApplication().getCode() == 200) {
                                    ((MainActivity) getActivity()).showCustomListFragment(R.string.fragment_custom_list_application_info_title, FragmentCustomList.Type.APPLICATION, project.getDefaultResponseApplication().getResult());
                                } else {
                                    Toast.makeText(getActivity(), SourceHelper.getString(getActivity(), R.string.fragment_create_project_message_server_address_is_no_valid_lighthouse_address), Toast.LENGTH_SHORT).show();
                                }
                                bShowApplicationInfo.setEnabled(true);
                                setLoadingViewVisible(false);
                            }
                        }.execute();
                    }
                }.execute();
            }
        };
    }

    // ** APPLICATION INFO BUTTON CLICK ********************************************************* //
    // ** FINAL BUTTON CLICK ******************************************************************** //
    public View.OnClickListener onFinalButtonClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Project project = getProjectWithViewComponents();
                switch (fragmentStatus) {
                    case ADD:
                    case UPDATE:
                        onClickProjectAddUpdate(project);
                        break;
                    case DELETE:
                        onClickProjectDelete(project);
                        break;
                }
            }
        };
    }

    public void onClickProjectDelete(final Project project) {
        getSQLiteHelper().deleteProject(project);
        ((MainActivity) getActivity()).showProjectListFragment();
    }

    public void onClickProjectAddUpdate(final Project project) {
        setLoadingViewVisible(true);
        new AsyncTask<Object, Void, Long>(){
            @Override
            protected Long doInBackground(Object... objects) {
                return HttpAddressExists.exists(project.getServerAddress());
            }

            @Override
            protected void onPostExecute(Long responseCode) {
                if (responseCode != 200) {
                    getAlertDialog().show();
                    bShowApplicationInfo.setEnabled(true);
                    setLoadingViewVisible(false);
                    return;
                }

                new AsyncTask<Object, Void, Object>(){
                    @Override
                    protected Object doInBackground(Object... objects) {
                        requestHandler.sendRequestApplication(project);
                        if (project.getDefaultResponseApplication().getCode() != 200) {
                            Toast.makeText(getActivity(), SourceHelper.getString(getActivity(), R.string.fragment_create_project_message_server_address_is_no_valid_lighthouse_address), Toast.LENGTH_SHORT).show();
                            return null;
                        }

                        ResponseApplication responseApplication = (ResponseApplication) JsonHelper.fromJsonGeneric(ResponseApplication.class, project.getDefaultResponseApplication().getResult());
                        if (!responseApplication.getBuild().getVersion().startsWith(MainActivity.ACCEPTED_PROJECT_VERSION)) {
                            Toast.makeText(getActivity(),
                                    SourceHelper.getString(getActivity(), R.string.fragment_create_project_message_application_version_equals_not_2_0)
                                            + " - [" + responseApplication.getBuild().getVersion() + "]", Toast.LENGTH_SHORT).show();
                            return null;
                        }

                        requestHandler.sendRequestLogin(project);
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Object o) {
                        if (project.getDefaultResponseLogin().getCode() != 200) {
                            Toast.makeText(getActivity(), SourceHelper.getString(getActivity(), R.string.fragment_create_project_message_login_data_not_correct), Toast.LENGTH_SHORT).show();
                        }else{
                            project.loadFromNetwork();
                            switch (fragmentStatus) {
                                case ADD:
                                    getSQLiteHelper().addProject(project);
                                    break;
                                case UPDATE:
                                    getSQLiteHelper().updateProject(project);
                                    break;
                            }
                            ((MainActivity) getActivity()).showProjectListFragment();
                        }

                        bShowApplicationInfo.setEnabled(true);
                        setLoadingViewVisible(false);
                    }
                }.execute();
            }
        }.execute();
    }

    public enum Status {
        ADD,
        UPDATE,
        DELETE;
    }
}
