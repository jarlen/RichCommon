/*
 *          Copyright (C) 2016 jarlen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package cn.jarlen.richcommon.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import cn.jarlen.richcommon.R;
import cn.jarlen.richcommon.utils.SystemUtil;

/**
 * DESCRIBE: watermark
 * Created by jarlen on 2016/8/29.
 */
public class WaterMarkView extends View {

    private TextPaint mTextPaint = new TextPaint();

    /* rotate degree for WaterMark */
    private int mDegrees = -30;

    /* content of WaterMark */
    private String wmText = "";

    /* content color of WaterMark */
    private int wmTextColor;

    /* content size of WaterMark */
    private int wmTextSize;

    private int maxWidth = 1080;

    private int dx;

    private int dy;

    private int textWidth, textHeight;


    public WaterMarkView(Context context) {
        this(context, null);
    }

    public WaterMarkView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.WaterMarkView);

        mDegrees = typedArray.getInt(R.styleable.WaterMarkView_degree, -30);

        wmText = typedArray.getString(R.styleable.WaterMarkView_text);
        dx = typedArray.getDimensionPixelSize(R.styleable.WaterMarkView_dx, SystemUtil.dp2px(context, 50));
        dy = typedArray.getDimensionPixelSize(R.styleable.WaterMarkView_dy, SystemUtil.dp2px(context, 120));
        wmTextColor = typedArray.getColor(R.styleable.WaterMarkView_textColor, Color.parseColor("#66000000"));
        wmTextSize = typedArray.getDimensionPixelSize(R.styleable.WaterMarkView_textSize, SystemUtil.dp2px(context, 20));

        typedArray.recycle();

        setBackgroundColor(Color.parseColor("#00000000"));

        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(wmTextColor);
        mTextPaint.setTextSize(wmTextSize);
        if (!TextUtils.isEmpty(wmText)) {
            Rect tvRect = new Rect();
            mTextPaint.getTextBounds(wmText, 0, wmText.length(), tvRect);
            textWidth = tvRect.width();
            textHeight = tvRect.height();
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (TextUtils.isEmpty(wmText)) {
            return;
        }

        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();

        if (measuredWidth == 0 || measuredHeight == 0) {
            return;
        }

        canvas.rotate(mDegrees, measuredWidth / 2, measuredHeight / 2);

        int maxRow = measuredHeight / (textHeight + dy);

        for (int row = 1; row < maxRow; row++) {
            canvas.save();
            int maxCol = measuredWidth / (textWidth + dx);
            for (int col = 0; col < maxCol; col++) {
                canvas.drawText(wmText, (textWidth + dx) * col + dx, (textHeight + dy) * row, mTextPaint);
            }
            canvas.restore();
        }
    }

    /**
     * content of WaterMark
     * @param text
     */
    public void setWaterMarkText(String text) {
        this.wmText = text;

        Rect tvRect = new Rect();
        mTextPaint.getTextBounds(wmText, 0, wmText.length(), tvRect);
        textWidth = tvRect.width();
        textHeight = tvRect.height();

        int measuredWidth = getMeasuredWidth();

        maxWidth = (int) (measuredWidth / (Math.cos(mDegrees) * Math.cos(mDegrees)));

        postInvalidate();
    }


}
