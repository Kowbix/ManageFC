package com.example.ManageFC.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SettingsController {

    @GetMapping("/admin/settings")
    public String openSettings() {
        return "/settings/settingsPage";
    }
}
