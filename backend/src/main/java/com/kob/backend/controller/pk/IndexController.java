package com.kob.backend.controller.pk;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    @RequestMapping("/pk/index/") //index子目录
    public String index() {
        return "pk/index.html"; //返回对应的html文件，这个就是一个html的路径
    }
}
