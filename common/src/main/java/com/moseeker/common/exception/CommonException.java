package com.moseeker.common.exception;

import com.moseeker.common.util.StringUtils;

/**
 * 异常基类。继承RuntimeException{@see java.lang.RuntimeException}
 * <p>Company: MoSeeker</P>
 * <p>date: Mar 30, 2016</p>
 * <p>Email: wjf2255@gmail.com</p>
 *
 * @author wjf
 * @version Beta
 */
public class CommonException extends RuntimeException {


    public static final CommonException PROGRAM_EXCEPTION = new CommonException(99999, "发生异常，请稍候再试！");

    public static final CommonException NODATA_EXCEPTION = new CommonException(99998,"数据不存在！");

    public static final CommonException NO_PERMISSION_EXCEPTION = new CommonException(99997,"没有权限！");

    private static final long serialVersionUID = 1982007458282752099L;

    public CommonException() {
    }

    public CommonException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getMessage() {
        if (StringUtils.isNullOrEmpty(this.message)) {
            return super.getMessage();
        } else {
            return message;
        }

    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String msg) {
        this.message = msg;
    }

    public CommonException setMess(String message) {
        return new CommonException(code, message);
    }

    private int code;
    private String message;
}
