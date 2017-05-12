package ssi.ssn.com.ssi_service.fragment.create.filter.kpi.stub;

import android.widget.EditText;

import java.util.List;

import ssi.ssn.com.ssi_service.fragment.create.filter.kpi.view.VerificationButton;
import ssi.ssn.com.ssi_service.model.data.source.filter.kpi.FilterKpi;
import ssi.ssn.com.ssi_service.model.data.source.filter.kpi.VerificationObject;

public class AbstractKpiTypeStub {

    public FilterKpi loadFilterFromComponentViews() {
        throw new NullPointerException("No settings found...");
    }

    public List<EditText> getAllEditTextViews() {
        throw new NullPointerException("No settings found...");
    }

    public List<VerificationButton> getAllVerificationButtonViews() {
        throw new NullPointerException("No settings found...");
    }

    VerificationObject getVerificationObjectByIcon(String icon) {
        switch (icon) {
            case "<":
                return VerificationObject.LESS_THEN;
            case "<=":
                return VerificationObject.LESS_THEN_EQUALS;
            case ">":
                return VerificationObject.GREATER_THEN;
            case ">=":
                return VerificationObject.GREATER_THEN_EQUALS;
            case "=":
                return VerificationObject.EQUALS;
        }
        throw new NullPointerException("No " + VerificationObject.class.getSimpleName() + " found to icon: [" + icon + "].");
    }
}
