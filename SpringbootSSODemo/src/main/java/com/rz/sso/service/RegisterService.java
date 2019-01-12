package com.rz.sso.service;

import java.util.Map;

/**
 * Created by as on 2017/12/24.
 * 注册应用登出接口服务
 */
public interface RegisterService {

    /**
     * hash结构，注册应用登出接口地址
     *
     * @param key     hash key
     * @param hashKey hash value 对应的key
     * @param value   hash value 对应的value
     */
    public void registerAppLogoutURL(String key, String hashKey, String value);

    /**
     * 根据key获取对应map
     *
     * @param key
     * @return
     */
    public Map<String, String> getAppLogoutURL(String key);

    /**
     * 根据key删除对应的map
     * @param key
     */
    public void delAppLogoutURL(String key);
}
