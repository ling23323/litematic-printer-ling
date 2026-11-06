plugins {
    `kotlin-dsl`
}

repositories {
    mavenLocal()
    mavenCentral()
    gradlePluginPortal()
//    maven("https://maven.fabricmc.net")
}

kotlin {
    jvmToolchain(25)
}

gradlePlugin {
    plugins {
        register("mod-plugin") {
            id = "mod-plugin"
            implementationClass = "ModPlugin"
        }
    }
}
tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
}