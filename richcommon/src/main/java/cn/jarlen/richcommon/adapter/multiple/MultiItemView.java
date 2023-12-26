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
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import cn.jarlen.richcommon.adapter.ViewHolder;

/**
 * item view
 * Created by hjl on 2016/9/11.
 */

public abstract class MultiItemView<D> implements IMultiItemView<D> {

    protected Context mContext;

    public MultiItemView(Context context) {
        this.mContext = context;
    }

    @Override
    public boolean isForViewType(D item, int position) {
        boolean match = isForViewType(item);
        return match;
    }

    public ViewHolder getViewHolder(ViewGroup parent, View convertView, int position) {
        ViewHolder vh = ViewHolder.getViewHolder(mContext, parent, convertView, position, getLayoutResId());
        return vh;
    }

    @Override
    public void updateView(ViewHolder viewHolder, D item, int position) {
        onBindView(viewHolder, item, position);
    }

    /**
     * bind data onto view
     *
     * @param viewHolder the current view component
     * @param item       data
     */
    protected abstract void onBindView(ViewHolder viewHolder, D item, int position);

    /**
     * Check whether the current item
     *
     * @param item
     * @return
     */
    protected abstract boolean isForViewType(@NonNull D item);

}
