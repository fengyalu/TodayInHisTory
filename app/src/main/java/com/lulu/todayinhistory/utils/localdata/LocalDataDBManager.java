package com.lulu.todayinhistory.utils.localdata;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.lulu.todayinhistory.bean.localdata.TCollectMsg;
import com.lulu.todayinhistory.bean.localdata.TNotes;
import com.lulu.todayinhistory.dao.DaoMaster;
import com.lulu.todayinhistory.dao.DaoSession;
import com.lulu.todayinhistory.dao.TCollectMsgDao;
import com.lulu.todayinhistory.dao.TNotesDao;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by fyl on 2018/12/3 0003.
 */

public class LocalDataDBManager {
    private static final String TAG = "LocalDataDBManager";
    public static LocalDataDBManager instance;
    private WeakReference<Context> mContext;
    private static final String dbName = "todayinhistroy.db";
    private DaoMaster.OpenHelper openHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private TNotesDao tNotesDao;
    private TCollectMsgDao tCollectMsgDao;

    public LocalDataDBManager(Context context) {
        mContext = new WeakReference<Context>(context);
        openHelper = new DaoMaster.DevOpenHelper(mContext.get(), dbName, null);
        db = openHelper.getReadableDatabase();

        //该数据库连接属于DaoMaster，所以多个Session指的是相同的数据库连接
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
        tNotesDao = mDaoSession.getTNotesDao();
        tCollectMsgDao = mDaoSession.getTCollectMsgDao();
    }

    public static synchronized LocalDataDBManager getInstance(Context context) {
        if (instance == null) {
            instance = new LocalDataDBManager(context);
        }
        return instance;
    }

    /**
     * 查询全部笔记数据
     * @return
     */
    public List<TNotes> queryAllNotes() {
        return tNotesDao.loadAll();
    }

    /**
     * 查询全部收藏数据
     * @return
     */
    public List<TCollectMsg> queryAllCollectMsg() {
        return tCollectMsgDao.loadAll();
    }

    /**
     * 保存一条数据
     * @param tNotes
     * @return
     */
    public boolean saveNotes(final TNotes tNotes) {
        boolean flag = false;
        if (tNotes != null) {
            try {
                mDaoSession.runInTx(new Runnable() {
                    @Override
                    public void run() {
                        tNotesDao.insertOrReplace(tNotes);
                    }
                });
                flag = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return flag;
    }

    /**
     * 删除全部数据库
     * @return
     */
    public boolean deleteAllNotes() {
        boolean flag = false;
        try {
            mDaoSession.runInTx(new Runnable() {
                @Override
                public void run() {
                    tNotesDao.deleteAll();
                }
            });
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 删除一条Notes记录
     * @param tNotes
     * @return
     */
    public boolean deleteTNotes(TNotes tNotes) {
        boolean flag = false;
        try {
            tNotesDao.delete(tNotes);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 根据标题查询
     * @param title
     * @return
     */
    public TNotes queryNotes(String title) {
        TNotes tNotes = tNotesDao.queryBuilder().where(TNotesDao.Properties.Title.eq(title)).unique();
        if (null!=tNotes) {
            return tNotes;
        } else {
            return null;
        }
    }

    /**
     * 保存一条数据
     * @param tCollectMsg
     * @return
     */
    public boolean saveCollectMsg(final TCollectMsg tCollectMsg) {
        boolean flag = false;
        if (tCollectMsg != null) {
            try {
                mDaoSession.runInTx(new Runnable() {
                    @Override
                    public void run() {
                        tCollectMsgDao.insertOrReplace(tCollectMsg);
                    }
                });
                flag = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return flag;
    }


    /**
     * 删除一条CollectMsg记录
     * @param tCollectMsg
     * @return
     */
    public boolean delCollectMsg(TCollectMsg tCollectMsg) {
        boolean flag = false;
        try {
            tCollectMsgDao.delete(tCollectMsg);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
}
