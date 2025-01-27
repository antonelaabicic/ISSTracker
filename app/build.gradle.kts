plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
}

android {
    namespace = "hr.algebra.isstracker"
    compileSdk = 35

    defaultConfig {
        applicationId = "hr.algebra.isstracker"
        minSdk = 26
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.activity)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // animation
    implementation (libs.lottie)
    // preferences
    implementation(libs.androidx.preference.ktx)
    // worker
    implementation(libs.androidx.work.runtime.ktx)
    // navigation
    implementation(libs.androidx.navigation.fragment.ktx.v285)
    implementation(libs.androidx.navigation.ui.ktx.v285)
    // retrofit
    implementation(libs.retrofit)
    implementation(libs.gson)
    implementation(libs.retrofit2.converter.gson)
    // parsing
    implementation (libs.jsoup)
    // room
    implementation(libs.androidx.room.runtime)
    implementation("androidx.room:room-ktx:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")
    // maps
    implementation(libs.play.services.maps)
    implementation(libs.play.services.location)
    implementation (libs.android.maps.utils)
    // dexter
    implementation(libs.dexter)
    // picasso
    implementation(libs.picasso)
    implementation(libs.picasso.transformations)
}