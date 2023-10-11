package com.groupapi.content.service;

import com.groupapi.content.model.vo.InterfaceInfoVO;

import java.util.List;

/**
 * @author mendax
 * @version 2023/8/2 20:36
 */


public interface AnalysisService {


    /**
     * 分析用户调用接口信息
     * @param limit 限制条数
     * @return List<InterfaceInfoVO>
     */
    List<InterfaceInfoVO> listTopInvokeInterfaceInfo(int limit);
}
