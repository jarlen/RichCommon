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
import android.graphics.Paint;
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

    private int dx;

    private int dy;

    private int textWidth, textHeight;


    public WaterMarkView(Context context) {
        this(context, null);
    }

    public WaterMarkView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.WaterMarkView);

        mDegrees = typedArray.getInt(R.styleable.WaterMarkView_wm_degree, -30);

        wmText = typedArray.getString(R.styleable.WaterMarkView_wm_text);
        dx = typedArray.getDimensionPixelSize(R.styleable.WaterMarkView_wm_dx, SystemUtil.dp2px(context, 50));
        dy = typedArray.getDimensionPixelSize(R.styleable.WaterMarkView_wm_dy, SystemUtil.dp2px(context, 120));
        wmTextColor = typedArray.getColor(R.styleable.WaterMarkView_wm_textColor, Color.parseColor("#66000000"));
        wmTextSize = typedArray.getDimensionPixelSize(R.styleable.WaterMarkView_wm_textSize, SystemUtil.dp2px(context, 20));

        typedArray.recycle();

        setBackgroundColor(Color.parseColor("#00000000"));

        mTextPaint.setAntiAlias(true);
        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
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

        int canvasLength = measuredWidth > measuredHeight ? measuredWidth : measuredHeight;

        canvas.rotate(mDegrees, measuredWidth / 2, measuredHeight / 2);

        int ylocation = (measuredHeight - canvasLength) / 2;

        while (ylocation < canvasLength - (measuredHeight - canvasLength) / 2) {
            canvas.save();
            int xlocation = (measuredWidth - canvasLength) / 2;

            while (xlocation < canvasLength - (measuredWidth - canvasLength) / 2) {

                canvas.drawText(wmText, xlocation, ylocation, mTextPaint);
                xlocation = xlocation + textWidth + dx;
            }

            ylocation = ylocation + textHeight + dy;
            canvas.restore();
        }
    }

    /**
     * content of WaterMark
     *
     * @param text content of WaterMark
     */
    public void setWaterMarkText(String text) {
        this.wmText = text;

        Rect tvRect = new Rect();
        mTextPaint.getTextBounds(wmText, 0, wmText.length(), tvRect);
        textWidth = tvRect.width();
        textHeight = tvRect.height();
        postInvalidate();
    }


}
