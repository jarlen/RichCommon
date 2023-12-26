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

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;

import cn.jarlen.richcommon.adapter.ViewHolder;

/**
 * Created by jarlen on 2016/9/4.
 */

public interface IMultiItemView<D> {

    /**
     * get resource of this item's layout
     *
     * @return
     */
    @LayoutRes
    int getLayoutResId();

    /**
     * Update the current item data
     *
     * @param viewHolder
     * @param data
     * @param position
     */
    void updateView(ViewHolder viewHolder, D data, int position);

    /**
     * Check whether the current item
     *
     * @param data     data
     * @param position
     * @return
     */
    boolean isForViewType(D data, int position);

    /**
     * Get current reuse component
     *
     * @param parent
     * @param convertView
     * @param position
     * @return
     */
    ViewHolder getViewHolder(ViewGroup parent, View convertView, int position);
}
