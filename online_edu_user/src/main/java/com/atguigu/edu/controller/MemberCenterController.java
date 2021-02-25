package com.atguigu.edu.controller;


import com.atguigu.edu.service.MemberCenterService;
import com.atguigu.response.RetVal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2021-02-23
 */
@RestController
@RequestMapping("/member/center")
@CrossOrigin
public class MemberCenterController {
    @Autowired
    private MemberCenterService centerService;
    //统计每日有多少人注册
    @GetMapping("queryRegisterNum/{day}")
    public RetVal queryRegisterNum(@PathVariable("day") String day){
       Integer registerNum= centerService.queryRegisterNum(day);
        return RetVal.success().data("registerNum",registerNum);
    }
}

