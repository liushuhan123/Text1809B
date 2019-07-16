package com.example.project_text.login;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.project_text.R;
import com.example.project_text.activity.ImgActivity;
import com.example.project_text.base.BaseFragment;
import com.example.project_text.data.entity.User;
import com.example.project_text.utile.MyApp;
import com.example.project_text.view.LoadingPage;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends BaseFragment implements LoginContract.ILoginView {
    private static final int MODE_LOGIN = 1;
    private static final int MODE_REGISTER = -1;

    private LoginContract.ILoginPresenter mPresenter;

    private ImageView mImageView2;
    private EditText mLoginEtUsername;
    private TextInputLayout mLoginIetlUsername;
    private EditText mLoginEtPassword;
    private TextInputLayout mLoginIetlPassword;
    private TextInputEditText mLoginEtRepassword;
    private TextInputLayout mLoginIetlRepassword;
    private Button mLoginBtnSubmit;
    private TextView mLoginTvBottomHint;
    private int mCurrentMode = 0;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPresenter(new LoginPresenter());

    }

    @Override
    public boolean isNeedAnimation() {
        return true;
    }


    @Override
    protected boolean isNeedToAddBackStack() {
        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return initView(inflater.inflate(R.layout.fragment_login, container, false));
    }

    private View initView(View inflate) {

        mImageView2 = inflate.findViewById(R.id.imageView2);
        mLoginEtUsername = inflate.findViewById(R.id.login_et_username);
        mLoginIetlUsername = inflate.findViewById(R.id.login_ietl_username);
        mLoginEtPassword = inflate.findViewById(R.id.login_et_password);
        mLoginIetlPassword = inflate.findViewById(R.id.login_ietl_password);
        mLoginEtRepassword = inflate.findViewById(R.id.login_et_repassword);
        mLoginIetlRepassword = inflate.findViewById(R.id.login_ietl_repassword);
        mLoginBtnSubmit = inflate.findViewById(R.id.login_btn_submit);
        mLoginTvBottomHint = inflate.findViewById(R.id.login_tv_bottom_hint);
        mLoginTvBottomHint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchMode(-mCurrentMode);
            }
        });
        mLoginBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(mLoginEtUsername.getText().toString())){
                    if (mCurrentMode == MODE_LOGIN) {
                        mPresenter.login(getViewText(mLoginEtUsername), getViewText(mLoginEtPassword));
                    } else {
                        mPresenter.register(getViewText(mLoginEtUsername), getViewText(mLoginEtPassword), getViewText(mLoginEtRepassword));
                    }
                    showLoadingPage(LoadingPage.MODE_1);
                }else{
                    Toast.makeText(getContext(), "账号密码不能为空", Toast.LENGTH_SHORT).show();
                }

            }
        });

        mLoginEtPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    mPresenter.login(getViewText(mLoginEtUsername), getViewText(mLoginEtPassword));
                    return true;
                }
                return false;
            }
        });

        mLoginEtRepassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    mPresenter.register(getViewText(mLoginEtUsername), getViewText(mLoginEtPassword), getViewText(mLoginEtRepassword));
                    return true;
                }

                return false;
            }
        });
        mLoginEtRepassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    mPresenter.register(getViewText(mLoginEtUsername), getViewText(mLoginEtPassword), getViewText(mLoginEtRepassword));
                    return true;
                }
                return false;
            }
        });
        switchMode(MODE_LOGIN);
        return inflate;
    }

    private void switchMode(int i) {
        if (mCurrentMode == i) {
            return;
        }
        if (i == MODE_LOGIN) {
            mLoginIetlRepassword.setVisibility(View.GONE);
            mLoginBtnSubmit.setText("登录");
            mLoginTvBottomHint.setText("没有账号?去注册");
            mCurrentMode = MODE_LOGIN;
        } else {
            mLoginBtnSubmit.setText("注册");
            mLoginTvBottomHint.setText("注册完成,去登录");
            mLoginIetlRepassword.setVisibility(View.VISIBLE);
            mCurrentMode = MODE_REGISTER;
        }
    }

    private String getViewText(EditText editText) {
        return editText.getText().toString().trim();
    }

    @Override
    public void onSuccess(User user) {
        dismissLoadingPage();
        startActivity(new Intent(getActivity(), ImgActivity.class));
        MyApp.mIsLogin = true;
        getActivity().finish();

    }

    @Override
    public void onFail(String msg) {
        showToast(msg);
        MyApp.mIsLogin = false;
        dismissLoadingPage();
    }

    @Override
    public void setPresenter(LoginContract.ILoginPresenter presenter) {
        mPresenter = presenter;
        mPresenter.attachView(this);
    }

    @Override
    public Context getContextObject() {

        return getContext();
    }
}
