package com.example.gamezoneproject.web.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller for admin panel
 */
@Controller
public class AdminController {
    public static final  String NOTIFICATION_ATTRIBUTE = "notification";
    @GetMapping("/admin")
    public String getAdminPanel(){
        return "admin/admin";
    }
}
