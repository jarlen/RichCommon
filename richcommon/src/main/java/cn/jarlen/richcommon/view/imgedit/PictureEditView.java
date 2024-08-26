package cn.jarlen.richcommon.view.imgedit;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class PictureEditView extends FrameLayout {

    public static final String TAG = PictureEditView.class.getSimpleName();

    private int width;
    private int height;

    private PictureEditPresenter editPresenter = new PictureEditPresenter();

    public PictureEditView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PictureEditView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        onDrawImages(canvas);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (!changed) {
            return;
        }

        Log.e(PictureEditView.TAG, "onLayout--->\n" +
                "left:" + left + "\n" +
                "right:" + right + "\n" +
                "top:" + top + "\n" +
                "bottom:" + bottom);

        editPresenter.onLayout(left,top,right,bottom);
    }

    private void onDrawImages(Canvas canvas) {
        editPresenter.onDrawImage(canvas);
        editPresenter.onDrawStickers(canvas);
        editPresenter.onDrawClip(canvas);
    }

    public void setEditBitmap(Bitmap bitmap) {
        editPresenter.setBitmap(bitmap);
        invalidate();
    }

}
