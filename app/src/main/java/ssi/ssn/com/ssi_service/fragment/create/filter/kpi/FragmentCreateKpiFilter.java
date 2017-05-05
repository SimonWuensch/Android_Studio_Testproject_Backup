package ssi.ssn.com.ssi_service.fragment.create.filter.kpi;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import ssi.ssn.com.ssi_service.R;
import ssi.ssn.com.ssi_service.activity.MainActivity;
import ssi.ssn.com.ssi_service.fragment.AbstractFragment;
import ssi.ssn.com.ssi_service.fragment.create.CreateUpdateDeleteStatus;
import ssi.ssn.com.ssi_service.model.data.source.Project;
import ssi.ssn.com.ssi_service.model.data.source.cardobject.CardObjectKpi;
import ssi.ssn.com.ssi_service.model.data.source.filter.kpi.FilterKpi;
import ssi.ssn.com.ssi_service.model.helper.JsonHelper;
import ssi.ssn.com.ssi_service.model.helper.SourceHelper;
import ssi.ssn.com.ssi_service.model.network.response.kpi.definitions.ResponseKpiDefinition;

public class FragmentCreateKpiFilter extends AbstractFragment {

    public static String TAG = FragmentCreateKpiFilter.class.getSimpleName();

    private static int FRAGMENT_LAYOUT = R.layout.fragment_create_filter_kpi;

    private static String PROJECT_ID = TAG + "PROJECT_ID";
    private static String FILTER_KPI_JSON = TAG + "FILTER_KPI_JSON";
    private static String RESPONSE_DEFINITION_JSON = TAG + "RESPONSE_DEFINITION_JSON";

    private CreateUpdateDeleteStatus fragmentStatus;
    private boolean isStartedWithDefinition = false;

    private View rootView;

    private TextView tvKpiKey;
    private TextView tvKpiName;
    private TextView tvKpiDescription;
    private ViewStub vsKpiType;

    private Button bFinal;

    private Project project;
    private FilterKpi filter;
    private ResponseKpiDefinition definition;

    public static FragmentCreateKpiFilter newInstance(int projectID, ResponseKpiDefinition definition) {
        if (projectID == 0) {
            return new FragmentCreateKpiFilter();
        }

        FragmentCreateKpiFilter fragment = new FragmentCreateKpiFilter();
        Bundle bundle = new Bundle();
        bundle.putInt(PROJECT_ID, projectID);
        bundle.putString(RESPONSE_DEFINITION_JSON, JsonHelper.toJson(definition));
        fragment.setArguments(bundle);
        return fragment;
    }

    public static FragmentCreateKpiFilter newInstance(int projectID, FilterKpi filter) {
        if (projectID == 0) {
            return new FragmentCreateKpiFilter();
        }

        FragmentCreateKpiFilter fragment = new FragmentCreateKpiFilter();
        Bundle bundle = new Bundle();
        bundle.putInt(PROJECT_ID, projectID);
        bundle.putString(FILTER_KPI_JSON, JsonHelper.toJson(filter));
        fragment.setArguments(bundle);
        return fragment;
    }

    private void loadArguments() {
        if (getArguments() == null) {
            return;
        }

        if (getArguments().containsKey(RESPONSE_DEFINITION_JSON)) {
            definition = (ResponseKpiDefinition) JsonHelper.fromJsonGeneric(ResponseKpiDefinition.class, getArguments().getString(RESPONSE_DEFINITION_JSON));
            fragmentStatus = CreateUpdateDeleteStatus.ADD;
            isStartedWithDefinition = true;
            fillViewComponentsWithDefinitionInfo();
        } else if (getArguments().containsKey(FILTER_KPI_JSON)) {
            filter = (FilterKpi) JsonHelper.fromJsonGeneric(FilterKpi.class, getArguments().getString(FILTER_KPI_JSON));
            fragmentStatus = CreateUpdateDeleteStatus.DELETE;
            fillViewComponentsWithKpiFilter();
        }
        updateFinalButtonText();

        int projectID = getArguments().getInt(PROJECT_ID);
        project = getSQLiteDB().project().getByID(projectID);
        CardObjectKpi.init(getSQLiteDB(), project);
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
        tvHeadLine.setText(SourceHelper.getString(getActivity(), R.string.fragment_kpi_filter_title));

        tvKpiKey = (TextView) rootView.findViewById(R.id.fragment_create_kpi_filter_key_value);
        tvKpiName = (TextView) rootView.findViewById(R.id.fragment_create_kpi_filter_name_value);
        tvKpiDescription = (TextView) rootView.findViewById(R.id.fragment_create_kpi_filter_description_value);
        vsKpiType = (ViewStub) rootView.findViewById(R.id.fragment_create_kpi_filter_view_stub_kpi_type);



        bFinal = (Button) rootView.findViewById(R.id.fragment_create_kpi_filter_button_final);
        bFinal.setOnClickListener(onClickFinalButton());
    }

