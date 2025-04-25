package com.example.wms_springboot.service.impl;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.wms_springboot.dao.RecordOutDao;
import com.example.wms_springboot.dao.UserDao;
import com.example.wms_springboot.entity.User;
import com.example.wms_springboot.entity.deposityRecord;
import com.example.wms_springboot.service.IRecordOutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RecordOutImpl extends ServiceImpl<RecordOutDao, deposityRecord> implements IRecordOutService {


    @Resource
    private RecordOutDao recordOutDao;
    public List<deposityRecord> findAllRecordOut(){

        return recordOutDao.selectList(new QueryWrapper<deposityRecord>()
                .eq("type",0).orderByDesc("record_id"));
    }
}
