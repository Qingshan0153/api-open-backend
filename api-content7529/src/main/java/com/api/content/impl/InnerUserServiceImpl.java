package com.groupapi.content.dubboservice.impl;

import com.api.common.model.vo.UserVO;
import com.api.common.service.InnerUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.groupapi.base.common.ErrorCode;
import com.groupapi.base.exception.BusinessException;
import com.groupapi.content.service.UserService;
import com.groupapi.content.model.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;

import javax.annotation.Resource;

/**
 * @author mendax
 * @version 2023/7/31 15:26
 */

@DubboService
public class InnerUserServiceImpl implements InnerUserService {

    @Resource
    private UserService userService;

    @Override
    public UserVO getInvokeUser(String accessKey) {
        if (StringUtils.isBlank(accessKey)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getAccessKey, accessKey);
        User user = userService.getOne(wrapper);
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }
}
