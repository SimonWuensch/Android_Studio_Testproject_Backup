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
import android.widget.Toast;

import ssi.ssn.com.ssi_service.R;
import ssi.ssn.com.ssi_service.activity.MainActivity;
import ssi.ssn.com.ssi_service.fragment.AbstractFragment;
import ssi.ssn.com.ssi_service.fragment.create.CreateUpdateDeleteStatus;
import ssi.ssn.com.ssi_service.fragment.list.notificationfilter.FragmentNotificationFilterList;
import ssi.ssn.com.ssi_service.model.data.source.Project;
import ssi.ssn.com.ssi_service.model.data.source.cardobject.CardObjectNotification;
import ssi.ssn.com.ssi_service.model.data.source.filter.FilterNotification;
import ssi.ssn.com.ssi_service.model.helper.FormatHelper;
import ssi.ssn.com.ssi_service.model.helper.JsonHelper;
import ssi.ssn.com.ssi_service.model.helper.ObservationHelper;
import ssi.ssn.com.ssi_service.model.helper.SourceHelper;
import ssi.ssn.com.ssi_service.model.network.response.notification.objects.NotificationSeverity;
import ssi.ssn.com.ssi_service.model.network.response.notification.objects.ResponseNotification;

public class FragmentCreateNotificationFilter extends AbstractFragment {

    public static String TAG = FragmentCreateNotificationFilter.class.getSimpleName();

    private static int FRAGMENT_LAYOUT = R.layout.fragment_create_notification_filter;

    private static String PROJECT_ID = TAG + "PROJECT_ID";
    private static String FILTER_NOTIFICATION_JSON = TAG + "FILTER_NOTIFICATION_JSON";
    private static String RESPONSE_NOTIFICATION_JSON = TAG + "RESPONSE_NOTIFICATION_JSON";

    private CreateUpdateDeleteStatus fragmentStatus;
    private boolean isStartedWithNotification = false;

    private View rootView;

    private EditText etNode;
    private EditText etActiveTime;
    private EditText etText;

    private Button bSearch;
    private Button bFinal;

    private Spinner spTimeInput;
    private Spinner spSeverityInput;

    private Project project;
    private FilterNotification filter;
    private ResponseNotification notification;

    public static FragmentCreateNotificationFilter newInstance(int projectID) {
        if (projectID == 0) {
            return new FragmentCreateNotificationFilter();
        }

        FragmentCreateNotificationFilter fragment = new FragmentCreateNotificationFilter();
        Bundle bundle = new Bundle();
        bundle.putInt(PROJECT_ID, projectID);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static FragmentCreateNotificationFilter newInstance(int projectID, ResponseNotification notification) {
        if (projectID == 0) {
            return new FragmentCreateNotificationFilter();
        }

        FragmentCreateNotificationFilter fragment = new FragmentCreateNotificationFilter();
        Bundle bundle = new Bundle();
        bundle.putInt(PROJECT_ID, projectID);
        bundle.putString(RESPONSE_NOTIFICATION_JSON, JsonHelper.toJson(notification));
        fragment.setArguments(bundle);
        return fragment;
    }

    public static FragmentCreateNotificationFilter newInstance(int projectID, FilterNotification filter) {
        if (projectID == 0) {
            return new FragmentCreateNotificationFilter();
        }

        FragmentCreateNotificationFilter fragment = new FragmentCreateNotificationFilter();
        Bundle bundle = new Bundle();
        bundle.putInt(PROJECT_ID, projectID);
        bundle.putString(FILTER_NOTIFICATION_JSON, JsonHelper.toJson(filter));
        fragment.setArguments(bundle);
        return fragment;
    }

    private void loadArguments() {
        if (getArguments() == null) {
            return;
        }

        if (getArguments().containsKey(RESPONSE_NOTIFICATION_JSON)) {
            notification = (ResponseNotification) JsonHelper.fromJsonGeneric(ResponseNotification.class, getArguments().getString(RESPONSE_NOTIFICATION_JSON));
            fragmentStatus = CreateUpdateDeleteStatus.ADD;
            isStartedWithNotification = true;
            fillViewComponentsWithNotificationInfo();
        } else if (getArguments().containsKey(FILTER_NOTIFICATION_JSON)) {
            filter = (FilterNotification) JsonHelper.fromJsonGeneric(FilterNotification.class, getArguments().getString(FILTER_NOTIFICATION_JSON));
            fragmentStatus = CreateUpdateDeleteStatus.DELETE;
            bSearch.setVisibility(View.INVISIBLE);
            fillViewComponentsWithNotificationFilter();
        } else {
            fragmentStatus = CreateUpdateDeleteStatus.ADD;
        }
        updateFinalButtonText();

        int projectID = getArguments().getInt(PROJECT_ID);
        project = getSQLiteDB().project().getByID(projectID);
        CardObjectNotification.init(getSQLiteDB(), project);
        Log.d(TAG, "Fragment status [" + fragmentStatus + "].");
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

            initViewComponents();
            loadArguments();
        }
        return rootView;
    }

