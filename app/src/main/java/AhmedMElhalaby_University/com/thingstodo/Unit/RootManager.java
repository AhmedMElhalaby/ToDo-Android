package AhmedMElhalaby_University.com.thingstodo.Unit;

import android.content.Context;
import android.graphics.PorterDuff;
import android.text.TextUtils;
import android.widget.TextView;

import AhmedMElhalaby_University.com.thingstodo.Medules.Category;
import AhmedMElhalaby_University.com.thingstodo.R;

public class RootManager {


    public static String ColorCategory1 = "color1";
    public static String ColorCategory2 = "color2";
    public static String ColorCategory3 = "color3";
    public static String ColorCategory4 = "color4";
    public static String ColorCategory5 = "color5";
    public static String ColorCategory6 = "color6";

    public static void SetColorBackgroundCateogry(Context mContext, TextView textView, Category category) {
        if (category != null) {
            if (TextUtils.equals(category.getColor(), RootManager.ColorCategory1)) {
                textView.getBackground().setColorFilter(mContext.getResources().getColor(R.color.color1), PorterDuff.Mode.SRC_ATOP);
            } else if (TextUtils.equals(category.getColor(), RootManager.ColorCategory2)) {
                textView.getBackground().setColorFilter(mContext.getResources().getColor(R.color.color2), PorterDuff.Mode.SRC_ATOP);
            } else if (TextUtils.equals(category.getColor(), RootManager.ColorCategory3)) {
                textView.getBackground().setColorFilter(mContext.getResources().getColor(R.color.color3), PorterDuff.Mode.SRC_ATOP);
            } else if (TextUtils.equals(category.getColor(), RootManager.ColorCategory4)) {
                textView.getBackground().setColorFilter(mContext.getResources().getColor(R.color.color4), PorterDuff.Mode.SRC_ATOP);
            } else if (TextUtils.equals(category.getColor(), RootManager.ColorCategory5)) {
                textView.getBackground().setColorFilter(mContext.getResources().getColor(R.color.color5), PorterDuff.Mode.SRC_ATOP);
            } else if (TextUtils.equals(category.getColor(), RootManager.ColorCategory6)) {
                textView.getBackground().setColorFilter(mContext.getResources().getColor(R.color.color6), PorterDuff.Mode.SRC_ATOP);
            }

            textView.setText(category.getName());
        }
    }

}
