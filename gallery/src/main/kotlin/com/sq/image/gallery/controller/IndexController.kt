package com.sq.image.controller

import org.apache.logging.log4j.LogManager
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping


@Controller
class IndexController {

    @GetMapping("/index")
    fun index() = "index"

}
