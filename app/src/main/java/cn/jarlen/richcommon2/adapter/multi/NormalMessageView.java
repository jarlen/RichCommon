package cn.jarlen.richcommon2.adapter.multi;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.TextView;

import cn.jarlen.richcommon.adapter.RvViewHolder;
import cn.jarlen.richcommon.adapter.multiple.RvMultiItemView;
import cn.jarlen.richcommon2.R;
import cn.jarlen.richcommon2.data.Bean;

/**
 * DESCRIBE:
 * Created by hjl on 2017/2/21.
 */

public class NormalMessageView extends RvMultiItemView<Bean> {

    public NormalMessageView(Context context) {
        super(context);
    }

    @Override
    protected void onBindView(RvViewHolder viewHolder, Bean item) {

        ImageView imge = viewHolder.getView(R.id.user_head_img);
        imge.setImageResource(R.drawable.cs_logo);

        TextView textView = viewHolder.getView(R.id.message_tv);
        textView.setText(item.getName());

        if(item.getType()%7 == 6){
            TextView flag = viewHolder.getView(R.id.flag_tv);
            flag.setText("测试");
        }
    }

    @Override
    protected boolean isForViewType(@NonNull Bean item) {
        return item.getType()== 5 || item.getType()== 6;
    }

    @Override
    public int getLayoutResId(Bean item) {
        int flag = item.getType();
        switch (flag){
            case 5:
            return R.layout.layout_message_one_send;
            case 6:
                return R.layout.layout_message_one_receiver;
            default:
                return R.layout.layout_message_one_send;
        }
    }
}
