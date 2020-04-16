package com.vijay.service;

import com.vijay.domain.SysLog;

import java.util.List;

public interface SysLogService {

    void saveLog(SysLog sysLog) throws Exception;

    List<SysLog> findAll(int page,int size) throws Exception;
}
