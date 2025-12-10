package com.example

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.network.okHttpClient
import com.apollographql.cache.normalized.FetchPolicy
import com.apollographql.cache.normalized.apolloStore
import com.apollographql.cache.normalized.fetchPolicy
import com.apollographql.cache.normalized.memory.MemoryCacheFactory
import com.apollographql.cache.normalized.sql.SqlNormalizedCacheFactory
import com.example.cache.Cache.cache
import okhttp3.OkHttpClient
import java.net.InetSocketAddress
import java.net.Proxy

private const val USE_PROXY = false

suspend fun main() {
  val memoryFirstThenSqlCacheFactory = MemoryCacheFactory(10 * 1024 * 1024)
    .chain(SqlNormalizedCacheFactory("jdbc:sqlite:apollo.db"))

  val apolloClient = ApolloClient.Builder()
    .serverUrl("https://apollo-fullstack-tutorial.herokuapp.com/graphql")
    .cache(memoryFirstThenSqlCacheFactory)
    .apply {
      if (USE_PROXY) okHttpClient(
        OkHttpClient.Builder()
          .proxy(Proxy(Proxy.Type.HTTP, InetSocketAddress("localhost", 8888)))
          .ignoreAllSSLErrors()
          .build()
      )
    }
    .build()

  // Start with a fresh cache
  apolloClient.apolloStore.clearAll()

  // Fetch the data from the network
  var response = apolloClient.query(LaunchListQuery()).execute()
  println(response.toFormattedString())

  // Now it should be cached
  response = apolloClient.query(LaunchListQuery()).fetchPolicy(FetchPolicy.CacheOnly).execute()
  println(response.toFormattedString())

  //println(response.data!!.launches.launches.first()!!.launch.isBooked)

  apolloClient.close()
}
