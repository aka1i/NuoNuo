
import android.net.Uri
import android.os.Environment
import com.example.nuonuo.marco.Constant
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import okhttp3.MediaType
import android.util.Log
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.MultipartBody
import org.json.JSONException
import okio.Buffer
import java.io.File
import java.io.IOException
import java.net.URI
import java.nio.charset.Charset


object RetrofitHelper {
    private const val TAG = "RetrofitHelper"
    private const val CONTENT_PRE = "OkHttp: "
    private const val SAVE_USER_LOGIN_KEY = "user/login"
    private const val SAVE_USER_REGISTER_KEY = "user/register"
    private const val SET_COOKIE_KEY = "set-cookie"
    private const val COOKIE_NAME = "Cookie"
    private const val CONNECT_TIMEOUT = 30L
    private const val READ_TIMEOUT = 10L

    val retrofitService: RetrofitService =
        RetrofitHelper.getService(Constant.REQUEST_BASE_URL, RetrofitService::class.java)

    /**
     * create Retrofit
     */
    private fun create(url: String): Retrofit {
        // okHttpClientBuilder
        val okHttpClientBuilder = OkHttpClient().newBuilder().apply {
            connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            // get response cookie
//            addInterceptor {
//                val request = it
//                    .request()
//                    .newBuilder()
//                    .addHeader("Content-Type", "application/json")
//                    .addHeader("Accept"," application/json")
//                    .build()
//                val response = it.proceed(request)
//                Log.d(TAG, request.url().host())
//                Log.d(TAG, request.url().encodedPath())
//                Log.d(TAG, request.method())
//                try {
//                    val sink = Buffer()
//                    request.body()?.apply {
//                        writeTo(sink)
//                        val json = sink.readString(Charset.defaultCharset())
//                        val jsonObject = JSONObject(json)
//                        Log.d(TAG, json)
//                    }
//                } catch (e: JSONException) {
//                    e.printStackTrace()
//                } catch (e: IOException) {
//                    e.printStackTrace()
//                }catch (e: Exception){
//                    e.printStackTrace()
//                }
//
//
//                response
//            }
        }

        return RetrofitBuild(
            url = url,
            client = okHttpClientBuilder.build(),
            gsonFactory = GsonConverterFactory.create(),
            coroutineCallAdapterFactory = CoroutineCallAdapterFactory()
        ).retrofit
    }

    /**
     * get ServiceApi
     */
    private fun <T> getService(url: String, service: Class<T>): T = create(url).create(service)

    fun getRequestBodyByJson(json:JSONObject): RequestBody{
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json.toString())
    }

    fun getImageBody(path:String): MultipartBody.Part{
        var file = File(path)
        val imageBody = RequestBody.create(MediaType.parse("image/*"),file)
        val image = MultipartBody.Part.createFormData("file","test.jpg",imageBody)
        return image
    }

}

/**
 * create retrofit build
 */
class RetrofitBuild(
    url: String, client: OkHttpClient,
    gsonFactory: GsonConverterFactory,
    coroutineCallAdapterFactory: CoroutineCallAdapterFactory
) {
    val retrofit: Retrofit = Retrofit.Builder().apply {
        baseUrl(url)
        client(client)
        addConverterFactory(gsonFactory)
        addCallAdapterFactory(coroutineCallAdapterFactory)
    }.build()
}