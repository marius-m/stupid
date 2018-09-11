package lt.markmerkk.jtrainer

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.web.support.SpringBootServletInitializer
import org.springframework.boot.builder.SpringApplicationBuilder



@SpringBootApplication
class JTrainerApplication : SpringBootServletInitializer() {
    override fun configure(application: SpringApplicationBuilder): SpringApplicationBuilder {
        return application.sources(JTrainerApplication::class.java)
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(JTrainerApplication::class.java, *args)
}
