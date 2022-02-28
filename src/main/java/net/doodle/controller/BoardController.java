package net.doodle.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/community")
public class BoardController {

    @GetMapping("/")
    public String index() {
        log.info("index 화면");
        return "board/index";
    }
}
