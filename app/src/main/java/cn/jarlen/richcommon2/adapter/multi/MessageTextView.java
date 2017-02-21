package cn.jarlen.richcommon2.adapter.multi;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.TextView;

import cn.jarlen.richcommon.adapter.RvViewHolder;
import cn.jarlen.richcommon.adapter.multiple.BaseRvMultiItemView;
import cn.jarlen.richcommon2.R;
import cn.jarlen.richcommon2.data.Bean;

/**
 * DESCRIBE:
 * Created by hjl on 2017/2/21.
 */

public class MessageTextView extends BaseRvMultiItemView<Bean> {

    public MessageTextView(Context context) {
        super(context);
    }

    @Override
    protected void onBindView(RvViewHolder viewHolder, Bean item) {

        TextView textView = viewHolder.getView(R.id.message_tv);
        textView.setText(item.getName());
    }

    @Override
    protected boolean isForViewType(@NonNull Bean item) {
        return item.getType()== 0 || item.getType()== 1;
    }

    @Override
    public int getLayoutResId(Bean item) {
        int flag = item.getType();
        switch (flag){
            case 0:
            return R.layout.message_text_received;
            case 1:
                return R.layout.message_text_send;
            default:
                return R.layout.message_text_received;
        }
    }
}
