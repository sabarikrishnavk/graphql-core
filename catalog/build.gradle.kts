
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

dependencies {
    implementation(platform("com.netflix.graphql.dgs:graphql-dgs-platform-dependencies:latest.release"))
    api("com.netflix.graphql.dgs:graphql-dgs-spring-boot-starter")

    api(project(":foundation"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

}

tasks.withType<com.netflix.graphql.dgs.codegen.gradle.GenerateJavaTask> {
//    schemaPaths= mutableListOf<Any>("${rootDir}/schema/catalog")
    generateClient = true
    packageName = "com.galaxy.catalog.codegen"
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
        ports.set(listOf(8082))
        images.set(setOf("galaxy-catalog:1.0", "galaxy-catalog:latest"))
        jvmArgs.set(listOf("-Dspring.profiles.active=production", "-Xmx2048m"))
    }
}