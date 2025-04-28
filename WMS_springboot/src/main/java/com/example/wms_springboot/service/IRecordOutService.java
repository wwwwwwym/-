package com.example.wms_springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.wms_springboot.entity.recordIn;
import com.example.wms_springboot.entity.recordOut;

import java.util.List;

public interface IRecordOutService extends IService<recordOut> {
    List<recordOut> findAllRecordOut();
}
