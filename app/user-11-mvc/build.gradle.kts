import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.7.18"
	id("io.spring.dependency-management") version "1.1.4"
	kotlin("jvm") version "1.9.23"
	kotlin("plugin.spring") version "1.9.23"
	kotlin("plugin.jpa") version "1.9.24"
}

group = "io.crud"
version = "0.0.1"

java {
	sourceCompatibility = JavaVersion.VERSION_11
}

repositories {
	mavenCentral()
}

dependencyManagement {
	imports {
		mavenBom("io.micrometer:micrometer-bom:1.13.0")
	}
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-actuator")

	implementation("io.micrometer:micrometer-registry-prometheus")
	implementation("io.micrometer:micrometer-registry-otlp")
	implementation("io.micrometer:micrometer-observation")

	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")

	runtimeOnly("org.postgresql:postgresql")

	testImplementation("org.springframework.boot:spring-boot-starter-test")

	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
