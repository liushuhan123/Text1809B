package com.example.project_text.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import androidx.constraintlayout.widget.ConstraintLayout;

public class ButtonNavigationView extends ConstraintLayout implements CompoundButton.OnCheckedChangeListener {
    private OnTabCheckedChangedListener mChangedListener;

    public ButtonNavigationView(Context context) {
        super(context);
    }

    public ButtonNavigationView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ButtonNavigationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
//    也就是会在Activity中调用setContentView之后就会调用onFinishInflate这个方法，
//    这个方法就代表自定义控件中的子控件映射完成了，然后可以进行一些初始化控件的操作
//
//
//    比如： 可以通过findViewById 得到控件，得到控件之后进行一些初始化的操作
//    （既然能够得到控件你就可以，随心所欲了撒），就像在Activity中的onCreate方法里面一样，
//    当然在这个方法里面是得不到控件的高宽的，控件的高宽是必须在调用了onMeasure方法之后才能得到，
//    而onFinishInflate方法是在setContentView之后、onMeasure之前

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        View button;
        for (int i = 0; i < getChildCount(); i++) {
            button = getChildAt(i);
            if (button instanceof RadioButton) {
                ((RadioButton) getChildAt(i)).setOnCheckedChangeListener(this);
            }
        }


    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) { // 我们只关注 由未选中变为选中的情况，由选中变为未选中的情况我们不关注
            unCheckOtherButton(buttonView);

            if (mChangedListener != null) {
                mChangedListener.onCheckedChanged(buttonView, false);
            }
        }
    }

    private void unCheckOtherButton(CompoundButton buttonView) {
        View button;
        for (int i = 0; i < getChildCount(); i++) {
            button = getChildAt(i);
            if (button instanceof RadioButton && button != buttonView) {
                ((RadioButton) button).setChecked(false);
            }
        }
    }

    public void setOnTabChangedListener(OnTabCheckedChangedListener mChangedListener) {
        this.mChangedListener = mChangedListener;
    }

    public interface OnTabCheckedChangedListener {
        void onCheckedChanged(CompoundButton buttonView, boolean isChecked);
    }

}
