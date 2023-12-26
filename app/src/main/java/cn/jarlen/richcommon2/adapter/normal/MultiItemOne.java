package cn.jarlen.richcommon2.adapter.normal;

import android.content.Context;
import android.widget.TextView;

import androidx.annotation.NonNull;

import cn.jarlen.richcommon.adapter.ViewHolder;
import cn.jarlen.richcommon.adapter.multiple.MultiItemView;
import cn.jarlen.richcommon2.R;
import cn.jarlen.richcommon2.data.Bean;

/**
 * Created by hjl on 2017/2/22.
 */

public class MultiItemOne extends MultiItemView<Bean> {

    MultiItemOne(Context context) {
        super(context);
    }

    @Override
    protected void onBindView(ViewHolder viewHolder, Bean data, int position) {
        TextView textView = viewHolder.getView(R.id.message_tv);
        textView.setText(data.getName());
    }

    @Override
    protected boolean isForViewType(@NonNull Bean item) {
        return item.getType() == 0;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.message_text_send;
    }
}
