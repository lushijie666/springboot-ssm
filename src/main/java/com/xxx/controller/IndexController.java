package com.xxx.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class IndexController {


    @GetMapping("/")
    public String index() throws Exception {
        return "redirect:/user.html";
    }

    @GetMapping("/user")
    public String login() throws Exception {
        return "user.html";
    }

    @GetMapping("/course")
    public String course()  {
        return "course.html";
    }

}
