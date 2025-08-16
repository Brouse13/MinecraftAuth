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

        // Gson for JSON serialization/deserialization
        implementation("com.google.code.gson:gson:2.10.1")

        // SLF4J for logging
        implementation("ch.qos.logback:logback-classic:1.5.13")

        // Junit5 for tests
        testImplementation(platform("org.junit:junit-bom:5.9.1"))
        testImplementation("org.junit.jupiter:junit-jupiter")
    }
}

tasks.test {
    useJUnitPlatform()
}