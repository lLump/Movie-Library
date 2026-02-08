import com.android.build.api.variant.BuildConfigField
import java.io.FileInputStream
import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("org.jetbrains.kotlin.plugin.serialization")
}

android {
    namespace = "com.example.mymovielibrary"
    compileSdk = 36

    val file = rootProject.file("local.properties")
    val properties = Properties()
    properties.load(FileInputStream(file))

    defaultConfig {
        applicationId = "com.example.mymovielibrary"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        buildConfigField(
            "String",
            "SAFE_API_KEY",
            "\"${properties.getProperty("API_KEY")}\""
        )

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.4"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}

configurations.all {
    resolutionStrategy {
        force("org.jetbrains.kotlinx:kotlinx-serialization-core:1.6.3")
    }
}

dependencies {
    val retrofit = "2.9.0"
    val daggerHilt = "2.49"
    val moshi = "1.15.2"

    implementation("io.coil-kt:coil-compose:2.7.0") //AsyncImage (Coil)
    implementation("androidx.core:core-splashscreen:1.2.0") //splashScreen

    implementation("com.squareup.retrofit2:retrofit:$retrofit")
    implementation("com.squareup.retrofit2:converter-moshi:$retrofit")

    implementation("com.squareup.moshi:moshi:$moshi")
    implementation("com.squareup.moshi:moshi-kotlin:$moshi")
    implementation("com.squareup.moshi:moshi-adapters:$moshi")
    kapt("com.squareup.moshi:moshi-kotlin-codegen:$moshi")

    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.10.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.10.0")

    implementation("com.google.dagger:hilt-android:$daggerHilt")
    kapt("com.google.dagger:hilt-compiler:$daggerHilt")

    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
    implementation("androidx.navigation:navigation-compose:2.9.7")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")

    implementation("androidx.core:core-ktx:1.17.0")
    implementation("androidx.activity:activity-compose:1.12.3")

    //    implementation("androidx.constraintlayout:constraintlayout-compose:1.1.1") // нарисована одна кнопка в ButtonsSwitcher (не используется)
    implementation(platform("androidx.compose:compose-bom:2026.01.01"))
    implementation("com.google.android.material:material:1.13.0") // for current theme
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material-icons-extended:1.7.8") //additional icons
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}