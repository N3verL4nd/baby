package cn.lchospital.baby.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 首页
 */
@RestController
public class IndexController {

    @GetMapping(value = {"/", "", "/index"})
    public String index() {
        return "掌心宝贝";
    }
}
