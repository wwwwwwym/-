package com.example.wms_springboot.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.wms_springboot.dao.RecordInDao;
import com.example.wms_springboot.entity.deposityRecord;
import com.example.wms_springboot.service.IRecordInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RecordInImpl extends ServiceImpl<RecordInDao,deposityRecord> implements IRecordInService {

//    @Autowired
    @Resource
    private RecordInDao recordIndao;
    public List<deposityRecord> findAllRecordIn(){

        return recordIndao.selectList(new QueryWrapper<deposityRecord>()
                .eq("type",1).orderByDesc("record_id"));
    }
}
