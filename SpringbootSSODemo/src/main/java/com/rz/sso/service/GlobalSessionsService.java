package com.rz.sso.service;

import com.rz.sso.domain.TokenInfo;

/**
 * Created by as on 2018/3/25.
 * 管理全局会话服务
 */
public interface GlobalSessionsService {


    /**
     * 保存全局会话
     *
     * @param sessionId
     */
    public void saveGlobalSession(String sessionId, TokenInfo tokenInfo);

    /**
     * 获取全局会话id
     *
     * @param sessionId
     * @return
     */
    public TokenInfo getGlobalSession(String sessionId);

    /**
     * 更新全局会话id过去时间
     *
     * @param sessionId
     */
    public void updatetGlobalSessionIdExpireTime(String sessionId);

    /**
     * 删除会话
     *
     * @param sessionId
     */
    public void deleteGlobalSession(String sessionId);
}
