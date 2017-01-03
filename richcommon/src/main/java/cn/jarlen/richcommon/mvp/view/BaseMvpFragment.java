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
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.jarlen.richcommon.log.Log;
import cn.jarlen.richcommon.mvp.presenter.BaseFragmentPresenter;
import cn.jarlen.richcommon.mvp.presenter.IBasePresenter;

/**
 * View(Fragment) of MVP
 * Created by jarlen on 2016/11/23.
 */
public abstract class BaseMvpFragment<IP extends IBasePresenter, V extends IBaseView> extends Fragment implements IBaseView {

    private View rootView = null;
    private BaseFragmentPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(getLayoutId(), null);
        preBindView();
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            presenter = getPresenter().newInstance();
            presenter.setActivity(getActivity());
            presenter.setView(getProxyView());
            presenter.setArguments(getArguments());
            presenter.onCreated();
            onBindView(savedInstanceState);
            presenter.onBindView(savedInstanceState);
        } catch (java.lang.InstantiationException e) {
            Log.e("mvp", e.toString());
        } catch (IllegalAccessException e) {
            Log.e("mvp", e.toString());
        } catch (Exception e) {
            Log.e("mvp", e.toString());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (presenter != null) {
            presenter.onDestroyView();
            presenter = null;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (presenter != null) {
            presenter.onStart();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (presenter != null) {
            presenter.onPause();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (presenter != null) {
            presenter.onResume();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (presenter != null) {
            presenter.onStop();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.onDestroy();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (presenter != null) {
            presenter.onDetach();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (presenter != null) {
            presenter.onActivityResult(requestCode, resultCode, data);
        }
    }

    protected abstract Class<? extends BaseFragmentPresenter> getPresenter();

    protected IP getProxyPresenter() {
        try {
            return (IP) presenter;
        } catch (Exception e) {
            Log.e("mvp", e.toString());
            return null;
        }
    }

    protected abstract V getProxyView();

    protected View getRootView() {
        return rootView;
    }
}
