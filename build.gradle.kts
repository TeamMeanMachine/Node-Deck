import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
val wpiLibVersion = "2023.2.1"


plugins {
    java
    application
    kotlin("jvm") version "1.7.20"
    id("com.github.gmazzo.buildconfig") version "3.0.0"
    id("org.openjfx.javafxplugin") version "0.0.13"
}

buildConfig {
    buildConfigField ("long", "BUILD_TIME", "${System.currentTimeMillis()}L")
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
    mainClass.set("kilk.com.nodevisualizer.NodeVisualizer")
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")
    implementation("edu.wpi.first.ntcore:ntcore-java:$wpiLibVersion")
    implementation("edu.wpi.first.ntcore:ntcore-jni:$wpiLibVersion:windowsx86-64")
    implementation("edu.wpi.first.wpiutil:wpiutil-java:$wpiLibVersion")
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

