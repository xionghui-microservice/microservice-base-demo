package com.github.xionghui.microservice.base.dao.impl;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.page.PageMethod;
import com.github.xionghui.microservice.base.bean.domain.EmployeeDomain;
import com.github.xionghui.microservice.base.dao.IEmployeeDao;
import com.github.xionghui.microservice.base.dao.rule.employee.CheckEmployeeParamsInsertUpdateBeforeRule;
import com.github.xionghui.microservice.base.mapper.IEmployeeMapper;
import com.github.xionghuicoder.microservice.common.dao.AbstractBaseDao;
import com.github.xionghuicoder.microservice.common.dao.rule.IBeforeRule;

@Repository
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class EmployeeDaoImpl extends AbstractBaseDao<EmployeeDomain> implements IEmployeeDao {

  @Autowired
  private IEmployeeMapper iEmployeeMapper;

  @PostConstruct
  public void initIBaseDao() {
    this.iBaseDao = this.iEmployeeMapper;
  }

  @Override
  public void insertEmployee(EmployeeDomain bean) {
    IBeforeRule<EmployeeDomain> checkEmployeeParamsInsertBeforeRule =
        new CheckEmployeeParamsInsertUpdateBeforeRule();
    this.addBeforeRule(checkEmployeeParamsInsertBeforeRule);

    this.insert(bean);
  }

  @Override
  public void updateEmployee(EmployeeDomain bean) {
    IBeforeRule<EmployeeDomain> checkEmployeeParamsInsertUpdateBeforeRule =
        new CheckEmployeeParamsInsertUpdateBeforeRule();
    this.addBeforeRule(checkEmployeeParamsInsertUpdateBeforeRule);

    this.update(bean);
  }

  @Override
  public void deleteEmployee(EmployeeDomain bean) {
    super.delete(bean);
  }

  @Override
  public int batchDeleteEmployee(List<EmployeeDomain> beanList) {
    return super.batchDelete(beanList);
  }

  @Override
  @Transactional(readOnly = true)
  public List<EmployeeDomain> queryEmployee(String name, int pageNum, int pageSize) {
    PageMethod.startPage(pageNum, pageSize);
    if (name != null) {
      name = name.trim();
      if (name.length() == 0) {
        name = null;
      } else {
        name = "%" + name + "%";
      }
    }
    List<EmployeeDomain> domainList = this.iEmployeeMapper.query(name);
    return domainList;
  }

  @Override
  @Transactional(readOnly = true)
  public int countEmployeeName(String name) {
    return this.iEmployeeMapper.countEmployeeName(name);
  }

  @Override
  @Transactional(readOnly = true)
  public List<String> queryEmployeeNames() {
    return this.iEmployeeMapper.queryEmployeeNames();
  }
}
