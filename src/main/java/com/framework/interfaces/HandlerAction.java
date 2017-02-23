package com.framework.interfaces;

import com.framework.controllerActionPair.ControllerActionPair;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;

public interface HandlerAction {

    String executeControllerAction(HttpServletRequest request, ControllerActionPair controllerActionPair)
            throws InvocationTargetException, IllegalAccessException, InstantiationException, IllegalAccessException, NoSuchMethodException;


}
