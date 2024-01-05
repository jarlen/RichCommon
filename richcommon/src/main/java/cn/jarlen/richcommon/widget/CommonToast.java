package cn.jarlen.richcommon.widget;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import cn.jarlen.richcommon.R;

/**
 * @author jarlen
 * @date 2020/1/15
 * toast工具类
 */
public class CommonToast {

    /**
     * Toast下方错误提示
     *
     * @param context 上下文句柄 Ps:句柄用application级别的 不能为空
     * @param message 提示信息 不能为空
     */
    public static void showTopToast(@NonNull Context context, @NonNull String message) {
        Toast toast = new Toast(context);
        View toastView = getToastView(context, message, -1);
        toast.setView(toastView);
        toast.setDuration(Toast.LENGTH_SHORT);
        float scale = context.getResources().getDisplayMetrics().density;
        toast.setGravity(Gravity.TOP, 0, (int) (120 * scale + 0.5f));
        toast.show();
    }


    /**
     * Toast下方错误提示
     *
     * @param context 上下文句柄 Ps:句柄用application级别的 不能为空
     * @param message 提示信息 不能为空
     */
    public static void showBottomToast(@NonNull Context context, @NonNull String message) {
        Toast toast = new Toast(context);
        View toastView = getToastView(context, message, -1);
        toast.setView(toastView);
        toast.setDuration(Toast.LENGTH_SHORT);
        float scale = context.getResources().getDisplayMetrics().density;
        toast.setGravity(Gravity.BOTTOM, 0, (int) (120 * scale + 0.5f));
        toast.show();
    }


    /**
     * Toast下方错误提示
     *
     * @param context 上下文句柄 Ps:句柄用application级别的 不能为空
     * @param message 提示信息 不能为空
     */
    public static void showBottomFailToast(Context context, @NonNull String message) {
        if (context == null) {
            return;
        }
        Toast toast = new Toast(context);
        View toastView = getToastView(context, message, R.drawable.common_widget_ic_toast_fail);
        toast.setView(toastView);
        toast.setDuration(Toast.LENGTH_SHORT);
        float scale = context.getResources().getDisplayMetrics().density;
        toast.setGravity(Gravity.BOTTOM, 0, (int) (120 * scale + 0.5f));
        toast.show();
    }


    /**
     * Toast下方正确提示
     *
     * @param context 上下文句柄 不能为空
     * @param message 提示信息 不能为空
     */
    public static void showBottomSuccessToast(@NonNull Context context, @NonNull String message) {
        Toast toast = new Toast(context);
        View toastView = getToastView(context, message, R.drawable.common_widget_ic_toast_success);
        toast.setView(toastView);
        toast.setDuration(Toast.LENGTH_SHORT);
        float scale = context.getResources().getDisplayMetrics().density;
        toast.setGravity(Gravity.BOTTOM, 0, (int) (120 * scale + 0.5f));
        toast.show();
    }


    /**
     * Toast上方错误提示
     *
     * @param context 上下文句柄 不能为空
     * @param message 提示信息 不能为空
     */
    public static void showTopFailToast(@NonNull Context context, @NonNull String message) {
        Toast toast = new Toast(context);
        View toastView = getToastView(context, message, R.drawable.common_widget_ic_toast_fail);
        toast.setView(toastView);
        toast.setDuration(Toast.LENGTH_SHORT);
        float scale = context.getResources().getDisplayMetrics().density;
        toast.setGravity(Gravity.TOP, 0, (int) (120 * scale + 0.5f));
        toast.show();
    }

    /**
     * Toast下方正确提示
     *
     * @param context 上下文句柄 不能为空
     * @param message 提示信息 不能为空
     */
    public static void showTopSuccessToast(@NonNull Context context, @NonNull String message) {
        Toast toast = new Toast(context);
        View toastView = getToastView(context, message, R.drawable.common_widget_ic_toast_success);
        toast.setView(toastView);
        toast.setDuration(Toast.LENGTH_SHORT);
        float scale = context.getResources().getDisplayMetrics().density;
        toast.setGravity(Gravity.TOP, 0, (int) (120 * scale + 0.5f));
        toast.show();
    }

    private static View getToastView(Context context, String toastContent, int toastIconRes) {
        View toastView = LayoutInflater.from(context).inflate(R.layout.common_widget_toast, null);
        ImageView toastIcon = toastView.findViewById(R.id.iv_toast_icon);
        if (toastIconRes > 0) {
            toastIcon.setVisibility(View.VISIBLE);
            toastIcon.setImageResource(toastIconRes);
        } else {
            toastIcon.setVisibility(View.GONE);
        }
        TextView contentView = toastView.findViewById(R.id.toast_context);
        contentView.setText(toastContent);
        return toastView;
    }
}
