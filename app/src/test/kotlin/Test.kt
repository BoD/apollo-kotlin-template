import com.example.UsersQuery
import com.example.builder.Data
import kotlin.test.Test

class Test {
  @Test
  fun test() {
    val data2 = UsersQuery.Data {
//      users = listOf(null)
      this["users"] = listOf(null)
    }
  }
}
