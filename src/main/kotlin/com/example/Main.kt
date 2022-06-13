package com.example

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
import okhttp3.OkHttpClient
import java.net.InetSocketAddress
import java.net.Proxy

private const val USE_PROXY = false

suspend fun main() {
    val apolloClient = ApolloClient.Builder()
        .serverUrl("https://apollo-fullstack-tutorial.herokuapp.com/graphql")
        .apply {
            if (USE_PROXY) okHttpClient(
                OkHttpClient.Builder()
                    .proxy(Proxy(Proxy.Type.HTTP, InetSocketAddress("localhost", 8888)))
                    .ignoreAllSSLErrors()
                    .build()
            )
        }
        .build()

    val response = apolloClient.query(LaunchListQuery()).execute()
    println(response.toFormattedString())

    apolloClient.dispose()
}
