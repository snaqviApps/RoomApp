plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")

//    id("kotlin-kapt")      ---> replaced with below plugin: KSP
    id("com.google.devtools.ksp")
}

android {
    namespace = "learn.ghar.androidapps"
    compileSdk = 34

    defaultConfig {
        applicationId = "learn.ghar.androidapps"
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    dataBinding {
        enable = true
    }
}

dependencies {

    val lifecycle_version = "2.6.2"
    val arch_version = "2.2.0"
    val fragment_version = "1.6.1"
    val coroutine_version = "1.7.3"
    val room_version = "2.5.2"

    //Room
    implementation("androidx.room:room-runtime:$room_version")
//    kapt("androidx.room:room-compiler:$room_version")         // kapt is being replaced with ksp
    ksp("androidx.room:room-compiler:$room_version")
    // optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:$room_version")


    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // Annotation processor
//    kapt("androidx.lifecycle:lifecycle-compiler:$lifecycle_version")              // below line is java8 based replacement for faster processing
    implementation("androidx.lifecycle:lifecycle-common-java8:$lifecycle_version")

    // LiveData
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")
    // optional - Test helpers for LiveData
    testImplementation("androidx.arch.core:core-testing:$arch_version")

    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")

    // Kotlin
//    implementation "androidx.fragment:fragment-ktx:$fragment_version"   // Groovy-format
    implementation("androidx.fragment:fragment-ktx:$fragment_version")    // Kotlin-format

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutine_version")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutine_version")

    // test
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}