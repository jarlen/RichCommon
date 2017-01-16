package cn.jarlen.richcommon2;

import cn.jarlen.richcommon.mvp.presenter.BaseActivityPresenter;

/**
 * Created by jarlen on 2016/11/23.
 */
public class AddPresenter extends BaseActivityPresenter<IAddView> implements IAdd{

    @Override
    public void add(String a, String b) {
        int sum = Integer.valueOf(a) + Integer.valueOf(b);
        getProxyView().showAdd(""+sum);
    }
}
