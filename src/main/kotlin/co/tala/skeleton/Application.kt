package co.tala.skeleton

import co.tala.auth.EnableAtlasAuth
import co.tala.auth.config.SpringSecurityConfig
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.context.ApplicationPidFileWriter
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler

@EnableCaching
@EnableAtlasAuth
@SpringBootApplication
@EntityScan("co.tala.skeleton")
class Application

fun main(args: Array<String>) {
    val springApplication = SpringApplication(Application::class.java)
    springApplication.addListeners(ApplicationPidFileWriter())
    springApplication.run(*args)
}

@Bean
fun methodSecurityExpressionHandler(): DefaultMethodSecurityExpressionHandler? {
    return SpringSecurityConfig.methodSecurityExpressionHandler()
}