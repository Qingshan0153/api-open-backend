package com.groupapi.content.controller;

import com.groupapi.base.common.BaseResponse;
import com.groupapi.base.common.ResultUtils;
import com.groupapi.content.annotation.AuthCheck;
import com.groupapi.content.service.AnalysisService;
import com.groupapi.content.model.vo.InterfaceInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author mendax
 * @version 2023/8/2 20:19
 */

@RestController
@RequestMapping("/analysis")
@Slf4j
public class AnalysisController {

    @Resource
    private AnalysisService analysisService;

    @GetMapping("/top/interface/invoke")
    @AuthCheck(mustRole = "admin")
    public BaseResponse<List<InterfaceInfoVO>> listTopInvokeInterfaceInfo() {
        List<InterfaceInfoVO> interfaceInfoVoS = analysisService.listTopInvokeInterfaceInfo(3);
        for (InterfaceInfoVO interfaceInfoVo : interfaceInfoVoS) {
            log.info(interfaceInfoVo.toString());
        }
        return ResultUtils.success(interfaceInfoVoS);
    }

}
