plugins {
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.kotlin.plugin.spring") version "2.0.21"
}

group = "com.test.magicalhaven"
version = "0.0.1-SNAPSHOT"
java {
    sourceCompatibility = JavaVersion.VERSION_21
}

dependencies {
    val springVersion = "6.1.5"
    val tomcatVersion = "10.1.19"
    val jacksonVersion = "2.15.4"

    // Pure Spring (without Boot)
    implementation("org.springframework:spring-webmvc:$springVersion")
    implementation("org.springframework:spring-context:$springVersion")
    implementation("org.springframework:spring-aop:$springVersion")
    implementation("org.aspectj:aspectjweaver:1.9.21")

    // Embedded Tomcat
    implementation("org.apache.tomcat.embed:tomcat-embed-core:$tomcatVersion")

    // JSON Support
    implementation("com.fasterxml.jackson.core:jackson-databind:$jacksonVersion")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")

    // Swagger / OpenAPI support
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-api:2.5.0")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0")

    // Testing
    testImplementation("com.jayway.jsonpath:json-path:2.9.0")
    testImplementation("org.springframework:spring-test:$springVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.2")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.mockito:mockito-core:5.11.0")
    testImplementation("org.mockito:mockito-junit-jupiter:5.11.0")
    testImplementation("org.assertj:assertj-core:3.25.3")

    // Servlet API
    compileOnly("jakarta.servlet:jakarta.servlet-api:6.0.0")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
