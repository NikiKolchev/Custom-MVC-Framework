package com.framework.model;


import com.framework.interfaces.Model;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class ModelImpl implements Model{

    private HttpServletRequest request;

    private Map<String, Object> attributes;

    public ModelImpl(HttpServletRequest request) {
        this.request = request;
        this.attributes = new HashMap<>();
    }



    @Override
    public void addAttribute(String key, Object value) {
        this.attributes.put(key, value);
        this.sendParametersToView();
    }

    @Override
    public Map<String, Object> getAttribute() {
        return this.attributes;
    }

    private void sendParametersToView() {
        for (Map.Entry<String, Object> entry : attributes.entrySet()) {
            this.request.setAttribute(entry.getKey(), entry.getValue());
        }
    }
}
