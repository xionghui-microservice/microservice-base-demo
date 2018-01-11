package com.github.xionghui.microservice.base.service.impl;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.github.xionghui.microservice.base.bean.BaseConstants;
import com.github.xionghui.microservice.base.service.ILanguageService;
import com.github.xionghui.microservice.base.utils.ResourceBundleMessageSourceUtils;
import com.github.xionghuicoder.microservice.common.annotation.EnablePathConfigAnnotation;
import com.github.xionghuicoder.microservice.common.annotation.PathConfigAnnotation;
import com.github.xionghuicoder.microservice.common.bean.HttpResult;
import com.github.xionghuicoder.microservice.common.bean.ServiceParamsBean;
import com.github.xionghuicoder.microservice.common.bean.enums.HttpRequestMethod;
import com.github.xionghuicoder.microservice.common.bean.enums.HttpResultEnum;
import com.github.xionghuicoder.microservice.common.controller.CommonController;

@Component
@EnablePathConfigAnnotation
public class LanguageServiceImpl implements ILanguageService {
  private static final Logger LOGGER = LoggerFactory.getLogger(LanguageServiceImpl.class);

  @Autowired
  private ResourceBundleMessageSourceUtils ResourceBundleMessageSourceUtils;

  @Override
  @PathConfigAnnotation(value = BaseConstants.PAGE_LANGUAGE_FUNCTION,
      method = HttpRequestMethod.GET)
  public HttpResult<?> pageLanguage(ServiceParamsBean serviceParamsBean) {
    LOGGER.info("pageLanguage begin");
    Locale locale = CommonController.getLocale();
    JSONObject result = this.ResourceBundleMessageSourceUtils.clonePageLanguageJson(locale);
    LOGGER.info("pageLanguage end");
    return HttpResult.custom(HttpResultEnum.Success).setData(result).build();
  }
}
