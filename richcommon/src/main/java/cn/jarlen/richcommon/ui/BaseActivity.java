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
package cn.jarlen.richcommon.ui;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

/**
 * DESCRIBE:
 * Created by jarlen on 2016/7/22.
 */
public abstract class BaseActivity extends FragmentActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        preBindView();
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        onBindView();
    }

    protected void startActivity(Class<?> activity, Bundle args){
        Intent intent = new Intent(this,activity);
        if(args != null){
            intent.putExtras(args);
        }
        startActivity(intent);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    protected void startActivityForResult(Class<?> activity, int requestCode, Bundle args){
        Intent intent = new Intent(this,activity);
        if(args != null){
            intent.putExtras(args);
        }
        startActivityForResult(intent,requestCode,args);
    }

    protected abstract int getLayoutId();

    protected abstract void onBindView();

    protected void preBindView(){}
}
