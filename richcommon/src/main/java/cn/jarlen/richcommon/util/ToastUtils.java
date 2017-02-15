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
package cn.jarlen.richcommon.util;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * DESCRIBE:Toast util
 * Created by jarlen on 2016/6/22.
 */
public class ToastUtils {

    private String mText;

    private int picId = -1;

    private Context mContext;

    private Toast mToast;

    private int mGravity = BOTTOM;

    private int mDuration = Toast.LENGTH_SHORT;

    public static final int CENTER = 0;

    public static final int BOTTOM = 1;

    public static final int TOP = -1;


    private ToastUtils(Context ctx) {
        this.mContext = ctx;
        mToast = Toast.makeText(ctx, "", Toast.LENGTH_SHORT);
    }

    public static ToastUtils makeToast(Context ctx) {
        return new ToastUtils(ctx);
    }

    public ToastUtils setText(String message) {
        this.mText = message;
        return this;
    }

    /**
     * @param gravity
     */
    public ToastUtils setGravity(int gravity) {
        this.mGravity = gravity;
        return this;
    }

    public ToastUtils setImage(int picId) {
        this.picId = picId;
        return this;
    }

    public ToastUtils setDuration(int duration) {
        this.mDuration = duration;
        return this;
    }

    public void show() {

        if (TextUtils.isEmpty(mText) && picId == -1) {
            return;
        }

        if (picId == -1 && !TextUtils.isEmpty(mText)) {
            mToast.setText(mText);
        } else if (picId != -1 && TextUtils.isEmpty(mText)) {
            LinearLayout toastView = (LinearLayout) mToast.getView();
            ImageView icon = new ImageView(mContext);
            icon.setImageResource(picId);
            toastView.addView(icon, 0);
        } else {
            setTextWithImage(mText, picId);
        }

        setGravity(mGravity, null);
        mToast.setDuration(mDuration);
        mToast.show();
    }

    private void setTextWithImage(String text, int picId) {
        mToast.setText(text);
        LinearLayout toastView = (LinearLayout) mToast.getView();
        ImageView icon = new ImageView(mContext);
        icon.setImageResource(picId);
        toastView.addView(icon, 0);
    }

    private void setGravity(int gravity, String value) {
        switch (gravity) {
            case CENTER:
                mToast.setGravity(Gravity.CENTER, 0, 0);
                break;
            case BOTTOM:
                //do nothing
                break;
            case TOP:
                mToast.setGravity(Gravity.TOP, 0, 100);
                break;
            default:
                //do nothing
                break;
        }
    }

}
