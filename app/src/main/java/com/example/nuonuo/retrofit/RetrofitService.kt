import com.example.nuonuo.bean.RegisterResponse
import kotlinx.coroutines.Deferred
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

/**
 * Retrofit请求api
 */
interface RetrofitService {
    @POST("users/register")
    @Headers("Content-Type: application/json","Accept: application/json")
    fun register(
        @Body requestBody: RequestBody
    ): Deferred<RegisterResponse>

}