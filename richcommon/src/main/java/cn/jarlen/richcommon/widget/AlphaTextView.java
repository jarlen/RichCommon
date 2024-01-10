package cn.jarlen.richcommon.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

/**
 * press下透明度发生变化的TextView，复杂布局想实现该效果可见
 * {@link CommonClickLinearLayout}
 *
 * @author jarlen
 * @date 201/09/30
 */

public class AlphaTextView extends AppCompatTextView {

    public AlphaTextView(Context context) {
        super(context);
    }

    public AlphaTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        setAlpha(isPressed() ? 0.7f : 1f);
        invalidate();
    }
}