    public void initViewComponents() {
        TextView tvHeadLine = (TextView) rootView.findViewById(R.id.default_action_bar_text_view_headline);
        tvHeadLine.setText(SourceHelper.getString(getActivity(), R.string.fragment_notification_filter_title));

        etNode = (EditText) rootView.findViewById(R.id.fragment_create_notification_filter_edit_text_note);
        etActiveTime = (EditText) rootView.findViewById(R.id.fragment_create_notification_filter_edit_text_active_time);
        etText = (EditText) rootView.findViewById(R.id.fragment_create_notification_filter_edit_text_text);

        spTimeInput = (Spinner) rootView.findViewById(R.id.fragment_create_notification_filter_spinner_time_input);
        ArrayAdapter<CharSequence> adapterTimeInput = ArrayAdapter.createFromResource(getActivity(), R.array.fragment_create_project_drop_down_box_time_input, android.R.layout.simple_spinner_item);
        adapterTimeInput.setDropDownViewResource(R.layout.spinner_drop_down_item);
        spTimeInput.setAdapter(adapterTimeInput);

        spSeverityInput = (Spinner) rootView.findViewById(R.id.fragment_create_notification_filter_spinner_severity_input);
        ArrayAdapter<CharSequence> adapterSeverityInput = ArrayAdapter.createFromResource(getActivity(), R.array.fragment_create_notification_filter_drop_down_box_severity_input, android.R.layout.simple_spinner_item);
        adapterSeverityInput.setDropDownViewResource(R.layout.spinner_drop_down_item);
        spSeverityInput.setAdapter(adapterSeverityInput);

        bSearch = (Button) rootView.findViewById(R.id.fragment_create_notification_filter_button_show_notification_list);
        bSearch.setOnClickListener(onClickNotificationSearch());

        bFinal = (Button) rootView.findViewById(R.id.fragment_create_notification_filter_button_final);
        bFinal.setOnClickListener(onClickFinalButton());
    }

    // ** Start Fragment Status == ADD ******************************************************* //
    public void fillViewComponentsWithNotificationInfo() {
        if (notification != null) {
            String notePath = notification.getNodePath();
            String rNote = notePath == null ? "" : notification.getNodePath().substring(notePath.lastIndexOf(".") + 1, notePath.length());
            etNode.setText(rNote);
            etActiveTime.setText("30");
            etText.setText(notification.getText());
            spTimeInput.setSelection(0);
            selectSeverity(notification.getDefinition().getSeverity());
            Log.d(TAG, "Fragment view components filled with notification [" + JsonHelper.toJson(notification) + "].");
        }
    }


    // ** Start Fragment Status == DELETE ******************************************************* //
    public void fillViewComponentsWithNotificationFilter() {
        if (filter != null) {
            etNode.setText(filter.getNote());

            String activeTime;
            if (FormatHelper.formatMillisecondsToMinutes(filter.getActiveTime()) % 60 != 0) {
                activeTime = String.valueOf(FormatHelper.formatMillisecondsToMinutes(filter.getActiveTime()));
                spTimeInput.setSelection(0);
            } else {
                activeTime = String.valueOf(FormatHelper.formatMillisecondsToHours(filter.getActiveTime()));
                spTimeInput.setSelection(1);
            }
            etActiveTime.setText(activeTime);
            etText.setText(filter.getText());

            selectSeverity(filter.getSeverity());
            updateFinalButtonText();
            Log.d(TAG, "Fragment view components filled with filter [" + JsonHelper.toJson(filter) + "].");
        }
    }

    @Override
    public void doAfterChanged() {
        String initialNode = filter.getNote();
        String initialText = filter.getText();

        long millis = filter.getActiveTime();
        String initialActiveTime;
        int timeInputSelection;
        if (FormatHelper.formatMillisecondsToMinutes(millis) % 60 != 0) {
            initialActiveTime = String.valueOf(FormatHelper.formatMillisecondsToMinutes(millis));
            timeInputSelection = 0;
        } else {
            initialActiveTime = String.valueOf(FormatHelper.formatMillisecondsToHours(millis));
            timeInputSelection = 1;
        }

        int selectedPosition = spSeverityInput.getSelectedItemPosition();
        NotificationSeverity selectedSeverity = selectedPosition == 0 ? NotificationSeverity.ERROR : selectedPosition == 1 ? NotificationSeverity.WARN : NotificationSeverity.INFO;

        boolean isChangedNode = !initialNode.equals(etNode.getText().toString());
        boolean isChangedActiveTime = !initialActiveTime.equals(etActiveTime.getText().toString());
        boolean isChangedText = !initialText.equals(etText.getText().toString());
        boolean isChangedTimeInput = timeInputSelection != spTimeInput.getSelectedItemPosition();
        boolean isChangedSeverityInput = filter.getSeverity() != selectedSeverity;

        if (isChangedNode || isChangedActiveTime || isChangedText || isChangedTimeInput || isChangedSeverityInput) {
            fragmentStatus = CreateUpdateDeleteStatus.UPDATE;
        } else {
            fragmentStatus = CreateUpdateDeleteStatus.DELETE;
        }
        updateFinalButtonText();
    }

