plugins {
    kotlin("jvm") version "1.8.10"
    id("com.apollographql.apollo3") version "3.8.0"
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
    implementation("com.apollographql.apollo3", "apollo-runtime")
    implementation("com.apollographql.apollo3", "apollo-normalized-cache")
    implementation("com.apollographql.apollo3", "apollo-normalized-cache-sqlite")

    testImplementation(kotlin("test"))
    testImplementation("org.jetbrains.kotlinx", "kotlinx-coroutines-test", "1.7.3")
    testImplementation("com.apollographql.apollo3", "apollo-mockserver")
    testImplementation("com.apollographql.apollo3", "apollo-testing-support")
}

apollo {
    service("main") {
        packageName.set("com.example")

        introspection {
            endpointUrl.set("https://apollo-fullstack-tutorial.herokuapp.com/graphql")
            schemaFile.set(file("src/main/graphql/schema.graphqls"))
        }
    }
}

application {
    mainClass.set("com.example.MainKt")
}

// `./gradlew downloadServiceApolloSchemaFromIntrospection` or
// `./gradlew downloadApolloSchema --endpoint='https://apollo-fullstack-tutorial.herokuapp.com/graphql' --schema=`pwd`/src/main/graphql/schema.graphqls` to download the schema
// `./gradlew run` to run the app
