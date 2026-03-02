package uk.co.stegriff.hellojavalin

import com.sksamuel.hoplite.ConfigLoaderBuilder
import com.sksamuel.hoplite.PropertySource

data class AppConfig(
    val host: String,
    val port: Int
)

object ConfigLoader {
    fun load(): AppConfig {
        val env = System.getenv("APP_ENV") ?: "local"
        
        return ConfigLoaderBuilder.default()
            .addPropertySource(PropertySource.resource("/application-$env.properties", optional = true))
            .addPropertySource(PropertySource.resource("/application.properties"))
            .build()
            .loadConfigOrThrow<AppConfig>()
    }
}
