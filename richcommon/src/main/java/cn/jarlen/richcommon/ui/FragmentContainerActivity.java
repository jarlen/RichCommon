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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import cn.jarlen.richcommon.R;

public class FragmentContainerActivity extends FragmentActivity {

    int ACTIVITY_REULT = RESULT_CANCELED;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String className = getIntent().getStringExtra("className");
        if (TextUtils.isEmpty(className)) {
            finish();
            return;
        }

        setContentView(R.layout.activity_fragment);

        Bundle bundle = getIntent().getBundleExtra("args");

        if (savedInstanceState == null) {
            Fragment f = Fragment.instantiate(this, className, bundle);
            if (f != null)
                FragmentStack.replaceFragment(this,
                        R.id.fragment_container, f);

        } else {
            ACTIVITY_REULT = savedInstanceState.getInt("destoryForReulst", RESULT_CANCELED);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ACTIVITY_REULT = RESULT_CANCELED;
        ActivityStack.unbindReferences(getWindow().getDecorView());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);
        outState.putInt("destoryForReulst", ACTIVITY_REULT);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.anim_right_in, R.anim.anim_right_out);
    }


    public void destoryForReulst(int resultCode) {
        this.ACTIVITY_REULT = resultCode;
    }

    @Override
    public void finish() {
        Intent mIntent = new Intent();
        if (resultBundle != null)
            mIntent.putExtras(resultBundle);
        setResult(ACTIVITY_REULT, mIntent);
        super.finish();
        overridePendingTransition(R.anim.anim_right_in, R.anim.anim_right_out);
    }

    Bundle resultBundle;

    public void setResultBundle(Bundle result) {
        this.resultBundle = result;
    }

    public static void startFragmentActivity(Context context, String className) {
        Intent intent = new Intent(context, FragmentContainerActivity.class);
        intent.putExtra("className", className);
        ((Activity) context).startActivity(intent);
    }

    public static void startFragmentActivityForResult(Context context,
                                                      String className, int requestCode) {
        startFragmentActivityForResult(context, className, null, requestCode);
    }

    public static void startFragmentActivityForResult(Context context,
                                                      String className, Bundle bundle, int requestCode) {
        Intent intent = new Intent(context, FragmentContainerActivity.class);
        intent.putExtra("className", className);
        if (bundle != null)
            intent.putExtra("args", bundle);
        ((Activity) context).startActivityForResult(intent, requestCode);
    }

    public static void startFragmentActivity(Context context, String className,
                                             Bundle args) {
        Intent intent = new Intent(context, FragmentContainerActivity.class);
        intent.putExtra("className", className);
        if (args != null)
            intent.putExtra("args", args);
        ((Activity) context).startActivity(intent);
    }

}
