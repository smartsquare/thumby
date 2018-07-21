package com.sq.image.generator

import com.sq.image.generator.thumbnail.ThumbGenerator
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class GeneratorApp

fun main(args: Array<String>) {
    SpringApplication.run(GeneratorApp::class.java, *args)
}