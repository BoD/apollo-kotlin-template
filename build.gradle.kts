plugins {
    kotlin("jvm") version "2.1.10"
    id("com.apollographql.apollo") version "4.3.1"
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
    implementation("com.apollographql.cache:normalized-cache:1.0.0-alpha.3") // Memory cache
    implementation("com.apollographql.cache:normalized-cache-sqlite:1.0.0-alpha.3") // SQLite cache

    implementation("org.jetbrains.kotlinx", "kotlinx-coroutines-core", "1.10.2")

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

        plugin("com.apollographql.cache:normalized-cache-apollo-compiler-plugin:1.0.0-alpha.3") {
            argument("packageName", packageName.get())
        }
    }
}

application {
    mainClass.set("com.example.MainKt")
}

// `./gradlew downloadMainApolloSchemaFromIntrospection` or
// `./gradlew downloadApolloSchema --endpoint='https://apollo-fullstack-tutorial.herokuapp.com/graphql' --schema=`pwd`/src/main/graphql/schema.graphqls` to download the schema
// `./gradlew run` to run the app
