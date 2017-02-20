package cn.jarlen.richcommon.adapter.multiple;

import android.support.annotation.NonNull;

import cn.jarlen.richcommon.adapter.ViewHolder;

/**
 * DESCRIBE:
 * Created by hjl on 2017/2/20.
 */

public interface IMultiItemView<D>{

    boolean isForViewType(@NonNull D item, int position);

    void onBindView(ViewHolder viewHolder, D item);
}
