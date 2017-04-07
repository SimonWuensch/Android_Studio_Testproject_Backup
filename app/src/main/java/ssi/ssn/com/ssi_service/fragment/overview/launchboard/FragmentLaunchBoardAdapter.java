package ssi.ssn.com.ssi_service.fragment.overview.launchboard;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ssi.ssn.com.ssi_service.activity.MainActivity;
import ssi.ssn.com.ssi_service.model.data.source.Project;
import ssi.ssn.com.ssi_service.model.data.source.cardobject.AbstractCardObject;
import ssi.ssn.com.ssi_service.model.database.SQLiteDB;
import ssi.ssn.com.ssi_service.model.helper.ObservationHelper;

public class FragmentLaunchBoardAdapter extends RecyclerView.Adapter<FragmentLaunchBoardViewHolder> {

    private static String TAG = FragmentLaunchBoardAdapter.class.getSimpleName();

    private final int layoutCardView;
    private final FragmentLaunchBoard fragment;
    private CardView cardView;

    private Project project;
    private List<AbstractCardObject> cardObjects;
    private AbstractCardObject clickedCardObject;

    private Map<Integer, FragmentLaunchBoardViewHolder> viewHolderMap = new HashMap<>();

    FragmentLaunchBoardAdapter(int layoutCardView, final FragmentLaunchBoard fragment, Project project, List<AbstractCardObject> cardObjects) {
        this.layoutCardView = layoutCardView;
        this.fragment = fragment;
        this.project = project;
        this.cardObjects = cardObjects;
    }

    @Override
    public FragmentLaunchBoardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(layoutCardView, parent, false);
        return new FragmentLaunchBoardViewHolder((MainActivity) fragment.getActivity(), cardView);
    }

    @Override
    public void onBindViewHolder(FragmentLaunchBoardViewHolder viewHolder, int position) {
        viewHolder.assignData(project, cardObjects.get(position));
        viewHolderMap.put(position, viewHolder);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return cardObjects.size();
    }

    public void setClickedCardObject(AbstractCardObject clickedCardObject) {
        this.clickedCardObject = clickedCardObject;
    }


    public void reloadCardViews() {
        SQLiteDB sqLiteDB = ((MainActivity) fragment.getActivity()).getSQLiteDB();
        ObservationHelper.setLastObservationTimeToOLD(sqLiteDB, project);
        for (int i = 0; i < viewHolderMap.size(); i++) {
            FragmentLaunchBoardViewHolder viewHolder = viewHolderMap.get(i);
            viewHolder.assignData(project, project.getAllCardObjects().get(i));
        }
        project.setLastObservationTime(new Date().getTime());
        sqLiteDB.project().updateLastObservationTime(project);
    }
}