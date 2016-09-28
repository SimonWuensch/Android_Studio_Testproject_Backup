package ssi.ssn.com.ssi_service.fragment.customlist.source;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import ssi.ssn.com.ssi_service.R;

/**
 * Created by wuens on 28.09.2016.
 */

public class CustomListObject {

    private static View.OnClickListener onClickListener;

    public CustomListObject() {
    }

    public CustomListObject(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public View.OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public void reformatViewComponents(Activity activity, LinearLayout llKeyValue, TextView tvKey, TextView tvValue, View vHorizontalLine) {
    }

    public static class HeadLine extends CustomListObject {
        private String key;

        public HeadLine(String key) {
            this.key = key;
        }

        @Override
        public void reformatViewComponents(Activity activity, LinearLayout llKeyValue, TextView tvKey, TextView tvValue, View vHorizontalLine) {
            tvKey.setText(key);
            tvValue.setVisibility(View.GONE);
            llKeyValue.setBackgroundColor(ContextCompat.getColor(activity, R.color.defaultColor));
            String test;
        }
    }

    public static class Only_Key extends CustomListObject {
        private String key;

        public Only_Key(String key) {
            this.key = key;
        }

        @Override
        public void reformatViewComponents(Activity activity, LinearLayout llKeyValue, TextView tvKey, TextView tvValue, View vHorizontalLine) {
            tvKey.setText(key);
            tvValue.setVisibility(View.GONE);
        }
    }

    public static class Only_Value extends CustomListObject {
        private String value;

        public Only_Value(String value) {
            this.value = value;
        }

        @Override
        public void reformatViewComponents(Activity activity, LinearLayout llKeyValue, TextView tvKey, TextView tvValue, View vHorizontalLine) {
            tvValue.setText(value);
            tvKey.setVisibility(View.GONE);
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
        public void reformatViewComponents(Activity activity, LinearLayout llKeyValue, TextView tvKey, TextView tvValue, View vHorizontalLine) {
            tvKey.setText(key);
            tvValue.setText(value);
        }
    }

    public static class Horizontal_Line extends CustomListObject {
        public Horizontal_Line() {
        }

        @Override
        public void reformatViewComponents(Activity activity, LinearLayout llKeyValue, TextView tvKey, TextView tvValue, View vHorizontalLine) {
            tvKey.setVisibility(View.GONE);
            tvValue.setVisibility(View.GONE);
            vHorizontalLine.setVisibility(View.VISIBLE);
        }
    }


}
