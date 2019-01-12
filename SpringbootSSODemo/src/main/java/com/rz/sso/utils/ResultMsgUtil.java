package com.rz.sso.utils;

import com.rz.sso.domain.ResultMsg;
import com.rz.sso.domain.TokenInfo;

/**
 * Created by as on 2017/12/20.
 */
public class ResultMsgUtil {

    public static ResultMsg success(TokenInfo tokenInfo) {
        ResultMsg resultMsg = new ResultMsg();
        resultMsg.setRet("0");
        resultMsg.setMsg("验证成功");
        resultMsg.setData(tokenInfo.getData());
        resultMsg.setGlobalId(tokenInfo.getGlobalId());
        return resultMsg;
    }

    public static ResultMsg fail() {
        ResultMsg resultMsg = new ResultMsg();
        resultMsg.setRet("1");
        resultMsg.setMsg("验证失败");
        return resultMsg;
    }
}
