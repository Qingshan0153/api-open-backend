package com.api.common.service;


import com.api.common.model.entity.InterfaceInfo;

/**
 * @author mendax
 * 2023-07-04 21:59:59
 */
public interface InnerInterfaceInfoService {

    /**
     * 查询接口是否存在
     *
     * @param url    接口url
     * @param method 接口请求方法
     * @return boolean
     */
    InterfaceInfo getInterfaceInfo(String url, String method);
}
