import com.google.gms.googleservices.GoogleServicesPlugin.MissingGoogleServicesStrategy

val sampleAdMobAppId = "ca-app-pub-3940256099942544~3347511713"
val sampleBannerAdUnitId = "ca-app-pub-3940256099942544/6300978111"
val sampleInterstitialAdUnitId = "ca-app-pub-3940256099942544/1033173712"
val sampleRewardedAdUnitId = "ca-app-pub-3940256099942544/5224354917"

val releaseAdMobAppId = providers.environmentVariable("ADMOB_APP_ID").orElse(sampleAdMobAppId).get()
val releaseBannerAdUnitId = providers.environmentVariable("ADMOB_BANNER_AD_UNIT_ID").orElse(sampleBannerAdUnitId).get()
val releaseInterstitialAdUnitId = providers.environmentVariable("ADMOB_INTERSTITIAL_AD_UNIT_ID").orElse(sampleInterstitialAdUnitId).get()
val releaseRewardedAdUnitId = providers.environmentVariable("ADMOB_REWARDED_AD_UNIT_ID").orElse(sampleRewardedAdUnitId).get()

plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.compose)
  alias(libs.plugins.google.devtools.ksp)
  alias(libs.plugins.roborazzi)
  alias(libs.plugins.secrets)
  // alias(libs.plugins.google.services)
}

