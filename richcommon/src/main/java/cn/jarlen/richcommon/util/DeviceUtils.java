package cn.jarlen.richcommon.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * created on 2018/1/3.
 * author:jarlen
 * summary:工具类
 */

public class DeviceUtils {
    /**
     * 获取手机厂商
     *
     * @return 手机厂商
     */
    public static String getDeviceBrand() {
        return Build.BRAND;
    }

    /**
     * 获取System Model
     *
     * @return System Model
     */
    public static String getSystemModel() {
        return Build.MODEL;
    }

    /**
     * 检测Sdcard是否存在
     *
     * @return true存在，false 不存在
     */
    public static boolean isExitsSdcard() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * sdcard路径
     *
     * @return sdcard路径
     */
    public static String sdcardPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    /**
     * 隐藏键盘
     *
     * @param activity
     */
    public static void hideSoftInput(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    /**
     * 隐藏键盘
     *
     * @param view
     */
    public static void hideSoftInput(EditText view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (null != imm) {
            view.requestFocus();
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 显示键盘
     *
     * @param view
     */
    public static void showSoftInput(EditText view) {
        InputMethodManager imm = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            view.requestFocus();
            imm.showSoftInput(view, 0);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        }
    }

    /**
     * 检查是否有读取IMEI的权限
     *
     * @param context
     * @return true, 有读取权限
     */
    public static boolean isHasReadIMEIPermission(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            return (PackageManager.PERMISSION_GRANTED) == pm.checkPermission("android.permission.READ_PHONE_STATE", "com.xdja.HDSafeEMailClient");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取屏幕宽高
     *
     * @param mContext
     * @return [0] 屏幕高度
     * [1] 屏幕宽度
     */
    public static int[] getScreenHeightAndWidth(Context mContext) {
        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            windowManager.getDefaultDisplay().getRealMetrics(metrics);
        }
        int[] res = new int[2];
        res[0] = metrics.heightPixels;
        res[1] = metrics.widthPixels;
        return res;
    }
}
