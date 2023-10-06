import com.ewerk.gradle.plugins.tasks.QuerydslCompile

group = "megabrain"
version = "0.0.1"

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
    targetCompatibility = javaVersion
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
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.boot:spring-boot-configuration-processor")
    implementation("org.springframework.boot:spring-boot-starter-websocket")
    implementation("org.webjars:stomp-websocket:2.3.3-1")
    implementation("org.springframework.session:spring-session-data-redis")
    implementation("org.springframework.restdocs:spring-restdocs-mockmvc")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    implementation("org.springframework:spring-context-support")
    implementation("org.springdoc:springdoc-openapi-ui:1.6.9")
    implementation("org.projectlombok:lombok")
    implementation("com.googlecode.json-simple:json-simple:1.1.1")
    implementation("org.json:json:20230227")
//    implementation("org.springframework.session:spring-session-core")
//    implementation("org.springframework.session:spring-session-jdbc")
    compile("io.lettuce:lettuce-core:6.2.6.RELEASE")
    runtimeOnly("org.postgresql:postgresql")
    annotationProcessor("org.projectlombok:lombok")
    testAnnotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    implementation ("com.google.code.findbugs:jsr305:3.0.2")

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    implementation("org.springframework.cloud:spring-cloud-aws-context:2.2.6.RELEASE")


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
    compileOnly {
        extendsFrom(annotationProcessor.get())
    }
}
tasks.withType<QuerydslCompile> {
    options.annotationProcessorPath = configurations.querydsl.get()
    dependsOn("sourcesJar")
    dependsOn("compileQuerydslJava")
}

// 테스트
tasks.withType<Test> {
    useJUnitPlatform()
}
