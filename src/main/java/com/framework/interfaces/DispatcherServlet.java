package com.framework.interfaces;


import com.framework.controllerActionPair.ControllerActionPair;

import javax.servlet.http.HttpServletRequest;

public interface DispatcherServlet {

    ControllerActionPair dispatchRequest(HttpServletRequest request);

    String dispatchAction(HttpServletRequest request, ControllerActionPair controllerActionPair) throws NoSuchMethodException;


}
