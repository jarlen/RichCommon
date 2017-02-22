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
 * Created by hjl on 2017/2/22.
 */

public interface IMultiManager<D> {

    /**
     * 注册列表item类型
     * @param multiItemView
     */
    void registerMultiView(@NonNull IMultiItemView multiItemView);

    /**
     * 清除列表item类型
     */
    void unRegisterMultiViews();

    /**
     * 获得item类型数
     * @return
     */
    int getViewTypeCount();

    /**
     * 通过业务条件获取item类型
     * @param data
     * @return
     */
    int getItemViewType(D data,int position);

    IMultiItemView getMultiItemForViewType(int viewType);

    View getView(D data, int position, View convertView, ViewGroup parent);
}
