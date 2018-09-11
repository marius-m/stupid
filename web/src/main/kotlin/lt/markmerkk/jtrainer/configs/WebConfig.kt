package lt.markmerkk.jtrainer.configs

import org.springframework.context.annotation.Bean
import org.springframework.web.servlet.ViewResolver
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter
import org.thymeleaf.TemplateEngine
import org.thymeleaf.spring4.SpringTemplateEngine
import org.thymeleaf.spring4.view.ThymeleafViewResolver
import org.thymeleaf.templateresolver.ServletContextTemplateResolver
import org.thymeleaf.templateresolver.TemplateResolver

class WebConfig : WebMvcConfigurerAdapter() {

    @Bean
    fun viewResolver(templateEngine: TemplateEngine): ViewResolver {
        val resolver = ThymeleafViewResolver()
        resolver.templateEngine = templateEngine as SpringTemplateEngine
        return resolver
    }

    @Bean
    fun templateEngine(templateResolver: TemplateResolver): TemplateEngine {
        val templateEngine = SpringTemplateEngine()
        templateEngine.setTemplateResolver(templateResolver)
        return templateEngine
    }

    @Bean
    fun templateResolver(): TemplateResolver {
        val templateResolver = ServletContextTemplateResolver()
        templateResolver.suffix = ".html"
        templateResolver.templateMode = "HTML5"
        return templateResolver
    }

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        super.addResourceHandlers(registry)
        registry.addResourceHandler(
                *arrayOf(
                        "/static/**",
                        "/assets/**",
                        "../webapp/**"
                )
        ).addResourceLocations(
                *arrayOf(
                        "classpath:/static/",
                        "classpath:/assets/",
                        "classpath:../webapp/"
                )
        )
    }

    override fun configureDefaultServletHandling(configurer: DefaultServletHandlerConfigurer?) {
        configurer!!.enable()
    }
}