package cn.jarlen.richcommon.sample.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

import cn.jarlen.richcommon.adapter.RvCommonAdapter;
import cn.jarlen.richcommon.adapter.RvViewHolder;
import cn.jarlen.richcommon.sample.R;
import cn.jarlen.richcommon.sample.data.TestItem;

/**
 * Created by jarlen on 2018/2/8.
 */

public class TestAdapter extends RvCommonAdapter<TestItem> {


    public TestAdapter(Context context) {
        super(context);
    }

    @Override
    public void onBindView(RvViewHolder viewHolder, final TestItem item, int position) {
        TextView itemNameTv = viewHolder.getView(R.id.tv_item_name);
        itemNameTv.setText(item.getItemName());

        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent testIntent = new Intent(getContext(), item.getTestClass());
                getContext().startActivity(testIntent);
            }
        });

        Random read = new Random();
        int red = read.nextInt(255);
        int green = read.nextInt(255);
        int blue = read.nextInt(255);

        viewHolder.getConvertView().setBackgroundColor(Color.argb(255, red, green, blue));
    }

    @Override
    public int getLayoutResId(int viewType) {
        return R.layout.layout_test_item;
    }


}