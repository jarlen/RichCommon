package cn.jarlen.richcommon2.view;

import android.widget.ImageView;

import cn.jarlen.richcommon.ui.BaseActivity;
import cn.jarlen.richcommon2.R;

public class PreviewActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_preview;
    }

    @Override
    protected void onBindView() {
        ImageView mPhotoview = (ImageView) findViewById(R.id.photoview);
        mPhotoview.setImageResource(R.drawable.cs_logo);
    }

    @Override
    protected void preBindView() {

    }
}
