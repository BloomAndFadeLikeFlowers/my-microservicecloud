package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.common.ParamData;
import com.atguigu.springcloud.common.ResultCode;
import com.atguigu.springcloud.common.ResultData;
import com.atguigu.springcloud.config.ApplicationStaticConfig;
import com.atguigu.springcloud.entities.Dept;
import com.atguigu.springcloud.service.DeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/dept")
@Api(value = "DeptController", description = "DeptController")
public class DeptController {
    @Autowired
    private DeptService service;
    @Autowired
    private ApplicationStaticConfig applicationStaticConfig;

    @ApiOperation(value = "新增部门")
    @ApiImplicitParam(name = "dept", dataType = "Map", required = true,
            value = "部门信息\n" +
                    "{\n" +
                    "    \"deptName\": \"开发部123\",\n" +
                    "    \"dbSource\": \"clouddb01\"\n" +
                    "}")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResultData add(@RequestBody Dept dept) {
        ResultData resultData = new ResultData();
        try {
            resultData.setContent(service.add(dept));
            resultData.setResultCode(ResultCode.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            resultData.setErrorMessage(e.getMessage());
            resultData.setResultCode(ResultCode.ERROR);
        }
        return resultData;
    }

    @ApiOperation(value = "根据id查询部门")
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResultData get(@RequestParam("id") Long id) {
        ResultData resultData = new ResultData();
        try {
            resultData.setContent(service.get(id));
            resultData.setResultCode(ResultCode.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            resultData.setErrorMessage(e.getMessage());
            resultData.setResultCode(ResultCode.ERROR);
        }
        return resultData;
    }

    @ApiOperation(value = "根据id删除部门")
    @RequestMapping(value = "/del", method = RequestMethod.GET)
    public ResultData del(@RequestParam("id") Long id) {
        ResultData resultData = new ResultData();
        try {
            service.del(id);
            resultData.setResultCode(ResultCode.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            resultData.setErrorMessage(e.getMessage());
            resultData.setResultCode(ResultCode.ERROR);
        }
        return resultData;
    }

    @ApiOperation(value = "分页查询查询部门信息")
    @ApiImplicitParam(name = "paramData", required = true, dataType = "ParamData",
            value = "查询参数\n" +
                    "{\n" +
                    "  \"pageNumber\": 0,\n" +
                    "  \"pageSize\": 5,\n" +
                    "  \"paramMap\": {" +
                    "    \"deptNo\":1,\n" +
                    "    \"deptName\": \"开发部\",\n" +
                    "    \"dbSource\": \"clouddb01\"\n" +
                    "   }\n" +
                    "}")
    @RequestMapping(value = "/findAll", method = RequestMethod.POST)
    public ResultData findAll(@RequestBody ParamData paramData) {
        ResultData resultData = new ResultData();
        try {
            resultData.setContent(service.findAll(paramData));
            resultData.setResultCode(ResultCode.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            resultData.setErrorMessage(e.getMessage());
            resultData.setResultCode(ResultCode.ERROR);
        }
        return resultData;
    }
}
