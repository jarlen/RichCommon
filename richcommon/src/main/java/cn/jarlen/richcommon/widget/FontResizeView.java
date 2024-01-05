package cn.jarlen.richcommon.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import cn.jarlen.richcommon.R;

/**
 * 仿支付宝字体大小调整
 * 自定义属性：
 * lineWidth        线条粗细
 * lineColor        线条颜色
 * totalCount       线条格数
 * circleColor      球型颜色
 * circleRadius     球型颜色半径
 * textFontColor    文字颜色
 * smallSize        小“A” 字体大小
 * standerSize      “标准” 字体大小
 * bigSize          大“A” 字体大小
 * defaultPosition  默认位置
 *
 * @author jarlen
 */
public class FontResizeView extends View {

    /**
     * 默认线条颜色
     */
    private static final int DEFAULT_LINE_COLOR = Color.parseColor("#222222");

    /**
     * 是否重合
     */
    private boolean isCoincide;

    /**
     * FontAdjustView的宽高
     */
    private int width, height;

    /**
     * 最小字体大小
     */
    private float minSize;

    /**
     * 最大字体大小
     */
    private float maxSize;

    /**
     * 标准字体大小
     */
    private float standardSize;

    /**
     * 左边文本
     */
    private String leftText;

    /**
     * 中间文本
     */
    private String middleText;

    /**
     * 右边文本
     */
    private String rightText;

    /**
     * 左边文本颜色
     */
    private int leftTextColor;

    /**
     * 中间文本颜色
     */
    private int middleTextColor;

    /**
     * 右边文本颜色
     */
    private int rightTextColor;

    private int textBaseY;

    /**
     * 文本与标线的距离
     */
    private int textMargin;

    /**
     * 总的等级
     */
    private int totalGrade;
    /**
     * 标准等级
     */
    private int standardGrade;

    /**
     * 线条颜色
     */
    private int lineColor;

    /**
     * 横向线段长度
     */
    private int horizontalLineLength;

    /**
     * 纵向线段长度
     */
    private int verticalLineLength;

    /**
     * 线条宽度
     */
    private int lineStrokeWidth;

    /**
     * 每段水平线条的长度
     */
    private int lineGradeWidth;

    /**
     * 滑块等级
     */
    private int sliderGrade;

    private Drawable sliderDrawable;
    private float sliderRadius;
    private SliderRect sliderRect;

    /**
     * 画笔
     */
    private Paint mPaint;

    private Paint mLinePaint;

    private TextPaint mTextPaint;
    private Rect minTextRect;
    private Rect standardTextRect;
    private Rect maxTextRect;

    /**
     * 一条横线
     */
    private Line mHorizontalLine;

    /**
     * n条竖线
     */
    private Line[] mVerticalLines;

    /**
     * 手势检测
     */
    private GestureDetector mGestureDetector;

    /**
     * 字体size改变监听器
     */
    private OnFontChangeListener onFontChangeListener;

    public FontResizeView(Context context) {
        this(context, null);
    }

