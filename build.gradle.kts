// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    // TODO 11.1
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.ksp) apply false
}
