package com.mirco.curso.springboot.app.interceptor.sprinboot_interceptor.interceptors;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component("timeInterceptor")
public class LoadingTimeInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LoadingTimeInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        HandlerMethod controller = ((HandlerMethod) handler);
        logger.info("LoadingTimeInterceptor: preHandle() entrando ...." + controller.getMethod().getName());

        long start = System.currentTimeMillis();
        request.setAttribute("start", start);
        Random random = new Random();
        int delay = random.nextInt(500);
        Thread.sleep(delay);
        
        Map <String, String> json = new HashMap<>();
        json.put("Error" , "No tenes acceso a este recurso");

        ObjectMapper mapper = new ObjectMapper();
        String jsoString = mapper.writeValueAsString(json);
        response.serContentType("aplication/json");
        response.setStatus(401 );
        response.getWriter().write(jsoString);
        //return true;
        return false;
    }   

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            @Nullable ModelAndView modelAndView) throws Exception {
        long finish = System.currentTimeMillis();
        long start = (long) request.getAttribute("start");
        long result = finish - start;
        logger.info("Tiempo transcurrido: " + result + " Milisegundos");
        
        logger.info("LoadingTimeInterceptor: postHandle() saliendo ...." + ((HandlerMethod) handler). getMethod().getName());
    }


}
