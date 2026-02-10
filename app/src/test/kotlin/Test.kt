import com.apollographql.apollo.ApolloClient
import com.apollographql.cache.normalized.memory.MemoryCacheFactory
import com.apollographql.mockserver.MockServer
import com.apollographql.mockserver.enqueueError
import com.example.GetMessageQuery
import com.example.cache.Cache.cache
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class Test {
  @Test
  fun test() = runTest {
    val mockServer = MockServer()
    val apolloClient = ApolloClient.Builder()
      .serverUrl(mockServer.url())
      .cache(MemoryCacheFactory(10 * 1024 * 1024))
      .build()

    mockServer.enqueueError(500)

    val queryResponse = apolloClient
      .query(GetMessageQuery("0"))
      .execute()
    println(queryResponse.exception)
  }
}
