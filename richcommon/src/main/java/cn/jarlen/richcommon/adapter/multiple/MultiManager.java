/*
 *          Copyright (C) 2016 jarlen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package cn.jarlen.richcommon.adapter.multiple;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.util.SparseArrayCompat;
import android.view.View;
import android.view.ViewGroup;

import cn.jarlen.richcommon.adapter.ViewHolder;
import cn.jarlen.richcommon.util.LogUtils;

/**
 * Created by hjl on 2017/2/22.
 */

public class MultiManager<D> implements IMultiManager<D> {

    private Context mContext;

    public MultiManager(Context context) {
        this.mContext = context;
    }

    private SparseArrayCompat<IMultiItemView> multiItemViews = new SparseArrayCompat<IMultiItemView>();

    @Override
    public void registerMultiView(@NonNull IMultiItemView multiItemView) {
        multiItemViews.put(multiItemView.getLayoutResId(), multiItemView);
    }


    @Override
    public void unRegisterMultiViews() {
        multiItemViews.clear();
    }

    @Override
    public int getViewTypeCount() {
        return multiItemViews.size();
    }

    @Override
    public int getItemViewType(D data, int position) {

        int itemViewCount = multiItemViews.size();

        for (int index = 0; index < itemViewCount; index++) {
            int viewType = multiItemViews.keyAt(index);
            IMultiItemView itemView = multiItemViews.get(viewType);
            boolean match = itemView.isForViewType(data, position);
            if (match) {
                return index;
            }
        }

        throw new NullPointerException(
                "No multiItemView added that matches in data source");
    }

    @Override
    public IMultiItemView getMultiItemForResId(int viewType) {
        IMultiItemView multiItemView = multiItemViews.get(viewType);
        return multiItemView;
    }

    @Override
    public View getView(D data, int position, View convertView, ViewGroup parent) {
        int itemViewType = getItemViewType(data, position);
        int resId = multiItemViews.keyAt(itemViewType);
        IMultiItemView multiItemView = getMultiItemForResId(resId);
        ViewHolder viewHolder = multiItemView.getViewHolder(parent, convertView, position);
        multiItemView.updateView(viewHolder, data);
        return viewHolder.getConvertView();
    }
}
