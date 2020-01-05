import com.example.nuonuo.bean.*
import kotlinx.coroutines.Deferred
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.Response
import okhttp3.ResponseBody
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


    @GET("v1/nuoUsers/info")
    fun getMyInfo(@Header("Authorization") access_token: String): Deferred<LoginResponse>

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
    fun getMessageReciveList(
        @Header("Authorization") access_token: String
    ): Deferred<MessageListResponse>

    @GET("v1/message/send")
    fun getMessageSendList(
        @Header("Authorization") access_token: String
    ): Deferred<MessageListResponse>

    @POST("v1/message/reply/{id}")
    fun sendMessage(
        @Body requestBody: RequestBody,
        @Header("Authorization") access_token: String,
        @Path("id")id:Int
    ): Deferred<SendMessageResponse>

    @GET("https://aip.baidubce.com/oauth/2.0/token?grant_type=client_credentials&client_id=qK5G2nQ2I1irOTQQO3Xylo5L&client_secret=NN2jPoc2UyZYHCLn9PL5sywtjHf4fY3x&")
    fun getBaiduToken(): Deferred<BaiduTokenResponse>

    @FormUrlEncoded
    @Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    @POST("https://aip.baidubce.com/rest/2.0/ocr/v1/general_basic")
    fun getORC(
        @Field("image") image: String,
        @Query("access_token") access_token: String
    ): Deferred<BaiduOCRResponse>

    @POST("community/statement")
    fun newTrend(
        @Body newTrend: NewTrend,
        @Header("Authorization") access_token: String
    ): Deferred<NewTrendResponse>
    

    @Multipart
    @POST("http://xyt.fzu.edu.cn:54321/v1/files")
    fun uploadFile(
        @Part file: MultipartBody.Part,
        @Header("Authorization") access_token: String
    ):Deferred<UploadFileResponse>


    @GET("v1/photo/info")
    fun getSelfCarCode(
        @Header("Authorization") access_token: String
    ): Deferred<SelfCarCodeResponse>

    @POST("v1/photo/upload")
    fun blindCode(
        @Body requestBody: RequestBody,
        @Header("Authorization") access_token: String
    ): Deferred<LoginResponse>

    @GET("v1/photo/{photoId}")
    fun getOwnerList(
        @Path("photoId")id:String
    ): Deferred<GetCarOnwerResponse>

    //获取匿名电话验证码cookie和token
    @GET("v1/message/pic")
    fun getPhoneCode(): Deferred<PhoneCodeResponse>

    @POST("v1/message/phone")
    fun phoneCall(
        @Body requestBody: PhoneCallBean,
        @Header("Authorization") access_token: String
    ): Deferred<GetPhoneCallResponse>

    @POST("v1/nuoUsers/plus/{id}")
    fun dianzan(
        @Path("id") id: Int,
        @Header("Authorization") access_token: String
    ): Deferred<LoginResponse>

}