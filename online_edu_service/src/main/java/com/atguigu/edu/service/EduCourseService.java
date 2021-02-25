package com.atguigu.edu.service;

import com.atguigu.edu.entity.EduCourse;
import com.atguigu.request.CourseCondition;
import com.atguigu.request.CourseInfoVO;
import com.atguigu.response.CourseComfirmVO;
import com.atguigu.response.CourseDetailInfoVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author zhangqiang
 * @since 2020-11-07
 */
public interface EduCourseService extends IService<EduCourse> {

    void saveCourseInfo(CourseInfoVO courseInfoVO);

    void queryCoursePageByCondition(Page<EduCourse> coursePage, CourseCondition courseCondition);

    CourseInfoVO getCourseById(String courseId);

    void updateCourseInfo(CourseInfoVO courseInfoVO);

    CourseComfirmVO queryCourseConfirmInfo(String courseId);

    void deleteCourse(String courseId);

    CourseDetailInfoVO queryCourseDetailById(String courseId);
}
