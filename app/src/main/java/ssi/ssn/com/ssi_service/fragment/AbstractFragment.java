package ssi.ssn.com.ssi_service.fragment;

import android.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ssi.ssn.com.ssi_service.activity.MainActivity;
import ssi.ssn.com.ssi_service.model.helper.SQLiteHelper;

public class AbstractFragment extends Fragment {

    public SQLiteHelper getSQLiteHelper() {
        return ((MainActivity) getActivity()).getSQLiteHelper();
    }

    public void setLoadingViewVisible(boolean isVisible){
        ((MainActivity)getActivity()).setLoadingViewVisible(isVisible);
    }

    public void onTextChangeListener(EditText editText){
        editText.addTextChangedListener(new TextWatcher() {
            private ExecutorService executor = Executors.newSingleThreadExecutor();
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

    public void doAfterChanged(){}
    public void doBeforeTextChanged(){}
    public void doTextChanged(){}
}
