package com.atguigu.edu.controller.front;

import com.atguigu.edu.service.EduChapterService;
import com.atguigu.edu.service.EduCourseService;
import com.atguigu.response.ChapterVO;
import com.atguigu.response.CourseDetailInfoVO;
import com.atguigu.response.RetVal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/edu/front/course")
@CrossOrigin
public class FrontCourseController {
    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduChapterService chapterService;
    //1.课程列表信息(作业 选做)


    //2.课程的详情信息
    @GetMapping("queryCourseDetailById/{courseId}")
    public RetVal queryCourseDetailById(@PathVariable String courseId){
        //1、课程详情信息 没有章节小节信息
        CourseDetailInfoVO courseDetailInfoVO=courseService.queryCourseDetailById(courseId);
        List<ChapterVO> chapterAndSection = chapterService.getChapterAndSection(courseId);
        return RetVal.success().data("courseDetailInfoVO",courseDetailInfoVO).data("chapterAndSection",chapterAndSection);
    }


}
