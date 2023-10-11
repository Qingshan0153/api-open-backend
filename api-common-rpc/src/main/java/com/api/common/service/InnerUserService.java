package com.api.common.service;

import com.api.common.model.vo.UserVO;

/**
 * 用户服务
 *
 * @author mendax
 */
public interface InnerUserService {

    /**
     * 查询用户是否存在
     *
     * @param accessKey ak
     * @return 用户
     */
    UserVO getInvokeUser(String accessKey);
}
