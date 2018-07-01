package com.sq.image

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import java.nio.file.Files
import java.nio.file.Paths

@SpringBootApplication
class GalleryApp

fun main(args: Array<String>) {

    checkCloudCredentials()

    SpringApplication.run(GalleryApp::class.java, *args)
}

private fun checkCloudCredentials() {
    val message = "GOOGLE_APPLICATION_CREDENTIALS environment variable not set. Can not access GCP account. Check: https://cloud.google.com/docs/authentication/getting-started"
    val gccEnv = System.getenv("GOOGLE_APPLICATION_CREDENTIALS")

    when {
        gccEnv == null -> throw IllegalStateException(message)
        gccEnv.isEmpty() -> throw IllegalStateException(message)
        !Files.exists(Paths.get(gccEnv)) -> throw IllegalStateException(message)
    }
}