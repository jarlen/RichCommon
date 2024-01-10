package cn.jarlen.richcommon.adapter;

import android.view.View;

public interface OnRecycleViewItemClickListener<T> {
    void onItemClick(View view, T data, int position);
}
