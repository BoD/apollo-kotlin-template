package com.example

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import java.net.InetSocketAddress
import java.net.Proxy

suspend fun main() {
    // Shared OkHttp
    val okHttpClient = OkHttpClient.Builder()
        .proxy(Proxy(Proxy.Type.HTTP, InetSocketAddress("localhost", 8888)))
        .ignoreAllSSLErrors()
        .build()


    // 1st client
    var apolloClient = ApolloClient.Builder()
        .serverUrl("https://apollo-fullstack-tutorial.herokuapp.com/graphql")
        .webSocketServerUrl("wss://apollo-fullstack-tutorial.herokuapp.com/graphql")
        .okHttpClient(okHttpClient)
        .build()

    // A query
    var response = apolloClient.query(LaunchListQuery()).execute()
    println(response.toFormattedString())

    // A subscription
    GlobalScope.launch {
        apolloClient.subscription(TripsBookedSubscription()).toFlow().collect { resp ->
            println(resp.toFormattedString())
        }
    }

    // Simulate other activity in the app...
    Thread.sleep(3000)

    apolloClient.close()


    // 2nd client
    apolloClient = ApolloClient.Builder()
        .serverUrl("https://apollo-fullstack-tutorial.herokuapp.com/graphql")
        .webSocketServerUrl("wss://apollo-fullstack-tutorial.herokuapp.com/graphql")
        .okHttpClient(okHttpClient)
        .build()

    // Fetch again
    response = apolloClient.query(LaunchListQuery()).execute()
    println(response.toFormattedString())

    apolloClient.close()
}
