import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {

    val kotlinVersion = "1.2.51"

    base
    kotlin("jvm") version "${kotlinVersion}" apply false
    id("org.jetbrains.kotlin.plugin.spring") version "${kotlinVersion}" apply false
    id("org.springframework.boot") version "2.0.3.RELEASE" apply false
    id("io.spring.dependency-management") version "1.0.5.RELEASE" apply false

    id("com.google.cloud.tools.jib") version "0.9.8" apply false
}

allprojects {

    group = "com.sq.image"
    version = "1.0.0"

    repositories {
        jcenter()
        mavenCentral()
        maven("https://repo.spring.io/milestone")
    }


    ext {
        set("coroutines-version","0.22.5")
        set("google-cloud-storage-version","1.35.0")
        set("kotlin-version","1.2.51")
    }

}

buildscript {

    val kotlinVersion = "1.2.51"

    repositories {
        jcenter()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${kotlinVersion}") // Required for Kotlin integration
        classpath("org.jetbrains.kotlin:kotlin-allopen:${kotlinVersion}") // See https://kotlinlang.org/docs/reference/compiler-plugins.html#kotlin-spring-compiler-plugin
        classpath("org.springframework.boot:spring-boot-gradle-plugin:2.0.3.RELEASE")
    }

}

dependencies {
    // Make the root project archives configuration depend on every subproject
    subprojects.forEach {
        archives(it)
    }
}