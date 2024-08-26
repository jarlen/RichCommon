package cn.jarlen.richcommon.view.imgedit;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

import cn.jarlen.richcommon.view.imgedit.crop.ClipMode;

public class PictureEditPresenter {

    private Bitmap mPicBitmap;
    private final RectF mPicFrame = new RectF();

    // 可视区域，无Scroll偏移区域
    private final RectF mShowWindow = new RectF();

    // 裁剪区域（显示的图片区域）
    private final RectF mClipFrame = new RectF();
    /*剪切区域模式*/
    private ClipMode clipMode = ClipMode.SQUARE;
    /*剪切区域画笔*/
    private final Paint mClipPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private final Matrix matrix = new Matrix();

    // private boolean isInitial = false;

    public PictureEditPresenter() {
        mPicBitmap = getDefaultImage();

        /*剪切区域画笔*/
        mClipPaint.setStyle(Paint.Style.STROKE);
        mClipPaint.setStrokeCap(Paint.Cap.SQUARE);
        mClipPaint.setColor(Color.WHITE);
        mClipPaint.setStrokeWidth(4f);
    }

    public void setClipMode(ClipMode clipMode) {
        this.clipMode = clipMode;
    }

    private Bitmap getDefaultImage() {
        return Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
    }

    public void setBitmap(Bitmap bitmap) {
        if (bitmap == null || bitmap.isRecycled()) {
            return;
        }
        this.mPicBitmap = bitmap;
    }

    /**
     * 绘制底图
     *
     * @param canvas 画布
     */
    public void onDrawImage(Canvas canvas) {
        /*绘制底图*/

        Log.e(PictureEditView.TAG, "onDrawImage--->\n" +
                "left:" + mPicFrame.left + "\n" +
                "right:" + mPicFrame.right + "\n" +
                "top:" + mPicFrame.top + "\n" +
                "bottom:" + mPicFrame.bottom + "\n" +
                "地图大小：" + mPicBitmap.getWidth() + "x" + mPicBitmap.getHeight());

        canvas.drawBitmap(mPicBitmap, null, mPicFrame, null);
    }

    /**
     * 绘制贴图
     *
     * @param canvas 画布
     */
    public void onDrawStickers(Canvas canvas) {


    }

    /**
     * 绘制剪切区域
     *
     * @param canvas 画布
     */
    public void onDrawClip(Canvas canvas) {
        canvas.drawRect(mClipFrame, mClipPaint);
    }

    public void onLayout(int left, int top, int right, int bottom) {
        mShowWindow.set(left, top, right, bottom);

        int showWidth = (int) mShowWindow.width();
        int showHeight = (int) mShowWindow.height();

        /*初始化图片显示区域*/
        onInitialPicFrame(showWidth, showHeight);

        /*初始化剪切区域*/
        onInitialClipFrame(showWidth, showHeight);
    }


    private void onInitialPicFrame(int width, int height) {
        if (mPicBitmap == null || mPicBitmap.isRecycled()) {
            mPicFrame.set(mShowWindow.left, mShowWindow.top, mShowWindow.right, mShowWindow.bottom);
        } else {

            int picWidth = mPicBitmap.getWidth();
            int picHeight = mPicBitmap.getHeight();

            float scale = Math.min(
                    mPicBitmap.getWidth() / mShowWindow.width(),
                    mPicBitmap.getHeight() / mShowWindow.height());


        }
    }

    private void onInitialClipFrame(int width, int height) {
        int mClipRadius = Math.min(width, height) / 2;

        int centerX = (int) ((mShowWindow.left + mShowWindow.right) / 2);
        int centerY = (int) ((mShowWindow.top + mShowWindow.bottom) / 2);
        mClipFrame.set(centerX - mClipRadius,
                centerY - mClipRadius,
                centerX + mClipRadius,
                centerY + mClipRadius);
    }


}
