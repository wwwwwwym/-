package com.example.wms_springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.wms_springboot.entity.recordIn;
import com.example.wms_springboot.entity.stockInfo;

import java.util.List;

public interface IRecordInService extends IService<recordIn> {
    List<recordIn> findUserRecordPending();

    List<recordIn> findUserRecordCompleted();
    List<recordIn> findUserRecordRejected();

    List<recordIn> findMyRecordPending();

    List<recordIn> findMyRecordCompleted();

    List<recordIn> findMyRecordRejected();
}
