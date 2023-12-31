@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    id("kotlin-parcelize")
}

android {
    namespace = "io.john6.watchprivacydog"
    compileSdk = 34

    defaultConfig {
        applicationId = "io.john6.watchprivacydog"
        minSdk = 21
        targetSdk = 34
        versionCode = 2
        versionName = "1.1"
        setProperty("archivesBaseName", "WatchPrivacyDog-$versionName")
    }

    buildTypes {
        release {
            isShrinkResources = true
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":tablayout"))
    implementation(libs.appcompat)
    implementation(libs.viewpager2)
    compileOnly(libs.xposed)
}