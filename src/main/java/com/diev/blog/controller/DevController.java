package com.diev.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dev")
public class DevController {

    @GetMapping("/")
    public String devHome() {
        return "dev/dev_home";
    }
}
