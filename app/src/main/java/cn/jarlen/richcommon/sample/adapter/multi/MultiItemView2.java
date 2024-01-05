package cn.jarlen.richcommon.sample.adapter.multi;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;

import cn.jarlen.richcommon.adapter.RvViewHolder;
import cn.jarlen.richcommon.adapter.multiple.RvMultiItemView;
import cn.jarlen.richcommon.sample.R;
import cn.jarlen.richcommon.sample.data.Bean;
import cn.jarlen.richcommon.util.LogUtils;

/**
 * DESCRIBE:
 * Created by hjl on 2017/1/12.
 */

public class MultiItemView2 extends RvMultiItemView<Bean> {
    public MultiItemView2(Context context) {
        super(context);
        LogUtils.d("jarlen", "creat");
    }

    @Override
    protected void onBindView(RvViewHolder viewHolder, Bean item) {
        Log.e("jarlen", "onBindView--->" + viewHolder);
        TextView name = viewHolder.getView(R.id.name);
        name.setText("MultiItemView2 : " + item.getName());
    }

    @Override
    protected boolean isForViewType(@NonNull Bean item) {
        return item.getType() == 2;
    }


    @Override
    public int getLayoutResId(Bean item) {
        return R.layout.layout_rv_item_two;
    }
}
