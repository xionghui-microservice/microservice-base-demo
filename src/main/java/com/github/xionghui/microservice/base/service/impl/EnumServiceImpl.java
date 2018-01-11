package com.github.xionghui.microservice.base.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.github.xionghui.microservice.base.bean.enums.SexEnum;
import com.github.xionghui.microservice.base.service.IEnumService;
import com.github.xionghuicoder.microservice.common.BusinessException;
import com.github.xionghuicoder.microservice.common.annotation.EnablePathConfigAnnotation;
import com.github.xionghuicoder.microservice.common.annotation.PathConfigAnnotation;
import com.github.xionghuicoder.microservice.common.bean.HttpResult;
import com.github.xionghuicoder.microservice.common.bean.ServiceParamsBean;
import com.github.xionghuicoder.microservice.common.bean.enums.HttpRequestMethod;
import com.github.xionghuicoder.microservice.common.bean.enums.HttpResultEnum;

@Component
@EnablePathConfigAnnotation
public class EnumServiceImpl implements IEnumService {
  private static final Logger LOGGER = LoggerFactory.getLogger(EnumServiceImpl.class);

  @Override
  @PathConfigAnnotation(value = "querySex", supportZuul = false, supportFeign = true,
      method = HttpRequestMethod.GET)
  public HttpResult<?> querySex(ServiceParamsBean serviceParamsBean) {
    LOGGER.info("querySex begin");
    JSONArray data = SexEnum.getArray();
    LOGGER.info("querySex end");
    return HttpResult.custom(HttpResultEnum.Success).setData(data).build();
  }

  @Override
  @PathConfigAnnotation(value = "checkSex", supportZuul = false, supportFeign = true,
      method = HttpRequestMethod.GET)
  public HttpResult<?> checkSex(ServiceParamsBean serviceParamsBean) {
    LOGGER.info("checkSex begin");
    Boolean sex = serviceParamsBean.getBodyJson().getBoolean("sex");
    if (sex == null) {
      throw new BusinessException("requireType is null", HttpResultEnum.ParamsError);
    }
    boolean result = SexEnum.checkValue(sex);
    LOGGER.info("checkSex end");
    return HttpResult.custom(HttpResultEnum.Success).setData(result).build();
  }
}
