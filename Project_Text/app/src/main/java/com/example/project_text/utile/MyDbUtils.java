package com.example.project_text.utile;

import com.example.project_text.data.entity.DaoMaster;
import com.example.project_text.data.entity.DaoSession;
import com.example.project_text.data.entity.TencentTab;
import com.example.project_text.data.entity.TencentTabDao;

import java.util.List;

public class MyDbUtils {

    private static MyDbUtils sMyHelper;
    private TencentTabDao mDataDao;

    public static MyDbUtils getMyHelper() {
        if (sMyHelper == null) {
            synchronized (MyDbUtils.class) {
                if (sMyHelper == null) {
                    sMyHelper = new MyDbUtils();
                }
            }
        }
        return sMyHelper;
    }

    MyDbUtils() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(MyApp.getmApplicationContext(), "b.db");
        DaoMaster daoMaster = new DaoMaster(helper.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        mDataDao = daoSession.getTencentTabDao();
    }

    //插入数据
    public void insert(List<TencentTab> list) {
        mDataDao.insertInTx(list);
    }

    //删除数据
    public void delete(TencentTab data) {
        mDataDao.delete(data);
    }

    //修改数据
    public void update(TencentTab data) {
        mDataDao.update(data);
    }

    //查找ID
    public Long idid(TencentTab data) {
        return mDataDao.getKey(data);
    }

    //查询
    public List<TencentTab> select() {
        return mDataDao.queryBuilder().list();
    }

    //分页查询
    public List<TencentTab> selectPage(int page, int count) {
        return mDataDao.queryBuilder().offset(page * count).limit(count).list();
    }
}
