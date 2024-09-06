package com.mirco.curso.springboot.webapp.springboot_webapp.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mirco.curso.springboot.webapp.springboot_webapp.models.dto.ParamDto;
import com.mirco.curso.springboot.webapp.springboot_webapp.models.dto.ParamMixDto;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/params")
public class RequestParamsController {

@GetMapping("/foo")
public ParamDto foo(@RequestParam String message) {

    ParamDto param = new ParamDto();
    param.setMesagge(message);
    return param;
}

@GetMapping("/bar")
public ParamMixDto bar(@RequestParam String text, @RequestParam Integer code){

    ParamMixDto params = new ParamMixDto();
    params.setText(text);
    params.setCode(code);

    return params;
}

@GetMapping("/asd")
public ParamMixDto request(HttpServletRequest request) {
    Integer code= 0;
    try {
        code = Integer.parseInt(request.getParameter("code"));

    }catch (NumberFormatException e){

    }
    ParamMixDto params = new ParamMixDto();

    params.setCode(code);
    params.setText(request.getParameter("text"));

    return params;
}

}
