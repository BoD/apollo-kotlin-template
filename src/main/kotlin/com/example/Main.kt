@file:OptIn(ApolloExperimental::class, ApolloInternal::class)

package com.example

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.annotations.ApolloExperimental
import com.apollographql.apollo3.annotations.ApolloInternal
import com.apollographql.apollo3.cache.normalized.FetchPolicy
import com.apollographql.apollo3.cache.normalized.api.MemoryCacheFactory
import com.apollographql.apollo3.cache.normalized.fetchPolicy
import com.apollographql.apollo3.cache.normalized.normalizedCache
import com.apollographql.apollo3.cache.normalized.watch
import com.apollographql.apollo3.mockserver.MockServer
import com.apollographql.apollo3.mockserver.enqueue
import com.apollographql.apollo3.testing.internal.runTest
import kotlinx.coroutines.launch

suspend fun main() = runTest {
    val mockServer = MockServer()
    val apolloClient = ApolloClient.Builder()
        .serverUrl(mockServer.url())
        .normalizedCache(MemoryCacheFactory())
        .build()

    launch {
        apolloClient.query(CacheMiss2Query()).fetchPolicy(FetchPolicy.CacheOnly).watch().collect {
            println("watch: ${it.dataAssertNoErrors}")
        }
    }

    println("Execute CacheMiss2Query")
    mockServer.enqueue(
        """
        {
          "data": {
            "query1": {"a": "a"},
            "query2": null,
            "query3": {"a": "a"},
            "query4": {"a": "a"}
          }
        }
      """.trimIndent()
    )
    apolloClient.query(CacheMiss2Query()).fetchPolicy(FetchPolicy.NetworkOnly).execute()

    println("Execute CacheHit2Query")
    mockServer.enqueue(
        """
        {
          "data": {
            "query2": {"a": "a"}
          }
        }
      """.trimIndent()
    )
    apolloClient.query(CacheHit2Query()).fetchPolicy(FetchPolicy.NetworkOnly).execute()


}
