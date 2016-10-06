package ssi.ssn.com.ssi_service.fragment;

import android.app.Fragment;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ssi.ssn.com.ssi_service.activity.MainActivity;
import ssi.ssn.com.ssi_service.model.handler.SQLiteHelper;

public class AbstractFragment extends Fragment {

    public SQLiteHelper getSQLiteHelper() {
        return ((MainActivity) getActivity()).getSQLiteHelper();
    }

    public void onTextChangeListener(EditText editText, AsyncTask afterTextChangedTask){
        onTextChangeListener(editText, afterTextChangedTask, null, null);
    }

    private void onTextChangeListener(final EditText editText, final AsyncTask afterTextChangedTask, final AsyncTask beforeTextChangedTask, final AsyncTask onTextChangedTask){
        editText.addTextChangedListener(new TextWatcher() {
            private ExecutorService executor = Executors.newSingleThreadExecutor();
            public void afterTextChanged(Editable s) {
                if(afterTextChangedTask != null) {
                    afterTextChangedTask.executeOnExecutor(executor);
                    editText.removeTextChangedListener(this);
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(beforeTextChangedTask != null) {
                    beforeTextChangedTask.executeOnExecutor(executor);
                    editText.removeTextChangedListener(this);
                }
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(onTextChangedTask != null) {
                    onTextChangedTask.executeOnExecutor(executor);
                    editText.removeTextChangedListener(this);
                }
            }
        });
    }
}
