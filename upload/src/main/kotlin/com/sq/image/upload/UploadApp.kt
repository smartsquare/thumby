package com.sq.image

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class UploadApp

fun main(args: Array<String>) {
    SpringApplication.run(UploadApp::class.java, *args)
}