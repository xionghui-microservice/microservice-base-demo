package com.github.xionghui.microservice.base.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.xionghui.microservice.base.bean.domain.EmployeeDomain;
import com.github.xionghui.microservice.base.bean.enums.SexEnum;
import com.github.xionghui.microservice.base.dao.IEmployeeDao;
import com.github.xionghui.microservice.base.service.IEmployeeService;
import com.github.xionghui.microservice.base.utils.AgeYearUtils;
import com.github.xionghuicoder.microservice.common.BusinessException;
import com.github.xionghuicoder.microservice.common.annotation.EnablePathConfigAnnotation;
import com.github.xionghuicoder.microservice.common.annotation.MenuAnnotation;
import com.github.xionghuicoder.microservice.common.annotation.PathConfigAnnotation;
import com.github.xionghuicoder.microservice.common.bean.CommonConstants;
import com.github.xionghuicoder.microservice.common.bean.HttpResult;
import com.github.xionghuicoder.microservice.common.bean.ServiceParamsBean;
import com.github.xionghuicoder.microservice.common.bean.enums.HttpRequestMethod;
import com.github.xionghuicoder.microservice.common.bean.enums.HttpResultEnum;
import com.github.xionghuicoder.microservice.common.utils.BatchOperationParamsUtils;
import com.github.xionghuicoder.microservice.common.utils.CommonJsonUtils;

