package com.github.xionghui.microservice.base.utils;

import java.util.HashSet;
import java.util.Set;

import com.alibaba.fastjson.JSONArray;

public class AgeYearUtils {
  private static final JSONArray VALUE_ARRAY = new JSONArray();
  private static final Set<Integer> VALUE_SET = new HashSet<>();

  static {
    for (int begin = 18, end = 65; begin <= end; begin++) {
      VALUE_ARRAY.add(begin);
      VALUE_SET.add(begin);
    }
  }

  public static JSONArray getValues() {
    JSONArray copy = new JSONArray();
    for (Object obj : VALUE_ARRAY) {
      copy.add(obj);
    }
    return copy;
  }

  public static boolean containsValue(Integer value) {
    return VALUE_SET.contains(value);
  }
}
