package com.example.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.example.pojo.LimitTime;
import com.example.pojo.Trail;
import com.example.service.TrailService;
import com.example.util.ResultBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/studentTrack")
@Api(value="各种足迹查询，以及对足迹的相关统计")
public class TrailController {
    @Autowired
    private TrailService trailService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @PostMapping("/query")
    @ApiOperation(value = "通过学号studentNo来查询该学生的全部足迹信息",notes = "返回足迹信息List集合")
    @ApiModelProperty(value = "studentNo",notes = "{studentNo:\"\"}")
    public ResultBean queryTrackByStudenNo(@RequestBody String params) throws Exception{
        logger.info("查询所需要的参数:"+params);
        Trail trail= JSON.parseObject(params,new TypeReference<Trail>(){});
        return trailService.queryTrackByStudenNo(trail);
    };

    @PostMapping("/limitTime")
    @ApiOperation(value = "通过studentNo、startTime、stopTime查询足迹信息",notes = "返回足迹信息List集合")
    @ApiModelProperty(value = "studentNo、startTime、stopTime",notes = "{studentNo:'',startTime:'',stopTime:''}")
    public ResultBean queryTrackByLimitTime(@RequestBody String params)throws Exception{
        logger.info("查询所需要的参数:"+params);
        LimitTime limitTime = JSON.parseObject(params,new TypeReference<LimitTime>(){});
        return trailService.queryTrackByLimitTime(limitTime);
    }

//    @PostMapping("/getHeatingData")
//    public ResultBean queryHeatingMes(@RequestBody String params)throws Exception{
//        logger.info("查询所需要的参数:"+params);
//        LimitTime limitTime = JSON.parseObject(params,new TypeReference<LimitTime>(){});
//        return trailService.queryHeatingMes(limitTime);
//    }

    @PostMapping("/queryStudentNumByBuilding")
    @ApiOperation(value = "根据建筑物名称来统计一段时间内的人数",notes = "返回根据建筑物统计人数的结果")
    @ApiModelProperty(value = "startTime、stopTime",notes = "{startTime:'',stopTime:''}")
    public ResultBean queryStudentNumByBuilding(@RequestBody String params) throws Exception{
        logger.info("查询所需要的参数:"+params);
        LimitTime limitTime = JSON.parseObject(params,new TypeReference<LimitTime>(){});
        return trailService.queryStudentNumByBuilding(limitTime);
    }

    @PostMapping("/trackingQuery")
    @ApiOperation(value = "根据疑似病例学号来查询其确诊日期前14天接触的人的信息",notes = "返回相关人员信息的List集合")
    @ApiModelProperty(value = "studentNo、stopTime",notes = "{studentNo:'',stopTime:''}")
    public ResultBean trackingQuery(@RequestBody String params) throws Exception{
        logger.info("查询所需要的参数:"+params);
        LimitTime limitTime = JSON.parseObject(params,new TypeReference<LimitTime>(){});
        return trailService.trackingQuery(limitTime);
    }
}
