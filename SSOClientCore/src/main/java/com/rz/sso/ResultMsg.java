package com.rz.sso;

import java.util.Map;

/**
 * Created by as on 2017/12/16.
 */
public class ResultMsg {

    private String ret;//返回结果代码字符串 “0”代表成功
    private String msg;//返回结果说明字符串
    private String globalId;//全局会话id
    private Map<String, Object> data;//返回值

    public String getRet() {
        return ret;
    }

    void setRet(String ret) {
        this.ret = ret;
    }

    public String getMsg() {
        return msg;
    }

    void setMsg(String msg) {
        this.msg = msg;
    }

    public String getGlobalId() {
        return globalId;
    }

    void setGlobalId(String globalId) {
        this.globalId = globalId;
    }

    public Map<String, Object> getData() {
        return data;
    }

    void setData(Map<String, Object> data) {
        this.data = data;
    }


    @Override
    public String toString() {
        return "ResultMsg{" +
                "ret='" + ret + '\'' +
                ", msg='" + msg + '\'' +
                ", globalId='" + globalId + '\'' +
                ", data=" + data +
                '}';
    }
}
