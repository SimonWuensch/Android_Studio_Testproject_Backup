package ssi.ssn.com.ssi_service.fragment.componentlist;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import ssi.ssn.com.ssi_service.fragment.launchboard.source.CardObjectComponent;
import ssi.ssn.com.ssi_service.model.helper.XMLHelper;

class FragmentComponentListAdapter extends RecyclerView.Adapter<FragmentComponentListViewHolder> {

    private static String TAG = FragmentComponentListAdapter.class.getSimpleName();

    private final int layoutCardView;
    private final FragmentComponentList fragment;
    private CardView cardView;

    private List<XMLHelper.XMLObject> componentObjects;
    private Activity activity;

    FragmentComponentListAdapter(int layoutCardView, final FragmentComponentList fragment, String responseApplicationConfig) {
        this.layoutCardView = layoutCardView;
        this.fragment = fragment;
        this.activity = fragment.getActivity();
        componentObjects = CardObjectComponent.searchObjectsInResponseXML(responseApplicationConfig);
    }

    @Override
    public FragmentComponentListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(layoutCardView, parent, false);
        return new FragmentComponentListViewHolder(fragment.getActivity(), cardView);
    }

    @Override
    public void onBindViewHolder(FragmentComponentListViewHolder viewHolder, int position) {
        XMLHelper.XMLObject object = componentObjects.get(position);
        if (object.getAttributes().containsKey(CardObjectComponent.XML_ATTRIBUTE_MANAGE)) {
            viewHolder.assignData(object);
        }
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return componentObjects.size();
    }

}