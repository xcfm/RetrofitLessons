import com.parkingwang.okhttp3.LogInterceptor.LogInterceptor;
import okhttp3.*;
import org.junit.Before;
import org.junit.Test;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Lesson1 {
    private Api api;


    @Before
    public void init() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LogInterceptor(System.out::println))
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://127.0.0.1:8080")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        api = retrofit.create(Api.class);
    }

    @Test
    public void testGet() throws IOException {
        api.getHome().execute();
    }

    @Test
    public void testPostForm() throws IOException {
        api.postForm("123").execute();

    }

    @Test
    public void testBody() throws IOException {
        api.body(Arrays.asList("a", "b", "c")).execute();
    }

    @Test
    public void testInfo() throws IOException {
        api.info("abc").execute();
    }
    @Test
    public void testInfo2() throws IOException {
        Response<ResponseBody> abc = api.info("abc").execute();
        System.out.println(abc.body().string());
    }

    @Test
    public void testUpload() throws IOException {
        RequestBody body = RequestBody.create(MediaType.parse("application/otcet-stream"), new File("1.txt"));
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", "1.txt", body);
        api.upload(part).execute();
    }
}
