package cn.jarlen.richcommon2.view;

import cn.jarlen.richcommon.ui.BaseActivity;
import cn.jarlen.richcommon.view.WaterMarkView;
import cn.jarlen.richcommon2.R;

public class WaterMarkActivity extends BaseActivity {

    private WaterMarkView markView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_water_mark;
    }

    @Override
    protected void onBindView() {
        markView = (WaterMarkView) findViewById(R.id.wmView);
        markView.setWaterMarkText("测试110110");
    }

    @Override
    protected void preBindView() {

    }
}
