package lt.markmerkk.stupid.components

import lt.markmerkk.stupid.services.GameService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope


@Configuration
class ServiceComponents {

    @Bean
    @Scope("singleton")
    fun gameService(): GameService {
        val gameService = GameService()
        gameService.createNewGameInstance() // todo: Remove debug options later on
        return gameService
    }
}