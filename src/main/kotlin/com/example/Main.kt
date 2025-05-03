package com.example

import com.apollographql.apollo.ApolloClient
import com.example.type.Language

suspend fun main() {
  val apolloClient = ApolloClient.Builder()
    .serverUrl("http://server.jraf.org:4000/")
    .build()
  apolloClient.query(DeferQuery(Language.EN)).toFlow().collect { response ->
    println(response.data)
  }
  apolloClient.close()
}
