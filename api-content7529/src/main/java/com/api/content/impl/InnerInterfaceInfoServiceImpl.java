package com.groupapi.content.dubboservice.impl;

import com.api.common.model.entity.InterfaceInfo;
import com.api.common.service.InnerInterfaceInfoService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.groupapi.base.common.ErrorCode;
import com.groupapi.base.exception.BusinessException;
import com.groupapi.content.service.InterfaceInfoService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * @author mendax
 * @version 2023/7/31 15:25
 */

@DubboService
public class InnerInterfaceInfoServiceImpl implements InnerInterfaceInfoService {

    @Resource
    private InterfaceInfoService interfaceInfoService;

    @Override
    public InterfaceInfo getInterfaceInfo(String url, String method) {
        if (StringUtils.isBlank(url) || StringUtils.isBlank(method)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        LambdaQueryWrapper<InterfaceInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(InterfaceInfo::getUrl, url).eq(InterfaceInfo::getMethod, method);
        return interfaceInfoService.getOne(wrapper);

    }
}
