package com.groupapi.content.model.dto.interfaceinfo;

import lombok.Data;

import java.io.Serializable;

/**
 * 更新请求
 * @author mendax
 */
@Data
public class InterfaceInfoUpdateRequest implements Serializable {

    /**
     *
     */
    private int id;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 接口地址
     */
    private String url;

    /**
     * 请求参数
     */
    private String requestParams;

    /**
     * 请求头
     */
    private String requestHeader;

    /**
     * 响度头
     */
    private String responseHeader;

    /**
     * 接口状态(0-关用.1-开启)
     */
    private int status;

    /**
     * 请求类
     */
    private String method;

}