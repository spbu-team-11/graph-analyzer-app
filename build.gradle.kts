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
    implementation(project("logger"))

    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))
    implementation("no.tornado:tornadofx:$`tornadofx-version`") {
        exclude("org.jetbrains.kotlin")
    }

    implementation("org.jetbrains.exposed", "exposed-core", "0.31.1")
    implementation("org.jetbrains.exposed", "exposed-dao", "0.31.1")
    implementation("org.jetbrains.exposed", "exposed-jdbc", "0.31.1")
    implementation("org.xerial", "sqlite-jdbc", "3.34.0")

    implementation("nl.cwts", "networkanalysis", "1.1.0-5-ga3f342d.dirty")
    implementation(files("libs/force-atlas2.jar", "libs/force-atlas2-tools.jar"))
    implementation("org.jgrapht", "jgrapht-core", "1.5.1")
    implementation("org.xerial", "sqlite-jdbc", "3.8.11.2")

    implementation("com.github.doyaaaaaken:kotlin-csv-jvm:0.15.2")
    implementation("io.github.blackmo18:kotlin-grass-jvm:0.7.1")

    testImplementation("org.testfx:testfx-core:4.0.16-alpha")
    testImplementation("org.testfx:testfx-junit5:4.0.16-alpha")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.5.1")
    testImplementation("org.junit.jupiter:junit-jupiter:5.5.1")
    testImplementation("org.hamcrest", "hamcrest", "2.1")

}

application {
    mainClass.set("MainApp")
}

val `javafx-version`: String by project

javafx {
    version = `javafx-version`
    modules("javafx.controls")
}


tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

tasks{
    test{
        useJUnitPlatform()
    }
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "MainAppKt"
    }
    from(sourceSets.main.get().output)
    dependsOn(configurations.runtimeClasspath)
    from({
        configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
    })
}