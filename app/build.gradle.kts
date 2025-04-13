plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)
//    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.tma"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.tma"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.auth)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
//    implementation("com.google.firebase:firebase-auth:22.0.0")
//    implementation("com.google.firebase:firebase-firestore:24.9.1")
    // Firebase BoM to ensure all dependencies use compatible versions
    implementation(platform("com.google.firebase:firebase-bom:33.12.0"))

    // Firebase Authentication
    implementation("com.google.firebase:firebase-auth")

    // Firebase Firestore
    implementation("com.google.firebase:firebase-firestore")

    // Firebase Common (Remove any explicit version if it exists)
    implementation("com.google.firebase:firebase-common")


}