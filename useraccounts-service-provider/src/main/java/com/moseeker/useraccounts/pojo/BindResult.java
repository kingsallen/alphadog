package com.moseeker.useraccounts.pojo;

/**
 * 账号绑定结果
 * Created by jack on 27/09/2017.
 */
public class BindResult {

    private int status;
    private String message;
    private Account account;

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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Account createAccount() {
        Account account = new Account();
        return account;
    }
}
