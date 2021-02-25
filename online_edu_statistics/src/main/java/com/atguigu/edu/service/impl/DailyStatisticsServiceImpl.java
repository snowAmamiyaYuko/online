package com.atguigu.edu.service.impl;

import com.atguigu.edu.entity.DailyStatistics;
import com.atguigu.edu.mapper.DailyStatisticsMapper;
import com.atguigu.edu.service.DailyStatisticsService;
import com.atguigu.edu.service.UserServiceFeign;
import com.atguigu.response.RetVal;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author zhangqiang
 * @since 2021-02-23
 */
@Service
public class DailyStatisticsServiceImpl extends ServiceImpl<DailyStatisticsMapper, DailyStatistics> implements DailyStatisticsService {
    @Autowired
    private UserServiceFeign userServiceFeign;

    @Override
    public void generateData(String day) {
        //来源于user微服务
        RetVal retVal = userServiceFeign.queryRegisterNum(day);
        Integer registerNum =(Integer) retVal.getData().get("registerNum");
        DailyStatistics statistics = new DailyStatistics();
        statistics.setDateCalculated(day);
        statistics.setRegisterNum(registerNum);

        //造数据 来自其他微服务
        statistics.setLoginNum(RandomUtils.nextInt(300,400));
        statistics.setVideoViewNum(RandomUtils.nextInt(400,500));
        statistics.setCourseNum(RandomUtils.nextInt(200,300));
        baseMapper.insert(statistics);
    }

    @Override
    public Map<String, Object> showStatistics(String dataType, String beginTime, String endTime) {
        //返回数据有X轴数据(日期)和Y轴数据(对应日期和类型的数据)
        QueryWrapper<DailyStatistics> wrapper = new QueryWrapper<>();
        wrapper.between("date_calculated",beginTime,endTime);
        wrapper.orderByDesc("date_calculated");
        List<DailyStatistics> statisticsList = baseMapper.selectList(wrapper);

        List<String> xData = new ArrayList<>();
        List<Integer> yData = new ArrayList<>();
        for (DailyStatistics dailyStatistics : statisticsList) {
            String dateCalculated = dailyStatistics.getDateCalculated();
            xData.add(dateCalculated);
            switch (dataType){
                case "register_num":
                    Integer registerNum = dailyStatistics.getRegisterNum();
                    yData.add(registerNum);
                    break;
                case "login_num":
                    Integer loginNum = dailyStatistics.getLoginNum();
                    yData.add(loginNum);
                    break;
                case "video_view_num":
                    Integer videoViewNum = dailyStatistics.getVideoViewNum();
                    yData.add(videoViewNum);
                    break;
                case "course_num":
                    Integer courseNum = dailyStatistics.getCourseNum();
                    yData.add(courseNum);
                    break;
                default:
                    break;
            }
        }
        Map<String, Object>retMap=new HashMap<>();
        retMap.put("xData",xData);
        retMap.put("yData",yData);
        return retMap;
    }
}
