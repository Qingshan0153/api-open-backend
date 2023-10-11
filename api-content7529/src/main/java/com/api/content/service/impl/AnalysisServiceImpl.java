package com.groupapi.content.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.api.common.model.entity.InterfaceInfo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.groupapi.base.common.ErrorCode;
import com.groupapi.base.exception.BusinessException;
import com.groupapi.content.mapper.UserInterfaceInfoMapper;
import com.groupapi.content.model.entity.UserInterfaceInfo;
import com.groupapi.content.model.vo.InterfaceInfoVO;
import com.groupapi.content.service.AnalysisService;
import com.groupapi.content.service.InterfaceInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author mendax
 * @version 2023/8/2 20:37
 */

@Service
@Slf4j
public class AnalysisServiceImpl implements AnalysisService {

    @Resource
    private UserInterfaceInfoMapper userInterfaceInfoMapper;

    @Resource
    private InterfaceInfoService interfaceInfoService;

    @Override
    public List<InterfaceInfoVO> listTopInvokeInterfaceInfo(int limit) {
        List<UserInterfaceInfo> userInterfaceInfos = userInterfaceInfoMapper.listTopInvokeInterfaceInfo(limit);
        Map<Long, List<UserInterfaceInfo>> interfaceMap = userInterfaceInfos.stream()
                .collect(Collectors.groupingBy(UserInterfaceInfo::getInterfaceInfoId));

        LambdaQueryWrapper<InterfaceInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(InterfaceInfo::getId, interfaceMap.keySet());
        List<InterfaceInfo> interfaceInfos = interfaceInfoService.list(wrapper);

        if (CollectionUtil.isEmpty(interfaceInfos)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        return interfaceInfos.stream().map(interfaceInfo -> {
            InterfaceInfoVO interfaceInfoVO = new InterfaceInfoVO();
            BeanUtils.copyProperties(interfaceInfo, interfaceInfoVO);
            // todo  此处返回 NPE 异常
            Integer totalNum = interfaceMap.get(interfaceInfo.getId()).get(0).getTotalNum();
            interfaceInfoVO.setTotalNum(totalNum);
            return interfaceInfoVO;
        }).collect(Collectors.toList());

    }
}
