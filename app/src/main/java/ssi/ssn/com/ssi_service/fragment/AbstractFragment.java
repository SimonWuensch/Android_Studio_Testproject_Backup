package ssi.ssn.com.ssi_service.fragment;

import android.app.Fragment;

import ssi.ssn.com.ssi_service.activity.MainActivity;
import ssi.ssn.com.ssi_service.model.handler.SQLiteHelper;

public class AbstractFragment extends Fragment {

    public SQLiteHelper getSQLiteHelper() {
        return ((MainActivity) getActivity()).getSQLiteHelper();
    }
}
