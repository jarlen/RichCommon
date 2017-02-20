package cn.jarlen.richcommon2.adapter.multi;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.TextView;

import cn.jarlen.richcommon.adapter.RvViewHolder;
import cn.jarlen.richcommon.adapter.multiple.BaseRvMultiItemView;
import cn.jarlen.richcommon2.R;
import cn.jarlen.richcommon2.data.Bean;

/**
 * DESCRIBE:
 * Created by hjl on 2017/1/12.
 */

public class MultiItemView2 extends BaseRvMultiItemView<Bean> {
    public MultiItemView2(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.layout_rv_item_two;
    }

    @Override
    protected void onBindView(RvViewHolder viewHolder, Bean item) {
        Log.e("jarlen","onBindView--->"+viewHolder);
        TextView name = viewHolder.getView(R.id.name);
        name.setText(item.getName());
    }

    @Override
    public boolean isForViewType(@NonNull Bean item, int position) {
        return 2 == item.getType();
    }
}
