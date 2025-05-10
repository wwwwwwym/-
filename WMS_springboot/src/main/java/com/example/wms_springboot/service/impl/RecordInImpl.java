package com.example.wms_springboot.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.wms_springboot.dao.RecordInDao;
import com.example.wms_springboot.entity.recordIn;
import com.example.wms_springboot.service.IRecordInService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RecordInImpl extends ServiceImpl<RecordInDao, recordIn> implements IRecordInService {

//    @Autowired
    @Resource
    private RecordInDao recordIndao;
    public List<recordIn> findAllRecordIn(){

        return recordIndao.selectList(new QueryWrapper<recordIn>()
                .eq("type","进货单"));
    }
    public List<recordIn> findAllRecordOut(){

        return recordIndao.selectList(new QueryWrapper<recordIn>()
                .eq("type","出货单"));
    }

}
