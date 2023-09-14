import com.ewerk.gradle.plugins.tasks.QuerydslCompile

plugins {
    java
    id("org.springframework.boot") version "2.7.14"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
    id("com.ewerk.gradle.plugins.querydsl") version "1.0.10"

}

group = "megabrain"
version = "0.0.1-SNAPSHOT"

val queryDslVersion = "5.0.0"

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
}


repositories {
    mavenCentral()
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
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



    implementation("com.querydsl:querydsl-jpa:${queryDslVersion}")
    annotationProcessor("com.querydsl:querydsl-apt:${queryDslVersion}")

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

val querydslDir = "$buildDir/generated/querydsl" //

querydsl {
    jpa = true
    querydslSourcesDir = querydslDir
}
// 소스 루트 지정
sourceSets {
    main {
        java {
            srcDir(querydslDir)

        }
    }
}

configurations {
    named("querydsl") {
        extendsFrom(configurations.compileClasspath.get())
    }
}

tasks.withType<QuerydslCompile> {
    options.annotationProcessorPath = configurations.querydsl.get()
}


