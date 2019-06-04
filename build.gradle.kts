plugins {
    `build-scan` 
    kotlin("jvm") version "1.3.31"
}

repositories {
    jcenter() 
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))
    testImplementation(kotlin("test"))
    testImplementation(kotlin("test-junit"))
}

buildScan {
    termsOfServiceUrl = "https://gradle.com/terms-of-service" 
    termsOfServiceAgree = "yes"
    publishAlways() 
}