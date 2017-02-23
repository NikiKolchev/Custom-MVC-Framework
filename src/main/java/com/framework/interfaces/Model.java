package com.framework.interfaces;


import java.util.Map;

public interface Model {

    void addAttribute(String key, Object value);

    Map<String, Object> getAttribute();

}
