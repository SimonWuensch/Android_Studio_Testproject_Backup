package ssi.ssn.com.ssi_service.fragment.create.filter.kpi.stub;

import android.view.View;
import android.view.ViewStub;
import android.widget.EditText;

import ssi.ssn.com.ssi_service.R;

public class average {

    private static int STUB_LAYOUT = R.layout.fragment_create_filter_kpi_stub_average;

    public static void initViewComponents(ViewStub viewStub) {
        viewStub.setLayoutResource(R.layout.fragment_create_filter_kpi_stub_average);
        View inflated = viewStub.inflate();

        EditText average = (EditText) inflated.findViewById(R.id.fragment_create_kpi_filter_stub_average_edit_text_average);
        average.setText("testInput");
    }
}
