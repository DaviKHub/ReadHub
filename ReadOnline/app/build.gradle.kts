plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.gms.google-services") version "4.4.2" apply false
    id("kotlin-kapt") // Room, Hilt
}

android {
    namespace = "com.kukareku.readonline"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.kukareku.readonline"
        minSdk = 26
        targetSdk = 34
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

    buildFeatures {
        viewBinding = true
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    // УДАЛИ ЭТОТ БЛОК — НЕ НУЖЕН ПРИ ИСПОЛЬЗОВАНИИ COMPOSE BOM
    // composeOptions {
    //     kotlinCompilerExtensionVersion = "1.5.15"
    // }
}

dependencies {
    // === AndroidX Core ===
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // === SplashScreen (уже есть — решает ошибку!) ===
    implementation("androidx.core:core-splashscreen:1.0.1")

    // === Compose (BOM управляет версиями) ===
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // === ViewBinding + Classic Views (если используешь XML) ===
    implementation(libs.androidx.drawerlayout)
    implementation(libs.androidx.coordinatorlayout)
    implementation(libs.material)
    implementation(libs.androidx.swiperefreshlayout)

    implementation("androidx.appcompat:appcompat:1.7.0") // НЕ НУЖНО
    implementation("androidx.recyclerview:recyclerview:1.4.0")
    implementation(libs.firebase.auth.ktx)
    implementation(libs.androidx.room.common.jvm) // НЕ НУЖНО

    // === Тесты ===
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}