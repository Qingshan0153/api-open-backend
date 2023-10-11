package com.groupapi.content.model.dto.interfaceinfo;

import lombok.Data;

import java.io.Serializable;

/**
 * 新增参数
 *
 * @author mendax
 */
@Data
public class InterfaceInfoAddRequest implements Serializable {
    /**
     * 名称
     */
    private String name;

    /**
     * 描达
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
     * 请求类
     */
    private String method;

}