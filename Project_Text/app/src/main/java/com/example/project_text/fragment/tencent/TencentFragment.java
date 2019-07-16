package com.example.project_text.fragment.tencent;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.project_text.R;
import com.example.project_text.activity.DragActivity;
import com.example.project_text.activity.MainActivity;
import com.example.project_text.base.BaseFragment;
import com.example.project_text.data.entity.ArticleData;
import com.example.project_text.data.entity.TencentTab;
import com.example.project_text.fragment.home.HomeRepository;
import com.google.android.material.tabs.TabLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TencentFragment extends BaseFragment implements TencentContract.ITencentView {


    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private TencentContract.ITencentPresnter mITencentPresnter;
    private ImageView mTp;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPresenter(new TencentPresenter());

        mITencentPresnter.getTab(TencentRepository.TencentLoadType.LOAD_TYPE_LOAD);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_tencent, container, false);
        mTabLayout = view.findViewById(R.id.ten_tab);
        mViewPager = view.findViewById(R.id.ten_vp);
        mTp = view.findViewById(R.id.iv);


        return view;
    }

    @Override
    public void TopTencent(final List<TencentTab> tabs, String msg) {
        Log.d("TencentFragment", "TopTencent: " + tabs.size());
        List<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < tabs.size(); i++) {
            fragments.add(new BlankFragment().newInstance(tabs.get(i).getId()));

        }
        TenVpAdapter adapter = new TenVpAdapter(getChildFragmentManager(), tabs, fragments);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity activity = (MainActivity) getActivity();
                Intent intent = new Intent(activity, DragActivity.class);
                startActivity(intent);


            }
        });

    }

    @Override
    public void ListTencent(ArticleData articleData, String msg) {

    }

    @Override
    public void setPresenter(TencentContract.ITencentPresnter presenter) {
        mITencentPresnter = presenter;
        mITencentPresnter.attachView(this);
    }

    @Override
    public Context getContextObject() {
        return null;
    }
}
