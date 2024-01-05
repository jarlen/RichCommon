package cn.jarlen.richcommon.sample;

import android.widget.ImageView;

import cn.jarlen.richcommon.ui.BaseActivity;

public class PreviewActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_preview;
    }

    @Override
    protected void onBindView() {
        ImageView mPhotoview = (ImageView) findViewById(R.id.photoview);
        mPhotoview.setImageResource(R.drawable.img_preview);
    }

    @Override
    protected void preBindView() {

    }
}
