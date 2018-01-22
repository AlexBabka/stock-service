package com.github.alexbabka.stock.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomePageController {
    @RequestMapping(value = "/ui")
    public ModelAndView index() {
        return new ModelAndView("index.html");
    }
}
