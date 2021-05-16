
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    kotlin("jvm")
    id("com.netflix.dgs.codegen")
    kotlin("plugin.spring")
    java
    id("com.bmuschko.docker-spring-boot-application") version "6.7.0"
}

group = "com.galaxy"
version = "0.0.1"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

configurations {
    all {
        exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
    }
}
dependencies {
    api(project(":foundation"))

    implementation("org.drools:drools-core:7.49.0.Final")
    implementation("org.drools:drools-templates:7.49.0.Final")
    implementation("org.drools:drools-compiler:7.49.0.Final")
    implementation("org.drools:drools-mvel:7.49.0.Final")


    testImplementation("org.springframework.boot:spring-boot-starter-test")

}

tasks.withType<com.netflix.graphql.dgs.codegen.gradle.GenerateJavaTask> {
    generateClient = true
    packageName = "com.galaxy.promotion.codegen"
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

docker {
    springBootApplication {
        baseImage.set("openjdk:11-jdk-slim")
        ports.set(listOf(8085))
        images.set(setOf("galaxy-promotion:1.0", "galaxy-promotion:latest"))
        jvmArgs.set(listOf("-Dspring.profiles.active=production", "-Xmx2048m"))
    }
}
