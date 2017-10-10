package com.moseeker.useraccounts.pojo;

/**
 * 账号绑定结果
 * Created by jack on 27/09/2017.
 */
public class BindResult {

    private int status;
    private String message;
    private Account data;

    public class Account{
        private int accountId;

        public int getAccountId() {
            return accountId;
        }

        public void setAccountId(int accountId) {
            this.accountId = accountId;
        }
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Account getData() {
        return data;
    }

    public void setData(Account data) {
        this.data = data;
    }

    public Account createAccount() {
        Account account = new Account();
        return account;
    }
}
