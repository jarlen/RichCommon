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

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

/**
 * manager of list item type
 * Created by jarlen on 2016/9/4.
 */

public interface IMultiManager<D> {

    /**
     * Register list item type
     *
     * @param multiItemView
     * item type
     */
    void registerMultiView(@NonNull IMultiItemView multiItemView);

    /**
     * Clear list item type
     */
    void unRegisterMultiViews();

    /**
     * Get the number of item types
     * @return
     */
    int getViewTypeCount();

    /**
     * Get item type by business condition
     *
     * @param data
     * data
     * @return
     */
    int getItemViewType(D data, int position);

    /**
     * Get the current item type by item layout resource ID
     * @param resId
     * resource ID
     * @return
     */
    IMultiItemView getMultiItemForResId(int resId);

    /**
     * updata view
     * @param data
     * data
     * @param position
     * position of updating the current item
     * @param convertView
     * the current item view
     * @param parent
     * parent view of the current item
     * @return
     */
    View getView(D data, int position, View convertView, ViewGroup parent);
}
