package com.example.mylibrary;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.Scroller;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class JBanner extends ConstraintLayout {

    private ViewPager mViewPager;

    private TextView mTextView;

    private JBannerIndicator mIndicator;

    private ImageView mImageView;

    private int mInt = 100;

    private List<? extends Object> mDatas = new ArrayList<>();

    private List<String> mTitles = new ArrayList<>();

    private JBannerAdapter mAdapter;

    private JBannerIndicator mJBannerIndicator;

    private boolean loop;

    private boolean mIsManualScroll;

    // 自动轮播间隔时间，默认为一秒
    private int mInterval = 1000;

    //设置是否轮播
    public void setLoop(boolean loop) {
        this.loop = loop;
    }


    public JBanner(Context context) {
        super(context);
        initView();
    }

    public JBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public JBanner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();

    }

    private void initView() {


        //viewpager
        mViewPager = new ViewPager(getContext());
        mViewPager.setId(mInt++);


        setViewPagerScroller();
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                // 页面被选中时更新title，更新指示器
                mTextView.setText(getTitleByPosition(position % mDatas.size()));
                mIndicator.setCurrentIndex(position % mDatas.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        ConstraintSet set = new ConstraintSet();
        set.clone(this);
        set.connect(mViewPager.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT);
        set.connect(mViewPager.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT);
        set.connect(mViewPager.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
        set.connect(mViewPager.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
        set.constrainHeight(mViewPager.getId(), ConstraintSet.MATCH_CONSTRAINT);
        set.constrainWidth(mViewPager.getId(), ConstraintSet.MATCH_CONSTRAINT);
        addView(mViewPager);


        //背景
        mImageView = new ImageView(getContext());
        mImageView.setId(mInt++);
        mImageView.setBackgroundColor(Color.parseColor("#40000000"));
        set.connect(mImageView.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT);
        set.connect(mImageView.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
        set.connect(mImageView.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT);
        set.constrainWidth(mImageView.getId(), ConstraintSet.MATCH_CONSTRAINT);
        set.constrainHeight(mImageView.getId(), JBannerUtil.dp2px(getContext(), 33));
        addView(mImageView);


        //指针
        mIndicator = new JBannerIndicator(getContext());
        mIndicator.setId(mInt++);

        set.connect(mIndicator.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT);
        set.connect(mIndicator.getId(), ConstraintSet.BOTTOM, mImageView.getId(), ConstraintSet.BOTTOM);
        set.connect(mIndicator.getId(), ConstraintSet.TOP, mImageView.getId(), ConstraintSet.TOP);

        set.constrainWidth(mIndicator.getId(), ConstraintSet.WRAP_CONTENT);
        set.constrainHeight(mIndicator.getId(), ConstraintSet.WRAP_CONTENT);
        addView(mIndicator);


        //文本
        mTextView = new TextView(getContext());
        mTextView.setId(mInt++);
        mTextView.setTextColor(Color.WHITE);
        mTextView.setMaxLines(1);
        mTextView.setEllipsize(TextUtils.TruncateAt.END);

        set.connect(mTextView.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT);
        set.connect(mTextView.getId(), ConstraintSet.RIGHT, mIndicator.getId(), ConstraintSet.LEFT);
        set.connect(mTextView.getId(), ConstraintSet.TOP, mImageView.getId(), ConstraintSet.TOP);
        set.connect(mTextView.getId(), ConstraintSet.BOTTOM, mImageView.getId(), ConstraintSet.BOTTOM);

        set.constrainHeight(mTextView.getId(), ConstraintSet.WRAP_CONTENT);
        set.constrainWidth(mTextView.getId(), ConstraintSet.MATCH_CONSTRAINT);
        addView(mTextView);
        set.applyTo(this);
    }

    public void setAdapter(JBannerAdapter adapter1) {
        mAdapter = adapter1;
        mViewPager.setAdapter(new BannerAdapter());

        if (mDatas != null && mDatas.size() > 1) {
            int i = Integer.MAX_VALUE / 2;
            int j = i % mDatas.size();
            if (j != 0) {
                i = (mDatas.size() - j) + i;
            }
            mViewPager.setCurrentItem(i);
        }
        if (loop) {
            mViewPager.postDelayed(mLooper, 3000);
        }
    }

    private Runnable mLooper = new Runnable() {
        @Override
        public void run() {

            int cr = mViewPager.getCurrentItem();

            mViewPager.setCurrentItem(++cr); // 每次切换一页page

            mViewPager.postDelayed(this, 3000); //继续下一次轮播

        }
    };

    //设置轮播图数据和title
    public void setDatas(List<? extends Object> datas, List<String> titles) {

        //判断如果没不为空
        if (datas != null && titles != null) {
            //两个list，都有值的情况下，如果他们不相等，那就抛异常，
            if (datas.size() != titles.size()) {
                throw new IllegalArgumentException("ata size not equals title size");
            }

        }

        ///设置指示器的个数
        mIndicator.setIndicatorCount(datas == null ? 0 : datas.size());
        mDatas = datas;
        mTitles = titles;
    }
    /**
     * 通过position 获取title
     *
     * @param position viewpager 中的当前页的position
     * @return 如果有title 返回，没有title 返回空串
     */
    private String getTitleByPosition(int position) {
        if (mTitles == null || mTitles.size() == 0) {
            return "";
        }

        return mTitles.get(position);
    }

    /**
     * 通过反射修改 viewpager 自动切换下一页的时间，因为默认的时间只有200ms,太短
     */
    private void setViewPagerScroller() {

        try {
            Field scrollerField = ViewPager.class.getDeclaredField("mScroller");

            scrollerField.setAccessible(true);

            Field interpolator = ViewPager.class.getDeclaredField("sInterpolator");


            interpolator.setAccessible(true);

            Scroller scroller = new Scroller(getContext(), (Interpolator) interpolator.get(null)) {
                @Override
                public void startScroll(int startX, int startY, int dx, int dy, int duration) {
                    int newDuration;
                    // 如果这次是手滑，那么剩下的那一部分滑动的时间我们不修改。就用默认的。
                    if (mIsManualScroll) {
                        newDuration = duration;
                    } else {
                        // 自动切换时，修改默认切换的时间
                        newDuration = mInterval;
                    }
                    super.startScroll(startX, startY, dx, dy, newDuration);    // 这里是关键，将duration变长或变短
                }
            };
            scrollerField.set(mViewPager, scroller);
        } catch (NoSuchFieldException e) {
            // Do nothing.
        } catch (IllegalAccessException e) {
            // Do nothing.
        }
    }

    public class BannerAdapter extends PagerAdapter {


        @Override
        public int getCount() {
            return mDatas == null ? 0 : Integer.MAX_VALUE;
        }


        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {

            ImageView imageView = new ImageView(container.getContext());
            //宽高
            imageView.setLayoutParams(new ViewPager.LayoutParams());
            //样式
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            //如果不为空就去执行
            if (mAdapter != null) {
                mAdapter.fillBannerItemData(JBanner.this, imageView, mDatas.get(position % mDatas.size()), position % mDatas.size());
            }

            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }
    }
}
