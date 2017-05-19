package ssi.ssn.com.ssi_service.fragment.create.filter.kpi;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import ssi.ssn.com.ssi_service.R;
import ssi.ssn.com.ssi_service.activity.MainActivity;
import ssi.ssn.com.ssi_service.fragment.AbstractFragment;
import ssi.ssn.com.ssi_service.fragment.create.CreateUpdateDeleteStatus;
import ssi.ssn.com.ssi_service.fragment.create.filter.kpi.source.KpiTypeStubHandler;
import ssi.ssn.com.ssi_service.fragment.create.filter.kpi.stub.AbstractKpiTypeStub;
import ssi.ssn.com.ssi_service.fragment.create.filter.kpi.stub.StubAverage;
import ssi.ssn.com.ssi_service.fragment.create.filter.kpi.stub.StubSingularDouble;
import ssi.ssn.com.ssi_service.fragment.create.filter.kpi.stub.StubSingularLong;
import ssi.ssn.com.ssi_service.fragment.create.filter.kpi.stub.StubSpectrum;
import ssi.ssn.com.ssi_service.fragment.create.filter.kpi.stub.StubStatusEvent;
import ssi.ssn.com.ssi_service.fragment.create.filter.kpi.view.VerificationButton;
import ssi.ssn.com.ssi_service.fragment.list.kpifilter.FragmentKpiFilterList;
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

    private AbstractKpiTypeStub stub;

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
        } else if (getArguments().containsKey(FILTER_KPI_JSON)) {
            filter = (FilterKpi) JsonHelper.fromJsonGeneric(FilterKpi.class, getArguments().getString(FILTER_KPI_JSON));
            definition = filter.getDefinition();
            fragmentStatus = CreateUpdateDeleteStatus.DELETE;
        }
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
        if (rootView != null) {
            return rootView;
        }
        rootView = inflater.inflate(FRAGMENT_LAYOUT, container, false);
        Log.d(TAG, "Fragment inflated [" + getActivity().getResources().getResourceName(FRAGMENT_LAYOUT) + "].");
        initArguments();
        return rootView;
    }

    public void initArguments() {
        new AsyncTask<Object, Void, Object>() {
            @Override
            protected Object doInBackground(Object... objects) {
                loadArguments();
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                initViewComponents();
            }
        }.execute();
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

        stub = isStartedWithDefinition ? KpiTypeStubHandler.initStub(getActivity(), vsKpiType, definition) : KpiTypeStubHandler.initStub(getActivity(), vsKpiType, definition, filter);

        updateFinalButtonText();
        if (definition != null) {
            fillViewComponentsWithDefinitionInfo();
        }
        if (filter != null) {
            fillViewComponentsWithKpiFilter();
        }
    }

    // ** Start Fragment Status == ADD ********************************************************** //
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
        String oldFilterJson = JsonHelper.toJson(filter);
        String newFilterJson = JsonHelper.toJson(stub.loadFilterFromComponentViews());

        if(!oldFilterJson.equals(newFilterJson)){
            fragmentStatus = CreateUpdateDeleteStatus.UPDATE;
        }else{
            fragmentStatus = CreateUpdateDeleteStatus.DELETE;
        }
        updateFinalButtonText();
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
                for (EditText editText : stub.getAllEditTextViews()) {
                    super.onTextChangeListener(editText);
                }
                for (Spinner spinner : stub.getAllSpinnerViews()) {
                    super.onSpinnerSelectionChangedListener(spinner);
                }
                for (VerificationButton button : stub.getAllVerificationButtonViews()) {
                    super.onVerificationButtonSelectionChangedListener(button);
                }
                break;
        }
    }

    // ** ClickListener ************************************************************************* //

    private View.OnClickListener onClickFinalButton() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity activity = (MainActivity) getActivity();
                FilterKpi filter = stub.loadFilterFromComponentViews();
                if(!stub.isReadyForCreation()){
                    Toast.makeText(getActivity(), SourceHelper.getString(getActivity(), R.string.fragment_create_kpi_filter_message_please_insert_all_information_in_the_input_field), Toast.LENGTH_SHORT).show();
                    return;
                }


                boolean isSuccessful;
                switch (fragmentStatus) {
                    case ADD:
                        boolean filterListIsEmpty = project.getCardObjectKpi().getKpiFilters().isEmpty();
                        isSuccessful = project.getCardObjectKpi().addKpiFilter(getSQLiteDB(), filter);
                        if (!isSuccessful) {
                            Log.e(TAG, "Add failed. Kpi filter: [" + JsonHelper.toJson(filter) + "]");
                            Toast.makeText(getActivity().getBaseContext(), R.string.fragment_create_Kpi_filter_alert_text_filter_already_exists, Toast.LENGTH_LONG).show();
                            return;
                        }

                        Log.i(TAG, "Add successful. Kpi filter: [" + JsonHelper.toJson(filter) + "]");
                        activity.onBackPressed();
                        activity.onBackPressed();

                        if(filterListIsEmpty){
                            activity.showKpiFilterListFragment(project.get_id());
                        }
                        break;

                    case UPDATE:
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

                FragmentKpiFilterList fragment = (FragmentKpiFilterList) activity.getFragmentManager().findFragmentByTag(FragmentKpiFilterList.TAG);
                if (fragment != null) {
                    fragment.updateDataSet();
                }
            }
        };
    }
}
