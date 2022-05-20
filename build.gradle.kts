plugins {
    kotlin("jvm") version "1.6.21"
    id("com.apollographql.apollo3") version "3.3.0"
    application
}

group = "org.example"
version = "1.0.0-SNAPSHOT"

allprojects {
    repositories {
//        mavenLocal()
        google()
        mavenCentral()
    }
}


dependencies {
    implementation("com.apollographql.apollo3", "apollo-runtime", "3.3.0")
}

apollo {
    packageName.set("com.example")
}

application {
    mainClass.set("com.example.MainKt")
}

// `./gradlew downloadApolloSchema --endpoint='https://apollo-fullstack-tutorial.herokuapp.com/graphql' --schema=`pwd`/src/main/graphql/schema.graphqls` to download the schema
// `./gradlew run` to run the app
