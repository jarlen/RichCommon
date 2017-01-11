package cn.jarlen.richcommon.adapter.multiple;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.ViewGroup;

import java.util.List;

import cn.jarlen.richcommon.adapter.RvViewHolder;

/**
 * DESCRIBE:
 * Created by jarlen on 2017/1/11.
 */

public abstract class BaseAdapterDelegate<D> extends AdapterDelegate<List<D>>{

    protected Context mContext;

    public BaseAdapterDelegate(Context context){
        this.mContext = context;
    }

    @NonNull
    @Override
    protected RvViewHolder onCreateViewHolder(ViewGroup parent) {
        RvViewHolder viewHolder = RvViewHolder.getViewHolder(mContext, parent, getLayoutResId());
        return viewHolder;
    }

    protected abstract int getLayoutResId();


}
