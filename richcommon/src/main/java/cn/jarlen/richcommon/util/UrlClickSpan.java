package cn.jarlen.richcommon.util;

import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

/**
 * @author jarlen
 * @date 2020/3/12
 * 可点击url Span
 */
public class UrlClickSpan extends ClickableSpan {

    private int mColorValue;
    private String mUrl;
    private boolean mUnderlineText;

    public UrlClickSpan(String url, int colorValue, boolean underlineText) {
        this.mColorValue = colorValue;
        this.mUrl = url;
        this.mUnderlineText = underlineText;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        ds.setColor(mColorValue);
        ds.setUnderlineText(mUnderlineText);
    }

    @Override
    public void onClick(View widget) {
        toWeb(mUrl);
        widget.clearFocus();
    }

    protected void toWeb(String url) {
    }
}
