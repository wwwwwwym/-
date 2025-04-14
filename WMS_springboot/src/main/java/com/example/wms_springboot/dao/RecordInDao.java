package com.example.wms_springboot.dao;


import com.example.wms_springboot.entity.deposityRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RecordInDao {
    List<deposityRecord> findAllRecordIn();

}
