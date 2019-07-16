package com.example.project_text.fragment.home;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mylibrary.JBanner;
import com.example.mylibrary.JBannerAdapter;
import com.example.project_text.R;
import com.example.project_text.data.entity.ArticleData;
import com.example.project_text.data.entity.Banner;
import com.example.project_text.view.GlideApp;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;


public class HomeArticleAdapter extends RecyclerView.Adapter {

    private static final int TYPE_BANNER = 0x100;
    private static final int TYPE_ARTICLE= 0X101;
    private static final int TYPE_ARTICLE_PIC = 0X102;

    private List<Banner> mBannerList;

    private List<ArticleData.Article> mTopArticleList;
    private List<ArticleData.Article> mArticleList;




    public void setBanner(List<Banner> banners){
        mBannerList = banners;
    }

    public void setTopArticleList(List<ArticleData.Article > topArticleList){
        mTopArticleList = topArticleList;
    }

    public void setArticle(List<ArticleData.Article> list){
        mArticleList = list;
    }

    /**
     * 加载更多的时候，往list 里面追加
     * @param list
     */
    public void addArticle(List<ArticleData.Article> list){
        int start = mArticleList.size();
        mArticleList.addAll(list);
        notifyItemRangeChanged(start, list.size());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Class<? extends RecyclerView.ViewHolder> aClass = null;
        int id = 0;
        if(viewType == TYPE_BANNER){
            aClass = BannerHolder.class;
            id = R.layout.item_home_top_banner;
        }else if(viewType == TYPE_ARTICLE ){
            aClass = ArticleHolder.class;
            id = R.layout.item_home_article;
        }else{
            aClass = ArticleForPicHolder.class;
            id = R.layout.item_home_article_pic;
        }

        try {
            Constructor<? extends RecyclerView.ViewHolder> constructor =  aClass.getConstructor(HomeArticleAdapter.class,View.class);
            return  constructor.newInstance(this,LayoutInflater.from(parent.getContext()).inflate(id, parent, false));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int type  = getItemViewType(position);

        if(type == TYPE_BANNER){
            ((BannerHolder)holder).setData(mBannerList);
        }else if(type == TYPE_ARTICLE){
            if(hasBanner()){
                ((ArticleHolder)holder).setData(mArticleList.get(--position));
            }else{
                ((ArticleHolder)holder).setData(mArticleList.get(position));
            }
        }else if(type == TYPE_ARTICLE_PIC){
            if(hasBanner()){
                ((ArticleForPicHolder)holder).setData(mArticleList.get(--position));
            }else{
                ((ArticleForPicHolder)holder).setData(mArticleList.get(position));
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0 && hasBanner()){
            return TYPE_BANNER;
        }

        int newPosition;

        if(hasBanner()){
            newPosition = position -1;
        }else{
            newPosition = position;
        }

        if(TextUtils.isEmpty(mArticleList.get(newPosition).getEnvelopePic())){
            return TYPE_ARTICLE;
        }else{
            return TYPE_ARTICLE_PIC;
        }

    }


    private boolean hasBanner(){
        return mBannerList != null && mBannerList.size() > 0;
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if(hasBanner() ){
            count++;
        }
        if(mArticleList != null && mArticleList.size() > 0){
            count = count + mArticleList.size();
        }

        // return mArticleList == null ? count : mArticleList.size() + count;

        return count;
    }


    private class BannerHolder extends RecyclerView.ViewHolder{

        private JBanner mJBanner;
        public BannerHolder(@NonNull View itemView) {
            super(itemView);
            mJBanner = itemView.findViewById(R.id.home_item_banner);
        }

        private void setData(List<Banner> banners){
                mJBanner.setLoop(banners.size() > 0);
                ArrayList<String> titles = new ArrayList<>();
                for(Banner banner : banners){
                    titles.add(banner.getTitle());
                }
                mJBanner.setDatas(banners,titles);

                mJBanner.setAdapter(new JBannerAdapter<Banner>() {
                    @Override
                    public void fillBannerItemData(JBanner banner, ImageView imageView, Banner mode, int position) {
                        GlideApp.with(banner).load(mode.getImagePath()).into(imageView);
                    }
                });
        }
    }

    private class ArticleHolder extends RecyclerView.ViewHolder{

        private TextView mTvTop;
        private TextView mTvRefresh;
        private TextView mTvTag;
        private TextView mTvAuthor;
        private TextView mTvDate;
        private TextView mTvTitle;
        private TextView mTvChapterName;
        private CheckBox mCbLike;

        public ArticleHolder(@NonNull View itemView) {
            super(itemView);
            mTvTop = itemView.findViewById(R.id.home_item_tv_top);
            mTvRefresh = itemView.findViewById(R.id.home_item_tv_refesh);
            mTvTag = itemView.findViewById(R.id.home_item_tv_tag);
            mTvAuthor = itemView.findViewById(R.id.home_item_tv_author);
            mTvDate = itemView.findViewById(R.id.home_item_tv_date);
            mTvTitle = itemView.findViewById(R.id.home_item_tv_title);

            mTvChapterName = itemView.findViewById(R.id.home_item_tv_chapter_name);

            mCbLike = itemView.findViewById(R.id.home_item_cb_like);
        }

        public void setData(ArticleData.Article data){
            if(data.isTop()){
                mTvTop.setVisibility(View.VISIBLE);
            }else{
                mTvTop.setVisibility(View.GONE);
            }

            if(data.isFresh()){
                mTvRefresh.setVisibility(View.VISIBLE);
            }else{
                mTvRefresh.setVisibility(View.GONE);
            }

            if(data.getTags() != null && data.getTags().size() > 0){
                mTvTag.setVisibility(View.VISIBLE);
                mTvTag.setText(data.getTags().get(0).getName());
            }else{
                mTvTag.setVisibility(View.GONE);
            }

            mTvAuthor.setText(data.getAuthor());

            mTvDate.setText(data.getNiceDate());

            mTvTitle.setText(data.getTitle());

            mTvChapterName.setText(itemView.getContext().getResources().getString(R.string.home_item_chapter_name,data.getSuperChapterName(),data.getChapterName()));

            mCbLike.setChecked(data.isCollect());

        }
    }

    private class ArticleForPicHolder extends ArticleHolder{

        private ImageView mIvPic;

        public ArticleForPicHolder(@NonNull View itemView) {
            super(itemView);
            mIvPic  = itemView.findViewById(R.id.home_item_iv_pic);
        }

        @Override
        public void setData(ArticleData.Article data) {
            super.setData(data);
            Glide.with(itemView).load(data.getEnvelopePic()).into(mIvPic);
        }
    }
}
