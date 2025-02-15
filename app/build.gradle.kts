import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
}

val localProperties = Properties()
file("../local.properties").inputStream().use { localProperties.load(it) }

val clientSecret: String = localProperties.getProperty("CLIENT_SECRET") ?: "'default_value'"
val redirectUri: String = localProperties.getProperty("REDIRECT_URI") ?: "'default_value'"
val clientId: String = localProperties.getProperty("CLIENT_ID") ?: "'default_value'"

android {
    namespace = "com.victorl000.spotipass"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.victorl000.spotipass"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        manifestPlaceholders += mapOf(
            "redirectSchemeName" to "localhost",
            "redirectHostName" to "auth"
        )
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "CLIENT_SECRET", "\"${clientSecret}\"")
            buildConfigField("String", "CLIENT_ID", "\"${clientId}\"")
            buildConfigField("String", "REDIRECT_URI", "\"${redirectUri}\"")
        }
        debug {
            buildConfigField("String", "CLIENT_SECRET", "\"${clientSecret}\"")
            buildConfigField("String", "CLIENT_ID", "\"${clientId}\"")
            buildConfigField("String", "REDIRECT_URI", "\"${redirectUri}\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(project(":streetpassble"))
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(libs.androidx.lifecycle.livedata)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.auth.v210)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation ( "com.squareup.okhttp3:logging-interceptor:4.12.0" )
}