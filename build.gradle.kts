group = "megabrain"
version = "0.0.1"

val javaVersion = JavaVersion.VERSION_1_8
val springBootVersion: String = "2.7.14"

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

plugins {
    java
    idea
    jacoco

    id("org.springframework.boot") version "2.7.14"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
}

java {
    sourceCompatibility = javaVersion
    withSourcesJar()
}

sourceSets {
    main {
        java {
            srcDir(file("src/main/java"))
        }

    }
}

tasks.withType<Jar>() {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

tasks.register<Copy>("check exist application.yml file") {
    val ymlFile = File("src/main/resources/application.yml");
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
    implementation("org.hibernate:hibernate-core")
    implementation("org.hibernate:hibernate-entitymanager")
    runtimeOnly("org.postgresql:postgresql")
    annotationProcessor("org.projectlombok:lombok")
    testAnnotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
    useJUnitPlatform()
}