package com.example.wms_springboot.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.wms_springboot.dao.DeposityDao;
import com.example.wms_springboot.entity.Deposity;
import com.example.wms_springboot.service.IDeposityService;
import org.springframework.stereotype.Service;

@Service
public class DeposityImpl extends ServiceImpl<DeposityDao, Deposity> implements IDeposityService {


}
