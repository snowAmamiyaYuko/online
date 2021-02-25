package com.atguigu.edu.service;

import com.atguigu.edu.entity.EduSubject;
import com.atguigu.response.SubjectVo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author zhangqiang
 * @since 2020-11-06
 */
public interface EduSubjectService extends IService<EduSubject> {

    void uploadSubject(MultipartFile file) throws Exception;

    //判断条件是标题与parentId不同
    EduSubject existSubject(String title, String parentId);

    List<SubjectVo> getAllSubject();

    boolean deleteSubjectById(String id);

    boolean saveParentSubject(EduSubject subject);

    boolean saveChildSubject(EduSubject subject);
}
