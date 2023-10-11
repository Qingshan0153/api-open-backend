package com.groupapi.content.dubboservice.impl;

import com.api.common.service.InnerUserInterfaceInfoService;
import com.groupapi.content.service.UserInterfaceInfoService;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * @author mendax
 * @version 2023/7/31 15:26
 */

@DubboService
public class InnerUserInterfaceInfoServiceImpl implements InnerUserInterfaceInfoService {

    @Resource(name = "UserInterfaceInfoService")
    private UserInterfaceInfoService userInterfaceInfoService;

    @Override
    public boolean invokeCount(long interfaceInfoId, long userId) {
        return userInterfaceInfoService.invokeCount(interfaceInfoId, userId);
    }
}
