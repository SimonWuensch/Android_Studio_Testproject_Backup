package ssi.ssn.com.ssi_service.fragment;

import android.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import ssi.ssn.com.ssi_service.activity.MainActivity;
import ssi.ssn.com.ssi_service.model.database.DBProject;
import ssi.ssn.com.ssi_service.model.database.SQLiteDB;

public class AbstractFragment extends Fragment {

    public SQLiteDB getSQLiteDB() {
        return ((MainActivity) getActivity()).getSQLiteDB();
    }

    public void setLoadingViewVisible(boolean isVisible) {
        ((MainActivity) getActivity()).setLoadingViewVisible(isVisible);
    }

    public void onTextChangeListener(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                doAfterChanged();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                doBeforeTextChanged();
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                doTextChanged();
            }
        });
    }

    public void doAfterChanged() {
    }

    public void doBeforeTextChanged() {
    }

    public void doTextChanged() {
    }
}
