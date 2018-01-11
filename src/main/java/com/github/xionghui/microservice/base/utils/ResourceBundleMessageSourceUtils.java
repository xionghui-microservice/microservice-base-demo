package com.github.xionghui.microservice.base.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import com.alibaba.fastjson.JSONObject;
import com.github.xionghuicoder.microservice.common.BusinessException;
import com.github.xionghuicoder.microservice.common.bean.enums.LanguageEnum;
import com.github.xionghuicoder.microservice.common.utils.CommonResourceBundleMessageSourceUtils;
import com.github.xionghuicoder.microservice.common.utils.PropertiesUtils;
import com.google.common.io.Resources;

@Component
public class ResourceBundleMessageSourceUtils extends CommonResourceBundleMessageSourceUtils {
  private static final String LANGUAGE_PATH = "language/page/";

  private final Map<Locale, JSONObject> pageLanguageMap = new HashMap<>();

  @Override
  @PostConstruct
  public void init() {
    synchronized (this.pageLanguageMap) {
      this.initPageLanguage();
    }
    super.initDefaultPath();
  }

  public JSONObject clonePageLanguageJson(Locale locale) {
    JSONObject pageLanguageJson = this.pageLanguageMap.get(locale);
    return pageLanguageJson == null ? new JSONObject() : (JSONObject) pageLanguageJson.clone();
  }

  private void initPageLanguage() {
    String path = Resources.getResource(LANGUAGE_PATH).getFile();
    File pFile = new File(path);
    File[] files = pFile.listFiles();
    boolean hasDir = false;
    for (LanguageEnum languageEnum : LanguageEnum.values()) {
      PropertiesUtils propertiesUtils = new PropertiesUtils();
      ResourceBundle.Control controller = new ResourceBundle.Control() {};
      for (File f : files) {
        if (f.isFile()) {
          continue;
        }
        String[] fs = f.list();
        if (fs == null || fs.length == 0) {
          continue;
        }
        hasDir = true;
        String name = f.getName();
        String basename = LANGUAGE_PATH + name + "/" + name;
        this.initPage(basename, languageEnum.locale);
        this.checkKeyRepeat(basename, languageEnum.locale, propertiesUtils, controller);
      }
    }
    if (!hasDir) {
      throw new BusinessException("no dir under page");
    }
    // help gc
    ResourceBundle.clearCache(this.getBundleClassLoader());
  }

  private void initPage(String basename, Locale locale) {
    ResourceBundle bundle = this.doGetBundle(basename, locale);
    if (bundle == null) {
      throw new BusinessException(
          "basename: " + basename + " with locale: " + locale + " is illegal");
    }
    Enumeration<String> keys = bundle.getKeys();
    while (keys.hasMoreElements()) {
      String key = CommonResourceBundleMessageSourceUtils.charsetUtf8(keys.nextElement());
      String value = CommonResourceBundleMessageSourceUtils.charsetUtf8(bundle.getString(key));
      JSONObject pageLanguageJson = this.pageLanguageMap.get(locale);
      if (pageLanguageJson == null) {
        pageLanguageJson = new JSONObject();
        this.pageLanguageMap.put(locale, pageLanguageJson);
      }
      Object oldValue = pageLanguageJson.put(key, value);
      if (oldValue != null) {
        throw new BusinessException("page language put key repeat, key: " + key + ", value: "
            + value + ", oldValue: " + oldValue);
      }
    }
  }

  private void checkKeyRepeat(String basename, Locale locale, PropertiesUtils propertiesUtils,
      ResourceBundle.Control controller) {
    ClassLoader classLoader = ClassUtils.getDefaultClassLoader();
    String path =
        controller.toResourceName(controller.toBundleName(basename, locale), "properties");
    try {
      InputStream is = classLoader.getResourceAsStream(path);
      propertiesUtils.load(path, is);
    } catch (IOException e) {
      throw new BusinessException(e);
    } catch (NullPointerException e) {
      throw new BusinessException("load " + path + " NullPointerException", e);
    }
  }
}
