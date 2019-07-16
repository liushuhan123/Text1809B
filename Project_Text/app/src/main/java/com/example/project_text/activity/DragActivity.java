package com.example.project_text.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_text.R;
import com.example.project_text.adapter.DragAdapter;
import com.example.project_text.adapter.MyDrag;
import com.example.project_text.data.entity.TencentTab;
import com.example.project_text.utile.MyDbUtils;

import java.util.List;

public class DragActivity extends AppCompatActivity {

    private static final String TAG = DragActivity.class.getName();
    private RecyclerView mDarRec;
    private List<TencentTab> mSelect;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag);
        mSelect = MyDbUtils.getMyHelper().select();
        initView();
        Log.d(TAG, "onCreate: " + mSelect.size());



    }

    private void initView() {
        mDarRec = (RecyclerView) findViewById(R.id.dar_rec);
        mToolbar = (Toolbar) findViewById(R.id.dra_too);


        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        mDarRec.setLayoutManager(new GridLayoutManager(this, 3));
        final DragAdapter adapter = new DragAdapter(mSelect, this);
        mDarRec.setAdapter(adapter);


        final MyDrag drag = new MyDrag(adapter);
        final ItemTouchHelper helper = new ItemTouchHelper(drag);
        helper.attachToRecyclerView(mDarRec);


    }
}
