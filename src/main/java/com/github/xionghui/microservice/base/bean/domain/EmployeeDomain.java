package com.github.xionghui.microservice.base.bean.domain;

import com.github.xionghuicoder.microservice.common.bean.CommonDomain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class EmployeeDomain extends CommonDomain {

  private String name;
  private Integer age;
  private Boolean sex;

}