    // ** Start Fragment Status == ADD ******************************************************* //
    public void fillViewComponentsWithDefinitionInfo() {
        if (definition != null) {
            tvKpiKey.setText(definition.getKey());
            tvKpiName.setText(definition.getName());
            tvKpiDescription.setText(definition.getDescription());
            Log.d(TAG, "Fragment view components filled with definition [" + JsonHelper.toJson(definition) + "].");
        }
    }


    // ** Start Fragment Status == DELETE ******************************************************* //
    public void fillViewComponentsWithKpiFilter() {
        if (filter != null) {
            tvKpiKey.setText(filter.getDefinition().getKey());
            tvKpiName.setText(filter.getDefinition().getName());
            tvKpiDescription.setText(filter.getDefinition().getDescription());
            updateFinalButtonText();
            Log.d(TAG, "Fragment view components filled with filter [" + JsonHelper.toJson(filter) + "].");
        }
    }

    @Override
    public void doAfterChanged() {
        /*String initialNode = filter.getNote();
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
        */
    }

    // ** Settings ****************************************************************************** //

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
                /*
                TODO Set on Change listener in Container
                super.onTextChangeListener(etNode);
                super.onTextChangeListener(etActiveTime);
                super.onTextChangeListener(etText);
                super.onSpinnerSelectionChangedListener(spTimeInput);
                super.onSpinnerSelectionChangedListener(spSeverityInput);
                */
                break;
        }
    }

    private FilterKpi loadFilterFromComponentViewInputs() {
        //TODO Load Filter Settings from Container
        /*if (filter != null) {
            newFilter.setId(filter.getId());
        }
        return newFilter;*/
        return null;
    }

    // ** ClickListener ************************************************************************* //

    private View.OnClickListener onClickFinalButton() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity activity = (MainActivity) getActivity();
                FilterKpi filter = loadFilterFromComponentViewInputs();
                boolean isSuccessful;
                switch (fragmentStatus) {
                    case ADD:
                        isSuccessful = project.getCardObjectKpi().addKpiFilter(getSQLiteDB(), filter);
                        if (!isSuccessful) {
                            Log.e(TAG, "Add failed. Kpi filter: [" + JsonHelper.toJson(filter) + "]");
                            Toast.makeText(getActivity().getBaseContext(), R.string.fragment_create_Kpi_filter_alert_text_filter_already_exists, Toast.LENGTH_LONG).show();
                            return;
                        }
                        Log.i(TAG, "Add successful. Kpi filter: [" + JsonHelper.toJson(filter) + "]");

                        if (isStartedWithDefinition) {
                            activity.onBackPressed();
                            activity.onBackPressed();
                        }
                        activity.onBackPressed();
                        break;

                    case UPDATE:
                        /*
                        TODO SET UPDATE FILTER SETTINGS
                        filter.setNotificationTable(new ResponseNotificationTable());
                        filter.setActiveTimeReachedNotificationTable(new ResponseNotificationTable());
                         */
                        isSuccessful = project.getCardObjectKpi().updateKpiFilter(getSQLiteDB(), filter);
                        if (!isSuccessful) {
                            Log.e(TAG, "Update failed. Kpi filter: [" + JsonHelper.toJson(filter) + "]");
                            return;
                        }
                        Log.i(TAG, "Update successful. Kpi filter: [" + JsonHelper.toJson(filter) + "]");
                        activity.onBackPressed();
                        break;

                    case DELETE:
                        isSuccessful = project.getCardObjectKpi().removeKpiFilter(getSQLiteDB(), filter);
                        if (!isSuccessful) {
                            Log.e(TAG, "Delete failed. kpi filter: [" + JsonHelper.toJson(filter) + "]");
                            return;
                        }
                        Log.i(TAG, "Delete successful. kpi filter: [" + JsonHelper.toJson(filter) + "]");
                        activity.onBackPressed();
                        break;
                }

                //TODO Show Kpi Filter List Fragment
                //FragmentNotificationFilterList fragment = (FragmentNotificationFilterList) activity.getFragmentManager().findFragmentByTag(FragmentNotificationFilterList.TAG);
                //if (fragment != null) {
                //    fragment.updateDataSet();
                //}
            }
        };
    }
}