@Component
@EnablePathConfigAnnotation
@MenuAnnotation("base/employee")
public class EmployeeServiceImpl implements IEmployeeService {
  private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceImpl.class);

  @Autowired
  private ApplicationContext applicationContext;

  private static final String[] QUERY_KEY = new String[] {"uuid", "name", "age", "sex", "note"};

  @Override
  @PathConfigAnnotation(value = "queryEmployeeAges", method = HttpRequestMethod.GET)
  public HttpResult<?> queryEmployeeAges(ServiceParamsBean serviceParamsBean) {
    LOGGER.info("queryEmployeeAges begin");
    JSONArray data = AgeYearUtils.getValues();
    LOGGER.info("queryEmployeeAges end");
    return HttpResult.custom(HttpResultEnum.Success).setData(data).build();
  }

  @Override
  @PathConfigAnnotation(value = "queryEmployeeSexes", method = HttpRequestMethod.GET)
  public HttpResult<?> queryEmployeeSexes(ServiceParamsBean serviceParamsBean) {
    LOGGER.info("queryEmployeeSexes begin");
    JSONArray data = SexEnum.getArray();
    LOGGER.info("queryEmployeeSexes end");
    return HttpResult.custom(HttpResultEnum.Success).setData(data).build();
  }

  @Override
  @PathConfigAnnotation(value = "insertEmployee", method = HttpRequestMethod.POST)
  public HttpResult<?> insertEmployee(ServiceParamsBean serviceParamsBean) {
    LOGGER.info("insertEmployee begin");
    EmployeeDomain bean = JSON.parseObject(serviceParamsBean.getBody(), EmployeeDomain.class);
    String creator = serviceParamsBean.getUser().getEmpId();
    bean.setCreator(creator);
    IEmployeeDao iEmployeeDao = this.applicationContext.getBean(IEmployeeDao.class);
    iEmployeeDao.insertEmployee(bean);
    JSONObject result = new JSONObject();
    result.put(CommonConstants.UUID, bean.getUuid());
    LOGGER.info("insertEmployee end");
    return HttpResult.custom(HttpResultEnum.InsertSuccess).setData(result).build();
  }

  @Override
  @PathConfigAnnotation(value = "updateEmployee", method = HttpRequestMethod.POST)
  public HttpResult<?> updateEmployee(ServiceParamsBean serviceParamsBean) {
    LOGGER.info("updateEmployee begin");
    EmployeeDomain bean = BatchOperationParamsUtils
        .dealUpdateParams(serviceParamsBean.getBodyJson(), EmployeeDomain.class);
    String updater = serviceParamsBean.getUser().getEmpId();
    bean.setUpdater(updater);
    IEmployeeDao iEmployeeDao = this.applicationContext.getBean(IEmployeeDao.class);
    iEmployeeDao.updateEmployee(bean);
    LOGGER.info("updateEmployee end");
    return HttpResult.custom(HttpResultEnum.UpdateSuccess).build();
  }

  @Override
  @PathConfigAnnotation(value = "deleteEmployee", method = HttpRequestMethod.POST)
  public HttpResult<?> deleteEmployee(ServiceParamsBean serviceParamsBean) {
    LOGGER.info("deleteEmployee begin");
    EmployeeDomain bean = BatchOperationParamsUtils
        .dealDeleteParams(serviceParamsBean.getBodyJson(), EmployeeDomain.class);
    IEmployeeDao iEmployeeDao = this.applicationContext.getBean(IEmployeeDao.class);
    iEmployeeDao.deleteEmployee(bean);
    LOGGER.info("deleteEmployee end");
    return HttpResult.custom(HttpResultEnum.DeleteSuccess).build();
  }

  @Override
  @PathConfigAnnotation(value = "batchDeleteEmployee", method = HttpRequestMethod.POST)
  public HttpResult<?> batchDeleteEmployee(ServiceParamsBean serviceParamsBean) {
    LOGGER.info("batchDeleteEmployee begin");
    List<EmployeeDomain> beanList = BatchOperationParamsUtils
        .dealBatchDeleteParams(serviceParamsBean.getBodyJson(), EmployeeDomain.class);
    IEmployeeDao iEmployeeDao = this.applicationContext.getBean(IEmployeeDao.class);
    int count = iEmployeeDao.batchDeleteEmployee(beanList);
    LOGGER.info("batchDeleteEmployee end");
    return HttpResult.custom(HttpResultEnum.BatchDeleteSuccess).setArgs(String.valueOf(count))
        .build();
  }

  @Override
  @PathConfigAnnotation(value = "queryEmployee", method = HttpRequestMethod.GET)
  public HttpResult<?> queryEmployee(ServiceParamsBean serviceParamsBean) {
    LOGGER.info("queryEmployee begin");
    JSONObject bodyJson = serviceParamsBean.getBodyJson();
    Integer pageNum = bodyJson.getInteger("pageNum");
    Integer pageSize = bodyJson.getInteger("pageSize");
    if (pageNum == null || pageNum <= 0 || pageSize == null || pageSize <= 0) {
      throw new BusinessException("params is not full", HttpResultEnum.ParamsError);
    }
    String name = bodyJson.getString("name");
    IEmployeeDao iEmployeeDao = this.applicationContext.getBean(IEmployeeDao.class);
    Page<EmployeeDomain> domainPage =
        (Page<EmployeeDomain>) iEmployeeDao.queryEmployee(name, pageNum, pageSize);
    JSONObject result = new JSONObject();
    result.put("total", domainPage.getTotal());
    result.put("pages", domainPage.getPages());
    result.put("pageNum", domainPage.getPageNum());
    result.put("pageSize", domainPage.getPageSize());
    JSONArray data = new JSONArray();
    result.put("data", data);
    for (EmployeeDomain domain : domainPage) {
      JSONObject json = CommonJsonUtils.object2Json(domain, QUERY_KEY);
      data.add(json);

      Boolean sex = json.getBoolean("sex");
      json.put("sex", SexEnum.getValue(sex));
    }
    LOGGER.info("queryEmployee end");
    return HttpResult.custom(HttpResultEnum.QuerySuccess).setData(result).build();
  }

  @Override
  @PathConfigAnnotation(value = "checkEmployeeName", supportZuul = false, supportFeign = true,
      method = HttpRequestMethod.GET)
  public HttpResult<?> checkEmployeeName(ServiceParamsBean serviceParamsBean) {
    LOGGER.info("checkEmployeeName begin");
    String employeeName = serviceParamsBean.getBodyJson().getString("employeeName");
    if (employeeName == null) {
      throw new BusinessException("employeeName is null", HttpResultEnum.ParamsError);
    }
    IEmployeeDao iEmployeeDao = this.applicationContext.getBean(IEmployeeDao.class);
    int count = iEmployeeDao.countEmployeeName(employeeName);
    LOGGER.info("checkEmployeeName end");
    return HttpResult.custom(HttpResultEnum.QuerySuccess).setData(count > 0).build();
  }

  @Override
  @PathConfigAnnotation(value = "queryEmployeeNames", supportZuul = false, supportFeign = true,
      method = HttpRequestMethod.GET)
  public HttpResult<?> queryEmployeeNames(ServiceParamsBean serviceParamsBean) {
    LOGGER.info("queryEmployeeNames begin");
    IEmployeeDao iEmployeeDao = this.applicationContext.getBean(IEmployeeDao.class);
    List<String> employeeNameList = iEmployeeDao.queryEmployeeNames();
    JSONArray result = new JSONArray();
    for (String employeeName : employeeNameList) {
      result.add(employeeName);
    }
    LOGGER.info("queryEmployeeNames end");
    return HttpResult.custom(HttpResultEnum.QuerySuccess).setData(result).build();
  }
}
