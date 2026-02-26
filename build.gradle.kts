plugins {
    kotlin("jvm") version "2.3.0"
    application
    id("com.gradleup.shadow") version "9.3.1"
}

application {
    mainClass = "uk.co.stegriff.hellojavalin.MainKt"
}

tasks.jar {
    manifest {
        attributes(
            "Main-Class" to "uk.co.stegriff.hellojavalin.MainKt"
        )
    }
}

group = "uk.co.stegriff.hellojavalin"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("io.javalin:javalin:6.7.0")
    implementation("org.slf4j:slf4j-simple:2.0.16")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.2")
}

kotlin {
    jvmToolchain(25)
}

tasks.test {
    useJUnitPlatform()
}