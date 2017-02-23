package com.framework.handler;

import com.framework.annotations.parameters.PathVariable;
import com.framework.annotations.parameters.RequestVariable;
import com.framework.controllerActionPair.ControllerActionPair;
import com.framework.interfaces.HandlerAction;
import com.framework.model.ModelImpl;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;


public class HandlerActionImpl implements HandlerAction {



    @Override
    public String executeControllerAction(HttpServletRequest request, ControllerActionPair controllerActionPair) throws InvocationTargetException, IllegalAccessException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        //TODO Get the controller and it respective method to execute
        Class controller = controllerActionPair.getControllerClass();
        Method method = controllerActionPair.getMethod();
        List<Object> arguments = new ArrayList();
        Parameter[] parameters = method.getParameters();

        for (Parameter parameter : parameters) {
            Object argument = null;
            if(parameter.isAnnotationPresent(PathVariable.class)){
                //TODO Set the path variable value
                PathVariable pathVariableAnnotation = parameter.getAnnotation(PathVariable.class);
                String pathVariableKey = pathVariableAnnotation.value();
                argument = controllerActionPair.getPathVariable(pathVariableKey);

            }

            if(parameter.isAnnotationPresent(RequestVariable.class)){
                //TODO Set the request parameter value
                RequestVariable requestVariableAnotaion = parameter.getAnnotation(RequestVariable.class);
                String requestName = requestVariableAnotaion.value();
                argument = request.getParameter(requestName);
            }

            if(parameter.getType().isAssignableFrom(ModelImpl.class)){
                //TODO Pass the model values to the view
                Constructor constructor = parameter.getType().getConstructor(HttpServletRequest.class);
               argument = constructor.newInstance(request);
            }
            arguments.add(argument);
        }
        //TODO Finally, Invoke the method
        String view = (String) method.invoke(controller.newInstance(), (Object[]) arguments.toArray());
        return view;
    }

    private <T> T convertArgument(Parameter parameter, String pathVariable){
        Object object = null;
        String parameterType = parameter.getType().getSimpleName();
        switch (parameterType) {
            case "Integer":
                object = Integer.valueOf(pathVariable);
                break;
            case "int":
                object = Integer.parseInt(pathVariable);
                break;
            case "Long":
                object = Long.valueOf(pathVariable);
                break;
            case "long":
                object = Long.parseLong(pathVariable);
                break;
        }
    //TODO Find the correct regex can receive different types of parameters
    return (T) object;
    }
}
