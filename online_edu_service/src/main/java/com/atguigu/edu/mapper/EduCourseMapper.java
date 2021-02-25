package com.atguigu.edu.mapper;

import com.atguigu.edu.entity.EduCourse;
import com.atguigu.response.CourseComfirmVO;
import com.atguigu.response.CourseDetailInfoVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author zhangqiang
 * @since 2020-11-07
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    CourseComfirmVO queryCourseConfirmInfo(String courseId);

    CourseDetailInfoVO queryCourseDetailById(String courseId);
}
