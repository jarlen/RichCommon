package cn.jarlen.richcommon.sample.font;

import android.util.TypedValue;
import android.widget.TextView;

import cn.jarlen.richcommon.sample.R;
import cn.jarlen.richcommon.ui.BaseActivity;
import cn.jarlen.richcommon.util.DensityUtils;
import cn.jarlen.richcommon.util.StatusBarUtil;
import cn.jarlen.richcommon.widget.FontResizeView;
import cn.jarlen.richcommon.widget.toolbar.CommonToolBar;

/**
 * @author jarlen
 */
public class FontResizeActivity extends BaseActivity {

    CommonToolBar commonToolBar;
    FontResizeView fontResizeView;
    TextView tvContent;

    private float deltaFontSize;
    private float minContentFontSize;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_font_resize;
    }

    @Override
    protected void onBindView() {
        commonToolBar = findViewById(R.id.toolbar_font_resize);
        StatusBarUtil.immersive(this);
        StatusBarUtil.setPaddingSmart(this, commonToolBar);
        StatusBarUtil.darkMode(this);

        tvContent = findViewById(R.id.tv_content);

        fontResizeView = findViewById(R.id.fsv);
        fontResizeView.setOnFontChangeListener(new FontResizeView.OnFontChangeListener() {
            @Override
            public void onFontChange(float factor, int grade) {
                changeContentFont(factor, grade);
            }
        });

        float minFontSize = DensityUtils.dip2px(this, 12.6f);
        float maxFontSize = DensityUtils.dip2px(this, 16.8f);
        float maxFontFactor = maxFontSize / minFontSize;
        minContentFontSize = DensityUtils.dip2px(this, 12.6f);
        deltaFontSize = minContentFontSize * maxFontFactor - minContentFontSize;
    }

    private void changeContentFont(float factor, int grade) {
        float contentFontSize = minContentFontSize + deltaFontSize * factor;
        tvContent.setTextSize(TypedValue.COMPLEX_UNIT_PX, contentFontSize);
    }
}
