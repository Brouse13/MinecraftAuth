plugins {
    id("java")
}

group = { project.properties["group"] }
version = { project.properties["version"] }

repositories {
    mavenCentral()
}

subprojects {
    apply(plugin = "java")

    repositories {
        mavenCentral()
    }

    dependencies {
        // Lombok
        compileOnly("org.projectlombok:lombok:1.18.34")
        annotationProcessor("org.projectlombok:lombok:1.18.34")

        // Lombok for tests
        testCompileOnly("org.projectlombok:lombok:1.18.34")
        testAnnotationProcessor("org.projectlombok:lombok:1.18.34")

        implementation("com.fasterxml.jackson.core:jackson-databind:2.20.0-rc1") {
            exclude(group = "com.google.guava", module = "guava")
        }
        implementation("com.fasterxml.jackson.core:jackson-core:2.20.0-rc1") {
            exclude(group = "com.google.guava", module = "guava")
        }
        implementation("com.fasterxml.jackson.core:jackson-annotations:3.0-rc5") {
            exclude(group = "com.google.guava", module = "guava")
        }

        // SLF4J for logging
        implementation("org.slf4j:slf4j-api:2.0.9")

        // Junit5 for tests
        testImplementation(platform("org.junit:junit-bom:5.9.1"))
        testImplementation("org.junit.jupiter:junit-jupiter")
    }
}

tasks.test {
    useJUnitPlatform()
}