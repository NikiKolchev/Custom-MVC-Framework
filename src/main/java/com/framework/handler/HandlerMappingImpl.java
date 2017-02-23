package com.framework.handler;

import com.framework.annotations.controller.Contoller;
import com.framework.annotations.requests.GetMapping;
import com.framework.annotations.requests.PostMapping;
import com.framework.controllerActionPair.ControllerActionPair;
import com.framework.interfaces.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class HandlerMappingImpl implements HandlerMapping {


    @Override
    public ControllerActionPair findController(HttpServletRequest request) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        String uriPath = request.getRequestURI();
        String projectPath = URLDecoder.decode(request.getServletContext()
                .getResource("/WEB-INF/classes").getPath(), "UTF-8");
        List<Class> controllers = this.findAllControllers(projectPath);
        ControllerActionPair controllerActionPair = null;

        for (Class controller : controllers) {
            Method[] methods = controller.getMethods();
            for (Method method : methods) {
                String methodPath = this.findMethodPath(request, method);
                if(methodPath == null) {
                    continue;
                }
                if(this.isMatching(uriPath, methodPath)){
                   controllerActionPair= new ControllerActionPair(controller, method);
                   this.addPathVariable(controllerActionPair, uriPath, methodPath);
                }

            }
        }

        return controllerActionPair;
    }

    private void addPathVariable(ControllerActionPair controllerActionPair, String urlPath,
                                 String methodPath) {

        String[] uriToke = urlPath.split("/");
        String[] methodTokens = methodPath.split("/");

        for (int i = 0; i < methodTokens.length; i++) {
            if(methodTokens[i].startsWith("{") && methodTokens[i].endsWith("}")){
                String key = methodTokens[i].replaceAll("[\\{\\}]", "");
                Object value = uriToke[i];
                controllerActionPair.addPathVariables(key, value);
            }
        }
    }

    private boolean isMatching(String urlPath, String methodPath) {
        boolean isPathMatching = true;
        String[] uriTokens = urlPath.split("/");
        String[] methodTokens = methodPath.split("/");

        if(uriTokens.length != methodTokens.length) {
            isPathMatching = false;
            return isPathMatching;
        }

        for (int i = 0; i < uriTokens.length; i++) {
            if(methodTokens[i].startsWith("{") && methodTokens[i].endsWith("}")){
                continue;
            }

            if(!uriTokens[i].endsWith(methodTokens[i])){
                isPathMatching = false;
                break;
            }
        }

        return isPathMatching;
    }

    private String findMethodPath(HttpServletRequest request, Method method) {
        String path = null;
        String methodType = request.getMethod();

        switch (methodType){
            case "GET":
                if(method.isAnnotationPresent(GetMapping.class)){
                    GetMapping getMapping = method.getAnnotation(GetMapping.class);
                    path = getMapping.value();
                }
                break;
            case "POST":
                if(method.isAnnotationPresent(PostMapping.class)){
                    PostMapping postMapping = method.getAnnotation(PostMapping.class);
                    path = postMapping.value();
                }
                break;
        }

        return path;
    }

    private List<Class> findAllControllers(String projectDirectory) throws ClassNotFoundException {

        List<Class> controllerClasses = new ArrayList<>();
        File directory = new File(projectDirectory);
        File[] files = directory.listFiles();

        for (File file : files) {
             if(file.isFile()) {
                 Class controller = this.getClass(file);
                 if (controller != null) {

                     if (controller.isAnnotationPresent(Contoller.class)) {
                         controllerClasses.add(controller);
                     }
                 }
             } else if (file.isDirectory()) {
                 String subDirectory = file.getAbsolutePath();
                 controllerClasses.addAll(this.findAllControllers(subDirectory));
             }
        }
        return controllerClasses;
    }

    private Class getClass(File file) throws ClassNotFoundException {
        String absolutePath = file.getAbsolutePath();
        String className = absolutePath.split("classes/")[1].replaceAll("/", ".").replaceAll(".class", "");
        Class currentClass = null;

        if(!className.endsWith("DispatcherServlet")){
            currentClass = Class.forName(className);
        }

        return currentClass;
    }
}
