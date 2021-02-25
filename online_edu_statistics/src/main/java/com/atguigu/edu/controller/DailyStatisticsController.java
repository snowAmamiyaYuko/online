package com.atguigu.edu.controller;


import com.atguigu.edu.service.DailyStatisticsService;
import com.atguigu.response.RetVal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2021-02-23
 */
@RestController
@RequestMapping("/daily/statistics")
@CrossOrigin
public class DailyStatisticsController {
    @Autowired
    private DailyStatisticsService statisticsService;
    //1.生成指定日期的数据
    @GetMapping("generateData/{day}")
    public RetVal generateData(@PathVariable String day){
        statisticsService.generateData(day);
        return RetVal.success();
    }
    //2.数据显示
    @GetMapping("showStatistics/{dataType}/{beginTime}/{endTime}")
    public RetVal showStatistics(@PathVariable String dataType,
                               @PathVariable String beginTime,
                               @PathVariable String endTime){
        Map<String, Object> retMap=statisticsService.showStatistics(dataType,beginTime,endTime);
        return RetVal.success().data(retMap);
    }
}

