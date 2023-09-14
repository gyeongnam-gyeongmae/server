import com.ewerk.gradle.plugins.tasks.QuerydslCompile

group = "megabrain"
version = "0.0.1-SNAPSHOT"

val javaVersion = JavaVersion.VERSION_1_8
val springBootVersion: String = "2.7.14"
val queryDslVersion = "5.0.0"

buildscript {
    repositories {
        gradlePluginPortal()
    }
    dependencies {
        classpath("org.yaml:snakeyaml:1.10")
        classpath("org.springframework.boot:spring-boot-gradle-plugin")
    }
}

// 종속성 조회에 사용되는 레퍼지토리입니다.
repositories {
    mavenCentral()
}

sourceSets {
    main {
        java.srcDir("src/core/java")
    }
}

plugins {
    java
    idea
    jacoco

    id("org.springframework.boot") version "2.7.14"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
    id("com.ewerk.gradle.plugins.querydsl") version "1.0.10"
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

java {
    sourceCompatibility = javaVersion
    withSourcesJar()
}

tasks.register<Copy>("check exist application.yml file") {
    val ymlFile = File("src/main/resources/application.yml")
    if (!ymlFile.exists()) {
        logger.error("We were unable to find the application.yml file, please verify that it is located in the resource folder.")
    } else {
        logger.error("found yml")
    }
}

springBoot {
    buildInfo()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.restdocs:spring-restdocs-mockmvc")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    implementation("org.springdoc:springdoc-openapi-ui:1.6.9")
    implementation("org.projectlombok:lombok")
    runtimeOnly("org.postgresql:postgresql")
    annotationProcessor("org.projectlombok:lombok")
    testAnnotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    implementation("com.querydsl:querydsl-jpa:${queryDslVersion}")
    annotationProcessor("com.querydsl:querydsl-apt:${queryDslVersion}")
}

// 쿼리DSL 설치 검증
val querydslDir = "$buildDir/generated/querydsl"
querydsl {
    jpa = true
    querydslSourcesDir = querydslDir
}
sourceSets.getByName("main") {
    java.srcDir(querydslDir)
}
configurations {
    named("querydsl") {
        extendsFrom(configurations.compileClasspath.get())
    }
}
tasks.withType<QuerydslCompile> {
    options.annotationProcessorPath = configurations.querydsl.get()
    dependsOn("sourcesJar")
}

// 테스트
tasks.withType<Test> {
    useJUnitPlatform()
}
