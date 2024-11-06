import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Optional
import com.apollographql.apollo.cache.normalized.FetchPolicy
import com.apollographql.apollo.cache.normalized.api.MemoryCacheFactory
import com.apollographql.apollo.cache.normalized.api.NormalizedCache
import com.apollographql.apollo.cache.normalized.apolloStore
import com.apollographql.apollo.cache.normalized.fetchPolicy
import com.apollographql.apollo.cache.normalized.normalizedCache
import com.apollographql.mockserver.MockServer
import com.apollographql.mockserver.enqueueString
import com.example.GetMessageQuery
import com.example.SetMessageMutation
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class Test {
  @Test
  fun test() = runTest {
    val mockServer = MockServer()
    val apolloClient = ApolloClient.Builder()
      .serverUrl(mockServer.url())
      .normalizedCache(MemoryCacheFactory(10 * 1024 * 1024))
      .build()

    mockServer.enqueueString(
      """
            {
              "data": {
                "setMessage": {
                  "__typename": "Message",
                  "id": "0",
                  "message": "message0"
                }
              }
            }
            """.trimIndent()
    )

    val mutationResponse = apolloClient
      .mutation(SetMessageMutation(Optional.present("message0")))
      .fetchPolicy(FetchPolicy.CacheOnly)
      .execute()
    println(mutationResponse.data)
    println(NormalizedCache.prettifyDump(apolloClient.apolloStore.dump()))

    val queryResponse = apolloClient
      .query(GetMessageQuery())
      .fetchPolicy(FetchPolicy.CacheOnly)
      .execute()
    println(queryResponse.data)
  }
}
