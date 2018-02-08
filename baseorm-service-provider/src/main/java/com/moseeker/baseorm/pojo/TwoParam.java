package com.moseeker.baseorm.pojo;

public class TwoParam<R1,R2> {
    private R1 r1;
    private R2 r2;

    public TwoParam() {
    }

    public TwoParam(R1 r1, R2 r2) {
        this.r1 = r1;
        this.r2 = r2;
    }

    public R1 getR1() {
        return r1;
    }

    public void setR1(R1 r1) {
        this.r1 = r1;
    }

    public R2 getR2() {
        return r2;
    }

    public void setR2(R2 r2) {
        this.r2 = r2;
    }
}
