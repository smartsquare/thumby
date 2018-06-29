package com.sq.image

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class GalleryApp

fun main(args: Array<String>) {
    SpringApplication.run(GalleryApp::class.java, *args)
}