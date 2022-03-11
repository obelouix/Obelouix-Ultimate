import net.minecrell.pluginyml.bukkit.BukkitPluginDescription

plugins {
    `java-library`
    idea //force Intellij to generate project file, cause i don't know why it refuse to import dependencies
    id("io.papermc.paperweight.userdev") version "1.3.5"
    id("xyz.jpenilla.run-paper") version "1.0.6" // Adds runServer and runMojangMappedServer tasks for testing
    id("net.minecrell.plugin-yml.bukkit") version "0.5.1" // Generates plugin.yml

    // Shades and relocates dependencies into our plugin jar. See https://imperceptiblethoughts.com/shadow/introduction/
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "fr.obelouix.ultimate"
version = "1.0.0-SNAPSHOT"

java {
    // Configure the java toolchain. This allows gradle to auto-provision JDK 17 on systems that only have JDK 8 installed for example.
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}


repositories {
    mavenCentral()
    // Paper
    maven("https://papermc.io/repo/repository/maven-public/")
    // Purpur
    // maven("https://repo.pl3x.net/")
    // Sonatype
    maven("https://oss.sonatype.org/content/groups/public/")
    // JitPack
    maven("https://jitpack.io")
    // dmulloy2 repo (ProtocolLib)
    maven("https://repo.dmulloy2.net/repository/public/")
    // Sponge
    maven("https://repo.spongepowered.org/maven/")
    // Minecraft
    maven("https://libraries.minecraft.net/")
    // CodeMC
    maven("https://repo.codemc.org/repository/maven-public/")
    maven("https://repo.codemc.io/repository/nms/")
    // Aikar's repo
    maven("https://repo.aikar.co/content/groups/aikar/")
    // EngineHub
    maven("https://maven.enginehub.org/repo/")
    // FAWE
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
    // SpigotMC
    maven("https://hub.spigotmc.org/nexus/content/groups/public/")
    // Mikeprimm's repo (Dynmap)
    maven("https://repo.mikeprimm.com")
    // FrankHeijden
    maven("https://repo.fvdh.dev/releases")
    // EssentialsX
    maven("https://repo.essentialsx.net/snapshots/")
    maven("https://repo.essentialsx.net/releases/")
    // fren_gor
    maven("https://nexus.frengor.com/repository/public/")
}

dependencies {
    paperDevBundle("1.18.1-R0.1-SNAPSHOT")
    // paperweightDevBundle("com.example.paperfork", "1.18-R0.1-SNAPSHOT")

    // You will need to manually specify the full dependency if using the groovy gradle dsl
    // (paperDevBundle and paperweightDevBundle functions do not work in groovy)
    // paperweightDevelopmentBundle("io.papermc.paper:dev-bundle:1.18-R0.1-SNAPSHOT")

    // Shadow will include the runtimeClasspath by default, which implementation adds to.
    // Dependencies you don't want to include go in the compileOnly configuration.
    // Make sure to relocate shaded dependencies!
    compileOnly("io.papermc.paper:paper-api:1.18.2-R0.1-SNAPSHOT")

    // Cloud Command Framework
    implementation("cloud.commandframework", "cloud-paper", "1.6.1")
    implementation("cloud.commandframework", "cloud-minecraft-extras", "1.6.1")

    // Aikar's Timing
    implementation("co.aikar:minecraft-timings:1.0.4")
    compileOnly("net.luckperms:api:5.4")

    // Sponge Configurate
    implementation("org.spongepowered:configurate-core:4.1.2")
    implementation("org.spongepowered:configurate-hocon:4.1.2")
    implementation("org.spongepowered:configurate-yaml:4.1.2")

    // NBT API
    implementation("de.tr7zw:item-nbt-api-plugin:2.9.2")

    // ServerUtils
    compileOnly("net.frankheijden.serverutils:ServerUtils:3.4.2")

    // EssentialsX
    compileOnly("net.essentialsx:EssentialsX:2.20.0-SNAPSHOT")

    // FAWE
    compileOnly("com.fastasyncworldedit:FastAsyncWorldEdit-Core:2.0.1")
    compileOnly("com.fastasyncworldedit:FastAsyncWorldEdit-Bukkit:2.1.1-SNAPSHOT") { isTransitive = false }

    // WorldGuard
    compileOnly("com.sk89q.worldguard:worldguard-bukkit:7.0.7-20211230.194325-8")

    // Dynmap
    compileOnly("us.dynmap:dynmap-api:3.2-beta-1")
    //compileOnly("us.dynmap:dynmap-core:3.0-SNAPSHOT")
    //compileOnly("us.dynmap:dynmap-bukkit:3.0-SNAPSHOT")

    // UltimateAdvancementAPI
    implementation("com.frengor:ultimateadvancementapi-shadeable:2.0.5")

    implementation("org.bstats:bstats-bukkit:3.0.0")
    implementation("org.inventivetalent:reflectionhelper:1.18.7-SNAPSHOT")
    //PacketAPI Listener
    implementation("org.inventivetalent.packetlistenerapi:api:3.9.9-SNAPSHOT")
    // GlowAPI
    //implementation("com.github.InventivetalentDev:GlowAPI:master-SNAPSHOT")
}

tasks {

    runServer {
        minecraftVersion("1.18.1")
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

        // relocate cloud and it's transitive dependencies
        reloc("cloud.commandframework")
        reloc("io.leangen.geantyref")
        reloc("co.aikar.timings")
        reloc("com.typesafe")
        reloc("de.tr7zw")
        reloc("org.intellij")
        reloc("org.jetbrains")
        reloc("org.spongepowered")
        reloc("com.fren_gor.ultimateAdvancementAPI")
        reloc("com.github.inventivetalentDev")
        reloc("org.inventivetalent")
    }
}

// Configure plugin.yml generation
bukkit {
    load = BukkitPluginDescription.PluginLoadOrder.POSTWORLD
    main = "fr.obelouix.ultimate.ObelouixUltimate"
    apiVersion = "1.18"
    authors = listOf("Obelouix")
    softDepend = listOf("dynmap", "worldguard")
}
