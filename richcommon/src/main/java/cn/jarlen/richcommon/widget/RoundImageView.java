package cn.jarlen.richcommon.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import cn.jarlen.richcommon.R;
import cn.jarlen.richcommon.util.DensityUtils;

/**
 * 圆角ImageView
 * Created by jarlen on 2018/8/16.
 */

public class RoundImageView extends androidx.appcompat.widget.AppCompatImageView {

    private float roundRadius = 6;

    private int width;
    private int height;


    public RoundImageView(Context context) {
        this(context, null);
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundImageView);
        this.roundRadius = typedArray.getDimension(R.styleable.RoundImageView_round_radius, DensityUtils.dp2px(context, 2.0f));
        typedArray.recycle();

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        this.width = getWidth();
        this.height = getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (width >= roundRadius && height > roundRadius) {
            Path path = new Path();
            //四个圆角
            path.moveTo(roundRadius, 0);
            path.lineTo(width - roundRadius, 0);
            path.quadTo(width, 0, width, roundRadius);
            path.lineTo(width, height - roundRadius);
            path.quadTo(width, height, width - roundRadius, height);
            path.lineTo(roundRadius, height);
            path.quadTo(0, height, 0, height - roundRadius);
            path.lineTo(0, roundRadius);
            path.quadTo(0, 0, roundRadius, 0);

            canvas.clipPath(path);
        }
        super.onDraw(canvas);
    }
}
