package cn.jarlen.richcommon.sample.adapter.normal;

import android.content.Context;
import android.widget.TextView;

import androidx.annotation.NonNull;

import cn.jarlen.richcommon.adapter.ViewHolder;
import cn.jarlen.richcommon.adapter.multiple.MultiItemView;
import cn.jarlen.richcommon.sample.R;
import cn.jarlen.richcommon.sample.data.Bean;

/**
 * Created by hjl on 2017/2/22.
 */

public class MultiItemTwo extends MultiItemView<Bean> {

    MultiItemTwo(Context context) {
        super(context);
    }

    @Override
    protected void onBindView(ViewHolder viewHolder, Bean data, int position) {
        TextView textView = viewHolder.getView(R.id.message_tv);
        textView.setText(data.getName());
    }

    @Override
    protected boolean isForViewType(@NonNull Bean item) {
        return item.getType() == 1;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.message_text_received;
    }
}
