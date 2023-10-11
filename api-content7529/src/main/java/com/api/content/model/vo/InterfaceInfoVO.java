package com.groupapi.content.model.vo;

import com.api.common.model.entity.InterfaceInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 接口信息视图（脱敏）
 *
 * @author mendax
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class InterfaceInfoVO extends InterfaceInfo implements Serializable {

    /**
     * 调用次数
     */
    private Integer totalNum;


    private static final long serialVersionUID = 1L;
}