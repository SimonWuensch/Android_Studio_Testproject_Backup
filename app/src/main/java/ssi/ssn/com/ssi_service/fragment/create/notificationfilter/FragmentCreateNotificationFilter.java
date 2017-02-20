package ssi.ssn.com.ssi_service.fragment.create.notificationfilter;

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

import ssi.ssn.com.ssi_service.R;
import ssi.ssn.com.ssi_service.activity.MainActivity;
import ssi.ssn.com.ssi_service.fragment.AbstractFragment;
import ssi.ssn.com.ssi_service.fragment.create.CreateUpdateDeleteStatus;
import ssi.ssn.com.ssi_service.model.data.source.Project;
import ssi.ssn.com.ssi_service.model.data.source.cardobject.CardObjectNotification;
import ssi.ssn.com.ssi_service.model.helper.JsonHelper;
import ssi.ssn.com.ssi_service.model.helper.ObservationHelper;
import ssi.ssn.com.ssi_service.model.helper.SourceHelper;
import ssi.ssn.com.ssi_service.model.network.response.notification.objects.ResponseNotification;

public class FragmentCreateNotificationFilter extends AbstractFragment {

    public static String TAG = FragmentCreateNotificationFilter.class.getSimpleName();

    private static int FRAGMENT_LAYOUT = R.layout.fragment_create_notification_filter;

    private static String PROJECT_ID = TAG + "PROJECT_ID";
    private static String RESPONSE_NOTIFICATION_JSON = TAG + "RESPONSE_NOTIFICATION_JSON";

    private CreateUpdateDeleteStatus fragmentStatus;

    private View rootView;

    private EditText etNode;
    private EditText etActiveTime;
    private EditText etText;

    private Button bSearch;
    private Button bFinal;

    private Spinner spTimeInput;
    private Spinner spSeverityInput;


    private Project project;
    private ResponseNotification notification;

    public static FragmentCreateNotificationFilter newInstance(Project project, ResponseNotification notification) {
        if (project == null) {
            return new FragmentCreateNotificationFilter();
        }

        FragmentCreateNotificationFilter fragment = new FragmentCreateNotificationFilter();
        Bundle bundle = new Bundle();
        bundle.putInt(PROJECT_ID, project.get_id());
        bundle.putString(RESPONSE_NOTIFICATION_JSON, JsonHelper.toJson(notification));
        fragment.setArguments(bundle);
        return fragment;
    }

    private void loadArguments() {
        if (getArguments() == null) {
            fragmentStatus = CreateUpdateDeleteStatus.ADD;
            return;
        }

        fragmentStatus = CreateUpdateDeleteStatus.DELETE;
        int projectID = getArguments().getInt(PROJECT_ID);
        project = getSQLiteDB().project().getByID(projectID);
        notification = (ResponseNotification) JsonHelper.fromJsonGeneric(ResponseNotification.class, getArguments().getString(RESPONSE_NOTIFICATION_JSON));
        Log.d(TAG, "Fragment status [" + fragmentStatus + "].");
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

            initViewComponents();
            if (!fragmentStatus.equals(CreateUpdateDeleteStatus.ADD) && notification != null) {
                fillViewComponentsWithNotificationInfo();
            }
        }
        return rootView;
    }

    public void initViewComponents() {
        TextView tvHeadLine = (TextView) rootView.findViewById(R.id.default_action_bar_text_view_headline);
        tvHeadLine.setText(SourceHelper.getString(getActivity(), R.string.fragment_notification_filter_title));

        etNode = (EditText) rootView.findViewById(R.id.fragment_create_notification_filter_edit_text_note);
        etActiveTime = (EditText) rootView.findViewById(R.id.fragment_create_notification_filter_edit_text_active_time);
        etText = (EditText) rootView.findViewById(R.id.fragment_create_notification_filter_edit_text_text);

        bSearch = (Button) rootView.findViewById(R.id.fragment_create_notification_filter_button_show_notification_list);
        bSearch.setOnClickListener(onClickNotificationSearch());

        bFinal = (Button) rootView.findViewById(R.id.fragment_create_notification_filter_button_final);

        spTimeInput = (Spinner) rootView.findViewById(R.id.fragment_create_notification_filter_spinner_time_input);
        ArrayAdapter<CharSequence> adapterTimeInput = ArrayAdapter.createFromResource(getActivity(), R.array.fragment_create_project_drop_down_box_time_input, android.R.layout.simple_spinner_item);
        adapterTimeInput.setDropDownViewResource(R.layout.spinner_drop_down_item);
        spTimeInput.setAdapter(adapterTimeInput);

        spSeverityInput = (Spinner) rootView.findViewById(R.id.fragment_create_notification_filter_spinner_severity_input);
        ArrayAdapter<CharSequence> adapterSeverityInput = ArrayAdapter.createFromResource(getActivity(), R.array.fragment_create_notification_filter_drop_down_box_severity_input, android.R.layout.simple_spinner_item);
        adapterSeverityInput.setDropDownViewResource(R.layout.spinner_drop_down_item);
        spSeverityInput.setAdapter(adapterSeverityInput);
    }

    public void fillViewComponentsWithNotificationInfo() {
        if (notification != null) {
            String notePath = notification.getNodePath();
            String rNote = notePath == null ? "" : notification.getNodePath().substring(notePath.lastIndexOf(".") + 1, notePath.length());
            etNode.setText(rNote);
            etActiveTime.setText("30");
            etText.setText(notification.getText());
            spTimeInput.setSelection(0);

            switch (notification.getDefinition().getSeverity()) {
                case ERROR:
                    spSeverityInput.setSelection(0);
                    break;
                case WARN:
                    spSeverityInput.setSelection(1);
                    break;
                case INFO:
                    spSeverityInput.setSelection(2);
                    break;
            }
            Log.d(TAG, "Fragment view components filled with notification [" + JsonHelper.toJson(notification) + "].");
        }
    }

    private View.OnClickListener onClickNotificationSearch() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AsyncTask<Object, Void, Object>() {
                    @Override
                    protected Object doInBackground(Object... objects) {
                        if (ObservationHelper.isProjectOutOfDate(project)) {
                            CardObjectNotification.init(getSQLiteDB(), project);
                            project.getCardObjectNotification().loadFromNetwork(((MainActivity) getActivity()).getRequestHandler(), project);
                            project.getCardObjectNotification().detectCardStatus(getSQLiteDB());
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Object o) {
                        ((MainActivity) getActivity()).showNotificationListFragment(project.get_id());
                    }
                }.execute();
            }
        };
    }

}
