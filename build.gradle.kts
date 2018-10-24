import java.io.FileInputStream
import java.util.Properties

plugins {

    val kotlinVersion = "1.2.71"

    base
    kotlin("jvm") version kotlinVersion apply false
    id("org.jetbrains.kotlin.plugin.spring") version kotlinVersion apply false
    id("org.springframework.boot") version "2.0.6.RELEASE" apply false
    id("io.spring.dependency-management") version "1.0.6.RELEASE" apply false

    id("com.google.cloud.tools.jib") version "0.9.13" apply false

    id("io.gitlab.arturbosch.detekt") version "1.0.0.RC8" apply true
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
        set("coroutines-version", "0.30.2")
        set("google-cloud-storage-version", "1.49.0")
        set("kotlin-version", "1.2.71")
        set("hamcrest-version", "2.0.0.0")
        set("json-path-version", "2.4.0")
        set("mockk-version", "1.8.9")
        set("kluent-version", "1.42")
        set("detekt-version", "1.0.0-gradle-rework-beta3")

        set("webjar-vue-version", "2.5.17")
        set("webjar-vue-bootstrap-version", "2.0.0-rc.11")
        set("webjar-axios-version", "0.19.0-beta.1")
        set("webjar-font-awesome-version", "5.3.1")
    }
}

buildscript {

    val kotlinVersion = "1.2.71"

    repositories {
        jcenter()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        classpath("org.jetbrains.kotlin:kotlin-allopen:$kotlinVersion")
        classpath("org.springframework.boot:spring-boot-gradle-plugin:2.0.6.RELEASE")
    }
}

dependencies {
    detekt("io.gitlab.arturbosch.detekt:detekt-formatting:${ext["detekt-version"]}")

    // Make the root project archives configuration depend on every subproject
    subprojects.forEach {
        archives(it)
    }
}

