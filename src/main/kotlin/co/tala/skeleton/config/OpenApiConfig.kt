package co.tala.skeleton.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.actuate.info.InfoEndpoint
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Swagger docs configuration
 */
@Configuration
class OpenApiConfig {

    @Autowired
    var infoEndpoint: InfoEndpoint? = null

    @Bean
    fun customOpenAPI(
        @Value("\${service.name}") apiTitle: String?
    ): OpenAPI {
        val version = Info().title(apiTitle)

        infoEndpoint?.info()?.get("git")?.let {

            val map = it as LinkedHashMap<*, *>
            val branch = map["branch"]
            val commitMap = map["commit"] as LinkedHashMap<*, *>
            val commit = commitMap["id"]
            val time = commitMap["time"]
            val currentVersion = "Branch: $branch\nCommit: $commit\nTime: $time"

            version.version(currentVersion)
        }

        return OpenAPI().components(Components()).info(version)
    }
}
