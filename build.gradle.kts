import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val kotlinVersion = "1.7.10"
val spekVersion = "2.0.19"
val mockkVersion = "1.13.1"
plugins {
    kotlin("jvm") version "1.7.10"
}

group = "com.calculator"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")
    testImplementation("org.amshove.kluent:kluent:1.68")
    testImplementation("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
    testImplementation("org.spekframework.spek2:spek-dsl-jvm:$spekVersion")
    testRuntimeOnly("org.spekframework.spek2:spek-runner-junit5:$spekVersion")
    testRuntimeOnly("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")
    testImplementation("io.mockk:mockk:${mockkVersion}")
}

tasks.test {
    useJUnitPlatform {
        includeEngines("spek2")
        testLogging {
            events ("passed", "skipped", "failed")
        }
    }
}


tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

