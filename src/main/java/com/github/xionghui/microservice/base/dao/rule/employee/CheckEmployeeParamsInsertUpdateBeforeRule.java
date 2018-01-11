package com.github.xionghui.microservice.base.dao.rule.employee;

import com.github.xionghui.microservice.base.bean.BaseConstants;
import com.github.xionghui.microservice.base.bean.domain.EmployeeDomain;
import com.github.xionghui.microservice.base.bean.enums.BaseHttpResultEnum;
import com.github.xionghui.microservice.base.utils.AgeYearUtils;
import com.github.xionghuicoder.microservice.common.BusinessException;
import com.github.xionghuicoder.microservice.common.bean.enums.HttpResultEnum;
import com.github.xionghuicoder.microservice.common.dao.rule.IBeforeRule;

public class CheckEmployeeParamsInsertUpdateBeforeRule implements IBeforeRule<EmployeeDomain> {

  @Override
  public void beforeRule(EmployeeDomain bean, EmployeeDomain originBean) {
    String name = bean.getName();
    Integer age = bean.getAge();
    Boolean sex = bean.getSex();
    if (name == null || age == null || sex == null) {
      throw new BusinessException("params is not full", HttpResultEnum.ParamsError);
    }

    boolean isNameMatch = name.matches(BaseConstants.NAME_REGIX);
    if (!isNameMatch) {
      throw new BusinessException("name is illegal", BaseHttpResultEnum.EmployeeNameIllegal);
    }

    boolean valid = AgeYearUtils.containsValue(age);
    if (!valid) {
      throw new BusinessException("age is illegal", BaseHttpResultEnum.EmployeeAgeIllegal);
    }
  }
}
