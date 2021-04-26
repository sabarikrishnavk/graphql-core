
plugins {
    java
    kotlin("jvm") version "1.4.32"
    kotlin("plugin.spring") version "1.4.32"

    id("org.springframework.boot") version "2.4.0"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"

    id("com.netflix.dgs.codegen") version "4.4.3"
}
sourceSets {
    main {
        java {
            srcDir(file("src/main/kotlin"))
        }
    }
    test {
        java {
            srcDir(file("src/test/kotlin"))
        }
    }
}
repositories {
    mavenCentral()
}