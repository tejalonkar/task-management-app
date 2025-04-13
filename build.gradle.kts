// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.google.gms.google.services) apply false
//    id("com.android.application") version "8.2.0" apply false
//    id("com.android.library") version "8.2.0" apply false
//    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
}

buildscript {
    dependencies {
        // Correct way to add Google Services Plugin in Kotlin DSL
        classpath("com.google.gms:google-services:4.4.2")
    }
}

