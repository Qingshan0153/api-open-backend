package com.groupapi.content.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.groupapi.content.model.entity.UserInterfaceInfo;

/**
 * @author mendax
 * @description 针对表【user_interface_info(用户调用接口关系表)】的数据库操作Service
 * @createDate 2023-07-25 20:56:45
 */
public interface UserInterfaceInfoService extends IService<UserInterfaceInfo> {

    /**
     * 校验接口信息
     *
     * @param userInterfaceInfo 用户接口信息
     * @param b                 布尔
     */
    void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean b);


    /**
     * 接口调用次数统计
     *
     * @param interfaceInfoId 接口id
     * @param userId          调用接口用户id
     * @return 受影响的行数
     */
    boolean invokeCount(long interfaceInfoId, long userId);
}
