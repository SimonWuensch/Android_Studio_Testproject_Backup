package ssi.ssn.com.ssi_service.fragment.modulelist;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ssi.ssn.com.ssi_service.fragment.launchboard.source.CardObjectModule;
import ssi.ssn.com.ssi_service.model.helper.XMLHelper;

class FragmentModuleListAdapter extends RecyclerView.Adapter<FragmentModuleListViewHolder> {

    private static String TAG = FragmentModuleListAdapter.class.getSimpleName();

    private final int layoutCardView;
    private final FragmentModuleList fragment;
    private CardView cardView;

    private List<XMLHelper.XMLObject> moduleObjects;
    private Activity activity;

    FragmentModuleListAdapter(int layoutCardView, FragmentModuleList fragment, String responseApplicationConfig) {
        this.layoutCardView = layoutCardView;
        this.fragment = fragment;
        this.activity = fragment.getActivity();
        //moduleObjects = CardObjectModule.searchObjectsInResponseXML(responseApplicationConfig);
        moduleObjects = new ArrayList<>();
    }

    @Override
    public FragmentModuleListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(layoutCardView, parent, false);
        return new FragmentModuleListViewHolder(fragment.getActivity(), cardView);
    }

    @Override
    public void onBindViewHolder(FragmentModuleListViewHolder viewHolder, int position) {
        viewHolder.assignData(moduleObjects.get(position));
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return moduleObjects.size();
    }

}