package com.github.xionghui.microservice.base.bean.enums;

import com.github.xionghuicoder.microservice.common.bean.enums.IHttpResultEnum;

public enum BaseHttpResultEnum implements IHttpResultEnum {
  EmployeeNameIllegal(1000, "m00000"), // 员工名称格式不正确
  EmployeeAgeIllegal(1002, "m00001"), // 员工年龄不正确
  ;

  public final int code;
  public final String languageCode;

  private BaseHttpResultEnum(int code, String languageCode) {
    this.code = code;
    this.languageCode = languageCode;
  }

  @Override
  public int getCode() {
    return this.code;
  }

  @Override
  public String getLanguageCode() {
    return this.languageCode;
  }
}
