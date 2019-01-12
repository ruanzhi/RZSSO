package com.rz.sso.domain;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by as on 2017/12/16.
 */
public class TokenInfo implements Serializable {
    private String ssoClient; //来自登录请求的某应用系统标识
    private String globalId; //本次登录成功的全局会话sessionId
    private Map<String,Object> data;//返回数据库的值


    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public String getSsoClient() {
        return ssoClient;
    }

    public void setSsoClient(String ssoClient) {
        this.ssoClient = ssoClient;
    }

    public String getGlobalId() {
        return globalId;
    }

    public void setGlobalId(String globalId) {
        this.globalId = globalId;
    }

    @Override
    public String toString() {
        return "TokenInfo{" +
                "ssoClient='" + ssoClient + '\'' +
                ", globalId='" + globalId + '\'' +
                ", data=" + data +
                '}';
    }
}
