package cn.jarlen.richcommon.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * 通用嵌套布局容器用来处理统一点击效果
 *
 * @author jarlen
 * @date 2018/4/27 0027
 */

public class CommonClickRelativeLayout extends RelativeLayout {

    public CommonClickRelativeLayout(Context context) {
        super(context);
        initView();
    }

    public CommonClickRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public void initView() {
        setClickable(true);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            if (childView != null) {
                childView.setClickable(false);
            }
        }
    }


    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        boolean pressed = isPressed();
        for (int i = 0; i < this.getChildCount(); i++) {
            View childAt = this.getChildAt(i);
            if (childAt == null) {
                continue;
            }
            childAt.setAlpha(pressed ? 0.7f : 1f);
        }
        invalidate();
    }
}
