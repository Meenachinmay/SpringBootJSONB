package org.polarmeet.postgresqljsonb.config.Db

import org.polarmeet.postgresqljsonb.entities.MedicalInstitution
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.mapping.event.BeforeConvertCallback
import reactor.core.publisher.Mono
import java.util.UUID

@Configuration
class R2dbcCallbackConfig {
    @Bean
    fun beforeConvertCallback(): BeforeConvertCallback<MedicalInstitution> {
        return BeforeConvertCallback { entity, _ ->
            if (entity.institutionId == null) {
                Mono.just(entity.copy(institutionId = UUID.randomUUID()))
            } else {
                Mono.just(entity)
            }
        }
    }
}