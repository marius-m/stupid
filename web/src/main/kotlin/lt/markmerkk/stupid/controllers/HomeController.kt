package lt.markmerkk.stupid.controllers

import lt.markmerkk.stupid.services.GameService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod


@Controller
class HomeController(
        private val gameService: GameService
) {

    @RequestMapping(
            value = arrayOf("/"),
            method = arrayOf(RequestMethod.GET)
    )
    fun index(): String {

        return "index"
    }

}