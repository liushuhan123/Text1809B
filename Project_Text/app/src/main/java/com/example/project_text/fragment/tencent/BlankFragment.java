package com.example.project_text.fragment.tencent;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.project_text.R;
import com.example.project_text.base.BaseFragment;
import com.example.project_text.data.entity.ArticleData;
import com.example.project_text.data.entity.TencentTab;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends BaseFragment implements TencentContract.ITencentView {

    private int page = 0;
    private TencentContract.ITencentPresnter mITencentPresnter;
    private RecyclerView mRecyclerView;

    public static BlankFragment newInstance(int id) {
        Bundle args = new Bundle();
        args.putInt("id", id);
        BlankFragment fragment = new BlankFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_blank, container, false);
        mRecyclerView = inflate.findViewById(R.id.blan_rec);
        final Bundle bundle = getArguments();
        int id = bundle.getInt("id");

        setPresenter(new TencentPresenter());
        mITencentPresnter.getList(TencentRepository.TencentLoadType.LOAD_TYPE_LOAD, id, page);
        return inflate;
    }

    @Override
    public void TopTencent(List<TencentTab> tabs, String msg) {

    }
    @Override
    public void ListTencent(ArticleData articleData, String msg) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
//        final TcAdapter adapter = new TcAdapter(articleData, getContext());
//        mRecyclerView.setAdapter(adapter);
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
