package com.example.wms_springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.wms_springboot.entity.deposityRecord;

import java.util.List;

public interface IRecordOutService extends IService<deposityRecord> {
    List<deposityRecord> findAllRecordOut();
}
