import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    base
    java

    val kotlinVersion = "1.2.50"

    kotlin("jvm")
    id("org.jetbrains.kotlin.plugin.spring")

    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

apply {
    plugin("kotlin")
    plugin("kotlin-spring")
    plugin("io.spring.dependency-management")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = listOf("-Xjsr305=strict")
    }
}


dependencies {
    compile(kotlin("stdlib"))
    compile("org.jetbrains.kotlin:kotlin-reflect")

    compile("org.springframework.boot:spring-boot-starter-web") {
        exclude(module = "spring-boot-starter-validation")
    }
    compile("org.springframework.boot:spring-boot-starter-thymeleaf")
    compile("org.springframework.boot:spring-boot-devtools")

    compile("org.apache.logging.log4j:log4j-api:2.11.0")
    compile("org.apache.logging.log4j:log4j-core:2.11.0")

    compile("com.google.cloud:google-cloud-storage:1.35.0")

    testCompile("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.2.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.2.0")
    testCompile("org.assertj:assertj-core:3.9.1")
}