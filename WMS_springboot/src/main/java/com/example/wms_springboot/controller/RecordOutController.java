package com.example.wms_springboot.controller;


import com.example.wms_springboot.entity.deposityRecord;

import com.example.wms_springboot.service.IRecordOutService;
import com.example.wms_springboot.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/recordOut")
public class RecordOutController {

    @Autowired
    private IRecordOutService recordOutService;

    @GetMapping("/allRecordOutData")
    public ResponseResult getDate(){

        List <deposityRecord> all=recordOutService.findAllRecordOut();
        return ResponseResult.success(all);
//        return ResponseResult.success(recordInService.findAllRecordIn());

    }

}
