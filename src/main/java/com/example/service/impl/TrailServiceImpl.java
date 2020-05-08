package com.example.service.impl;

import com.example.mapper.TrailMapper;
import com.example.pojo.CountStudentNum;
import com.example.pojo.Heating;
import com.example.pojo.LimitTime;
import com.example.pojo.Trail;
import com.example.service.TrailService;
import com.example.util.GPSUtil;
import com.example.util.ResultBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import cn.hutool.core.collection.CollUtil;

@Service
public class TrailServiceImpl implements TrailService {
    @Autowired
    private TrailMapper trailMapper;
    private Logger logger = LoggerFactory.getLogger(getClass());

    public ResultBean queryTrackByStudenNo(Trail trail) throws Exception{
        ResultBean resultBean = new ResultBean();
        List<Trail> studentTrack = trailMapper.selectTrack(trail);
        // 除去返回有.
        for(Trail i : studentTrack){
            String inTime = i.getInTime();
            String outTime = i.getOutTime();
            i.setInTime(inTime.substring(0,inTime.indexOf('.')));
            i.setOutTime(outTime.substring(0,outTime.indexOf('.')));
        }
        if (studentTrack.size() == 0){
            resultBean.setMessage("无此学生足迹记录!");
            resultBean.setSuccess(false);
        }else {
            resultBean.setMessage("查询成功");
            resultBean.setSuccess(true);
        }
        resultBean.setDataList(studentTrack);
        return resultBean;
    };

    public ResultBean queryTrackByLimitTime(LimitTime limitTime) throws Exception{
        ResultBean resultBean = new ResultBean();
        List<Trail> studentTrackByTimeFilter = trailMapper.queryLimitTime(limitTime);
        for(Trail i : studentTrackByTimeFilter){
            String inTime = i.getInTime();
            String outTime = i.getOutTime();
            i.setInTime(inTime.substring(0,inTime.indexOf('.')));
            i.setOutTime(outTime.substring(0,outTime.indexOf('.')));
        }
        if (studentTrackByTimeFilter.size() == 0){
            resultBean.setMessage("无此学生足迹记录!");
            resultBean.setSuccess(false);
        }else {
            resultBean.setMessage("查询成功");
            resultBean.setSuccess(true);
        }
        resultBean.setDataList(studentTrackByTimeFilter);
        return resultBean;
    }

    public ResultBean queryHeatingMes(LimitTime limitTime) throws Exception{
        ResultBean resultBean = new ResultBean();
        //多线程配置
        List<Trail> studentTrackByTimeFilter = new ArrayList<>();
        try{
            //符合要求的数据条数
            Integer total = trailMapper.countResult(limitTime);
            //每页大小设置为10条
            int threadSize = total / 10;
            //总页数
            int threadNum = total % threadSize == 0 ? total / threadSize : total / threadSize + 1;
            ExecutorService exec = Executors.newFixedThreadPool(threadNum); //threadNum线程数
            List<Callable<Integer>> tasks = new ArrayList<>();
            Callable<Integer> task = null;
            //用来分页存储
            Map<Integer,List<Trail>> map = new HashMap();
            for(int i = 1; i <= threadNum; i++){
                final int pageNum = i;
                task = new Callable<Integer>() {
                    @Override
                    public Integer call() throws Exception {
                        //分页查询符合的数据
                        List<Trail> subTrailList = trailMapper.findByPage(limitTime,(pageNum-1)*threadSize,threadSize);
                        map.put(pageNum,subTrailList);
                        return 1;
                    }
                };
                tasks.add(task);
            }
            //执行全部线程
            exec.invokeAll(tasks);
            if(CollUtil.isNotEmpty(map)){
                for(int i = 1; i<= threadNum;i++){
                    List<Trail> trailVos = map.get(i);
                    //把得到的数据放到一个总List中
                    studentTrackByTimeFilter.addAll(trailVos);
                    map.remove(i);
                }
            }
            exec.shutdown();
        }catch (Exception e){
            logger.info(e.getMessage());
            resultBean.setMessage(e.getMessage());
        };
        //获取Trail所有属性
        List<Heating> heatingList = new ArrayList<>();
        //把Trail中的相关属性存储到Heating中
        for (Trail trail : studentTrackByTimeFilter) {
            //用来从Trail中存取值到Heating
            Heating container = new Heating();
            //计算停留的时间
            Timestamp inTime = Timestamp.valueOf(trail.getInTime());
            Timestamp outTime =  Timestamp.valueOf(trail.getOutTime());
            long stayTime =(outTime.getTime()-inTime.getTime());
            container.setStayTime(stayTime);
            Double lon = Double.valueOf(trail.getLongitude());
            Double lat = Double.valueOf(trail.getLatitude());
            //百度坐标转换为高德坐标
            GPSUtil.bd_decrypt(lat,lon,trail);
            //存取其他信息
            try {
                BeanUtils.copyProperties(trail, container);
            } catch (Exception error) {
                continue;
            }
            //重新组合成List
            heatingList.add(container);
        };
        resultBean.setSuccess(true);
        resultBean.setMessage("查询成功");
        resultBean.setDataList(heatingList);
        return resultBean;
    }

    public ResultBean queryStudentNumByBuilding(LimitTime limitTime) throws Exception{
        ResultBean resultBean = new ResultBean();
        List<CountStudentNum> countStudentNums = trailMapper.countStudentNumByBuilding(limitTime);
        for(CountStudentNum item : countStudentNums){
            Double lon = Double.valueOf(item.getLongitude());
            Double lat = Double.valueOf(item.getLatitude());
            GPSUtil.bd_decryptNum(lat,lon,item);
        }
        resultBean.setDataList(countStudentNums);
        resultBean.setMessage("查询成功");
        resultBean.setSuccess(true);
        return resultBean;
    }

    public ResultBean trackingQuery(LimitTime limitTime) throws Exception{
        ResultBean resultBean = new ResultBean();
        List<Trail> trails = trailMapper.trackingQuery(limitTime);
        // 除去返回有.
        for(Trail i : trails){
            String inTime = i.getInTime();
            String outTime = i.getOutTime();
            i.setInTime(inTime.substring(0,inTime.indexOf('.')));
            i.setOutTime(outTime.substring(0,outTime.indexOf('.')));
        }
        if (trails.size() == 0){
            resultBean.setMessage("无此对应足迹记录!");
            resultBean.setSuccess(true);
        }else {
            resultBean.setMessage("查询成功");
            resultBean.setSuccess(true);
        }
        resultBean.setDataList(trails);
        return resultBean;
    }
}
