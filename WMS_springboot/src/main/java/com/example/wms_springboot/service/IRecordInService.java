package com.example.wms_springboot.service;

import com.example.wms_springboot.entity.deposityRecord;

import java.util.List;

public interface IRecordInService {
    List<deposityRecord> findAllRecordIn();
}
