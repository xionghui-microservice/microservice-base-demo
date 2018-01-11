package com.github.xionghui.microservice.base.dao;

import java.util.List;

import com.github.xionghui.microservice.base.bean.domain.EmployeeDomain;

public interface IEmployeeDao {

  void insertEmployee(EmployeeDomain bean);

  void updateEmployee(EmployeeDomain bean);

  void deleteEmployee(EmployeeDomain bean);

  int batchDeleteEmployee(List<EmployeeDomain> beanList);

  List<EmployeeDomain> queryEmployee(String name, int pageNum, int pageSize);

  int countEmployeeName(String name);

  List<String> queryEmployeeNames();
}
