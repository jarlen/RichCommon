package cn.jarlen.richcommon2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import cn.jarlen.richcommon.mvp.view.BaseMvpActivity;

/**
 * Created by jarlen on 2016/11/23.
 */
public class AddActivity extends BaseMvpActivity<AddPresenter, AddView> implements AddView, View.OnClickListener {

    private TextView result;
    private Button testBtn;

    private EditText inputOne;
    private EditText inputTwo;

    @Override
    public Class<AddPresenter> getPresenter() {
        return AddPresenter.class;
    }

    @Override
    public AddView getProxyView() {
        return this;
    }


    @Override
    public int getLayoutId() {
        return R.layout.layout_mvp;
    }

    @Override
    public void onBindView(Bundle savedInstanceState) {
        result = (TextView) findViewById(R.id.result);
        testBtn = (Button) findViewById(R.id.testBtn);
        testBtn.setOnClickListener(this);
        inputOne = (EditText) findViewById(R.id.input_one);
        inputTwo = (EditText) findViewById(R.id.input_two);
    }

    @Override
    public void preBindView() {

    }

    @Override
    public void showAdd(String sum) {
        result.setText(sum);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.testBtn:
                getProxyPresenter().add(inputOne.getText().toString(), inputTwo.getText().toString());
                break;
            default:

                break;
        }
    }
}
