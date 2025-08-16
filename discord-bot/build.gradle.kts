plugins {
    id("application")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = property("group") as String
version = property("version") as String

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":common-utils"))

    // JDA
    implementation("net.dv8tion:JDA:5.6.1") {
        exclude(module="opus-java") // Required for encoding audio into opus
        exclude(module="tink")      // Required for encrypting and decrypting audio
    }
}

application {
    mainClass.set("es.brouse.minecraftauth.DiscordBot")
}

tasks.shadowJar {
    manifest {
        attributes["Main-Class"] = application.mainClass.get()
    }
}

java.toolchain {
    languageVersion.set(JavaLanguageVersion.of(21))
}

tasks.test {
    useJUnitPlatform()
}