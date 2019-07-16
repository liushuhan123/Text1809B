package com.example.project_text.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.project_text.R;
import com.example.project_text.activity.TounchCallBack;
import com.example.project_text.data.entity.TencentTab;
import java.util.Collections;
import java.util.List;

public class DragAdapter extends RecyclerView.Adapter implements TounchCallBack {

    private List<TencentTab> mTabs;
    private Context mContext;



    public DragAdapter(List<TencentTab> tabs, Context context) {
        mTabs = tabs;
        mContext = context;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_drag, null);
        final MyHolder holder = new MyHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final MyHolder holder1 = (MyHolder) holder;
        holder1.mTv.setText(mTabs.get(position).getName());
    }


    @Override
    public int getItemCount() {
        return mTabs.size();
    }


    @Override
    public void onMoveTounchItem(int fromPosition, int toPosition) {
        Collections.swap(mTabs, fromPosition, toPosition);

        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void DeleteItem(int postition) {
        mTabs.remove(postition);
        notifyItemRemoved(postition);
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        private final TextView mTv;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            mTv = itemView.findViewById(R.id.tv1);
        }
    }


}
