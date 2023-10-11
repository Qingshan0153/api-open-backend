package com.groupapi.content.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.groupapi.content.model.entity.UserInterfaceInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author mendax
 */
@Mapper
public interface UserInterfaceInfoMapper extends BaseMapper<UserInterfaceInfo> {

    /**
     * 分析用户调用接口信息
     *
     * @param limit 限制条数
     * @return List<UserInterfaceInfo>
     */
    List<UserInterfaceInfo> listTopInvokeInterfaceInfo(@Param("limit") int limit);
}




