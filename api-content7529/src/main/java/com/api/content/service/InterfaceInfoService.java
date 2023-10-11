package com.groupapi.content.service;

import com.api.common.model.entity.InterfaceInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author mendax
 * @description 针对表【interface_info(接口信息)】的数据库操作Service
 * @createDate 2023-07-04 21:59:59
 */
public interface InterfaceInfoService extends IService<InterfaceInfo> {
    /**
     * 参数校验
     *
     * @param interfaceInfo interfaceInfo
     * @param b             b
     */
    void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean b);
}
