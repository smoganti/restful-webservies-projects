package com.resources.rest.webservies.restfulwebservicesadvanced.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TranslatorController {

    @RequestMapping(value = "/translate/home")
    public String getHome(){
        return "home";
    }

}
