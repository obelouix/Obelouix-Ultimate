import net.minecrell.pluginyml.bukkit.BukkitPluginDescription

plugins {
    `java-library`
    idea //force Intellij to generate project file, cause i don't know why it refuse to import dependencies
    id("io.papermc.paperweight.userdev") version "1.3.8"
    id("xyz.jpenilla.run-paper") version "1.0.6" // Adds runServer and runMojangMappedServer tasks for testing
    id("net.minecrell.plugin-yml.bukkit") version "0.5.2" // Generates plugin.yml

    // Shades and relocates dependencies into our plugin jar. See https://imperceptiblethoughts.com/shadow/introduction/
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "fr.obelouix.ultimate"
version = "1.0.2-SNAPSHOT"

java {
    // Configure the java toolchain. This allows gradle to auto-provision JDK 17 on systems that only have JDK 8 installed for example.
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}


repositories {
    mavenCentral()
    listOf(
        "https://repo.papermc.io/repository/maven-public/",              // Paper
        "https://oss.sonatype.org/content/groups/public/",              // Sonatype
        "https://jitpack.io",                                           // Jitpack
        "https://repo.dmulloy2.net/repository/public/",                 // dmulloy2
        "https://repo.spongepowered.org/maven/",                        // Sponge
        "https://libraries.minecraft.net/",                             // Minecraft
        "https://repo.aikar.co/content/groups/aikar/",                  // Airkar
        "https://maven.enginehub.org/repo/",                            // EngineHub
        "https://s01.oss.sonatype.org/content/repositories/snapshots/", // FAWE
        "https://oss.sonatype.org/content/repositories/snapshots",      // Sonatype snapshots
        "https://hub.spigotmc.org/nexus/content/groups/public/",        // Spigot
        "https://repo.mikeprimm.com",                                   // Dynmap
        "https://repo.fvdh.dev/releases",                               // FrankHeijden
        "https://repo.essentialsx.net/snapshots/",                      // EssentialsX
        "https://repo.essentialsx.net/releases/",                       // EssentialsX
        "https://nexus.frengor.com/repository/public/",                 // fren_gor
        "https://repo.codemc.org/repository/maven-public/",             // CodeMC
        "https://repo.codemc.io/repository/nms/"                        // CodeMC
    ).forEach {
        maven(it)
    }

}

dependencies {
    paperDevBundle("1.19-R0.1-SNAPSHOT")
    // paperweightDevBundle("com.example.paperfork", "1.18-R0.1-SNAPSHOT")

    // You will need to manually specify the full dependency if using the groovy gradle dsl
    // (paperDevBundle and paperweightDevBundle functions do not work in groovy)
    // paperweightDevelopmentBundle("io.papermc.paper:dev-bundle:1.18-R0.1-SNAPSHOT")

    // Shadow will include the runtimeClasspath by default, which implementation adds to.
    // Dependencies you don't want to include go in the compileOnly configuration.
    // Make sure to relocate shaded dependencies!
    compileOnly("io.papermc.paper:paper-api:1.19-R0.1-SNAPSHOT")


    // Cloud Command Framework
    //implementation("cloud.commandframework", "cloud-core", "1.7.0")
    implementation("cloud.commandframework", "cloud-paper", "1.7.0")
    //implementation("cloud.commandframework", "cloud-cloud-annotations", "1.7.0-SNAPSHOT")
    implementation("cloud.commandframework", "cloud-minecraft-extras", "1.7.0")

    // Comodore
    //implementation("me.lucko:commodore:2.0")

    // Aikar's Timing
    implementation("co.aikar:minecraft-timings:1.0.4")

    // LuckPerms
    compileOnly("net.luckperms:api:5.4")

    // Sponge Configurate
    implementation("org.spongepowered:configurate-core:4.1.2")
    implementation("org.spongepowered:configurate-hocon:4.1.2")
    implementation("org.spongepowered:configurate-yaml:4.1.2")

    // NBT API
    implementation("de.tr7zw:item-nbt-api-plugin:2.9.2")

    // ServerUtils
    compileOnly("net.frankheijden.serverutils:ServerUtils:3.4.4")

    // EssentialsX
    compileOnly("net.essentialsx:EssentialsX:2.20.0-SNAPSHOT")
    compileOnly("net.essentialsx:EssentialsXSpawn:2.20.0-SNAPSHOT")
    compileOnly("net.essentialsx:EssentialsXProtect:2.20.0-SNAPSHOT")
    compileOnly("net.essentialsx:EssentialsXDiscord:2.20.0-SNAPSHOT")

    // FAWE
    compileOnly("com.fastasyncworldedit:FastAsyncWorldEdit-Bukkit:2.4.9") { isTransitive = false }
    compileOnly("com.fastasyncworldedit:FastAsyncWorldEdit-Bukkit:2.4.9") { isTransitive = false }

    // WorldGuard
    compileOnly("com.sk89q.worldguard:worldguard-bukkit:7.0.7-20211230.194325-8")

    // Dynmap
    compileOnly("us.dynmap:dynmap-api:3.1-beta-2")
    //compileOnly("us.dynmap:dynmap-bukkit:3.0-SNAPSHOT")

    // UltimateAdvancementAPI
    implementation("com.frengor:ultimateadvancementapi-shadeable:2.1.2")

    implementation("org.bstats:bstats-bukkit:3.0.0")
    implementation("org.inventivetalent:reflectionhelper:1.18.10-SNAPSHOT")
    //PacketAPI Listener
    implementation("org.inventivetalent.packetlistenerapi:api:3.9.10-SNAPSHOT")

    // Interfaces (inventory)
    implementation("com.github.Incendo.interfaces:interfaces-paper:ee352a5c8b")
    implementation("com.github.Incendo.interfaces:interfaces-core:ee352a5c8b")

    implementation("com.j256.ormlite:ormlite-core:6.1")
    implementation("com.j256.ormlite:ormlite-jdbc:6.1")
    implementation("com.h2database:h2:2.1.214")

}

tasks {

    runServer {
        minecraftVersion("1.18.2")
    }
    // Configure reobfJar to run when invoking the build task
    build {
        dependsOn(reobfJar)
    }

    compileJava {
        options.encoding = Charsets.UTF_8.name() // We want UTF-8 for everything

        // Set the release flag. This configures what version bytecode the compiler will emit, as well as what JDK APIs are usable.
        // See https://openjdk.java.net/jeps/247 for more information.
        options.release.set(17)
    }
    javadoc {
        options.encoding = Charsets.UTF_8.name() // We want UTF-8 for everything
    }
    processResources {
        filteringCharset = Charsets.UTF_8.name() // We want UTF-8 for everything
    }

    /*
    reobfJar {
      // This is an example of how you might change the output location for reobfJar. It's recommended not to do this
      // for a variety of reasons, however it's asked frequently enough that an example of how to do it is included here.
      outputJar.set(layout.buildDirectory.file("libs/PaperweightTestPlugin-${project.version}.jar"))
    }
     */

    shadowJar {
        // helper function to relocate a package into our package
        fun reloc(pkg: String) = relocate(pkg, "fr.obelouix.ultimate.dependency.$pkg")

        val deps = listOf(
            "cloud.commandframework",
            "io.leangen.geantyref",
            "co.aikar.timings",
            "com.typesafe",
            "de.tr7zw",
            "org.intellij",
            "org.jetbrains",
            "org.spongepowered",
            "com.fren_gor.ultimateAdvancementAPI",
            "com.github.inventivetalentDev",
            "org.inventivetalent",
            "com.github.Incendo.interfaces",
            "com.j256.ormlite",
            "com.h2database"
        )

        //relocate every dependencies
        deps.forEach {
            reloc(it)
        }
    }
}

// Configure plugin.yml generation
bukkit {
    load = BukkitPluginDescription.PluginLoadOrder.POSTWORLD
    main = "fr.obelouix.ultimate.ObelouixUltimate"
    apiVersion = "1.19"
    authors = listOf("Obelouix")
    softDepend = listOf("dynmap", "worldguard")
}
