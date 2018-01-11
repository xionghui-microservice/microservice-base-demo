package com.github.xionghui.microservice.base.bean.enums;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.xionghuicoder.microservice.common.bean.CommonConstants;
import com.github.xionghuicoder.microservice.common.bean.enums.ILanguageEnum;

public enum SexEnum implements ILanguageEnum {
  Male(true, "base-p00000"), // 男
  Female(false, "base-p00001"), // 女
  ;

  public final Boolean code;
  public final String languageCode;

  private SexEnum(Boolean code, String languageCode) {
    this.code = code;
    this.languageCode = languageCode;
  }

  private static final JSONArray VALUE_ARRAY = new JSONArray();
  private static final Map<Boolean, JSONObject> VALUE_MAP = new HashMap<>();
  private static final Set<Boolean> VALUE_SET = new HashSet<>();

  static {
    for (SexEnum theEnum : SexEnum.values()) {
      JSONObject value = new JSONObject();
      VALUE_ARRAY.add(value);
      value.put("code", theEnum.code);
      value.put(CommonConstants.LANGUAGE_CODE_ENUM, theEnum.getLanguageCode());

      VALUE_MAP.put(theEnum.code, value);
      VALUE_SET.add(theEnum.code);
    }
  }

  public static JSONArray getArray() {
    JSONArray copy = new JSONArray();
    for (Object obj : VALUE_ARRAY) {
      JSONObject json = (JSONObject) obj;
      copy.add(json.clone());
    }
    return copy;
  }

  public static JSONObject getValue(Boolean value) {
    JSONObject json = VALUE_MAP.get(value);
    if (json == null) {
      json = new JSONObject();
      json.put("code", value);
      return json;
    }
    return (JSONObject) json.clone();
  }

  public static boolean checkValue(Boolean value) {
    return VALUE_SET.contains(value);
  }

  @Override
  public String getLanguageCode() {
    return this.languageCode;
  }
}
