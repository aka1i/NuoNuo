import com.example.nuonuo.bean.LoginResponse
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
    /**
     * 注册
     */
    @POST("users/register")
    fun register(
        @Body requestBody: RequestBody
    ): Deferred<RegisterResponse>


    /**
     * 登入
     */
    @POST("users/login")
    fun login(
        @Body requestBody: RequestBody
    ): Deferred<LoginResponse>


}