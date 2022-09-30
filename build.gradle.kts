import net.minecrell.pluginyml.bukkit.BukkitPluginDescription

plugins {
    id("java")
    kotlin("jvm") version "1.7.20"

    id("io.papermc.paperweight.userdev") version "1.3.8"
    id("xyz.jpenilla.run-paper") version "1.0.6"
    id("net.minecrell.plugin-yml.bukkit") version "0.5.2"

    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "dev.peopo"
version = "1.0.0"
description = "Demo for Orbital Studios."

val pluginName: String = "OrbitalDemo"
val configNames: List<String> = File("$projectDir\\src\\main\\resources\\" ).list()!!.toList()
val configPath: String = "$projectDir\\run\\plugins\\$pluginName\\"


repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://jitpack.io" )
}

dependencies {
    paperDevBundle("1.18.2-R0.1-SNAPSHOT")

    implementation("com.github.mertunctuncer:bukkit-scope:1.0.0")
    implementation("com.github.mertunctuncer:skuerrel:1.1.0")

    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.7.20")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")

}

tasks {
    shadowJar {
        minimize {
            exclude(dependency("org.jetbrains.kotlin:.*:.*"))
            exclude(dependency("org.jetbrains.kotlinx:.*:.*"))
        }
    }

    compileJava {
        options.encoding = Charsets.UTF_8.name()

        options.release.set(17)
    }
    javadoc {
        options.encoding = Charsets.UTF_8.name()
    }
    processResources {
        filteringCharset = Charsets.UTF_8.name()
    }

    runServer {
        minecraftVersion("1.18.2")
    }

    val cleanConfigs = register<Delete>("cleanConfigs") {
        group = "other"
        for(config in configNames) {
            File("$configPath\\$config").delete()
        }
    }

    register("cleanBuildThenRun") {
        group = "run paper"
        dependsOn(cleanConfigs)
        dependsOn(clean)
        dependsOn(runServer)
    }

    register("cleanConfigThenRun") {
        group = "run paper"
        dependsOn(cleanConfigs)
        dependsOn(runServer)
    }
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

bukkit {
    name = pluginName
    load = BukkitPluginDescription.PluginLoadOrder.STARTUP
    main = "$group.${rootProject.name.replace("-","")}.${pluginName}Plugin"
    version = project.version.toString()
    description = project.description
    apiVersion = "1.18"
    authors = listOf("Aki..#0001")
    depend = listOf("")
    commands {
    }
}
