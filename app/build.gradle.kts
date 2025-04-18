plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.bassanthassan"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.bassanthassan"
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
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    // ZXing for QR code scanning
    implementation(libs.zxing.android.embedded)

// Room components
    implementation(libs.room.runtime)
    annotationProcessor(libs.room.compiler)

// Lifecycle & ViewModel
    implementation(libs.lifecycle.extensions)
}