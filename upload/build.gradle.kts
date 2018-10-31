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

    from.image = "openjdk:8-jre-alpine"
    to.image = "gcr.io/$gcloudProject/upload-service"

    container {
        jvmFlags = listOf("-Djava.security.egd=file:/dev/./urandom")
        ports = listOf("8080")
    }
}

tasks {

    val serviceName = "upload-service"
    val gcloudProject = rootProject.ext.get("gcloud-project")
    val dockerHubTag = "smartsquare/thumby-$serviceName:latest"

    val dockerTag by registering(Exec::class) {
        commandLine("docker", "tag", "gcr.io/$gcloudProject/$serviceName:latest", dockerHubTag)
    }

    val dockerHubPush by registering(Exec::class) {
        dependsOn(dockerTag)
        commandLine("docker", "push", dockerHubTag)
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

tasks.withType<Test> {
    useJUnitPlatform()
}


dependencies {
    compile(project(":shared"))

    compile(kotlin("stdlib"))
    compile("org.jetbrains.kotlin:kotlin-reflect")

    compile("org.springframework.boot:spring-boot-starter-web") {
        exclude(module = "spring-boot-starter-validation")
    }
    compile("org.springframework.boot:spring-boot-starter-thymeleaf")
    compile("org.springframework.boot:spring-boot-devtools")

    compile("org.apache.logging.log4j:log4j-api")
    compile("org.apache.logging.log4j:log4j-core")

    compile("org.webjars.npm:vue:${ext["webjar-vue-version"]}")
    compile("org.webjars.npm:bootstrap-vue:${ext["webjar-vue-bootstrap-version"]}")
    compile("org.webjars.npm:axios:${ext["webjar-axios-version"]}")
    compile("org.webjars:font-awesome:${ext["webjar-font-awesome-version"]}")


    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.mockk:mockk:${ext["mockk-version"]}")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.hamcrest:java-hamcrest:${ext["hamcrest-version"]}")
    testImplementation("com.jayway.jsonpath:json-path:${ext["json-path-version"]}")
    testImplementation("org.amshove.kluent:kluent:${ext["kluent-version"]}")

    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}
