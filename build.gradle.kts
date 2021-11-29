// Configure Auto Relocation
import com.github.jengelman.gradle.plugins.shadow.tasks.ConfigureShadowRelocation
import net.minecrell.pluginyml.bukkit.BukkitPluginDescription.Permission.Default.NOT_OP
import net.minecrell.pluginyml.bukkit.BukkitPluginDescription.Permission.Default.OP
import net.minecrell.pluginyml.bukkit.BukkitPluginDescription.PluginLoadOrder.STARTUP

plugins {
    java
    id("xyz.jpenilla.run-paper") version "1.0.4"
    id("com.github.johnrengelman.shadow") version "7.1.0"
//    id("com.palantir.git-version") version "0.12.3"
    id("net.minecrell.plugin-yml.bukkit") version "0.5.0"
}

group = "fr.obelouix.ultimate"
version = "1.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_16
    targetCompatibility = JavaVersion.VERSION_16
}

repositories {

    mavenCentral()
    // Paper
    maven("https://papermc.io/repo/repository/maven-public/")
    // Purpur
    maven("https://repo.pl3x.net/")
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
    // SpigotMC
    maven("https://hub.spigotmc.org/nexus/content/groups/public/")
    // Mikeprimm's repo (Dynmap)
    maven("https://repo.mikeprimm.com")
    // Intellectualsites (FAWE)
    maven("https://mvn.intellectualsites.com/content/groups/public/")
}


dependencies {
    // Paper
    compileOnly("io.papermc.paper:paper:1.17.1-R0.1-SNAPSHOT")
    compileOnly("io.papermc.paper:paper-api:1.17.1-R0.1-SNAPSHOT")
    compileOnly("io.papermc.paper:paper-mojangapi:1.17.1-R0.1-SNAPSHOT")
    implementation("io.papermc:paperlib:1.0.7")

    //Purpur
    compileOnly("net.pl3x.purpur:purpur-api:1.17.1-R0.1-SNAPSHOT")

    // Luckperms
    compileOnly("net.luckperms:api:5.3")

    // ProtocolLib
    compileOnly("com.comphenix.protocol:ProtocolLib:4.7.0")

    // WorldEdit
    compileOnly("com.fastasyncworldedit:FAWE-Bukkit:1.17-268") { isTransitive = false }
    compileOnly("com.fastasyncworldedit:FAWE-Core:1.17-268")

    // WorldGuard
    compileOnly("com.sk89q.worldguard:worldguard-bukkit:7.0.7-SNAPSHOT")

    // Commodore (Minecraft Brigadier)
    implementation("me.lucko:commodore:1.10")

    // Aikar's Timing
    implementation("co.aikar:minecraft-timings:1.0.4")

    // Dynmap
    compileOnly("us.dynmap:dynmap-api:3.2-beta-1")

    // Sponge Configurate
    implementation("org.spongepowered:configurate-core:4.1.2")
    implementation("org.spongepowered:configurate-hocon:4.1.2")

    // NBT API
    implementation("de.tr7zw:item-nbt-api-plugin:2.8.0")

    implementation("org.apache.commons:commons-collections4:4.4")
    implementation("com.h2database:h2:2.0.202")

    //PacketListenerAPI
    implementation("org.inventivetalent.packetlistenerapi:api:3.9.8-SNAPSHOT")

    //test dependencies
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

tasks {
    runServer {
        // Configure the Minecraft version for our task.
        // This is the only required configuration besides applying the plugin.
        // Your plugin's jar (or shadowJar if present) will be used automatically.
        minecraftVersion("1.17.1")
    }

}
//automatic relocation of dependencies
tasks.create<ConfigureShadowRelocation>("relocateShadowJar") {
    target = tasks["shadowJar"] as com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
    prefix = "fr.obelouix.libs"
}

tasks.shadowJar {
    //dependsOn(tasks.processResources)
    dependsOn(tasks["relocateShadowJar"])
    //exclude dependencies as they are included in the server jar
    dependencies {
        exclude(dependency("com.mojang:brigadier"))
//        exclude(dependency("org.yaml:snakeyaml"))
    }
}

tasks.jar {
    enabled = false
}

tasks.assemble {
    dependsOn(tasks.shadowJar)
}

bukkit {
    main = "fr.obelouix.ultimate.ObelouixUltimate"
    apiVersion = "1.17"
    softDepend = listOf("ProtocolLib", "LuckPerms", "FastAsyncWorldEdit", "WorldGuard")
    load = STARTUP
    prefix = "Obelouix Ultimate"
    defaultPermission = OP

    permissions {
        register("bukkit.command.difficulty") {
            description = "Allows you to run the difficulty command"
            default = OP
        }
        register("bukkit.command.xp") {
            description = "Allows you to run the xp command"
            default = OP
        }

        register("obelouix.break.block.*") {
            description = "Allows you to control which block players are allowed to break"
            default = NOT_OP
            childrenMap = mapOf(
                "obelouix.break.block.minecraft.tnt" to false,
                "obelouix.break.block.minecraft.lava" to false,
                "obelouix.break.block.minecraft.bedrock" to false
            )
        }

        register("obelouix.place.block.*") {
            description = "Allows you to control which block players are allowed to place"
            default = NOT_OP
            childrenMap = mapOf(
                "obelouix.place.block.minecraft.tnt" to false,
                "obelouix.place.block.minecraft.bedrock" to false
            )
        }

        register("obelouix.commands.day") {
            description = "Allows you to run the day command"
            default = OP
        }

        register("obelouix.commands.settings") {
            description = "Allows you to run the settings command"
            default = OP
        }

        register("obelouix.commands.admin") {
            description = "Allows you to run the admin command"
            default = OP
        }

        register("obelouix.commands.openinv") {
            description = "Allows you to run the openinv command"
            default = OP
        }

        register("obelouix.commands.enderchest") {
            description = "Allows you to run the enderchest command"
            default = OP
            children = listOf("obelouix.commands.enderchest.others")
        }

        register("obelouix.commands.tppos") {
            description = "Allows you to run the tppos command"
            default = OP
        }

        register("obelouix.commands.fly") {
            description = "Allows you to run the fly command"
            default = OP
            children = listOf("obelouix.commands.fly.others")
        }

        register("obelouix.commands.gamemode") {
            description = "Allows you to run the gamemode command"
            default = OP
            children = listOf(
                "obelouix.commands.gamemode.adventure",
                "obelouix.commands.gamemode.creative",
                "obelouix.commands.gamemode.spectator",
                "obelouix.commands.gamemode.survival",
                "obelouix.commands.gamemode.others.adventure",
                "obelouix.commands.gamemode.others.creative",
                "obelouix.commands.gamemode.others.spectator",
                "obelouix.commands.gamemode.others.survival"
            )
        }

        register("obelouix.admin.playerdetails") {
            description = "Allows you to see critical information about a player when hovering his name in the chat"
            default = OP
        }

        register("obelouix.chat.formatting") {
            description = "Allows you to colorize and format the chat"
            default = OP
        }

        register("obelouix.fly"){
            description = "Allows you to fly without being kicked or banned by watchdog"
            default = OP
        }

    }

}