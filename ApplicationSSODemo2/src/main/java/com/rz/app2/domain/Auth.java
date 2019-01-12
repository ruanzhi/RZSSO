package com.rz.app2.domain;

/**
 * Created by as on 2017/12/17.
 */
public class Auth {
    private int uid;//用户id
    private String username;//用户名字
    private String globalId;//全局会话id

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGlobalId() {
        return globalId;
    }

    public void setGlobalId(String globalId) {
        this.globalId = globalId;
    }

    @Override
    public String toString() {
        return "Auth{" +
                "uid=" + uid +
                ", username='" + username + '\'' +
                ", globalId='" + globalId + '\'' +
                '}';
    }
}
