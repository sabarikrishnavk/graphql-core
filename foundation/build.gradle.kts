///*
// * This file was generated by the Gradle "init" task.
// *
// * This generated file contains a sample Kotlin library project to get you started.
// * For more details take a look at the "Building Java & JVM projects" chapter in the Gradle
// * User Manual available at https://docs.gradle.org/6.7.1/userguide/building_java_projects.html
// */
//
plugins {

    kotlin("jvm")
    kotlin("plugin.spring")

    id("org.springframework.boot")
    id("io.spring.dependency-management")

    id("com.netflix.dgs.codegen")
}

group = "com.galaxy"
version = "0.0.1"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}
//
dependencies {
    implementation ("org.springframework.boot:spring-boot-starter")
    testImplementation ("org.springframework.boot:spring-boot-starter-test")

    implementation("com.graphql-java:graphql-java-extended-scalars:1.0")
    implementation("com.github.javafaker:javafaker:1.+")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}
tasks.bootJar{
    enabled = false
}
tasks.jar{
    enabled = true
}
tasks.generateJava{
    enabled = false
}


tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
