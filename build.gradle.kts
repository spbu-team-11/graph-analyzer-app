import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    kotlin("jvm")
    id("org.openjfx.javafxplugin")
}

repositories {
    mavenCentral()
    jcenter()
    flatDir {
        dirs("libs")
    }
}

val `tornadofx-version`: String by project

dependencies {
    implementation(project("logger")) // import our sub-project
    //implementation("com.example.demo:logger:0.0.1")
    // import artifact from remote/local repository
    // or gradle will substitute this dependency with local 'logger' sources
    //  if we add 'includeBuild("logger")' to our 'settings.gradle.kts' file

    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))
    implementation("no.tornado:tornadofx:$`tornadofx-version`") {
        exclude("org.jetbrains.kotlin")
    }

    implementation("org.jetbrains.exposed", "exposed-core", "0.31.1")
    implementation("org.jetbrains.exposed", "exposed-dao", "0.31.1")
    implementation("org.jetbrains.exposed", "exposed-jdbc", "0.31.1")
    implementation("org.xerial", "sqlite-jdbc","3.34.0")
    implementation("org.slf4j", "slf4j-simple", "1.7.29")

    implementation("nl.cwts", "networkanalysis", "1.1.0-5-ga3f342d.dirty")
    implementation(files("libs/force-atlas2.jar", "libs/force-atlas2-tools.jar"))
}

application {
    mainClass.set("com.example.demo.MainApp")
}

val `javafx-version`: String by project

javafx {
    version = `javafx-version`
    modules("javafx.controls")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}