    // ** Settings ****************************************************************************** //
    private void selectSeverity(NotificationSeverity severity) {
        switch (severity) {
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
    }

    private void updateFinalButtonText() {
        switch (fragmentStatus) {
            case ADD:
                bFinal.setText(SourceHelper.getString(getActivity(), R.string.add));
                break;
            case UPDATE:
                bFinal.setText(SourceHelper.getString(getActivity(), R.string.update));
                break;
            case DELETE:
                bFinal.setText(SourceHelper.getString(getActivity(), R.string.delete));
                super.onTextChangeListener(etNode);
                super.onTextChangeListener(etActiveTime);
                super.onTextChangeListener(etText);
                super.onSpinnerSelectionChangedListener(spTimeInput);
                super.onSpinnerSelectionChangedListener(spSeverityInput);
                break;
        }
    }

    private FilterNotification loadFilterFromComponentViewInputs() {
        long millis = 0;
        if (!etActiveTime.getText().toString().isEmpty()) {
            int activeTime = Integer.parseInt(etActiveTime.getText().toString());
            int minutes = spTimeInput.getSelectedItemPosition() == 0 ? activeTime : activeTime * 60;
            millis = FormatHelper.formatMinutesToMilliseconds(minutes);
        }
        int selectedPosition = spSeverityInput.getSelectedItemPosition();
        NotificationSeverity severity = selectedPosition == 0 ? NotificationSeverity.ERROR : selectedPosition == 1 ? NotificationSeverity.WARN : NotificationSeverity.INFO;
        FilterNotification newFilter = new FilterNotification(etNode.getText().toString(), millis, severity, etText.getText().toString());
        if (filter != null) {
            newFilter.setId(filter.getId());
        }
        return newFilter;
    }

    // ** ClickListener ************************************************************************* //
    private View.OnClickListener onClickNotificationSearch() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (filter != null) {
                    return;
                }
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

    private View.OnClickListener onClickFinalButton() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity activity = (MainActivity) getActivity();
                FilterNotification filter = loadFilterFromComponentViewInputs();
                boolean isSuccessful;
                switch (fragmentStatus) {
                    case ADD:
                        isSuccessful = project.getCardObjectNotification().addNotificationFilter(getSQLiteDB(), filter);
                        if (!isSuccessful) {
                            Log.e(TAG, "Adde failed. Notification filter: [" + JsonHelper.toJson(filter) + "]");
                            Toast.makeText(getActivity().getBaseContext(), R.string.fragment_create_notification_filter_alert_text_filter_already_exists, Toast.LENGTH_LONG).show();
                            return;
                        }
                        Log.i(TAG, "Add successful. Notification filter: [" + JsonHelper.toJson(filter) + "]");

                        if (isStartedWithNotification) {
                            activity.onBackPressed();
                            activity.onBackPressed();
                        }
                        activity.onBackPressed();
                        break;

                    case UPDATE:
                        isSuccessful = project.getCardObjectNotification().updateNotificationFilter(getSQLiteDB(), filter);
                        if (!isSuccessful) {
                            Log.e(TAG, "Update failed. Notification filter: [" + JsonHelper.toJson(filter) + "]");
                            return;
                        }
                        Log.i(TAG, "Update successful. Notification filter: [" + JsonHelper.toJson(filter) + "]");
                        activity.onBackPressed();
                        break;

                    case DELETE:
                        isSuccessful = project.getCardObjectNotification().removeNotificationFilter(getSQLiteDB(), filter);
                        if (!isSuccessful) {
                            Log.e(TAG, "Delete failed. Notification filter: [" + JsonHelper.toJson(filter) + "]");
                            return;
                        }
                        Log.i(TAG, "Delete successful. Notification filter: [" + JsonHelper.toJson(filter) + "]");
                        activity.onBackPressed();
                        break;
                }
                FragmentNotificationFilterList fragment = (FragmentNotificationFilterList) activity.getFragmentManager().findFragmentByTag(FragmentNotificationFilterList.TAG);
                if (fragment != null) {
                    fragment.updateDataSet();
                }
            }
        };
    }
}
