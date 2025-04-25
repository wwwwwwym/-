package com.example.wms_springboot.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.wms_springboot.entity.deposityRecord;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;

//@Mapper
public interface RecordInDao extends BaseMapper<deposityRecord> {
//    List<deposityRecord> findAllRecordIn();

}
