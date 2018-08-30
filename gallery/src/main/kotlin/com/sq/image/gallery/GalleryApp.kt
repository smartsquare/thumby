package com.sq.image.gallery

import org.apache.logging.log4j.LogManager
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication(scanBasePackages = ["com.sq.image.shared", "com.sq.image.gallery"])
class GalleryApp

private val log = LogManager.getLogger()

fun main(args: Array<String>) {

    val app = SpringApplication(GalleryApp::class.java)
    val env = app.run(*args).environment

    val bucket = env.getProperty("gcp.bucket-name")

    log.info("\n----------------------------------------------------------\n" +
            "Bucket Name: '$bucket' \n" +
            "\n----------------------------------------------------------")
}
