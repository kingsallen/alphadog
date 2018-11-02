package com.moseeker.servicemanager.web.controller.referral;

import com.moseeker.common.annotation.iface.CounterIface;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: jack
 * @Date: 2018/11/2
 */
@Controller
@CounterIface
public class RedPacketController {

    /**
     * 员工上传简历
     * @param file 简历文件
     * @param request 请求数据
     * @return 解析结果
     * @throws Exception 异常信息
     */
    @RequestMapping(value = "/v1/redpacket-activity/{id}", method = RequestMethod.POST)
    @ResponseBody
    public String startActivity(@RequestParam(value = "file", required = false) MultipartFile file,
                                   HttpServletRequest request) throws Exception {
        return null;
    }
}
