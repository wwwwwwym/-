package com.example.wms_springboot.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.wms_springboot.entity.recordIn;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


//@Mapper
public interface RecordInDao extends BaseMapper<recordIn> {
//    List<deposityRecord> findAllRecordIn();
//    @Select("select * from record where type='出货单'")
//    recordIn findAllRecordIn();
//    @Select("select * from record where type='进货单'")
//    recordIn findAllRecordOut();

}
