package com.rz.sso.utils;

import com.rz.sso.domain.TokenInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by as on 2017/12/17.
 */
public class TokenUtils {
    private static Map<String, TokenInfo> tokenInfoMap = new HashMap<>();

    // 存储临时令牌到redis中，存活期60秒
    public static void setToken(String tokenId, TokenInfo tokenInfo) {
        tokenInfoMap.put(tokenId, tokenInfo);
    }

    //根据token键取TokenInfo
    public static TokenInfo getToken(String tokenId) {
        return tokenInfoMap.get(tokenId);
    }

    // 删除某个 token键值
    public static void delToken(String tokenId) {
        tokenInfoMap.remove(tokenId);
    }

}
