plugins {
    id("com.android.application")
}

android {
    namespace = "com.moutamid.budgetmanagementapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.moutamid.budgetmanagementapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        setProperty("archivesBaseName", "BudgetingApp-$versionName")
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
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.activity:activity:1.8.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.0")
    implementation(platform("com.google.firebase:firebase-bom:32.2.3"))
    implementation("com.google.firebase:firebase-crashlytics")
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-database:19.4.0")
    implementation("com.fxn769:stash:1.3.2")
    implementation("com.google.firebase:firebase-auth:21.0.1")
    implementation("com.fxn769:stash:1.3.2")
    implementation("com.airbnb.android:lottie:3.4.0")
    implementation("com.github.smarteist:autoimageslider:1.3.9")
    implementation("com.github.bumptech.glide:glide:4.11.0")
    implementation("androidx.biometric:biometric:1.1.0")
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")
}