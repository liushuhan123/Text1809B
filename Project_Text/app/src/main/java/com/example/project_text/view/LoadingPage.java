package com.example.project_text.view;

import android.content.Context;
import android.graphics.Color;

import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;

import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;

import com.cunoraz.gifview.library.GifView;
import com.example.project_text.R;

public class LoadingPage extends ConstraintLayout {
    public static final int MODE_1 = 1;
    public static final int MODE_2 = 2;

    private Button mReload;
    private int mMode;
    private TextView mTv;
    private Group error;

    private GifView loading;
    private LinearLayout mLoadingLayout;

    public LoadingPage(Context context) {
        super(context);
    }

    public LoadingPage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LoadingPage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initView();
    }

    private void initView() {

        mReload = findViewById(R.id.loading_btn_reload);
        error = findViewById(R.id.loading_group_error);
        mTv = findViewById(R.id.loading_tv_msg);
         loading = findViewById(R.id.loading_gif_view);
        mLoadingLayout = findViewById(R.id.loading_layout);


    }

    public void show(int mode) {
        if (mode == MODE_1) {
            setBackgroundColor(Color.parseColor("#00000000"));
        } else {
            setBackgroundColor(Color.parseColor("#FFFFFFFF"));
        }
        error.setVisibility(View.GONE);
        mLoadingLayout.setVisibility(View.VISIBLE);
      loading.play();
    }

    public void onError(String msg) {
        error.setVisibility(View.VISIBLE);
        mLoadingLayout.setVisibility(View.GONE);
        mReload.setVisibility(GONE);
        mReload.setOnClickListener(null);
        mTv.setText(msg);
    }

    public void onError(String msg, final onReloadListener listener) {
        error.setVisibility(View.VISIBLE);
        mLoadingLayout.setVisibility(View.GONE);
        mReload.setVisibility(VISIBLE);
        mReload.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    show(MODE_2);
                    listener.reload();
                }
            }
        });
        mReload.requestFocus();
        mTv.setText(msg);
    }

    public void dismiss() {
     loading.pause();
        mReload.setOnClickListener(null);
        ViewGroup parent = (ViewGroup) getParent();
        if (parent != null) {
            parent.removeView(this);
        }
    }
    public interface onReloadListener{
        void reload();
    }
}
