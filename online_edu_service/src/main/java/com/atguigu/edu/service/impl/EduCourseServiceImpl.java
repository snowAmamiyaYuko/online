package com.atguigu.edu.service.impl;

import com.atguigu.edu.entity.EduCourse;
import com.atguigu.edu.entity.EduCourseDescription;
import com.atguigu.edu.mapper.EduCourseMapper;
import com.atguigu.edu.service.EduChapterService;
import com.atguigu.edu.service.EduCourseDescriptionService;
import com.atguigu.edu.service.EduCourseService;
import com.atguigu.edu.service.EduSectionService;
import com.atguigu.request.CourseCondition;
import com.atguigu.request.CourseInfoVO;
import com.atguigu.response.CourseComfirmVO;
import com.atguigu.response.CourseDetailInfoVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author zhangqiang
 * @since 2020-11-07
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {
    @Autowired
    private EduCourseDescriptionService descriptionService;
    @Autowired
    private EduChapterService chapterService;
    @Autowired
    private EduSectionService sectionService;
    @Override
    public void saveCourseInfo(CourseInfoVO courseInfoVO) {
        //c.保存课程基本信息
        EduCourse course = new EduCourse();
        BeanUtils.copyProperties(courseInfoVO,course);
        baseMapper.insert(course);
        //d.保存课程描述信息
        EduCourseDescription description = new EduCourseDescription();
        //e.它们两公用一个主键id
        description.setId(course.getId());
        description.setDescription(courseInfoVO.getDescription());
        descriptionService.save(description);
    }

    @Override
    public void queryCoursePageByCondition(Page<EduCourse> coursePage, CourseCondition courseCondition) {
        //获取每个查询参数
        String title = courseCondition.getTitle();
        String status = courseCondition.getStatus();
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        //判断以上传递过来的参数是否为空
        if(StringUtils.isNotEmpty(title)){
            wrapper.like("title",title);
        }

        if(StringUtils.isNotEmpty(status)){
            wrapper.ge("status",status);
        }
        baseMapper.selectPage(coursePage,wrapper);
    }

    @Override
    public CourseInfoVO getCourseById(String courseId) {
        CourseInfoVO courseInfoVO = new CourseInfoVO();
        EduCourse course = baseMapper.selectById(courseId);
        BeanUtils.copyProperties(course,courseInfoVO);
        EduCourseDescription description = descriptionService.getById(courseId);
        if(description!=null){
            String desc = description.getDescription();
            courseInfoVO.setDescription(desc);
        }
        return courseInfoVO;
    }

    @Override
    public void updateCourseInfo(CourseInfoVO courseInfoVO) {
        //c.修改课程基本信息
        EduCourse course = new EduCourse();
        BeanUtils.copyProperties(courseInfoVO,course);
        baseMapper.updateById(course);
        //d.修改课程描述信息
        EduCourseDescription description = new EduCourseDescription();
        //e.它们两公用一个主键id
        description.setId(course.getId());
        description.setDescription(courseInfoVO.getDescription());
        descriptionService.updateById(description);
    }

    @Override
    public CourseComfirmVO queryCourseConfirmInfo(String courseId) {
        return baseMapper.queryCourseConfirmInfo(courseId);
    }

    @Override
    public void deleteCourse(String courseId) {
        //a.该课程所对应的章节
        chapterService.deleteChapterByCourseId(courseId);
        //b.该课程所对应的小节
        sectionService.deleteSectionByCourseId(courseId);
        //c.该课程所对应的描述信息
        descriptionService.removeById(courseId);
        //d.该课程所对应的基本信息
        baseMapper.deleteById(courseId);
        //e.整个过程需要用事务保证原子性
    }

    @Override
    public CourseDetailInfoVO queryCourseDetailById(String courseId) {
        return baseMapper.queryCourseDetailById(courseId);
    }
}
