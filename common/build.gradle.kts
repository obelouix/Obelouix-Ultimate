val cloudVersion: String = "1.8.1"
val configurateHoconVersion: String = "4.1.2"

repositories {
    mavenCentral()
    listOf(
        "https://repo.papermc.io/repository/maven-public/",              // Paper
        "https://repo.papermc.io/repository/maven-snapshots/",            // Paper snapshots (for Folia)
        "https://oss.sonatype.org/content/repositories/snapshots/",      // Sonatype
        "https://repo.opencollab.dev/maven-snapshots",                  // Floodgate
        "https://repo.opencollab.dev/maven-releases"                   // Cumulus & GeyserMC
    ).forEach {
        maven(it)
    }
}

dependencies {
    //Cloud Command Framework
    implementation("cloud.commandframework", "cloud-paper", cloudVersion)
    implementation("cloud.commandframework", "cloud-minecraft-extras", cloudVersion)
    implementation("cloud.commandframework", "cloud-brigadier", cloudVersion)
    //No need to add the main configurate, paper bundle's it
    implementation("org.spongepowered", "configurate-hocon", configurateHoconVersion)
}