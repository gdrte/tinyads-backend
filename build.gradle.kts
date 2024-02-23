import org.jetbrains.kotlin.gradle.internal.ensureParentDirsCreated
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

description = "Tiny ads graphql backend"
val expediagroupGraphqlVersion="7.0.2"

plugins {
	id("org.springframework.boot") version "3.2.2"
	id("io.spring.dependency-management") version "1.1.4"
	id("com.netflix.dgs.codegen") version "6.1.4"
	kotlin("jvm") version "1.9.22"
	kotlin("plugin.spring") version "1.9.22"
}
group = "com.rnds"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencyManagement {
	imports {
		mavenBom("com.netflix.graphql.dgs:graphql-dgs-platform-dependencies:latest.release")
	}
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.netflix.graphql.dgs:graphql-dgs-spring-boot-starter")
	implementation("org.flywaydb:flyway-core")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	runtimeOnly("org.postgresql:postgresql")
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
	runtimeOnly("org.springframework.boot:spring-boot-devtools")
}
//
//configurations.all {
//	exclude("org.springframework.boot",  "spring-boot-starter-tomcat")
////	exclude( "org.apache.tomcat.embed",  "tomcat-embed-el")
//}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.generateJava {
	schemaPaths.add("${projectDir}/src/main/resources/graphql-client")
	packageName = "com.rnds.tinyads.codegen"
	generateClient = true
}

tasks.create("reload"){
	doLast {
		val f=file("${project.projectDir}/build/classes/kotlin/.reload/.reloadtrigger")
		if(!f.exists()){
			f.ensureParentDirsCreated()
			f.createNewFile()
		}
		if(f.setLastModified(System.currentTimeMillis())){
			println("Reload triggered!")
		}
	}
}