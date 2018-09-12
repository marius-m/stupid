package lt.markmerkk.stupid.controllers

import lt.markmerkk.stupid.entities.responses.ViewModelAllGames
import lt.markmerkk.stupid.services.GameService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.servlet.ModelAndView


@Controller
class HomeController(
        private val gameService: GameService
) {

    @RequestMapping(
            value = arrayOf("/"),
            method = arrayOf(RequestMethod.GET)
    )
    fun index(): ModelAndView {
        val gameData = ViewModelAllGames.from(gameService.games())
        val data = ModelAndView("games")
        data.addObject("gameData", gameData)
        return data
    }

}