    public FontResizeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FontResizeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.FontResizeView);
        minSize = ta.getDimension(R.styleable.FontResizeView_frv_minSize, dp2px(15));
        maxSize = ta.getDimension(R.styleable.FontResizeView_frv_maxSize, dp2px(25));
        totalGrade = ta.getInt(R.styleable.FontResizeView_frv_totalGrade, 5);
        standardGrade = ta.getInt(R.styleable.FontResizeView_frv_standardGrade, 2);
        if (standardGrade < 1 || standardGrade > 6) {
            standardGrade = 1;
        }
        sliderGrade = standardGrade;

        leftText = ta.getString(R.styleable.FontResizeView_frv_leftText);
        if (TextUtils.isEmpty(leftText)) {
            leftText = "A";
        }
        middleText = ta.getString(R.styleable.FontResizeView_frv_middleText);
        if (TextUtils.isEmpty(middleText)) {
            middleText = "标准";
        }
        rightText = ta.getString(R.styleable.FontResizeView_frv_rightText);
        if (TextUtils.isEmpty(rightText)) {
            rightText = "A";
        }

        leftTextColor = ta.getColor(R.styleable.FontResizeView_frv_leftTextColor, Color.BLACK);
        middleTextColor = ta.getColor(R.styleable.FontResizeView_frv_middleTextColor, Color.BLACK);
        rightTextColor = ta.getColor(R.styleable.FontResizeView_frv_rightTextColor, Color.BLACK);
        textMargin = ta.getDimensionPixelSize(R.styleable.FontResizeView_frv_textMargin, dp2px(9f));

        lineColor = ta.getColor(R.styleable.FontResizeView_frv_lineColor, DEFAULT_LINE_COLOR);
        lineStrokeWidth = ta.getDimensionPixelOffset(R.styleable.FontResizeView_frv_lineStrokeWidth, dp2px(0.5f));
        verticalLineLength = ta.getDimensionPixelOffset(R.styleable.FontResizeView_frv_verticalLineLength, -1);

        sliderDrawable = ta.getDrawable(R.styleable.FontResizeView_frv_sliderDrawable);
        sliderRadius = ta.getDimension(R.styleable.FontResizeView_frv_sliderRadius, dp2px(24));
        ta.recycle();

        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setStyle(Paint.Style.FILL);
        mLinePaint.setStrokeWidth(lineStrokeWidth);
        mLinePaint.setColor(lineColor);

        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setStyle(Paint.Style.FILL);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);

        width = getResources().getDisplayMetrics().widthPixels;
        height = dp2px(140);

        standardSize = (maxSize - minSize) / (totalGrade - 1) * (standardGrade - 1) + minSize;

        mHorizontalLine = new Line();
        mVerticalLines = new Line[totalGrade];
        for (int i = 0; i < mVerticalLines.length; i++) {
            mVerticalLines[i] = new Line();
        }

        sliderRect = new SliderRect();
        mGestureDetector = new GestureDetector(context, gestureListener);

        minTextRect = getMeasuredMinTextRect();
        standardTextRect = getMeasuredStandardTextRect();
        maxTextRect = getMeasuredMaxTextRect();
    }

    GestureDetector.SimpleOnGestureListener gestureListener = new GestureDetector.SimpleOnGestureListener() {

        @Override
        public boolean onDown(MotionEvent e) {
            isCoincide = sliderRect.coincide(e.getX(), e.getY());
            getParent().requestDisallowInterceptTouchEvent(true);
            return super.onDown(e);
        }

        /**
         * 单击事件
         */
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            final Line horizontalLine = mHorizontalLine;
            float x = e.getX();
            if (x > horizontalLine.stopX) {
                x = horizontalLine.stopX;
            } else if (x < horizontalLine.startX) {
                x = horizontalLine.startX;
            }
            moveSlider(x - horizontalLine.startX, true);
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            getParent().requestDisallowInterceptTouchEvent(true);
            if (isCoincide) {
                float x = sliderRect.rect.centerX();
                setSliderPointX(x - distanceX, false);
                postInvalidate();
                return true;
            }
            return super.onScroll(e1, e2, distanceX, distanceY);
        }
    };

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int specWidthSize = MeasureSpec.getSize(widthMeasureSpec);
        int specHeightSize = MeasureSpec.getSize(heightMeasureSpec);
        int specHeightMode = MeasureSpec.getMode(heightMeasureSpec);

        width = Math.min(width, specWidthSize);
        if (MeasureSpec.EXACTLY == specHeightMode) {
            height = specHeightSize;
        } else {
            height = (int) (sliderRadius * 2 + textMargin + maxTextRect.height()) + getPaddingTop() + getPaddingBottom();
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        horizontalLineLength = (int) (w - getPaddingLeft() - getPaddingRight() - sliderRadius * 2);

        if (verticalLineLength == -1) {
            verticalLineLength = dp2px(10);
        }

        lineGradeWidth = horizontalLineLength / (totalGrade - 1);

        //初始化横线起点位置
        int horizontalLineStartX = (width - horizontalLineLength) / 2;
        int horizontalLineStartY = (int) (height - getPaddingBottom() - sliderRadius - lineStrokeWidth / 2.0f);

        //初始化横线起点、终点位置
        mHorizontalLine.set(horizontalLineStartX, horizontalLineStartY, horizontalLineStartX + horizontalLineLength, horizontalLineStartY);
        float lineAverageWidth = horizontalLineLength * 1.0f / (totalGrade - 1);
        final Line[] verticalLines = mVerticalLines;
        for (int i = 0; i < verticalLines.length; i++) {
            float startX = horizontalLineStartX + lineAverageWidth * i;
            verticalLines[i].set(startX, horizontalLineStartY - verticalLineLength / 2f, startX, horizontalLineStartY + verticalLineLength / 2f);
        }

        //初始化滑块的等级及位置
        sliderRect.setGrade(sliderGrade);
        setSliderPointX(verticalLines[sliderGrade - 1].startX, true);
        float centerY = verticalLines[sliderGrade - 1].startY + verticalLines[sliderGrade - 1].getHeight() / 2;
        sliderRect.rect.top = (int) (centerY - sliderRadius);
        sliderRect.rect.bottom = (int) (centerY + sliderRadius);

        textBaseY = sliderRect.rect.top - textMargin;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        final Line horizontalLine = mHorizontalLine;

        /*绘制标尺线---横线、线段*/
        canvas.drawLine(horizontalLine.startX, horizontalLine.startY, horizontalLine.stopX, horizontalLine.stopY, mLinePaint);
        for (Line line : mVerticalLines) {
            canvas.drawLine(line.startX, line.startY, line.stopX, line.stopY, mLinePaint);
        }

        //绘制左边文本
        mTextPaint.setColor(leftTextColor);
        mTextPaint.setTextSize(minSize);
        float width = minTextRect.width();
        float lineStartY = sliderRect.rect.top - textMargin + 4;
        canvas.drawText(leftText, horizontalLine.startX - width / 2, lineStartY, mTextPaint);

        //绘制右边文本
        mTextPaint.setColor(rightTextColor);
        mTextPaint.setTextSize(maxSize);
        width = maxTextRect.width();
        canvas.drawText(rightText, horizontalLine.stopX - width / 2, lineStartY, mTextPaint);

        //绘制中间文本
        if (standardGrade != 1 && standardGrade != totalGrade) {
            mTextPaint.setColor(middleTextColor);
            mTextPaint.setTextSize(standardSize);
            width = standardTextRect.width();
            float startX = mVerticalLines[standardGrade - 1].startX - width / 2;
            canvas.drawText(middleText, startX, lineStartY, mTextPaint);
        }
        //绘制滑块
        sliderDrawable.setBounds(sliderRect.rect);
        sliderDrawable.draw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mGestureDetector.onTouchEvent(event)) {
            return true;
        }
        if (event.getAction() == MotionEvent.ACTION_UP && isCoincide) {
            //手指抬起时，滑块不在整点，则移动到整点
            float x = sliderRect.rect.centerX() - mHorizontalLine.startX;
            moveSlider(x, false);
        }
        return true;
    }

    /**
     * 移动滑块
     */
    private void moveSlider(float destX, final boolean isClick) {
        /*目标等级*/
        int grade = (int) destX / lineGradeWidth;
        float remainder = destX % lineGradeWidth;
        if (remainder > lineGradeWidth / 2) {
            grade++;
        }

        final int tempGrade = grade;
        int gradeDiffer = Math.abs(sliderRect.getGrade() - tempGrade);
        if (gradeDiffer == 0) {
//            if (isClick) {
//                return;
//            }
            gradeDiffer = 1;
        }
        ValueAnimator animator = ValueAnimator.ofFloat(sliderRect.rect.centerX(), mVerticalLines[tempGrade].startX);
        animator.setDuration(100 + gradeDiffer * 30);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Float value = (Float) animation.getAnimatedValue();
                //如果是单击触发的移动事件，动画未结束前仅更新滑块的位置，动画结束后再更新滑块的等级
                setSliderPointX(value, isClick);
                postInvalidate();
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                setSliderPointX(mVerticalLines[tempGrade].startX, false);
            }
        });
        animator.start();
    }

    /**
     * 设置滑块X坐标的统一入口
     *
     * @param x
     * @param onlySetX 是否仅仅更新滑块的位置
     */
    private void setSliderPointX(float x, boolean onlySetX) {
        float horizontalLineStartX = mHorizontalLine.startX;
        float horizontalLineStopX = mHorizontalLine.stopX;
        if (x < horizontalLineStartX) {
            x = horizontalLineStartX;
        } else if (x > horizontalLineStopX) {
            x = horizontalLineStopX;
        }
        sliderRect.rect.left = (int) (x - sliderRadius);
        sliderRect.rect.right = (int) (x + sliderRadius);
        if (onlySetX) {
            return;
        }

        int oldGrade = sliderRect.getGrade();
        float gradeStepX = x - horizontalLineStartX;
        int newGrade = (int) gradeStepX / lineGradeWidth + 1;
        if (newGrade != oldGrade) {
            sliderRect.setGrade(newGrade);
        }
        if (onFontChangeListener == null) {
            return;
        }
        float gradeStepFactor = gradeStepX / mHorizontalLine.getWidth();
        onFontChangeListener.onFontChange(gradeStepFactor, sliderRect.getGrade());
    }

    private Rect getMeasuredMaxTextRect() {
        mTextPaint.setTextSize(maxSize);
        Rect rect = new Rect();
        mTextPaint.getTextBounds(rightText, 0, rightText.length(), rect);
        return rect;
    }

    private Rect getMeasuredMinTextRect() {
        mTextPaint.setTextSize(minSize);
        Rect rect = new Rect();
        mTextPaint.getTextBounds(leftText, 0, leftText.length(), rect);
        return rect;
    }

    private Rect getMeasuredStandardTextRect() {
        mTextPaint.setTextSize(standardSize);
        Rect rect = new Rect();
        mTextPaint.getTextBounds(middleText, 0, middleText.length(), rect);
        return rect;
    }

    /**
     * @return 返回当前设置的字体大小  单位 ：sp
     */
    public float getFontSize() {
        float size = (maxSize - minSize) / (totalGrade - 1);
        return (minSize + size * (sliderGrade - 1)) / getResources().getDisplayMetrics().scaledDensity;
    }

    /**
     * 设置当前字体大小
     *
     * @param fontSize 字体大小 单位 ：sp
     */
    public void setFontSize(float fontSize) {
        fontSize *= getResources().getDisplayMetrics().scaledDensity;
        int grade = (int) ((fontSize - minSize) / ((maxSize - minSize) / (totalGrade - 1))) + 1;
        setSliderGrade(grade);
    }

    /**
     * 设置滑块等级
     *
     * @param grade 滑块等级
     */
    public void setSliderGrade(int grade) {
        if (grade < 0) {
            grade = 1;
        }
        if (grade > totalGrade) {
            grade = totalGrade;
        }
        sliderGrade = grade;
    }

    private int dp2px(float dipValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public void setOnFontChangeListener(OnFontChangeListener onFontChangeListener) {
        this.onFontChangeListener = onFontChangeListener;
    }

    public interface OnFontChangeListener {
        /**
         * 字体调节变化回调方法
         *
         * @param factor 变动比例
         * @param grade  标尺等级
         */
        void onFontChange(float factor, int grade);
    }

    class SliderRect {
        Rect rect = new Rect();
        int grade; //滑块等级

        public int getGrade() {
            return grade;
        }

        public void setGrade(int grade) {
            this.grade = grade;
        }

        //判断手指按下的点是否与滑块重合
        boolean coincide(float movingX, float movingY) {
            //开方，如果两点之间的距离小于规定的半径r则定义为重合
            return Math.sqrt((rect.centerX() - movingX) * (rect.centerX() - movingX)
                    + (rect.centerY() - movingY) * (rect.centerY() - movingY)) < rect.width() / 2 + dp2px(20);
        }
    }

    public class Line {

        float startX;
        float startY;
        float stopX;
        float stopY;

        void set(float startX, float startY, float stopX, float stopY) {
            this.startX = startX;
            this.startY = startY;
            this.stopX = stopX;
            this.stopY = stopY;
        }

        float getHeight() {
            return Math.abs(stopY - startY);
        }

        float getWidth() {
            return Math.abs(stopX - startX);
        }
    }
}