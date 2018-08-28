import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    base
    java

    kotlin("jvm") apply true
    id("org.jetbrains.kotlin.plugin.spring") apply true

    id("org.springframework.boot") apply true
    id("io.spring.dependency-management") apply true

    id("com.google.cloud.tools.jib") apply true
}

jib {

    val gcloudProject = rootProject.ext.get("gcloud-project")

    from {
        image = "openjdk:8-jre-alpine"
    }

    to {
        image = "gcr.io/${gcloudProject}/generator-service"
    }

    container {
        jvmFlags = listOf("-Djava.security.egd=file:/dev/./urandom")
        ports = listOf("8888")
    }
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
    compile(project(":shared"))

    compile("org.jetbrains.kotlinx:kotlinx-coroutines-core:${ext["coroutines-version"]}")

    compile(kotlin("stdlib"))
    compile("org.jetbrains.kotlin:kotlin-reflect")

    compile("org.springframework.boot:spring-boot-starter-web") {
        exclude(module = "spring-boot-starter-validation")
    }
    compile("org.springframework.boot:spring-boot-devtools")

    compile("net.coobird:thumbnailator:0.4.8")

    compile("org.apache.logging.log4j:log4j-api")
    compile("org.apache.logging.log4j:log4j-core")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.mockk:mockk:${ext["mockk-version"]}")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.hamcrest:java-hamcrest:${ext["hamcrest-version"]}")
    testImplementation("com.jayway.jsonpath:json-path:${ext["json-path-version"]}")

    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}