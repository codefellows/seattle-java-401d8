package com.ncarignan.dragon.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String showHome(){
        System.out.println("Trying to render the home page");
        return "home";
    }

    @GetMapping("/cool")
    public String showCool(){
        return "cool";
    }
}
