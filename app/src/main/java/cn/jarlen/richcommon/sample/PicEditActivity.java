package cn.jarlen.richcommon.sample;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import cn.jarlen.richcommon.util.PictureUtils;
import cn.jarlen.richcommon.view.imgedit.PictureEditView;

public class PicEditActivity extends AppCompatActivity {

    private PictureEditView pictureEditView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_edit);
        pictureEditView = findViewById(R.id.image_edit);

        loadImage();
    }

    private void loadImage(){
        Bitmap picBitmap = PictureUtils.getImageFromAssetsFile(this,"test_image.jpg");
        pictureEditView.setEditBitmap(picBitmap);
    }
}