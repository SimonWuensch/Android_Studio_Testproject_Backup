package ssi.ssn.com.ssi_service.fragment.create.filter.kpi.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.ArrayList;
import java.util.List;

import ssi.ssn.com.ssi_service.model.data.source.filter.kpi.VerificationObject;


public class VerificationButton extends AppCompatButton {

    private int position = 0;
    private List<VerificationObject> verificationObjects = new ArrayList<VerificationObject>() {
        {
            add(VerificationObject.LESS_THEN);
            add(VerificationObject.LESS_THEN_EQUALS);
            add(VerificationObject.GREATER_THEN);
            add(VerificationObject.GREATER_THEN_EQUALS);
            add(VerificationObject.EQUALS);
        }
    };

    public VerificationButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setText(verificationObjects.get(0).getIcon());
    }

    public void setVerificationObject(VerificationObject object) {
        position = object.getID();
        setText(object.getIcon());
    }

    public void setVerificationObjects(List<VerificationObject> verificationObjects){
        this.verificationObjects = verificationObjects;
        setText(verificationObjects.get(0).getIcon());
    }

    @Override
    public boolean performClick() {
        if (position >= verificationObjects.size() -1) {
            position = 0;
        } else {
            position++;
        }

        setText(verificationObjects.get(position).getIcon());
        return super.performClick();
    }

    // ** Override Must Methods ***************************************************************** //

    @Override
    public void setBackgroundResource(@DrawableRes int resId) {
        super.setBackgroundResource(resId);
    }

    @Override
    public void setBackgroundDrawable(Drawable background) {
        super.setBackgroundDrawable(background);
    }

    @Override
    public void setSupportBackgroundTintList(@Nullable ColorStateList tint) {
        super.setSupportBackgroundTintList(tint);
    }

    @Nullable
    @Override
    public ColorStateList getSupportBackgroundTintList() {
        return super.getSupportBackgroundTintList();
    }

    @Override
    public void setSupportBackgroundTintMode(@Nullable PorterDuff.Mode tintMode) {
        super.setSupportBackgroundTintMode(tintMode);
    }

    @Nullable
    @Override
    public PorterDuff.Mode getSupportBackgroundTintMode() {
        return super.getSupportBackgroundTintMode();
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
    }

    @Override
    public void setTextAppearance(Context context, int resId) {
        super.setTextAppearance(context, resId);
    }

    @Override
    public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
        super.onInitializeAccessibilityEvent(event);
    }

    @Override
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
    }

    @Override
    public void setSupportAllCaps(boolean allCaps) {
        super.setSupportAllCaps(allCaps);
    }
}
