package com.sq.image.generator

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication(scanBasePackages = ["com.sq.image.shared", "com.sq.image.generator"])
class GeneratorApp

fun main(args: Array<String>) {
    SpringApplication.run(GeneratorApp::class.java, *args)
}
