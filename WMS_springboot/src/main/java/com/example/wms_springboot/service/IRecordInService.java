package com.example.wms_springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.wms_springboot.entity.recordIn;

import java.util.List;

public interface IRecordInService extends IService<recordIn> {
    List<recordIn> findAllRecordIn();

    List<recordIn> findAllRecordOut();
}
