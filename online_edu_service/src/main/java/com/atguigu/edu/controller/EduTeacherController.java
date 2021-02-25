package com.atguigu.edu.controller;


import com.atguigu.edu.entity.EduTeacher;
import com.atguigu.edu.service.EduTeacherService;
import com.atguigu.request.TeacherConditionVO;
import com.atguigu.response.RetVal;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author zhangqiang
 * @since 2020-10-31
 */
@RestController
@RequestMapping("/edu/teacher")
@CrossOrigin
public class EduTeacherController {
    @Autowired
    private EduTeacherService teacherService;


    //1.查询所有讲师
    @GetMapping
    public  RetVal getAllTeacher(){
        List<EduTeacher> teacherList = teacherService.list(null);
//        try {
//            int a=1/0;
//        } catch (Exception e) {
//            throw new EduException();
//        }
        for (EduTeacher teacher : teacherList) {
            System.out.print(teacher.getName());
            System.out.print(teacher.getGmtCreate());
            System.out.println(teacher.getGmtModified());
        }
        return RetVal.success().data("teacherList",teacherList);
    }

    //2.删除讲师
    @DeleteMapping("{id}")
    public RetVal deleteTeacherById(@ApiParam(name = "id",value = "讲师id",required = true) @PathVariable String id){
        boolean flag = teacherService.removeById(id);
        if(flag){
            return RetVal.success();
        }else{
            return RetVal.error();
        }
    }

    //3.讲师列表分页查询
    @GetMapping("queryTeacherPage/{pageNum}/{pageSize}")
    public RetVal queryTeacherPage(
            @PathVariable("pageNum") long pageNum,
            @PathVariable("pageSize") long pageSize){

        Page<EduTeacher> teacherPage = new Page<>(pageNum, pageSize);
        teacherService.page(teacherPage, null);
        //总记录数
        long total = teacherPage.getTotal();
        //总的结果集
        List<EduTeacher> teacherList = teacherPage.getRecords();
        return RetVal.success().data("total",total).data("teacherList",teacherList);
    }

    //4.讲师列表分页查询带条件
    @GetMapping("queryTeacherPageByCondition/{pageNum}/{pageSize}")
    public RetVal queryTeacherPageByCondition(
            @PathVariable("pageNum") long pageNum,
            @PathVariable("pageSize") long pageSize,
            TeacherConditionVO teacherConditionVo){

        Page<EduTeacher> teacherPage = new Page<>(pageNum, pageSize);
        teacherService.queryTeacherPageByCondition(teacherPage, teacherConditionVo);
        //总记录数
        long total = teacherPage.getTotal();
        //总的结果集
        List<EduTeacher> teacherList = teacherPage.getRecords();
        return RetVal.success().data("total",total).data("teacherList",teacherList);
    }
    //4.添加讲师
    @PostMapping
    public RetVal saveTeacher(EduTeacher teacher){
        boolean flag = teacherService.save(teacher);
        if(flag){
            return RetVal.success();
        }else{
            return RetVal.error();
        }
    }
    //5.修改讲师
    @PutMapping
    public RetVal updateTeacher(EduTeacher teacher){
        boolean flag = teacherService.updateById(teacher);
        if(flag){
            return RetVal.success();
        }else{
            return RetVal.error();
        }
    }
    //6.根据id查询讲师
    @GetMapping("{id}")
    public RetVal queryTeacherById(@PathVariable String id){
        EduTeacher teacher = teacherService.getById(id);
        return RetVal.success().data("teacher",teacher);
    }

    public static void main(String[] args) {
        ArrayList<String> list=new ArrayList<String>();
        list.add("111");
        list.add("222");
        list.add("333");
        for(Iterator<String> iterator = list.iterator(); iterator.hasNext();){
            String ele=iterator.next();
            if(ele.equals("111")) //（1）处
                list.remove("222"); //(2)处
        }
        System.out.println(list);
    }
}

