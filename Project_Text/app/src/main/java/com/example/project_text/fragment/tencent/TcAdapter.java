package com.example.project_text.fragment.tencent;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.project_text.R;
import com.example.project_text.data.entity.ArticleData;


public class TcAdapter extends RecyclerView.Adapter {
    private ArticleData mBeans;
    private Context mContext;

    public TcAdapter(ArticleData beans, Context context) {
        mBeans = beans;
        mContext = context;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final View inflate = LayoutInflater.from(mContext).inflate(R.layout.layout_blank, null);
        final ViewHolder holder = new ViewHolder(inflate);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        final ViewHolder holder = (ViewHolder) viewHolder;

        holder.mAuthor.setText(mBeans.getDatas().get(i).getAuthor());
        holder.mTime.setText(mBeans.getDatas().get(i).getNiceDate());
        holder.mTitle.setText(mBeans.getDatas().get(i).getTitle());
        holder.mCom.setText(mBeans.getDatas().get(i).getAuthor() + "" + mBeans.getDatas().get(i).getSuperChapterName());
        holder.mTp.setChecked(false);



    }

    @Override
    public int getItemCount() {
        return mBeans.getDatas().size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {


        private final TextView mAuthor;
        private final TextView mTime;
        private final TextView mTitle;
        private final TextView mCom;
        private final CheckBox mTp;

        ViewHolder(View view) {
            super(view);
            mAuthor = view.findViewById(R.id.tc_author);
            mTime = view.findViewById(R.id.tc_time);
            mTitle = view.findViewById(R.id.tc_title);
            mCom = view.findViewById(R.id.tc_con);
            mTp = view.findViewById(R.id.tc_tp);
        }
    }


}
