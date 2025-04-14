package com.example.wms_springboot.controller;


import com.example.wms_springboot.entity.deposityRecord;
import com.example.wms_springboot.exception.CustomException;
import com.example.wms_springboot.service.IRecordInService;
import com.example.wms_springboot.utils.ResponseResult;
import com.example.wms_springboot.utils.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/recordIn")
public class RecordInController {

    @Autowired
    private IRecordInService recordInService;

    @GetMapping("/allRecordInData")
    public ResponseResult getDate(){

        List <deposityRecord> all=recordInService.findAllRecordIn();
        return ResponseResult.success(all);
//        return ResponseResult.success(recordInService.findAllRecordIn());

    }

}
