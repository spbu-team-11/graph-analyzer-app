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

/*repositories {
    maven {
        // Look for POMs and artifacts, such as JARs, here
        url = uri("https://github.com/klarman-cell-observatory/forceatlas2")
        // Look for artifacts here if not found at the above location
        artifactUrls("https://github.com/klarman-cell-observatory/forceatlas2/releases/download/1.0.3")
        //artifactUrls("http://repo.mycompany.com/jars2")
    }
}*/

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

    //implementation("org.gephi:gephi-toolkit:0.9.2")
    implementation("nl.cwts", "networkanalysis", "1.1.0-5-ga3f342d.dirty")
    implementation(files("libs/force-atlas2.jar", "libs/force-atlas2-tools.jar"))
    //implementation("com.github.kco:forceatlas2")
    implementation("org.xerial", "sqlite-jdbc", "3.8.11.2")

    implementation("com.github.doyaaaaaken:kotlin-csv-jvm:0.15.2")
    implementation("io.github.blackmo18:kotlin-grass-jvm:0.7.1")
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
