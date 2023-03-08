plugins {
    id("java")
    id("io.papermc.paperweight.userdev") version "1.5.3" // Paper dev bundle
    id("xyz.jpenilla.run-paper") version "2.0.1" // Adds runServer and runMojangMappedServer tasks for testing
   // id("net.minecrell.plugin-yml.bukkit") version "0.5.2" // Generates plugin.yml
}

group = "fr.obelouix"
version = "1.0-SNAPSHOT"

val cloudVersion:String = "1.8.1"
val configurateHoconVersion:String = "4.1.2"
val floodgateVersion:String = "2.2.2-SNAPSHOT"

java {
    // Configure the java toolchain. This allows gradle to auto-provision JDK 17 on systems that only have JDK 8 installed for example.
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

repositories {
    mavenCentral()
    listOf(
        "https://repo.papermc.io/repository/maven-public/",              // Paper
        "https://oss.sonatype.org/content/repositories/snapshots/",      // Sonatype
        "https://repo.opencollab.dev/org"                                // Floodgate
    ).forEach{
        maven(it)
    }
}

dependencies {
    paperweight.paperDevBundle("1.19.3-R0.1-SNAPSHOT")
    // paperweightDevBundle("com.example.paperfork", "1.18-R0.1-SNAPSHOT")

    // You will need to manually specify the full dependency if using the groovy gradle dsl
    // (paperDevBundle and paperweightDevBundle functions do not work in groovy)
    // paperweightDevelopmentBundle("io.papermc.paper:dev-bundle:1.18-R0.1-SNAPSHOT")

    // Shadow will include the runtimeClasspath by default, which implementation adds to.
    // Dependencies you don't want to include go in the compileOnly configuration.
    // Make sure to relocate shaded dependencies!
    compileOnly("io.papermc.paper:paper-api:1.19.3-R0.1-SNAPSHOT")

    //Cloud Command Framework
    implementation("cloud.commandframework", "cloud-paper", cloudVersion)
    implementation("cloud.commandframework", "cloud-minecraft-extras", cloudVersion)
    implementation("cloud.commandframework", "cloud-brigadier", cloudVersion)
    //No need to add the main configurate, paper bundle's it
    implementation("org.spongepowered", "configurate-hocon", configurateHoconVersion)
    //floodgate
    implementation("org.geysermc.floodgate", "api", floodgateVersion)
    
}
