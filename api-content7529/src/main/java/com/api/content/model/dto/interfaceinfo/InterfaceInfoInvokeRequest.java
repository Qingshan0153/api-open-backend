package com.groupapi.content.model.dto.interfaceinfo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author mendax
 * @version 2023/7/25 16:27
 */

@Data
public class InterfaceInfoInvokeRequest implements Serializable {
    /**
     *
     */
    private int id;


    /**
     * 用户请求参数
     */
    private String userRequestParams;

}
