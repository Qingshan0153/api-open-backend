package com.groupapi.content.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.groupapi.content.model.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户数据库操作
 * @author mendax
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




