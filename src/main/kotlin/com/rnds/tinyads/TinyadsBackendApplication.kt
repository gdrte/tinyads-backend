package com.rnds.tinyads

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


@SpringBootApplication
class TinyadsBackendApplication
//val logger:Logger = LoggerFactory.getLogger(TinyadsBackendApplication::class.java)
fun main(args: Array<String>) {
	runApplication<TinyadsBackendApplication>(*args)
}