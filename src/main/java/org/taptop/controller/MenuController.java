package org.taptop.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/menu")
public class MenuController {

    @GetMapping("/items")
    public String[] getMenuItems() {
        return new String[]{"Play Game", "Check Score", "Settings"};
    }
}
