package com.moseeker.candidate.service.pos;

/**
 * 数据库模型
 * 实例化之后不要改动updateToDB,或者saveToDB的
 * Created by jack on 13/02/2017.
 */
public abstract class Model {

    protected volatile boolean persist;           //是否持久化
    protected volatile boolean updatedFlag;       //是否更新
    protected volatile boolean exist;           //数据库是否存在该条记录

    /**
     * 将数据库对应的数据加载到类本身
     */
    public abstract void loadFromDB();

    /**
     *将修改的结果保存到数据库中
     */
    public abstract void updateToDB();

    /**
     * 将结果添加到数据库中
     */
    public abstract void saveToDB();

    /**
     * 从数据库中删除
     */
    public abstract void deleteFromDB();

    /**
     * 如果已经实例化了，那么
     */
    public void upsertToDB() {
        if(persist) {
            updateToDB();
        } else {
            saveToDB();
        }
    }

    /**
     * 数据库中是否存在。
     * @return
     */
    public boolean dbExist() {
        if(!persist) {
            loadFromDB();
        }
        return exist;
    }
}
