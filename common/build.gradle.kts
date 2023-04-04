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

    compileOnly(libs.paper.api)
    compileOnly(libs.folia.api)
    implementation(libs.paperLib)
    //Cloud Command Framework
    implementation(libs.bundles.cloudCommandFrameworkBundle)

    //Configurate
    implementation(libs.configurate.hocon)
}