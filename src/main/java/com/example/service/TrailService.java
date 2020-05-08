package com.example.service;

import com.example.pojo.LimitTime;
import com.example.pojo.Trail;
import com.example.util.ResultBean;

public interface TrailService {
    ResultBean queryTrackByStudenNo(Trail trail) throws Exception;
    ResultBean queryTrackByLimitTime(LimitTime limitTime) throws Exception;
    ResultBean queryHeatingMes(LimitTime limitTime) throws Exception;
    ResultBean queryStudentNumByBuilding(LimitTime limitTime) throws  Exception;
    ResultBean trackingQuery(LimitTime limitTime) throws Exception;
}
