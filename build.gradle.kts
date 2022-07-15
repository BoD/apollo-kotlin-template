plugins {
    kotlin("jvm") version "1.7.0"
    id("com.apollographql.apollo3") version "3.4.1-SNAPSHOT"
    application
}

group = "org.example"
version = "1.0.0-SNAPSHOT"

allprojects {
    repositories {
//        mavenLocal()
        maven {
            url = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
        }
        google()
        mavenCentral()
    }
}


dependencies {
    implementation("com.apollographql.apollo3", "apollo-runtime", "3.4.1-SNAPSHOT")
}

apollo {
    packageName.set("com.example")
}

application {
    mainClass.set("com.example.MainKt")
}

// `./gradlew downloadApolloSchema --endpoint='https://apollo-fullstack-tutorial.herokuapp.com/graphql' --schema=`pwd`/src/main/graphql/schema.graphqls` to download the schema
// `./gradlew run` to run the app
