package com.example.gamezoneproject.mail;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {
    private final EmailServiceImpl emailService;

    public EmailController(EmailServiceImpl emailService) {
        this.emailService = emailService;
    }
    @RequestMapping("/send")
    public void sendTestMail(){
//        emailService.sendMessage("yoda1991@wp.pl", TemporaryTokenNames.ACCOUNT_ACTIVATION,"tomek","www.youtube.com");
    }
}
