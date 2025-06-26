package com.ieum.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    public String serverTest() {
        return "이음(ieum) 프로젝트 서버가 성공적으로 실행되었습니다!";
    }
}