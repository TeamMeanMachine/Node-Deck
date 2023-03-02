import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.gradle.jvm.tasks.Jar
val wpiLibVersion = "2023.2.1"


plugins {
    java
    application
    kotlin("jvm") version "1.7.20"
    id("com.github.gmazzo.buildconfig") version "3.0.0"
    id("org.openjfx.javafxplugin") version "0.0.13"
}
val mainClass = "org.team2471.frc.nodeDeck.NodeDeck"

buildConfig {
    buildConfigField ("long", "BUILD_TIME", "${System.currentTimeMillis()}L")
}
tasks {
    register("fatJar", Jar::class.java) {
        archiveClassifier.set("all")
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        manifest {
            attributes("Main-Class" to mainClass)
        }
        from(configurations.runtimeClasspath.get()
            .onEach { println("add from dependencies: ${it.name}") }
            .map { if (it.isDirectory) it else zipTree(it) })
        val sourcesMain = sourceSets.main.get()
        sourcesMain.allSource.forEach { println("add from sources: ${it.name}") }
        from(sourcesMain.output)
    }
}

repositories {
    mavenCentral()
    maven { setUrl("https://frcmaven.wpi.edu/artifactory/release/")}
}

javafx {
    version = "19"
    modules = "javafx.controls,javafx.fxml".split(",").toMutableList()
}
application {
    mainClass.set("org.team2471.frc.nodeDeck.NodeDeck")
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")
    implementation("edu.wpi.first.ntcore:ntcore-java:$wpiLibVersion")
    implementation("edu.wpi.first.ntcore:ntcore-jni:$wpiLibVersion:osxuniversal")
    implementation("edu.wpi.first.ntcore:ntcore-jni:$wpiLibVersion:windowsx86-64")
    implementation("edu.wpi.first.wpiutil:wpiutil-java:$wpiLibVersion")
    implementation("edu.wpi.first.wpiutil:wpiutil-jni:$wpiLibVersion:osxuniversal")
    implementation("edu.wpi.first.wpiutil:wpiutil-jni:$wpiLibVersion:windowsx86-64")
}
java {
    withSourcesJar()
    withJavadocJar()
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = JavaVersion.VERSION_17.toString()
}

