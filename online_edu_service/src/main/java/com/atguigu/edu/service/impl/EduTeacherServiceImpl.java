package com.atguigu.edu.service.impl;

import com.atguigu.edu.entity.EduTeacher;
import com.atguigu.edu.mapper.EduTeacherMapper;
import com.atguigu.edu.service.EduTeacherService;
import com.atguigu.request.TeacherConditionVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author zhangqiang
 * @since 2020-10-31
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Override
    public void queryTeacherPageByCondition(Page<EduTeacher> teacherPage, TeacherConditionVO teacherConditionVO) {
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        String teacherName = teacherConditionVO.getName();
        Integer level = teacherConditionVO.getLevel();
        String beginTime = teacherConditionVO.getBeginTime();
        String endTime = teacherConditionVO.getEndTime();
        //判断讲师名称是否为空
        if(StringUtils.isNotEmpty(teacherName)){
            wrapper.like("name",teacherName);
        }
        if(level!=null){
            wrapper.eq("level",level);
        }
        if(StringUtils.isNotEmpty(beginTime)){
            wrapper.ge("gmt_create",beginTime);
        }
        if(StringUtils.isNotEmpty(endTime)){
            wrapper.le("gmt_create",endTime);
        }
        wrapper.orderByDesc("gmt_create");
        baseMapper.selectPage(teacherPage,wrapper);
    }

    @Override
    public Map<String, Object> queryTeacherPage(Page<EduTeacher> teacherPage) {
        baseMapper.selectPage(teacherPage, null);
        boolean hasPrevious = teacherPage.hasPrevious();
        boolean hasNext = teacherPage.hasNext();
        long currentPage = teacherPage.getCurrent();
        long pages = teacherPage.getPages();
        long total = teacherPage.getTotal();
        long size = teacherPage.getSize();
        List<EduTeacher> teacherList = teacherPage.getRecords();

        Map<String, Object> retMap = new HashMap<String, Object>();
        retMap.put("teacherList", teacherList);
        retMap.put("pages", pages);
        retMap.put("total", total);
        retMap.put("currentPage", currentPage);
        retMap.put("size", size);
        retMap.put("hasNext", hasNext);
        retMap.put("hasPrevious", hasPrevious);
        return retMap;
    }
}
