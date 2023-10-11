package com.groupapi.content.service.impl;


import com.api.common.model.entity.InterfaceInfo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.groupapi.base.common.ErrorCode;
import com.groupapi.base.exception.BusinessException;
import com.groupapi.content.mapper.InterfaceInfoMapper;
import com.groupapi.content.service.InterfaceInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @author mendax
 * @description 针对表【interface_info(接口信息)】的数据库操作Service实现
 * @createDate 2023-07-04 21:59:59
 */
@Service
public class InterfaceInfoServiceImpl extends ServiceImpl<InterfaceInfoMapper, InterfaceInfo>
        implements InterfaceInfoService {

    @Override
    public void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean b) {
        if (interfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String name = interfaceInfo.getName();
        // 创建时，所有参数必须为空
        if (b) {
            if (StringUtils.isAllBlank(name)) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }
        }
        if (StringUtils.isNotBlank(name) && name.length() > 50) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "名称过长");
        }

    }
}




