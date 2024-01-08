package cn.jarlen.richcommon.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import cn.jarlen.richcommon.R;

/**
 * @author jarlen
 * @date 2019/9/2
 * 带边框的TextView
 */
public class BorderTextView extends AppCompatTextView {

    /**
     * 边角圆角半径
     */
    private int mCornersRadius;

    /**
     * 边框宽度
     */
    private int mBorderWidth;

    /**
     * 边框颜色
     */
    private int mBorderColor;

    /**
     * 文本背景填充色
     */
    private int mSolidColor;
    private GradientDrawable gradientDrawable;

    public BorderTextView(Context context) {
        super(context, null);
    }

    public BorderTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BorderTextView);
        mCornersRadius = typedArray.getDimensionPixelSize(R.styleable.BorderTextView_btv_corners_radius, 0);
        mBorderWidth = typedArray.getDimensionPixelSize(R.styleable.BorderTextView_btv_border_width, 0);
        mBorderColor = typedArray.getColor(R.styleable.BorderTextView_btv_border_color, Color.TRANSPARENT);
        mSolidColor = typedArray.getColor(R.styleable.BorderTextView_btv_solid_color, Color.TRANSPARENT);
        typedArray.recycle();
        gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(mSolidColor);
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setCornerRadius(mCornersRadius);
        gradientDrawable.setStroke(mBorderWidth, mBorderColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (gradientDrawable == null) {
            return;
        }
        setBackground(gradientDrawable);
    }

    /**
     * 设置边角圆角半径
     *
     * @param cornersRadius
     */
    public void setCornersRadius(int cornersRadius) {
        if (mCornersRadius == cornersRadius) {
            return;
        }
        mCornersRadius = cornersRadius;
        refreshDrawable();
    }

    /**
     * 设置边框宽度
     *
     * @param borderWidth
     */
    public void setBorderWidth(int borderWidth) {
        if (mBorderWidth == borderWidth) {
            return;
        }
        mBorderWidth = borderWidth;
        refreshDrawable();
    }

    /**
     * 设置边框颜色
     *
     * @param borderColor
     */
    public void setBorderColor(int borderColor) {
        if (mBorderColor == borderColor) {
            return;
        }
        mBorderColor = borderColor;
        refreshDrawable();
    }

    /**
     * 设置文本背景填充色
     *
     * @param solidColor
     */
    public void setSolidColor(int solidColor) {
        if (mSolidColor == solidColor) {
            return;
        }
        mSolidColor = solidColor;
        refreshDrawable();
    }

    private void refreshDrawable() {
        if (gradientDrawable != null) {
            gradientDrawable.setStroke(mBorderWidth, mBorderColor);
            gradientDrawable.setColor(mSolidColor);
            gradientDrawable.setCornerRadius(mCornersRadius);
        }
        invalidate();
    }
}
