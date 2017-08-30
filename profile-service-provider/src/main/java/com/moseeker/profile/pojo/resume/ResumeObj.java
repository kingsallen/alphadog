
package com.moseeker.profile.pojo.resume;


public class ResumeObj {

    private Status status;
    private Account account;
    private Result result;
    private Eval eval;
    private Tags tags;

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Result getResult() {
        return result;
    }

    public void setEval(Eval eval) {
        this.eval = eval;
    }

    public Eval getEval() {
        return eval;
    }

    public void setTags(Tags tags) {
        this.tags = tags;
    }

    public Tags getTags() {
        return tags;
    }

}