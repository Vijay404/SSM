package com.vijay.dao;

import com.vijay.domain.SysLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SysLogDao {
    //保存操作日志
    @Insert("insert into sysLog(visitTime,username,ip,url,executionTime,method) values(#{visitTime},#{username},#{ip},#{url},#{executionTime},#{method})")
    void saveLog(SysLog sysLog) throws Exception;

    //查询所有的日志信息
    @Select("select * from sysLog")
    List<SysLog> findAll()throws Exception;
}
