package pers.anshay.tmall.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ForePageController
 *
 * @author: Anshay
 * @date: 2018/12/20
 */
@RestController
public class ForePageController {
    @GetMapping("/")
    public String index() {
        return "redirect:home";
    }

    @GetMapping("/home")
    public String home() {
        return "fore/home";
    }
}
