package com.rz.sso.service;

import com.rz.sso.domain.TokenInfo;

/**
 * Created by as on 2017/12/24.
 */
public interface TokenService {

    /**
     * 保存tokenInfo到redis里面，默认保存时间为60秒
     *
     * @param tokenId
     * @param tokenInfo
     */
    public void setToken(String tokenId, TokenInfo tokenInfo);

    /**
     * 根据tokenid删除某个值
     *
     * @param tokenId
     */
    public void delToken(String tokenId);

    /**
     * 得到某个值
     *
     * @param tokenId
     * @return
     */
    public TokenInfo getToken(String tokenId);

    public String get(String key);
}
