package com.atguigu.edu.service.impl;

import com.atguigu.edu.entity.EduSection;
import com.atguigu.edu.mapper.EduSectionMapper;
import com.atguigu.edu.service.EduSectionService;
import com.atguigu.edu.service.VideoServiceFeign;
import com.atguigu.exception.EduException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程小节 服务实现类
 * </p>
 *
 * @author zhangqiang
 * @since 2021-02-19
 */
@Service
public class EduSectionServiceImpl extends ServiceImpl<EduSectionMapper, EduSection> implements EduSectionService {
    @Autowired
    private VideoServiceFeign videoServiceFeign;

    @Override
    public void addSection(EduSection section) {
        //1.判断是否存在小节
        QueryWrapper<EduSection> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id",section.getCourseId());
        queryWrapper.eq("chapter_id",section.getChapterId());
        queryWrapper.eq("title",section.getTitle());
        EduSection existSection = baseMapper.selectOne(queryWrapper);
        if(existSection==null){
            baseMapper.insert(section);
        }else{
            throw new EduException(20001,"存在重复的小节");
        }
    }

    @Override
    public void deleteSection(String id) {
        //根据小节的id查询小节所对应的视频id
        EduSection section = baseMapper.selectById(id);
        String videoSourceId = section.getVideoSourceId();
        if(StringUtils.isNotEmpty(videoSourceId)){
            //通过远程方式调用video微服务删除视频
            videoServiceFeign.deleteSingleVideo(videoSourceId);
        }
        baseMapper.deleteById(id);
    }

    @Override
    public void deleteSectionByCourseId(String courseId) {
        //通过课程id找到该课程所有的小节
        QueryWrapper<EduSection> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        List<EduSection> sectionList = baseMapper.selectList(wrapper);
        //迭代所有的小节 找到所有的视频id封装成一个list
        List<String> videoIdList = new ArrayList<>();
        for (EduSection section : sectionList) {
            String videoSourceId = section.getVideoSourceId();
            if(StringUtils.isNotEmpty(videoSourceId)){
                videoIdList.add(videoSourceId);
            }
        }
        //远程调用video微服务 执行删除
        videoServiceFeign.deleteMultiVideo(videoIdList);
        //最后删除所有小节信息
        baseMapper.delete(wrapper);
    }
}
