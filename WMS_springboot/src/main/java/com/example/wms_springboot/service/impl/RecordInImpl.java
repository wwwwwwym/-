package com.example.wms_springboot.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.wms_springboot.dao.RecordInDao;
import com.example.wms_springboot.entity.User;
import com.example.wms_springboot.entity.recordIn;
import com.example.wms_springboot.service.IRecordInService;
import com.example.wms_springboot.utils.TokenUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RecordInImpl extends ServiceImpl<RecordInDao, recordIn> implements IRecordInService {

//    @Autowired
    @Resource
    private RecordInDao recordIndao;
    /**
     * 查找审核人为当前用户的申请
     */
    public List<recordIn> findUserRecordPending(){

        User currentUser = TokenUtils.getCurrentUser();
        return recordIndao.selectList(new QueryWrapper<recordIn>()
                .eq("state","待审核").eq("review_id",currentUser.getUserid()));
    }
    public List<recordIn> findUserRecordCompleted(){
        User currentUser = TokenUtils.getCurrentUser();
        return recordIndao.selectList(new QueryWrapper<recordIn>()
                .eq("review_id",currentUser.getUserid()).eq("state","已完成")
                .orderByDesc("review_time").last("limit 0,10"));
    }


    public List<recordIn> findMyRecordPending(){

        User currentUser = TokenUtils.getCurrentUser();
        return recordIndao.selectList(new QueryWrapper<recordIn>()
                .eq("state","待审核").eq("apply_id",currentUser.getUserid()));
    }
    public List<recordIn> findMyRecordCompleted(){
        User currentUser = TokenUtils.getCurrentUser();
        return recordIndao.selectList(new QueryWrapper<recordIn>()
                .eq("apply_id",currentUser.getUserid()).eq("state","已完成")
                .orderByDesc("review_time").last("limit 0,10"));
    }

}
