package com.groupapi.content.model.dto.interfaceinfo;

import com.groupapi.base.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 查询请求
 * @author mendax
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class InterfaceInfoQueryOneRequest extends PageRequest implements Serializable {

    /**
     *
     */
    private Integer id;

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
     * 接口状态(0-关用.1-开启)
     */
    private Integer status;

    /**
     * 请求类
     */
    private String method;

    /**
     * 创建人
     */
    private Long userId;

    /**
     * 创建时间"
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;


    /**
     * 是否删除(0-未删，1-已删)
     */
    private Integer isDelete;
}