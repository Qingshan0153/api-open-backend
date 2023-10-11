package com.groupapi.content.model.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 接口信息状态
 *
 * @author mendax
 * @version 2023/7/24 16:48
 */
public enum InterfaceInfoStatusEnum {
    /**
     * 接口下线、下线
     */
    OFFLINE("下线", 0),
    ONLINE("上线", 1);

    private final String text;
    private final Integer value;

    InterfaceInfoStatusEnum(String text, Integer value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 获取值列表
     *
     * @return List<Long>
     */
    public static List<Integer> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    public Integer getValue() {
        return value;
    }
}
