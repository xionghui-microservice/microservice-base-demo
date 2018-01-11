package com.github.xionghui.microservice.base.service;

import com.github.xionghuicoder.microservice.common.bean.HttpResult;
import com.github.xionghuicoder.microservice.common.bean.ServiceParamsBean;

public interface ILanguageService {

  HttpResult<?> pageLanguage(ServiceParamsBean serviceParamsBean);
}
