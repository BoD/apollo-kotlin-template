import com.apollographql.apollo.annotations.ApolloExperimental

plugins {
  kotlin("jvm")
  id("com.apollographql.apollo")
}

dependencies {
  implementation("com.apollographql.apollo:apollo-runtime")

  testImplementation(kotlin("test"))
  testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.10.2")
}

apollo {
  service("main") {
    packageName.set("com.example")

    @OptIn(ApolloExperimental::class)
    generateDataBuilders.set(true)
  }
}
