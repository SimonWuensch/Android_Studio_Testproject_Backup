package ssi.ssn.com.ssi_service.fragment.list.custom.source;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ssi.ssn.com.ssi_service.R;
import ssi.ssn.com.ssi_service.model.helper.SourceHelper;

public class CustomListObject {

    private View.OnClickListener onClickListener;

    private CustomListObject() {
    }

    public CustomListObject(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public View.OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public void reformatViewComponents(Activity activity, RelativeLayout rlTextViewFrame, TextView tvHeadline, TextView tvKey, TextView tvValue, View vHorizontalLine) {
        rlTextViewFrame.setBackgroundColor(Color.WHITE);
        rlTextViewFrame.setVisibility(View.VISIBLE);

        tvHeadline.setVisibility(View.GONE);
        tvHeadline.setTextColor(SourceHelper.getColor(activity, android.support.v7.appcompat.R.color.secondary_text_default_material_light));

        tvKey.setVisibility(View.VISIBLE);
        tvValue.setVisibility(View.VISIBLE);
        vHorizontalLine.setVisibility(View.GONE);
    }

    public static class HeadLine extends CustomListObject {
        private String headline;

        public HeadLine(String headline) {
            this.headline = headline;
        }

        @Override
        public void reformatViewComponents(Activity activity, RelativeLayout rlTextViewFrame, TextView tvHeadline, TextView tvKey, TextView tvValue, View vHorizontalLine) {
            super.reformatViewComponents(activity, rlTextViewFrame, tvHeadline, tvKey, tvValue, vHorizontalLine);
            tvHeadline.setText(headline);
            tvHeadline.setTextColor(SourceHelper.getColor(activity, R.color.colorWhite));
            tvHeadline.setVisibility(View.VISIBLE);
            tvKey.setVisibility(View.GONE);
            tvValue.setVisibility(View.GONE);
            rlTextViewFrame.setBackgroundColor(SourceHelper.getColor(activity, R.color.defaultColor));
        }
    }

    public static class Only_Key extends CustomListObject {
        private String key;

        public Only_Key(String key) {
            this.key = key;
        }

        @Override
        public void reformatViewComponents(Activity activity, RelativeLayout rlTextViewFrame, TextView tvHeadline, TextView tvKey, TextView tvValue, View vHorizontalLine) {
            super.reformatViewComponents(activity, rlTextViewFrame, tvHeadline, tvKey, tvValue, vHorizontalLine);
            tvHeadline.setText(key);
            tvHeadline.setVisibility(View.VISIBLE);
            tvKey.setVisibility(View.GONE);
            tvValue.setVisibility(View.GONE);
        }
    }

    public static class Key_Value extends CustomListObject {
        private String key;
        private String value;

        public Key_Value(String key, String value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public void reformatViewComponents(Activity activity, RelativeLayout rlTextViewFrame, TextView tvHeadline, TextView tvKey, TextView tvValue, View vHorizontalLine) {
            super.reformatViewComponents(activity, rlTextViewFrame, tvHeadline, tvKey, tvValue, vHorizontalLine);
            tvKey.setText(key);
            tvValue.setText(value);
        }
    }

    public static class Horizontal_Line extends CustomListObject {
        public Horizontal_Line() {
        }

        @Override
        public void reformatViewComponents(Activity activity, RelativeLayout rlTextViewFrame, TextView tvHeadline, TextView tvKey, TextView tvValue, View vHorizontalLine) {
            super.reformatViewComponents(activity, rlTextViewFrame, tvHeadline, tvKey, tvValue, vHorizontalLine);
            tvKey.setVisibility(View.GONE);
            tvValue.setVisibility(View.GONE);
            vHorizontalLine.setVisibility(View.VISIBLE);
        }
    }


}
