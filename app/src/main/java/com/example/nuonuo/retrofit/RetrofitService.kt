import com.example.nuonuo.bean.*
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
    @POST("v1/nuoUsers/register")
    fun register(
        @Body requestBody: RequestBody
    ): Deferred<RegisterResponse>


    /**
     * 登入
     */
    @POST("v1/nuoUsers/login")
    fun login(
        @Body requestBody: RequestBody
    ): Deferred<LoginResponse>

    @GET
    fun getForgotPasswordCode(@Url url:String): Deferred<LoginResponse>

    /**
     * 忘记密码
     */
    @POST("v1/nuoUsers/password/reset")
    fun forgotPassword(
        @Body requestBody: RequestBody
    ): Deferred<LoginResponse>


    @PUT("v1/nuoUsers/profile")
    fun modifyUserInfo(
        @Body userInfo: UserInfo,@Header("Authorization") access_token: String
    ): Deferred<LoginResponse>

    @GET("community/list")
    fun getTrendList(): Deferred<TrendListResponse>


    @GET("v1/message")
    fun getMessageList(
        @Header("Authorization") access_token: String
    ): Deferred<MessageListResponse>

}