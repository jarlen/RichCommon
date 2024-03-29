package cn.jarlen.richcommon.sample.adapter.multi;

import android.content.Context;
import android.widget.TextView;

import androidx.annotation.NonNull;

import cn.jarlen.richcommon.adapter.RvViewHolder;
import cn.jarlen.richcommon.adapter.multiple.RvMultiItemView;
import cn.jarlen.richcommon.sample.R;
import cn.jarlen.richcommon.sample.data.Bean;

/**
 * DESCRIBE:
 * Created by hjl on 2017/2/21.
 */

public class MessageTextView extends RvMultiItemView<Bean> {

    public MessageTextView(Context context) {
        super(context);
    }

    @Override
    protected void onBindView(RvViewHolder viewHolder, Bean item, int position) {
        TextView textView = viewHolder.getView(R.id.message_tv);
        textView.setText(item.getName());
    }

    @Override
    protected boolean isForViewType(@NonNull Bean item) {
        return item.getType() == 0 || item.getType() == 1;
    }

    @Override
    public int getLayoutResId(Bean item) {
        int flag = item.getType();
        switch (flag) {
            case 0:
                return R.layout.message_text_received;
            case 1:
                return R.layout.message_text_send;
            default:
                return R.layout.message_text_received;
        }
    }
}
