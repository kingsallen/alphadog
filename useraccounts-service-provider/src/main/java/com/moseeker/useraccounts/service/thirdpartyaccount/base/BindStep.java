package com.moseeker.useraccounts.service.thirdpartyaccount.base;

public interface BindStep<P,R> {
    R exec(R r);
}
