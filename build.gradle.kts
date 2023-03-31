group = "fr.obelouix"
version = "1.0-SNAPSHOT"

val cloudVersion: String = "1.8.1"
val configurateHoconVersion: String = "4.1.2"
val floodgateVersion: String = "2.2.2-SNAPSHOT"

plugins {
    id("java")
    id("io.papermc.paperweight.userdev") version "1.5.3" apply false // Paper dev bundle
    id("xyz.jpenilla.run-paper") version "2.0.1" apply false // Adds runServer and runMojangMappedServer tasks for testing
    // id("net.minecrell.plugin-yml.bukkit") version "0.5.2" // Generates plugin.yml
}

subprojects {

    group = "${project.property("group")}"
    version = "${project.property("version")}"

    plugins.apply("java")

    if (!project.name.contains("common")) {
        plugins.apply("io.papermc.paperweight.userdev")
        plugins.apply("xyz.jpenilla.run-paper")

    }


}

repositories {
    mavenCentral()
    listOf(
        "https://repo.papermc.io/repository/maven-public/",              // Paper
        "https://repo.papermc.io/repository/maven-snapshots/",            // Paper snapshots (fpr Folia)
        "https://oss.sonatype.org/content/repositories/snapshots/",      // Sonatype
        "https://repo.opencollab.dev/maven-snapshots",                  // Floodgate
        "https://repo.opencollab.dev/maven-releases"                   // Cumulus & GeyserMC
    ).forEach{
        maven(it)
    }
}

/*dependencies {
    //paperweight.paperDevBundle("1.19.4-R0.1-SNAPSHOT")
    // paperweightDevBundle("com.example.paperfork", "1.18-R0.1-SNAPSHOT")

    // You will need to manually specify the full dependency if using the groovy gradle dsl
    // (paperDevBundle and paperweightDevBundle functions do not work in groovy)
    // paperweightDevelopmentBundle("io.papermc.paper:dev-bundle:1.18-R0.1-SNAPSHOT")
    //paperweightDevelopmentBundle("dev.folia:dev-bundle:1.19.4-R0.1-SNAPSHOT")
    // Shadow will include the runtimeClasspath by default, which implementation adds to.
    // Dependencies you don't want to include go in the compileOnly configuration.
    // Make sure to relocate shaded dependencies!
    compileOnly("io.papermc.paper:paper-api:1.19.4-R0.1-SNAPSHOT")
    compileOnly("dev.folia:folia-api:1.19.4-R0.1-SNAPSHOT")

    //floodgate
    implementation("org.geysermc.floodgate", "api", floodgateVersion)
    
}*/
