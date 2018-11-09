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
    public static final CommonException PROGRAM_POST_FAILED = new CommonException(90011, "添加失败！");
    public static final CommonException PROGRAM_PUT_FAILED = new CommonException(90012, "保存失败！");
    public static final CommonException PROGRAM_DEL_FAILED = new CommonException(90013, "删除失败！");
    public static final CommonException PROGRAM_PARAM_NOTEXIST = new CommonException(90015, "参数不正确!");
    public static final CommonException NODATA_EXCEPTION = new CommonException(99998,"数据不存在！");
    public static final CommonException NO_PERMISSION_EXCEPTION = new CommonException(99997,"没有权限！");
    public static final CommonException PROGRAM_FETCH_TOO_MUCH = new CommonException(99999,"获取的数据太多，超过允许的限制！");

    public static final CommonException PROGRAM_UPDATE_FIALED = new CommonException(90016,"超过重试次数！");
    public static final CommonException PROGRAM_APPID_LOST = new CommonException(90017,"请设置appid！");
    public static final CommonException PROGRAM_APPID_REQUIRED = new CommonException(1,"请设置 appid!");
    public static final CommonException PROGRAM_DOUBLE_CLICK = new CommonException(90017,"重复录入!");

    public static final CommonException DATA_OPTIMISTIC = new CommonException(90018, "触发乐观锁！");

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

    public static CommonException validateFailed(String message) {
        return new CommonException(90014, message);
    }

    private int code;
    private String message;
}
