package com.javapath.springbootsecurityapp.controllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class UserController {

    @GetMapping
    public String getHello(){
        return "<h1>Hello All</h1>";
    }

    @GetMapping("/admin")
    public String admin(){
        return "<h1>Hello Admin</h1>";
    }

    @GetMapping("/user")
    public String user(){
        return "<h1>Hello User</h1>";
    }

    @GetMapping("/superuser")
    public String superuser(){
        return "<h1>Hello Supern User</h1>";
    }
}
