import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    base
    java

    kotlin("jvm") apply true
    id("org.jetbrains.kotlin.plugin.spring") apply true

    id("org.springframework.boot") apply true
    id("io.spring.dependency-management") apply true
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

    compile("org.apache.logging.log4j:log4j-api")
    compile("org.apache.logging.log4j:log4j-core")

    compile("com.google.cloud:google-cloud-storage:${ext["google-cloud-storage-version"]}")

    testCompile("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    testCompile("org.assertj:assertj-core")
}