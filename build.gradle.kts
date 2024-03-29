val cloudVersion: String = "1.8.1"
val configurateHoconVersion: String = "4.1.2"
val floodgateVersion: String = "2.2.2-SNAPSHOT"

plugins {
    id("java")
    id("io.papermc.paperweight.userdev") version "1.5.4" apply false // Paper dev bundle
    id("xyz.jpenilla.run-paper") version "2.0.1" apply false // Adds runServer and runMojangMappedServer tasks for testing
    id("com.github.johnrengelman.shadow") version "8.1.1" // Shadow plugin
    // id("net.minecrell.plugin-yml.bukkit") version "0.5.2" // Generates plugin.yml
}

group = "fr.obelouix.ultimate"
version = "1.0.0-SNAPSHOT"

subprojects {

    group = "${rootProject.property("group")}"
    version = "${rootProject.property("version")}"

    plugins.apply("java")
    plugins.apply("com.github.johnrengelman.shadow")

    project(":${rootProject.name}-paper") {
        plugins.apply("io.papermc.paperweight.userdev")
        plugins.apply("xyz.jpenilla.run-paper")
    }

    project(":${rootProject.name}-folia") {
        plugins.apply("io.papermc.paperweight.userdev")
        plugins.apply("xyz.jpenilla.run-paper")
    }

    java {
        // Configure the java toolchain. This allows gradle to auto-provision JDK 17 on systems that only have JDK 8 installed for example.
        toolchain.languageVersion.set(JavaLanguageVersion.of(17))
    }

    tasks {
        // Configure reobfJar to run when invoking the build task
        /*assemble {
            dependsOn(reobfJar)
        }*/

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

        shadowJar {
            if (project.name.equals(":${rootProject.name}-paper") || project.name.equals(":${rootProject.name}-folia")) {
                setProperty("zip64", true)
                archiveClassifier.set("noshade")
                archiveFileName.set("${project.name}-${project.version}.jar")
            }
        }

    }

    if (project.name.equals(":${rootProject.name}-paper") || project.name.equals(":${rootProject.name}-folia")) {

        synchronizeSharedResources()

    }

}

repositories {
    mavenCentral()
    listOf(
        "https://repo.papermc.io/repository/maven-public/",              // Paper
        "https://repo.papermc.io/repository/maven-snapshots/",            // Paper snapshots (for Folia)
        "https://oss.sonatype.org/content/repositories/snapshots/",      // Sonatype
        "https://repo.opencollab.dev/maven-snapshots",                  // Floodgate
        "https://repo.opencollab.dev/maven-releases"                   // Cumulus & GeyserMC
    ).forEach{
        maven(it)
    }
}

fun Project.synchronizeSharedResources() {
    sourceSets {
        main {
            resources.srcDir(project(":${rootProject.name}-common").sourceSets["main"].resources.srcDirs)
        }
        test {
            resources.srcDir(project(":${rootProject.name}-common").sourceSets["test"].resources.srcDirs)
        }
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
