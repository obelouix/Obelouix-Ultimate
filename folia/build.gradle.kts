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
    paperweight.foliaDevBundle("1.19.4-R0.1-SNAPSHOT")

    //paperweightDevelopmentBundle("dev.folia:dev-bundle:1.19.4-R0.1-SNAPSHOT")
    // paperweightDevBundle("com.example.paperfork", "1.18-R0.1-SNAPSHOT")

    // You will need to manually specify the full dependency if using the groovy gradle dsl
    // (paperDevBundle and paperweightDevBundle functions do not work in groovy)
    // paperweightDevelopmentBundle("io.papermc.paper:dev-bundle:1.18-R0.1-SNAPSHOT")
    //paperweightDevelopmentBundle("dev.folia:dev-bundle:1.19.4-R0.1-SNAPSHOT")
    // Shadow will include the runtimeClasspath by default, which implementation adds to.
    // Dependencies you don't want to include go in the compileOnly configuration.
    // Make sure to relocate shaded dependencies!
    compileOnly("dev.folia:folia-api:1.19.4-R0.1-SNAPSHOT")
    implementation(project(":${rootProject.name}-common"))
}

tasks.processResources {
    filesMatching("paper-plugin.yml") {
        filter { line ->
            line.replace("folia-supported: false", "folia-supported: true")
        }
    }
}

tasks.build {
    dependsOn(tasks.shadowJar)
}

tasks.shadowJar {
    configurations = listOf(project.configurations.compileClasspath.get())

    dependencies {
        include(project(":${rootProject.name}-common"))
        // exclude all dependencies because paper server can auto-download them using the PluginLoader
        from(configurations) {
            exclude { !it.name.contains(rootProject.name) }
        }
    }
}