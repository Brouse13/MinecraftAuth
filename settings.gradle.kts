rootProject.name = "MinecraftAuth"

gradle.rootProject {
    group = "es.brouse"
    version = "1.0.0"
}

pluginManagement {
    repositories {
        maven {
            name = "Fabric"
            url = uri("https://maven.fabricmc.net/")
        }
        gradlePluginPortal()
    }
}

include("common-utils")
include("discord-bot")
include("minecraft-fabric")
