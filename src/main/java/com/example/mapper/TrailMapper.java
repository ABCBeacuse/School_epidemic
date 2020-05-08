package com.example.mapper;

import com.example.pojo.CountStudentNum;
import com.example.pojo.LimitTime;
import com.example.pojo.Trail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface TrailMapper {
    List<Trail> selectTrack(Trail trail);
    List<Trail> queryLimitTime(LimitTime limitTime);
    Integer countResult(LimitTime limitTime);
    List<Trail> findByPage(@Param("limitTime") LimitTime limitTime, @Param("offset") Integer offset, @Param("pageSize") Integer pageSize);
    //根据时间来统计不同建筑物中的学生人数
    List<CountStudentNum> countStudentNumByBuilding(LimitTime limitTime);
    //先通过传来的studentNo和截止时间来进行查询该学生14天内的定位记录,然后用这些记录中的buildingName和in_Time和out_Time去查询有哪些学生这个时间段内在该建筑物中。
    List<Trail> trackingQuery(LimitTime limitTime);
}
