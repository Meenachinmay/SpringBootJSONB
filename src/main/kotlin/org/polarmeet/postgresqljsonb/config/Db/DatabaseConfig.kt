package org.polarmeet.postgresqljsonb.config.Db

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.polarmeet.postgresqljsonb.models.ContactInformation
import org.springframework.boot.autoconfigure.task.TaskExecutionProperties.Simple
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter
import org.springframework.data.convert.WritingConverter
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions
import org.springframework.data.r2dbc.dialect.PostgresDialect
import java.text.SimpleDateFormat

@Configuration
class DatabaseConfig {
    @Bean
    fun objectMapper(): ObjectMapper {
        return ObjectMapper().apply {
            registerModule(KotlinModule.Builder().build())
            registerModule(JavaTimeModule())
            // configure datetime serialization
            disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            setDateFormat(SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"))
        }
    }

    @Bean
    fun r2dbcCustomConversions(objectMapper: ObjectMapper): R2dbcCustomConversions {
        return R2dbcCustomConversions.of(
            PostgresDialect.INSTANCE,
            listOf(
                ContactInformationWriteConverter(objectMapper),
                ContactInformationReadConverter(objectMapper)
            )
        )
    }
}

@WritingConverter
class ContactInformationWriteConverter(private val objectMapper: ObjectMapper)
    : Converter<ContactInformation, io.r2dbc.postgresql.codec.Json> {
    override fun convert(source: ContactInformation): io.r2dbc.postgresql.codec.Json {
        return io.r2dbc.postgresql.codec.Json.of(objectMapper.writeValueAsString(source))
    }
}

@ReadingConverter
class ContactInformationReadConverter(private val objectMapper: ObjectMapper)
    : Converter<io.r2dbc.postgresql.codec.Json, ContactInformation> {
    override fun convert(source: io.r2dbc.postgresql.codec.Json): ContactInformation {
        return objectMapper.readValue(source.asString(), ContactInformation::class.java)
    }
}