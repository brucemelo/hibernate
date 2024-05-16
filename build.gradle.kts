plugins {
    id("java")
}

group = "com.github.brucemelo"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("com.h2database:h2:2.2.224")
    compileOnly("org.projectlombok:lombok:1.18.32")
    annotationProcessor("org.projectlombok:lombok:1.18.32")
    implementation("org.postgresql:postgresql:42.7.3")
    implementation("org.hibernate.orm:hibernate-core:6.5.1.Final")
    annotationProcessor("org.hibernate.orm:hibernate-jpamodelgen:6.5.1.Final")
}

tasks.test {
    useJUnitPlatform()
}