package com.atguigu.springcloud.controller;

import com.alibaba.fastjson.JSONArray;
import com.atguigu.springcloud.common.ResultCode;
import com.atguigu.springcloud.common.ResultData;
import com.atguigu.springcloud.config.ApplicationStaticConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@Api(value = "CheckHealthyController", description = "健康监测")
public class CommonController {

    @Autowired
    private DiscoveryClient client;
    @Autowired
    private ApplicationStaticConfig applicationStaticConfig;

    @Autowired
    Environment environment;

    private Map other = new LinkedHashMap();

    /**
     * 判断当前服务在注册中心中是否存在
     *
     * @return
     */
    @ApiOperation(value = "健康监测")
    @RequestMapping(value = "/checkHealthy", method = RequestMethod.GET)
    public ResultData checkHealthy() {
        other.put("LocalApplicationName", applicationStaticConfig.getApplicationName());
        ResultData resultData = new ResultData()
                .setResultCode(ResultCode.SUCCESS)
                .setContent(client.getServices()
                        .contains(applicationStaticConfig.getApplicationName()));
        other.put("RequestChainMessage", Arrays.asList(getRequestChainMessage()));
        resultData.setOther(other);
        return resultData;
    }

    private Map getRequestChainMessage() {
        Map requestChainMessage = new LinkedHashMap();
        requestChainMessage.put("ApplicationName", applicationStaticConfig.getApplicationName());
        InetAddress localHost = null;
        try {
            localHost = Inet4Address.getLocalHost();
        } catch (UnknownHostException e) {
        }
        requestChainMessage.put("LocalIp", localHost.getHostAddress());// 返回格式为：xxx.xxx.xxx
        requestChainMessage.put("Port", environment.getProperty("local.server.port"));
        requestChainMessage.put("HostName", localHost.getHostName());// 一般是返回电脑用户名
        return requestChainMessage;
    }

    /**
     * 查询注册中心的服务和实例列表
     *
     * @return
     */
    @ApiOperation(value = "查询注册中心的服务和实例列表")
    @RequestMapping(value = "/discovery", method = RequestMethod.GET)
    public Object discovery() {
        return this.client;
    }

}
