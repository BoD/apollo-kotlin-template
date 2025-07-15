import com.apollographql.apollo.annotations.ApolloExperimental

plugins {
    kotlin("jvm") version "2.2.0"
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
    implementation("com.apollographql.apollo:apollo-runtime")
    implementation("com.apollographql.cache:normalized-cache:1.0.0-alpha.4") // Memory cache
    implementation("com.apollographql.cache:normalized-cache-sqlite:1.0.0-alpha.4") // SQLite cache

    testImplementation(kotlin("test"))
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.10.2")
    testImplementation("com.apollographql.mockserver:apollo-mockserver:0.1.1")
}

apollo {
    service("main") {
        packageName.set("com.example")

        introspection {
            endpointUrl.set("https://apollo-fullstack-tutorial.herokuapp.com/graphql")
            schemaFile.set(file("src/main/graphql/schema.graphqls"))
        }

        @OptIn(ApolloExperimental::class)
        plugin("com.apollographql.cache:normalized-cache-apollo-compiler-plugin:1.0.0-alpha.4") {
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
