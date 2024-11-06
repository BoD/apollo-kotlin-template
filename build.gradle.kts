plugins {
    kotlin("jvm") version "2.0.0"
    id("com.apollographql.apollo") version "4.1.0"
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
    implementation("com.apollographql.apollo", "apollo-normalized-cache")
    implementation("com.apollographql.apollo", "apollo-normalized-cache-sqlite")
    implementation("com.apollographql.apollo", "apollo-http-cache")

    testImplementation(kotlin("test"))
    testImplementation("org.jetbrains.kotlinx", "kotlinx-coroutines-test", "1.7.3")
    testImplementation("com.apollographql.mockserver", "apollo-mockserver", "0.1.0")
}

apollo {
    service("main") {
        packageName.set("com.example")

        introspection {
            endpointUrl.set("https://apollo-fullstack-tutorial.herokuapp.com/graphql")
            schemaFile.set(file("src/main/graphql/schema.graphqls"))
        }

        generateOperationOutput.set(true)
        generateFragmentImplementations.set(true)
        generateSchema.set(true)
        generateDataBuilders.set(true)
    }
}

application {
    mainClass.set("com.example.MainKt")
}

// `./gradlew downloadServiceApolloSchemaFromIntrospection` or
// `./gradlew downloadApolloSchema --endpoint='https://apollo-fullstack-tutorial.herokuapp.com/graphql' --schema=`pwd`/src/main/graphql/schema.graphqls` to download the schema
// `./gradlew run` to run the app
