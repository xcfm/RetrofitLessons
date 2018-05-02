import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

interface Api {
    @GET("/")
    Call<ResponseBody> getHome();

    @POST("/")
    @FormUrlEncoded
    Call<ResponseBody> postForm(@Field("id") String id);

    @POST("/body")
    Call<ResponseBody> body(@Body List<String> id);

    @POST("/upload")
    @Multipart
    Call<ResponseBody> upload(@Part MultipartBody.Part file);

    @PUT("/")
    Call<ResponseBody> put(@Query("id") String id);

    @DELETE("/")
    Call<ResponseBody> delete(@Query("id") String id);

    @GET("/{id}/info")
    Call<ResponseBody> info(@Path("id") String id);

}