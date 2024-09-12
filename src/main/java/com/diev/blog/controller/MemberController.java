package com.diev.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
public class MemberController {

    @GetMapping("/register")
    public String register() {
        return "member/register";
    }

    @GetMapping("/login")
    public String login() {
        return "member/login";
    }


}
