package cn.jarlen.richcommon.util;

import android.content.Context;
import android.content.res.Resources;

import androidx.annotation.NonNull;


public class DensityUtils {

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param dipValue dp值
     * @return 转换后的int类型px
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 将px转换成dp
     * @param context 上下文
     * @param pxValue px值
     * @return 转换后的dp
     */
    public static int px2dip(Context context, int pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale);
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param dipValue dp值
     * @return 转换后的double类型px
     */
    public static double dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return dipValue*scale;
    }

    /**
     * 获取状态栏的高度
     * @param context 上下文
     * @return 状态栏的高度
     */
    public static int getStatusBarHeight(@NonNull Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen","android");
        return resources.getDimensionPixelSize(resourceId);
    }
}
