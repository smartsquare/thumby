import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.io.FileInputStream
import java.util.*

plugins {

    val kotlinVersion = "1.2.61"

    base
    kotlin("jvm") version "${kotlinVersion}" apply false
    id("org.jetbrains.kotlin.plugin.spring") version "${kotlinVersion}" apply false
    id("org.springframework.boot") version "2.0.4.RELEASE" apply false
    id("io.spring.dependency-management") version "1.0.6.RELEASE" apply false

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

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    val props = Properties()
    props.load(FileInputStream(".env"))

    ext {
        set("gcloud-project", props.getProperty("GCLOUD_PROJECT_ID"))

        // version management
        set("coroutines-version", "0.25.0")
        set("google-cloud-storage-version", "1.41.0")
        set("kotlin-version", "1.2.61")
        set("hamcrest-version", "2.0.0.0")
        set("json-path-version", "2.4.0")
        set("mockk-version", "1.8.6")
    }
}

buildscript {

    val kotlinVersion = "1.2.61"

    repositories {
        jcenter()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${kotlinVersion}") // Required for Kotlin integration
        classpath("org.jetbrains.kotlin:kotlin-allopen:${kotlinVersion}") // See https://kotlinlang.org/docs/reference/compiler-plugins.html#kotlin-spring-compiler-plugin
        classpath("org.springframework.boot:spring-boot-gradle-plugin:2.0.4.RELEASE")
    }

}

dependencies {
    // Make the root project archives configuration depend on every subproject
    subprojects.forEach {
        archives(it)
    }
}