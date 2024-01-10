package cn.jarlen.richcommon.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;

import cn.jarlen.richcommon.R;

/**
 * @author jarlen
 * @date 2020/7/11
 * 支持maxHeight的NestedScrollView
 * @see #
 * https://stackoverflow.com/questions/45358648/how-to-set-the-maximum-height-for-a-nestedscrollview-in-android
 */
public class MaxHeightNestedScrollView extends NestedScrollView {
    private int maxHeight = -1;

    public MaxHeightNestedScrollView(@NonNull Context context) {
        super(context);
    }

    public MaxHeightNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MaxHeightNestedScrollView);
        maxHeight = typedArray.getDimensionPixelSize(R.styleable.MaxHeightNestedScrollView_mhnsv_maxHeight, -1);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (maxHeight > 0) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(maxHeight, MeasureSpec.AT_MOST);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
