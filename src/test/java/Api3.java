import io.reactivex.Single;
import okhttp3.MultipartBody;
import retrofit2.http.*;

import java.util.List;

interface Api3 {
    @GET("/")
    Single<Result<String>> getHome();

    @POST("/")
    @FormUrlEncoded
    Single<Result<String>> postForm(@Field("id") String id);

    @POST("/body")
    Single<Result<String>> body(@Body List<String> id);

    @POST("/upload")
    @Multipart
    Single<Result<String>> upload(@Part MultipartBody.Part file);

    @PUT("/")
    Single<Result<String>> put(@Query("id") String id);

    @DELETE("/")
    Single<Result<String>> delete(@Query("id") String id);

    @GET("/{id}/info")
    Single<Result<String>> info(@Path("id") String id);

}