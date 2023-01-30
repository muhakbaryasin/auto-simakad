package com.akbar.autosimakad.controllers;

import com.akbar.autosimakad.Response;
import com.akbar.autosimakad.services.DosenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "home")
public class HomeController {
    @Autowired
    private DosenService dosenService;

    @GetMapping
    ResponseEntity<Response> get() {
        try {
            dosenService.login();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        String nameofCurrMethod = new Throwable()
                .getStackTrace()[0]
                .getMethodName();

        Response response = new Response();
        response.setService(this.getClass().getName() + nameofCurrMethod);
        response.setData(null);

        return  ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }
}
