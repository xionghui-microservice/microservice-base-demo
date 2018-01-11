package com.github.xionghui.microservice.base.service;

import com.github.xionghuicoder.microservice.common.bean.HttpResult;
import com.github.xionghuicoder.microservice.common.bean.ServiceParamsBean;

public interface IEmployeeService {

  HttpResult<?> queryEmployeeAges(ServiceParamsBean serviceParamsBean);

  HttpResult<?> queryEmployeeSexes(ServiceParamsBean serviceParamsBean);

  HttpResult<?> insertEmployee(ServiceParamsBean serviceParamsBean);

  HttpResult<?> updateEmployee(ServiceParamsBean serviceParamsBean);

  HttpResult<?> deleteEmployee(ServiceParamsBean serviceParamsBean);

  HttpResult<?> batchDeleteEmployee(ServiceParamsBean serviceParamsBean);

  HttpResult<?> queryEmployee(ServiceParamsBean serviceParamsBean);

  HttpResult<?> checkEmployeeName(ServiceParamsBean serviceParamsBean);

  HttpResult<?> queryEmployeeNames(ServiceParamsBean serviceParamsBean);
}
