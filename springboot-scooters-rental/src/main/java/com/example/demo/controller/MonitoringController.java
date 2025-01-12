package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = { "/admin/monitoring" })
public class MonitoringController {

    @GetMapping
    public String showMonitoringPage() {
        return "monitoring";  // 這會返回 monitoring.html 頁面
    }
}

