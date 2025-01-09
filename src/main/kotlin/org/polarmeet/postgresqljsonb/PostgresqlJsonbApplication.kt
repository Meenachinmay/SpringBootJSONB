package org.polarmeet.postgresqljsonb

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication
@EnableDiscoveryClient
class PostgresqlJsonbApplication

fun main(args: Array<String>) {
    runApplication<PostgresqlJsonbApplication>(*args)
}
