package lt.markmerkk.stupid.components

import lt.markmerkk.Consts
import lt.markmerkk.stupid.services.GameService
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope


@Configuration
class ServiceComponents {

    @Bean
    @Scope("singleton")
    fun gameService(): GameService {
        val gameService = GameService()
        for (index in 0..10) {
            val gameId = gameService.createNewGameInstance() // todo: Remove debug options later on
            logger.info("Register game with ${gameId}")
        }
        return gameService
    }

    companion object {
        private val logger = LoggerFactory.getLogger(Consts.TAG)
    }

}