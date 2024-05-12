package com.example.gamezoneproject.mail;

import java.util.Map;

public interface ThymeleafService {
    String createContent(String template, Map<String,Object> args);
}
