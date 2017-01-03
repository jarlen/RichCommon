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
package cn.jarlen.richcommon.mvp.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;

import cn.jarlen.richcommon.log.Log;
import cn.jarlen.richcommon.mvp.presenter.BaseActivityPresenter;
import cn.jarlen.richcommon.mvp.presenter.IBasePresenter;

/**
 * Created by jarlen on 2016/11/23.
 */
public abstract class BaseMvpActivity<IP extends IBasePresenter, V extends IBaseView> extends FragmentActivity implements IBaseView {

    private BaseActivityPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preBindView();
        setContentView(getLayoutId());
        try {
            presenter = getPresenter().newInstance();
            presenter.setIntent(getIntent());
            presenter.setActivity(this);
            presenter.setView(getProxyView());
            onBindView(savedInstanceState);
            presenter.onBindView(savedInstanceState);
        } catch (InstantiationException e) {
            Log.e("mvp", e.toString());
        } catch (IllegalAccessException e) {
            Log.e("mvp", e.toString());
        } catch (Exception e) {
            Log.e("mvp", e.toString());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (presenter != null) {
            presenter.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (presenter != null) {
            super.onKeyDown(keyCode, event);
            return presenter.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (presenter != null) {
            presenter.onStop();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (presenter != null) {
            presenter.onPause();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (presenter != null) {
            presenter.onStart();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (presenter != null) {
            presenter.onResume();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (presenter != null) {
            presenter.onRestart();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.onDestroy();
            presenter = null;
        }
    }

    protected abstract Class<? extends BaseActivityPresenter> getPresenter();

    protected IP getProxyPresenter() {
        try {
            return (IP) presenter;
        } catch (Exception e) {
            Log.e("mvp", e.toString());
            return null;
        }
    }

    protected abstract V getProxyView();
}