android {
  namespace = "com.example"
  compileSdk { version = release(36) { minorApiLevel = 1 } }

  defaultConfig {
    applicationId = "com.aistudio.mathverse.kqwzxy"
    minSdk = 24
    targetSdk = 36
    versionCode = 1
    versionName = "1.0"

    manifestPlaceholders["ADMOB_APP_ID"] = sampleAdMobAppId
    buildConfigField("String", "ADMOB_BANNER_AD_UNIT_ID", "\"$sampleBannerAdUnitId\"")
    buildConfigField("String", "ADMOB_INTERSTITIAL_AD_UNIT_ID", "\"$sampleInterstitialAdUnitId\"")
    buildConfigField("String", "ADMOB_REWARDED_AD_UNIT_ID", "\"$sampleRewardedAdUnitId\"")

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  signingConfigs {
    create("release") {
      val keystorePath = System.getenv("KEYSTORE_PATH") ?: "${rootDir}/my-upload-key.jks"
      storeFile = file(keystorePath)
      storePassword = System.getenv("STORE_PASSWORD")
      keyAlias = "upload"
      keyPassword = System.getenv("KEY_PASSWORD")
    }
    create("debugConfig") {
      storeFile = file("${rootDir}/debug.keystore")
      storePassword = "android"
      keyAlias = "androiddebugkey"
      keyPassword = "android"
    }
  }

  buildTypes {
    release {
      isCrunchPngs = false
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
      signingConfig = signingConfigs.getByName("release")
      manifestPlaceholders["ADMOB_APP_ID"] = releaseAdMobAppId
      buildConfigField("String", "ADMOB_BANNER_AD_UNIT_ID", "\"$releaseBannerAdUnitId\"")
      buildConfigField("String", "ADMOB_INTERSTITIAL_AD_UNIT_ID", "\"$releaseInterstitialAdUnitId\"")
      buildConfigField("String", "ADMOB_REWARDED_AD_UNIT_ID", "\"$releaseRewardedAdUnitId\"")
    }
    debug { signingConfig = signingConfigs.getByName("debugConfig") }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
  }
  buildFeatures {
    compose = true
    buildConfig = true
  }
  testOptions { unitTests { isIncludeAndroidResources = true } }
  dependenciesInfo {
    includeInApk = false
    includeInBundle = true
  }
}

val verifyReleaseAdConfiguration by tasks.registering {
  group = "verification"
  description = "Fails release builds when AdMob test IDs are still configured."
  doLast {
    val productionValues = listOf(
      "ADMOB_APP_ID" to releaseAdMobAppId,
      "ADMOB_BANNER_AD_UNIT_ID" to releaseBannerAdUnitId,
      "ADMOB_INTERSTITIAL_AD_UNIT_ID" to releaseInterstitialAdUnitId,
      "ADMOB_REWARDED_AD_UNIT_ID" to releaseRewardedAdUnitId
    )
    val testValues = setOf(sampleAdMobAppId, sampleBannerAdUnitId, sampleInterstitialAdUnitId, sampleRewardedAdUnitId)
    val missing = productionValues.filter { (_, value) -> value in testValues || value.isBlank() }.map { it.first }
    check(missing.isEmpty()) {
      "Release builds require production AdMob IDs. Configure: ${missing.joinToString()}"
    }
  }
}

tasks.matching { it.name == "bundleRelease" }.configureEach {
  dependsOn(verifyReleaseAdConfiguration)
}

// Configure the Secrets Gradle Plugin to use .env and .env.example files
// to match the convention used in Web projects.
secrets {
  propertiesFileName = ".env"
  defaultPropertiesFileName = ".env.example"
  ignoreList.add("FIREBASE_APPCHECK_DEBUG_TOKEN")
}

// googleServices { missingGoogleServicesStrategy = MissingGoogleServicesStrategy.WARN }

// Some unused dependencies are commented out below instead of being removed.
// This makes it easy to add them back in the future if needed.
dependencies {
  implementation(platform(libs.androidx.compose.bom))
  // implementation(platform(libs.firebase.bom))
  // implementation(libs.accompanist.permissions)
  implementation(libs.androidx.activity.compose)
  // implementation(libs.androidx.camera.camera2)
  // implementation(libs.androidx.camera.core)
  // implementation(libs.androidx.camera.lifecycle)
  // implementation(libs.androidx.camera.view)
  implementation(libs.androidx.compose.material.icons.core)
  implementation(libs.androidx.compose.material.icons.extended)
  implementation(libs.androidx.compose.material3)
  implementation(libs.androidx.compose.ui)
  implementation(libs.androidx.compose.ui.graphics)
  implementation(libs.androidx.compose.ui.tooling.preview)
  implementation(libs.androidx.core.ktx)
  // implementation(libs.androidx.datastore.preferences)
  implementation(libs.androidx.lifecycle.runtime.compose)
  implementation(libs.androidx.lifecycle.runtime.ktx)
  implementation(libs.androidx.lifecycle.viewmodel.compose)
  // implementation(libs.androidx.navigation.compose)
  implementation(libs.androidx.room.ktx)
  implementation(libs.androidx.room.runtime)
  implementation(libs.play.services.ads)
  // implementation(libs.coil.compose)
  implementation(libs.converter.moshi)
  // implementation(libs.firebase.ai)
  // Uncomment to use Firestore:
  // implementation(libs.firebase.firestore)

  // Uncomment ALL FOUR of the following dependencies together to use Firebase Auth and Google
  // Sign-In via Credential Manager:
  // implementation(libs.firebase.auth)
  // implementation(libs.androidx.credentials)
  // implementation(libs.androidx.credentials.play.services)
  // implementation(libs.googleid)
  // implementation(libs.firebase.appcheck.recaptcha)
  // implementation(libs.firebase.appcheck.debug)
  implementation(libs.kotlinx.coroutines.android)
  implementation(libs.kotlinx.coroutines.core)
  implementation(libs.logging.interceptor)
  implementation(libs.moshi.kotlin)
  implementation(libs.okhttp)
  // implementation(libs.play.services.location)
  implementation(libs.retrofit)
  testImplementation(libs.androidx.compose.ui.test.junit4)
  testImplementation(libs.androidx.core)
  testImplementation(libs.androidx.junit)
  testImplementation(libs.junit)
  testImplementation(libs.kotlinx.coroutines.test)
  testImplementation(libs.robolectric)
  testImplementation(libs.roborazzi)
  testImplementation(libs.roborazzi.compose)
  testImplementation(libs.roborazzi.junit.rule)
  androidTestImplementation(platform(libs.androidx.compose.bom))
  androidTestImplementation(libs.androidx.compose.ui.test.junit4)
  androidTestImplementation(libs.androidx.espresso.core)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.runner)
  debugImplementation(libs.androidx.compose.ui.test.manifest)
  debugImplementation(libs.androidx.compose.ui.tooling)
  "ksp"(libs.androidx.room.compiler)
  "ksp"(libs.moshi.kotlin.codegen)
}
