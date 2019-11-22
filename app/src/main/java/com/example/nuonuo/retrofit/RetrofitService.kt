import com.example.nuonuo.bean.LoginResponse
import com.example.nuonuo.bean.RegisterResponse
import kotlinx.coroutines.Deferred
import okhttp3.RequestBody
import retrofit2.http.*

/**
 * Retrofit请求api
 */
interface RetrofitService {
    /**
     * 注册
     */
    @POST("nuoUsers/register")
    fun register(
        @Body requestBody: RequestBody
    ): Deferred<RegisterResponse>


    /**
     * 登入
     */
    @POST("nuoUsers/login")
    fun login(
        @Body requestBody: RequestBody
    ): Deferred<LoginResponse>

    @GET
    fun getForgotPasswordCode(@Url url:String): Deferred<LoginResponse>

    /**
     * 忘记密码
     */
    @POST("nuoUsers/password/reset")
    fun forgotPassword(
        @Body requestBody: RequestBody
    ): Deferred<LoginResponse>

}