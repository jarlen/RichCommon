package cn.jarlen.richcommon.util;

import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * 软键盘弹出辅助类, 处理软键盘弹出遮挡布局的工具类
 *
 * @author jarlen
 * @date 2019/10/31 0031
 */
public class SoftKeyBroadHelper implements SoftKeyBroadManager.SoftKeyboardStateListener {
    private final View selectedPersonsLay;
    private float selectedViewY;
    private Activity context;

    /**
     * @param activity  activity
     * @param floatView 需要跟随键盘浮动的View（需要嵌套在RelativeLayout和LinearLayout内部）
     */
    public SoftKeyBroadHelper(Activity activity, View floatView) {
        this.selectedPersonsLay = floatView;
        context = activity;
        SoftKeyBroadManager softKeyBroadManager = new SoftKeyBroadManager(activity.getWindow().getDecorView());
        softKeyBroadManager.addSoftKeyboardStateListener(this);
    }

    @Override
    public void onSoftKeyboardOpened(int keyboardHeightInPx) {
/*        if (selectedViewY == 0) {
            int[] outLocation = new int[2];
            selectedPersonsLay.getLocationInWindow(outLocation);
            selectedViewY = outLocation[1];
        }
        int statusBarHeight = StatusBarUtil.getStatusBarHeight(context);
        int[] screenHeightAndWidth = DeviceUtils.getScreenHeightAndWidth(context);
        int height = (screenHeightAndWidth[0] - keyboardHeightInPx) + statusBarHeight - selectedPersonsLay.getMeasuredHeight();*/
        int statusBarHeight = StatusBarUtil.getStatusBarHeight(context);
        int navigationBarHeight = NavigationBarUtil.getNavigationBarHeightIfRoom(context);
        setMargin(keyboardHeightInPx - statusBarHeight - navigationBarHeight);
    }

    @Override
    public void onSoftKeyboardClosed() {
        setMargin(0);
    }

    private void setMargin(int margin) {
        if (selectedPersonsLay.getLayoutParams() instanceof RelativeLayout.LayoutParams) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) selectedPersonsLay.getLayoutParams();
            layoutParams.bottomMargin = margin;
            selectedPersonsLay.setLayoutParams(layoutParams);
        } else if (selectedPersonsLay.getLayoutParams() instanceof LinearLayout.LayoutParams) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) selectedPersonsLay.getLayoutParams();
            layoutParams.bottomMargin = margin;
            selectedPersonsLay.setLayoutParams(layoutParams);
        }
    }
}
