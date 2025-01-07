package org.polarmeet.postgresqljsonb

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PostgresqlJsonbApplication

fun main(args: Array<String>) {
    runApplication<PostgresqlJsonbApplication>(*args)
}
