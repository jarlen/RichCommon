package cn.jarlen.richcommon2.adapter.multi;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;

import cn.jarlen.richcommon.adapter.RvViewHolder;
import cn.jarlen.richcommon.adapter.multiple.RvMultiItemView;
import cn.jarlen.richcommon.util.LogUtils;
import cn.jarlen.richcommon2.R;
import cn.jarlen.richcommon2.data.Bean;

/**
 * DESCRIBE:
 * Created by hjl on 2017/1/12.
 */

public class MultiItemView5 extends RvMultiItemView<Bean> {

    public MultiItemView5(Context context) {
        super(context);
        LogUtils.d("jarlen", "creat");
    }

    @Override
    protected void onBindView(RvViewHolder viewHolder, Bean item) {
        Log.e("jarlen", "onBindView--->" + viewHolder);
        TextView name = viewHolder.getView(R.id.name);
        name.setText("MultiItemView4 : " + item.getName());
    }

    @Override
    protected boolean isForViewType(@NonNull Bean item) {
        return item.getType() == 5;
    }

    @Override
    public int getLayoutResId(Bean item) {
        return R.layout.layout_rv_item_five;
    }
}
