package com.api.common.service;

/**
 * @author mendax
 */
public interface InnerUserInterfaceInfoService {

    /**
     * 接口调用次数统计
     *
     * @param interfaceInfoId interfaceInfoId
     * @param userId          userId
     * @return boolean
     */
    boolean invokeCount(long interfaceInfoId, long userId);
}
