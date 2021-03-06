package com.sq.image.upload

import org.apache.logging.log4j.LogManager
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication(scanBasePackages = ["com.sq.image.shared", "com.sq.image.upload"])
class UploadApp

private val log = LogManager.getLogger()

fun main(args: Array<String>) {

    val app = SpringApplication(UploadApp::class.java).run(*args)
    val env = app.environment

    val bucket = env.getProperty("gcp.bucket-name")
    val generatorHost = env.getProperty("thumbnail-service.hostname")
    val generatorPort = env.getProperty("thumbnail-service.port")

    log.info("\n----------------------------------------------------------\n" +
            "Bucket Name: '$bucket' \n" +
            "Thumb Generator Host: '$generatorHost' \n" +
            "Thumb Generator Port: '$generatorPort'" +
            "\n----------------------------------------------------------")
}
