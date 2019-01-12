package com.rz.sso.domain;

import java.util.Map;

/**
 * Created by as on 2017/12/16.
 */
public class ResultMsg {

    private String ret;//返回结果代码字符串 “0”代表成功
    private String msg;//返回结果说明字符串
    private Map<String, Object> data;//返回值
    private String globalId;//全局会话id

    public String getRet() {
        return ret;
    }

    public void setRet(String ret) {
        this.ret = ret;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public String getGlobalId() {
        return globalId;
    }

    public void setGlobalId(String globalId) {
        this.globalId = globalId;
    }

    @Override
    public String toString() {
        return "ResultMsg{" +
                "ret='" + ret + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                ", globalId='" + globalId + '\'' +
                '}';
    }
}
