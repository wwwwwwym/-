package com.example.wms_springboot.service.impl;


import com.example.wms_springboot.dao.RecordInDao;
import com.example.wms_springboot.entity.deposityRecord;
import com.example.wms_springboot.service.IRecordInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecordInImpl implements IRecordInService {

    @Autowired
    private RecordInDao recordIndao;
    public List<deposityRecord> findAllRecordIn(){

        return recordIndao.findAllRecordIn();
    }
}
