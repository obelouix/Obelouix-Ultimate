rootProject.name = "ObelouixUltimate"

listOf(
    "common",
    "paper",
    "folia"
).forEach {
    include("${rootProject.name}-$it")
    project(":${rootProject.name}-$it").projectDir = file(it)
}

pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://maven.fabricmc.net/")
        maven("https://repo.papermc.io/repository/maven-public/")
    }
}

dependencyResolutionManagement {
    versionCatalogs {

        create("libs") {
            // Paper API
            version("paper-api", "1.19.4-R0.1-SNAPSHOT")
            library("paper-api", "io.papermc.paper", "paper-api").versionRef("paper-api")

            // Folia API
            version("folia-api", "1.19.4-R0.1-SNAPSHOT")
            library("folia-api", "dev.folia", "folia-api").versionRef("folia-api")

            //PaperLib
            version("paperLib", "1.0.7")
            library("paperLib", "io.papermc", "paperlib").versionRef("paperLib")

            //Cloud Command Framework
            version("cloudVersion", "1.8.1")
            library("cloud-command-framework", "cloud.commandframework", "cloud-paper").versionRef("cloudVersion")
            library(
                "cloud-minecraft-extras",
                "cloud.commandframework",
                "cloud-minecraft-extras"
            ).versionRef("cloudVersion")
            library("cloud-brigadier", "cloud.commandframework", "cloud-brigadier").versionRef("cloudVersion")

            bundle(
                "cloudCommandFrameworkBundle",
                listOf("cloud-command-framework", "cloud-minecraft-extras", "cloud-brigadier")
            )

            //Configurate
            version("configurateHoconVersion", "4.1.2")
            library("configurate-hocon", "org.spongepowered", "configurate-hocon").versionRef("configurateHoconVersion")
        }
    }
}
