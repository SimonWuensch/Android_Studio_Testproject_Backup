package ssi.ssn.com.ssi_service.fragment;

import android.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import ssi.ssn.com.ssi_service.activity.MainActivity;
import ssi.ssn.com.ssi_service.fragment.create.filter.kpi.view.VerificationButton;
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

    public void onSpinnerSelectionChangedListener(Spinner spinner) {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                doAfterChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
    }

    public void onVerificationButtonSelectionChangedListener(VerificationButton button) {
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                doAfterChanged();
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
