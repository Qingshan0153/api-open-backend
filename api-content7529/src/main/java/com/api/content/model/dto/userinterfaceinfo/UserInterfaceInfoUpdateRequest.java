package com.groupapi.content.model.dto.userinterfaceinfo;

import lombok.Data;

import java.io.Serializable;

/**
 * 更新请求
 *
 * @author mendax
 */
@Data
public class UserInterfaceInfoUpdateRequest implements Serializable {

    private Integer id;

    /**
     * 总调用次数
     */
    private Integer totalNum;

    /**
     * 剩余调用次数
     */
    private Integer leftNum;

    /**
     * 接口状态(0-关闭.1-开启)
     */
    private Long status;

}