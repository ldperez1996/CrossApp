// build.gradle.kts (Nivel de Proyecto)
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        // Actualizamos las versiones según las sugerencias
        classpath("com.android.tools.build:gradle:8.6.0") // Actualización del Android Gradle Plugin
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.10") // Actualización de Kotlin a la versión 1.9.10
        classpath("com.google.gms:google-services:4.4.0") // Actualización de Google Services
    }
}
plugins {
    // ...

    // Add the dependency for the Google services Gradle plugin
    id("com.google.gms.google-services") version "4.4.2" apply false

}
allprojects {
    repositories {
        google()
        mavenCentral()
    }
}
