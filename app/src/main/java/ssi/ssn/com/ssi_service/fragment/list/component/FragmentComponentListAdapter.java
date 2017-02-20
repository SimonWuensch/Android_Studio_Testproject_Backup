package ssi.ssn.com.ssi_service.fragment.list.component;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import ssi.ssn.com.ssi_service.model.network.response.component.ResponseComponent;

class FragmentComponentListAdapter extends RecyclerView.Adapter<FragmentComponentListViewHolder> {

    private static String TAG = FragmentComponentListAdapter.class.getSimpleName();

    private final int layoutCardView;
    private CardView cardView;

    private List<ResponseComponent> responseComponentList;
    private Activity activity;

    FragmentComponentListAdapter(int layoutCardView, final FragmentComponentList fragment, List<ResponseComponent> responseComponentList) {
        this.layoutCardView = layoutCardView;
        this.activity = fragment.getActivity();
        this.responseComponentList = responseComponentList;
    }

    @Override
    public FragmentComponentListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(layoutCardView, parent, false);
        return new FragmentComponentListViewHolder(activity, cardView);
    }

    @Override
    public void onBindViewHolder(FragmentComponentListViewHolder viewHolder, int position) {
        ResponseComponent responseComponent = responseComponentList.get(position);
        viewHolder.assignData(responseComponent);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return responseComponentList.size();
    }

}