package com.example.project_text.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.project_text.R;
import com.example.project_text.utile.Logger;
import com.example.project_text.view.LoadingPage;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.List;

public class BaseActivity extends RxAppCompatActivity {
    private String TAG;
    private LoadingPage mLoadingPage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = getClass().getSimpleName();
    }

    protected void addFragment(FragmentManager manager, Class<? extends BaseFragment> aClass, int containerId, Bundle args) {
        String tag = aClass.getName();
        Logger.d("%s add fragment %s", TAG, aClass.getSimpleName());
        Fragment fragment = manager.findFragmentByTag(tag);


        FragmentTransaction transaction = manager.beginTransaction(); // 开启一个事务

        if (fragment == null) {// 没有添加
            try {
                fragment = aClass.newInstance(); // 通过反射 new 出一个 fragment 的实例

                BaseFragment baseFragment = (BaseFragment) fragment; // 强转成我们base fragment

                // 设置 fragment 进入，退出， 弹进，弹出的动画
                transaction.setCustomAnimations(baseFragment.enter(), baseFragment.exit(), baseFragment.popEnter(), baseFragment.popExit());

                transaction.add(containerId, fragment, tag);

                if (baseFragment.isNeedToAddBackStack()) { // 判断是否需要加入回退栈
                    transaction.addToBackStack(tag); // 加入回退栈时制定一个tag，以便在找到指定的事务
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if (fragment.isAdded()) {
                if (fragment.isHidden()) {
                    transaction.show(fragment);
                }
            } else {
                transaction.add(containerId, fragment, tag);
            }
        }

        if (fragment != null) {
            fragment.setArguments(args);
            hideBeforeFragment(manager, transaction, fragment);
            transaction.commit();
        }
    }


    /**
     * 除当前 fragment 以外的所有 fragment 进行隐藏
     *
     * @param manager
     * @param transaction
     * @param currentFragment
     */
    private void hideBeforeFragment(FragmentManager manager, FragmentTransaction transaction, Fragment currentFragment) {

        List<Fragment> fragments = manager.getFragments();

        for (Fragment fragment : fragments) {
            if (fragment != currentFragment && !fragment.isHidden()) {
                transaction.hide(fragment);
            }
        }
    }


    protected void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    protected void showToast(@StringRes int rId) {
        Toast.makeText(this, rId, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onBackPressed() {

        if (mLoadingPage.getParent() != null) { // 如果 loading页面处于显示状态，拦截 back 事件，不让用户取消。

            return;
        }
        super.onBackPressed();

    }

    protected void onError(String msg, LoadingPage.onReloadListener listener) {
        if (mLoadingPage != null && mLoadingPage.getParent() != null) {
            mLoadingPage.onError(msg, listener);
        }
    }

    protected void showLoadingPage(int mode) {
        if (mLoadingPage == null) {
            mLoadingPage = (LoadingPage) LayoutInflater.from(this).inflate(R.layout.loadingpage, null);
        }
        if (mLoadingPage.getParent() == null) {
            ViewGroup viewGroup = findViewById(android.R.id.content);
            viewGroup.addView(mLoadingPage);
        }


        mLoadingPage.show(mode);
    }

    protected void dismissLoadingPage() {
        if (mLoadingPage != null) {
            mLoadingPage.dismiss();
        }
    }
}

