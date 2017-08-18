package com.moseeker.useraccounts.service.thirdpartyaccount;

import com.moseeker.baseorm.dao.hrdb.HRThirdPartyAccountDao;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyAccountDO;
import javafx.scene.control.SpinnerValueFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by zhangdi on 2017/6/16.
 */

@Service
public class ThirdPartyAccountSynctor {

    static Logger logger = LoggerFactory.getLogger(ThirdPartyAccountSynctor.class);


    @Autowired
    private HRThirdPartyAccountDao hrThirdPartyAccountDao;

    @Autowired
    BindTask bindTask;

    @Autowired
    SyncTask syncTask;

    /**
     * 异步的执行任务的方式
     */
    class AsyncRunnable implements Runnable {
        ExecuteTask executeTask;
        HrThirdPartyAccountDO hrThirdPartyAccount;
        Map<String, String> extras;

        AsyncRunnable(ExecuteTask executeTask, HrThirdPartyAccountDO hrThirdPartyAccount, Map<String, String> extras) {
            this.executeTask = executeTask;
            this.hrThirdPartyAccount = hrThirdPartyAccount;
            this.extras = extras;
        }

        @Override
        public void run() {
            try {
                hrThirdPartyAccount = executeTask.execute(hrThirdPartyAccount, extras);
                if (hrThirdPartyAccount.getBinding() > 100) {
                    logger.error("绑定时发现错误的返回码:{}", hrThirdPartyAccount.getBinding());
                    hrThirdPartyAccount.setBinding((short) executeTask.getErrorCode(null));
                }
                //保存到数据库
                bindTask.updateThirdPartyAccount(hrThirdPartyAccount, extras);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error(e.getMessage(), e);
                //系统的异常，异步的情况下发送邮件
                hrThirdPartyAccount.setBinding((short) executeTask.getErrorCode(e));
                hrThirdPartyAccount.setErrorMessage("系统异常，请重试");
                executeTask.updateThirdPartyAccount(hrThirdPartyAccount, extras);
            }
        }
    }


    /**
     * 绑定第三方帐号
     *
     * @param hrId
     * @param thirdPartyAccount
     * @param sync
     * @return
     * @throws Exception
     */
    public HrThirdPartyAccountDO bindThirdPartyAccount(int hrId, HrThirdPartyAccountDO thirdPartyAccount, Map<String, String> extras, boolean sync) throws Exception {
        return sync ? syncWithBindThirdPartyAccount(hrId, thirdPartyAccount, extras) : asyncWithBindThirdPartyAccount(hrId, thirdPartyAccount, extras);
    }

    /**
     * 同步第三方帐号的可发布数等
     *
     * @param thirdPartyAccount
     * @return
     * @throws Exception
     */
    public HrThirdPartyAccountDO syncThirdPartyAccount(HrThirdPartyAccountDO thirdPartyAccount, Map<String, String> extras, boolean sync) throws Exception {
        return sync ? syncWithSyncThirdPartyAccount(thirdPartyAccount, extras) : asyncWithSyncThirdPartyAccount(thirdPartyAccount, extras);
    }

    /**
     * 猎聘确认发送短信验证码
     */
    public HrThirdPartyAccountDO bindConfirm(HrThirdPartyAccountDO thirdPartyAccount, Map<String, String> extras, boolean confirm) throws Exception {
        return bindTask.bindConfirm(thirdPartyAccount, extras, confirm);
    }

    /**
     * 猎聘绑定确认发送验证码
     *
     * @return
     */
    public HrThirdPartyAccountDO bindMessage(HrThirdPartyAccountDO thirdPartyAccount, Map<String, String> extras, String code) throws Exception {
        thirdPartyAccount = bindTask.bindMessage(thirdPartyAccount, extras, code);
        if (thirdPartyAccount.getBinding() == 6 || thirdPartyAccount.getBinding() == 1) {
            hrThirdPartyAccountDao.updateData(thirdPartyAccount);
        }

        return thirdPartyAccount;
    }

    /**
     * 使用同步的方式同步第三方账号
     *
     * @param hrId
     * @param thirdPartyAccount
     * @return
     */
    private HrThirdPartyAccountDO syncWithBindThirdPartyAccount(int hrId, HrThirdPartyAccountDO thirdPartyAccount, Map<String, String> extras) throws Exception {

        if (thirdPartyAccount.getId() == 0) {
            thirdPartyAccount = hrThirdPartyAccountDao.addData(thirdPartyAccount);
        }

        thirdPartyAccount = bindTask.execute(thirdPartyAccount, extras);

        if (hrId > 0) {
            hrThirdPartyAccountDao.dispatchAccountToHr(thirdPartyAccount, hrId);
        }

        if (thirdPartyAccount.getBinding() < 100) {
            bindTask.updateThirdPartyAccount(thirdPartyAccount, extras);
        }

        return thirdPartyAccount;
    }

    /**
     * 同步的方式同步可发布数
     *
     * @param hrThirdPartyAccount
     * @return
     */
    private HrThirdPartyAccountDO syncWithSyncThirdPartyAccount(HrThirdPartyAccountDO hrThirdPartyAccount, Map<String, String> extras) throws Exception {
        HrThirdPartyAccountDO syncResult = syncTask.execute(hrThirdPartyAccount, extras);
        syncTask.updateThirdPartyAccount(syncResult, extras);
        int updateResult = hrThirdPartyAccountDao.updateData(syncResult);
        if (updateResult < 1) {
            //更新失败
            throw new BIZException(-1, "系统异常,请重试");
        }
        return syncResult;
    }

    /**
     * 异步的方式同步可发布数
     *
     * @param hrThirdPartyAccount
     * @return
     */
    private HrThirdPartyAccountDO asyncWithSyncThirdPartyAccount(HrThirdPartyAccountDO hrThirdPartyAccount, Map<String, String> extras) throws Exception {
        //先更新数据库的状态为刷新中3
        hrThirdPartyAccount.setBinding(Short.valueOf("3"));
        int updateResult = hrThirdPartyAccountDao.updateData(hrThirdPartyAccount);
        if (updateResult < 1) {
            //更新失败
            throw new BIZException(-1, "系统异常,请重试");
        }

        new Thread(new AsyncRunnable(syncTask, hrThirdPartyAccount, extras)).start();

        return hrThirdPartyAccount;
    }


    /**
     * 使用异步的方式去同步第三方账号
     *
     * @param hrId
     * @param thirdPartyAccount
     * @return
     */
    private HrThirdPartyAccountDO asyncWithBindThirdPartyAccount(int hrId, HrThirdPartyAccountDO thirdPartyAccount, Map<String, String> extras) throws Exception {
        //先保存信息到数据库,状态为2绑定中
        thirdPartyAccount.setBinding(Short.valueOf("2"));
        if (thirdPartyAccount.getId() > 0) {
            int updateResult = hrThirdPartyAccountDao.updateData(thirdPartyAccount);
            if (updateResult < 1) {
                throw new BIZException(-1, "无法保存数据，请重试");
            }
        } else {
            thirdPartyAccount = hrThirdPartyAccountDao.addThirdPartyAccount(hrId, thirdPartyAccount);
        }
        //开启线程后台取处理第三方账号同步
        new Thread(new AsyncRunnable(bindTask, thirdPartyAccount, extras)).start();
        return thirdPartyAccount;
    }
}
