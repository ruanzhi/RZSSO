package com.rz.sso;

/**
 * @author as
 * @create 2018-03-24 20:17
 * @desc SSO客户端上下文
 */
public class SSOClientContext {
    private static ThreadLocal<ResultMsg> ssoHolder = new ThreadLocal<ResultMsg>();

    public static ResultMsg getResultMsg() {
        return ssoHolder.get();
    }

    static void setResultMsg(ResultMsg resultMsg) {
        ssoHolder.set(resultMsg);
    }
}
