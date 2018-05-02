import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

interface Api2 {
    @GET("/")
    Call<Result<String>> getHome();

    @POST("/")
    @FormUrlEncoded
    Call<Result<String>> postForm(@Field("id") String id);

    @POST("/body")
    Call<Result<String>> body(@Body List<String> id);

    @POST("/upload")
    @Multipart
    Call<Result<String>> upload(@Part MultipartBody.Part file);

    @PUT("/")
    Call<Result<String>> put(@Query("id") String id);

    @DELETE("/")
    Call<Result<String>> delete(@Query("id") String id);

    @GET("/{id}/info")
    Call<Result<String>> info(@Path("id") String id);

}