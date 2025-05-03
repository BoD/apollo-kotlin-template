plugins {
    kotlin("jvm") version "2.1.10"
    id("com.apollographql.apollo") version "4.2.0"
    application
}

group = "org.example"
version = "1.0.0-SNAPSHOT"

allprojects {
    repositories {
        mavenLocal()
        google()
        mavenCentral()
    }
}

dependencies {
    implementation("com.apollographql.apollo", "apollo-runtime")
}

apollo {
    service("service") {
        packageName.set("com.example")

        introspection {
            endpointUrl.set("http://server.jraf.org:4000/")
            schemaFile.set(file("src/main/graphql/schema.graphqls"))
        }
    }
}

application {
    mainClass.set("com.example.MainKt")
}

// `./gradlew downloadServiceApolloSchemaFromIntrospection` to download the schema
// `./gradlew run` to run the app
