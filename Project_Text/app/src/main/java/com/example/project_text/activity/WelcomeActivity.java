package com.example.project_text.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.example.project_text.R;
import com.example.project_text.base.BaseActivity;
import com.example.project_text.utile.MyApp;
import com.example.project_text.utile.SPUtils;
import com.example.project_text.view.AppConstant;

public class WelcomeActivity extends BaseActivity implements View.OnClickListener {


    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        initView();
    }

    private void initView() {
        mButton = (Button) findViewById(R.id.button);
        mButton.setOnClickListener(this);
        String cookie = SPUtils.getValue(AppConstant.LoginParamsKey.SET_COOKIE_NAME);
        if (!TextUtils.isEmpty(cookie)) {
            MyApp.mIsLogin = true;
        } else {
            MyApp.mIsLogin = false;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.button:
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
