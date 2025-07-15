import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Optional
import com.apollographql.cache.normalized.FetchPolicy
import com.apollographql.cache.normalized.api.NormalizedCache
import com.apollographql.cache.normalized.apolloStore
import com.apollographql.cache.normalized.fetchPolicy
import com.apollographql.cache.normalized.memory.MemoryCacheFactory
import com.apollographql.mockserver.MockServer
import com.apollographql.mockserver.enqueueString
import com.example.GetMessageQuery
import com.example.SetMessageMutation
import com.example.cache.Cache.cache
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class Test {
  @Test
  fun test() = runTest {
    val mockServer = MockServer()
    val apolloClient = ApolloClient.Builder()
      .serverUrl(mockServer.url())
      .cache(MemoryCacheFactory(10 * 1024 * 1024))
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
      .query(GetMessageQuery("0"))
      .fetchPolicy(FetchPolicy.CacheOnly)
      .execute()
    println(queryResponse.data)
    assertEquals(mutationResponse.data!!.setMessage!!.id, queryResponse.data!!.message!!.id)
  }
}
