package com.framework.controllerActionPair;


import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ControllerActionPair {

    private Class controllerClass;

    private Method method;

    private Map<String, Object> pathVariables;

    public ControllerActionPair(Class controllerClass, Method method) {
        this.controllerClass = controllerClass;
        this.method = method;
        this.pathVariables = new HashMap<>();
    }

    public Class getControllerClass() {
        return controllerClass;
    }

    public Method getMethod() {
        return method;
    }

    public Map<String, Object> getPathVariables() {
        return pathVariables;
    }

    public void setPathVariables(Map<String, Object> pathVariables) {
        this.pathVariables = pathVariables;
    }

    public void addPathVariables(String key, Object value) {
        this.pathVariables.put(key, value);
    }

    public Object getPathVariable(String key) {
        return this.pathVariables.get(key);
    }
}
