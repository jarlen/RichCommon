package cn.jarlen.richcommon.adapter.multiple;

import android.support.annotation.NonNull;
import android.support.v4.util.SparseArrayCompat;

/**
 * DESCRIBE:
 * Created by hjl on 2017/2/20.
 */

public class MultiItemManager<T> {

    protected SparseArrayCompat<IRvMultiItemView<T>> multiItemViews = new SparseArrayCompat();


    public MultiItemManager<T> addMultiItemView(@NonNull IRvMultiItemView<T> multiItemView) {
        // algorithm could be improved since there could be holes,
        // but it's very unlikely that we reach Integer.MAX_VALUE and run out of unused indexes
        int viewType = multiItemViews.size();
        while (multiItemViews.get(viewType) != null) {
            viewType++;
        }
        return addMultiItemView(viewType, false, multiItemView);
    }

    public MultiItemManager<T> addMultiItemView(int viewType, boolean allowReplacingaItemView,
                                                @NonNull IRvMultiItemView<T> multiItemView) {
        if (multiItemView == null) {
            throw new NullPointerException("multiItemView is null!");
        }

        if (!allowReplacingaItemView && multiItemViews.get(viewType) != null) {
            throw new IllegalArgumentException(
                    "An multiItemView is already registered for the viewType = "
                            + viewType
                            + ". Already registered multiItemView is "
                            + multiItemViews.get(viewType));
        }

        multiItemViews.put(viewType, multiItemView);
        return this;
    }



}
