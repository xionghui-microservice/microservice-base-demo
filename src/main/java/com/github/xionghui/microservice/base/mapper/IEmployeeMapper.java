package com.github.xionghui.microservice.base.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.github.xionghui.microservice.base.bean.domain.EmployeeDomain;
import com.github.xionghuicoder.microservice.common.dao.IBaseDao;

@Mapper
public interface IEmployeeMapper extends IBaseDao<EmployeeDomain> {

  List<EmployeeDomain> query(String name);

  int countEmployeeName(String name);

  List<String> queryEmployeeNames();
}
