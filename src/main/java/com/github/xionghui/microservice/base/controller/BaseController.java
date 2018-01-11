package com.github.xionghui.microservice.base.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.xionghui.microservice.base.bean.BaseConstants;
import com.github.xionghuicoder.microservice.common.annotation.ControllerMappingAnnotation;
import com.github.xionghuicoder.microservice.common.bean.CommonConstants;
import com.github.xionghuicoder.microservice.common.bean.HttpResult;
import com.github.xionghuicoder.microservice.common.bean.enums.HttpResultEnum;
import com.github.xionghuicoder.microservice.common.bean.enums.LanguageEnum;
import com.github.xionghuicoder.microservice.common.controller.CommonController;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
@RequestMapping("/")
@ControllerMappingAnnotation("com.github.xionghui.microservice.base.service")
public class BaseController extends CommonController {

  static {
    CommonController.addNoLogFunction(BaseConstants.PAGE_LANGUAGE_FUNCTION);
  }

  @Override
  @HystrixCommand(fallbackMethod = "recieveFallback")
  @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST},
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @ResponseBody
  public HttpResult<?> recieve(HttpServletRequest request) {
    return super.recieve(request);
  }

  public HttpResult<?> recieveFallback(HttpServletRequest request) {
    // 需要设置多语，因为该方法在不同线程内执行
    String code = request.getHeader(CommonConstants.LANGUAGE_COOKIE_HEADER);
    LanguageEnum languageEnum = LanguageEnum.getLanguageEnum(code);
    return HttpResult.custom(HttpResultEnum.Failed).setLocale(languageEnum.locale).build();
  }
}
