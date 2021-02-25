package com.atguigu.handler;

import com.atguigu.edu.service.VideoServiceFeign;
import com.atguigu.response.RetVal;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class VideoServiceHandler implements VideoServiceFeign {
    @Override
    public RetVal deleteSingleVideo(String videoId) {
        //真实环境中你需要做好多业务逻辑
        return RetVal.error().message("返回兜底数据");
    }

    @Override
    public RetVal deleteMultiVideo(List<String> videoIdList) {
        //真实环境中你需要做好多业务逻辑
        return RetVal.error().message("返回兜底数据");
    }
}
