package com.example.project_text.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_text.activity.TounchCallBack;

public class MyDrag extends ItemTouchHelper.Callback {
    TounchCallBack mTounchCallBack;

    public MyDrag(TounchCallBack tounchCallBack) {
        mTounchCallBack = tounchCallBack;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        int move = ItemTouchHelper.UP | ItemTouchHelper.DOWN;

        int remover = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;

        return makeMovementFlags(move, remover);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        mTounchCallBack.onMoveTounchItem(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

    }
}
