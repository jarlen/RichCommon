package cn.jarlen.richcommon.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * DESCRIBE:
 * Created by jarlen on 2017/1/10.
 */

public class ImageUtil {

    /**
     * load a bitmap of arbitrarily large size into an ImageView from Resources.
     * <br>First decode with inJustDecodeBounds=true to check dimensions
     * <br>Calculate inSampleSize</>
     * <br>Decode bitmap with inSampleSize set</>
     * @param context
     * @param resId
     * Resources id
     * @param reqWidth
     * width of imageView
     * @param reqHeight
     * height of imageView
     * @return
     */
    public static Bitmap decodeSampledBitmapFromResource(Context context, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Resources res = context.getResources();
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
}
