package ssi.ssn.com.ssi_service.fragment.launchboard;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.owlike.genson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import ssi.ssn.com.ssi_service.R;
import ssi.ssn.com.ssi_service.fragment.launchboard.source.AbstractCardObject;
import ssi.ssn.com.ssi_service.fragment.launchboard.source.CardObjectComponent;
import ssi.ssn.com.ssi_service.fragment.launchboard.source.CardObjectKPI;
import ssi.ssn.com.ssi_service.fragment.launchboard.source.CardObjectModule;
import ssi.ssn.com.ssi_service.fragment.launchboard.source.CardObjectNotification;

public class FragmentLaunchBoardAdapter extends RecyclerView.Adapter<FragmentLaunchBoardViewHolder> {

    private static String TAG = FragmentLaunchBoardAdapter.class.getSimpleName();

    private final int layoutCardView;
    private final FragmentLaunchBoard fragment;
    private CardView cardView;

    private List<AbstractCardObject> cardInputs;
    private Activity activity;

    public FragmentLaunchBoardAdapter(int layoutCardView, final FragmentLaunchBoard fragment) {
        this.layoutCardView = layoutCardView;
        this.fragment = fragment;
        this.activity = fragment.getActivity();
        initDefaultInputs();
    }

    private void initDefaultInputs() {
        cardInputs = new LinkedList<AbstractCardObject>() {
            {
                add(new CardObjectModule(
                        R.string.fragment_launch_board_card_module,
                        R.drawable.icon_modul,
                        true
                ));
                add(new CardObjectComponent(
                        R.string.fragment_launch_board_card_component,
                        R.drawable.icon_component,
                        true
                ));
                add(new CardObjectNotification(
                        R.string.fragment_launch_board_card_notification,
                        R.drawable.icon_notification,
                        true
                ));
                add(new CardObjectKPI(
                        R.string.fragment_launch_board_card_kpi,
                        R.drawable.icon_kpi,
                        true
                ));
            }
        };
    }

    @Override
    public FragmentLaunchBoardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(layoutCardView, parent, false);
        FragmentLaunchBoardViewHolder viewHolder = new FragmentLaunchBoardViewHolder(fragment.getActivity(), cardView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FragmentLaunchBoardViewHolder viewHolder, int position) {
        viewHolder.assignData(cardInputs.get(position));
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return cardInputs.size();
    }

}