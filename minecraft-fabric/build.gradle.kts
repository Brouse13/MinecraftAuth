plugins {
    id("fabric-loom") version "1.11.4"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    java
}

group = property("group") as String
version = property("version") as String

repositories {
    maven {
        name = "Fabric"
        url = uri("https://maven.fabricmc.net/")
    }
}

dependencies {
    implementation(project(":common-utils"))
    shadow(project(":common-utils"))

    implementation(project(":discord-bot"))
    shadow(project(":discord-bot"))

    minecraft("com.mojang:minecraft:1.21.8")
    mappings("net.fabricmc:yarn:1.21.8+build.1:v2")
    modImplementation("net.fabricmc:fabric-loader:0.17.2")
    modImplementation("net.fabricmc.fabric-api:fabric-api:0.131.0+1.21.8")
}

loom {
    splitEnvironmentSourceSets()

    mods.create("minecraft-auth") {
        sourceSet(sourceSets["main"])
    }
}

tasks {
    withType<JavaCompile> {
        options.release.set(21) // fuerza compilación a Java 21
    }

    shadowJar {
        from(sourceSets.main.get().output)
        configurations = listOf(project.configurations["shadow"])
        //minimize()
    }

    remapJar {
        dependsOn(shadowJar)
        mustRunAfter(shadowJar)
        inputFile.set(shadowJar.get().archiveFile)
    }

    test {
        useJUnitPlatform()
    }
}
