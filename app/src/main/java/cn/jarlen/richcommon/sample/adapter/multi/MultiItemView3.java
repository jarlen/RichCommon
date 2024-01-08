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

public class MultiItemView3 extends RvMultiItemView<Bean> {

    public MultiItemView3(Context context) {
        super(context);
        LogUtils.d("jarlen", "creat");
    }

    @Override
    protected void onBindView(RvViewHolder viewHolder, Bean item, int position) {
        Log.e("jarlen", "onBindView--->" + viewHolder);
        TextView name = viewHolder.getView(R.id.name);
        name.setText("MultiItemView3 : " + item.getName());
    }

    @Override
    protected boolean isForViewType(@NonNull Bean item) {
        return item.getType() == 3;
    }

    @Override
    public int getLayoutResId(Bean item) {
        return R.layout.layout_rv_item_three;
    }
}
