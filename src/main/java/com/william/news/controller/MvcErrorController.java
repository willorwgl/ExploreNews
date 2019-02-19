package com.william.news.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MvcErrorController implements ErrorController {

    private final String ERROR_PATH = "/error";

    @GetMapping(ERROR_PATH)
    public String handleError() {
        return "error";
    }

    @Override
    public String getErrorPath() {
        return this.ERROR_PATH;
    }
}
