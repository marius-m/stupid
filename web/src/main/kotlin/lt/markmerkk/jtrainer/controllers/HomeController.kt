package lt.markmerkk.jtrainer.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam


@Controller
class HomeController {

    @RequestMapping(
            value = arrayOf("/"),
            method = arrayOf(RequestMethod.GET)
    )
    fun index(): String {
        return "index"
    }

}