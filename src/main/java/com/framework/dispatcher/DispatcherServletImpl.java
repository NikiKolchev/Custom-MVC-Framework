package com.framework.dispatcher;

import com.framework.controllerActionPair.ControllerActionPair;
import com.framework.handler.HandlerActionImpl;
import com.framework.handler.HandlerMappingImpl;
import com.framework.interfaces.DispatcherServlet;
import com.framework.interfaces.HandlerAction;
import com.framework.interfaces.HandlerMapping;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;


@WebServlet("/")
public class DispatcherServletImpl extends HttpServlet implements DispatcherServlet {

    private HandlerMapping handlerMapping;

    private HandlerAction handlerAction;

    public DispatcherServletImpl() {
        this.handlerMapping = new HandlerMappingImpl();
        this.handlerAction = new HandlerActionImpl();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            this.handleRequest(request, response);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ControllerActionPair controllerActionPair = this.dispatchRequest(request);
        if(controllerActionPair != null) {
            response.getWriter().print(controllerActionPair.getControllerClass().getSimpleName());
        }

        try {
            this.handleRequest(request, response);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ControllerActionPair dispatchRequest(HttpServletRequest request) {
        ControllerActionPair controllerActionPair = null;

        try {
            controllerActionPair = this.handlerMapping.findController(request);
        } catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return controllerActionPair;
    }

    @Override
    public String dispatchAction(HttpServletRequest request, ControllerActionPair controllerActionPair) throws NoSuchMethodException {
        String view = null;

        try {
            view = this.handlerAction.executeControllerAction(request, controllerActionPair);
        } catch (InvocationTargetException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return view;
    }

    private void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, NoSuchMethodException {
        ControllerActionPair controllerActionPair = this.dispatchRequest(request);
        String view = this.dispatchAction(request, controllerActionPair);
        request.getRequestDispatcher("/" + view + ".jsp").forward(request, response);
    }
}
