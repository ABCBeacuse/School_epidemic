package com.example.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.example.pojo.LimitTime;
import com.example.pojo.Trail;
import com.example.service.TrailService;
import com.example.util.ResultBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/studentTrack")
public class TrailController {
    @Autowired
    private TrailService trailService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @PostMapping("/query")
    public ResultBean queryTrackByStudenNo(@RequestBody String params) throws Exception{
        logger.info("查询所需要的参数:"+params);
        Trail trail= JSON.parseObject(params,new TypeReference<Trail>(){});
        return trailService.queryTrackByStudenNo(trail);
    };

    @PostMapping("/limitTime")
    public ResultBean queryTrackByLimitTime(@RequestBody String params)throws Exception{
        logger.info("查询所需要的参数:"+params);
        LimitTime limitTime = JSON.parseObject(params,new TypeReference<LimitTime>(){});
        return trailService.queryTrackByLimitTime(limitTime);
    }

    @PostMapping("/getHeatingData")
    public ResultBean queryHeatingMes(@RequestBody String params)throws Exception{
        logger.info("查询所需要的参数:"+params);
        LimitTime limitTime = JSON.parseObject(params,new TypeReference<LimitTime>(){});
        return trailService.queryHeatingMes(limitTime);
    }

    @PostMapping("/queryStudentNumByBuilding")
    public ResultBean queryStudentNumByBuilding(@RequestBody String params) throws Exception{
        logger.info("查询所需要的参数:"+params);
        LimitTime limitTime = JSON.parseObject(params,new TypeReference<LimitTime>(){});
        return trailService.queryStudentNumByBuilding(limitTime);
    }

    @PostMapping("/trackingQuery")
    public ResultBean trackingQuery(@RequestBody String params) throws Exception{
        logger.info("查询所需要的参数:"+params);
        LimitTime limitTime = JSON.parseObject(params,new TypeReference<LimitTime>(){});
        return trailService.trackingQuery(limitTime);
    }
}
