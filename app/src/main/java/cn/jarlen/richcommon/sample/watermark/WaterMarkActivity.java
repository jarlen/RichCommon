package cn.jarlen.richcommon.sample.watermark;

import cn.jarlen.richcommon.sample.R;
import cn.jarlen.richcommon.ui.BaseActivity;
import cn.jarlen.richcommon.view.WaterMarkView;

public class WaterMarkActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_water_mark;
    }

    @Override
    protected void onBindView() {
        WaterMarkView markView = (WaterMarkView) findViewById(R.id.wmView);
        markView.setWaterMarkText("302209");
    }

    @Override
    protected void preBindView() {

    }
}
