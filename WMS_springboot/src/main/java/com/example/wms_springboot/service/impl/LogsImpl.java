package com.example.wms_springboot.service.impl;



import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.wms_springboot.dao.LogsDao;
import com.example.wms_springboot.entity.logs;
import com.example.wms_springboot.service.ILogsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LogsImpl extends ServiceImpl<LogsDao, logs> implements ILogsService {


}
