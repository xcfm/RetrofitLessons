import com.parkingwang.okhttp3.LogInterceptor.LogInterceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import org.junit.Before;
import org.junit.Test;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class Lesson2 {
    private Api2 api;


    @Before
    public void init() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LogInterceptor(System.out::println))
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://127.0.0.1:8080")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(Api2.class);
    }


    @Test
    public void testInfo2() throws IOException {
        Response<Result<String>> abc = api.info("abc").execute();
        System.out.println(abc.body().data);

    }

    @Test
    public void testGet() throws IOException, InterruptedException {
        System.out.println(api.getHome().execute().body().data);
    }

    @Test
    public void testInfoAndUpload() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        System.out.println(Thread.currentThread().getName());
        api.info("123").enqueue(new Callback<Result<String>>() {
            @Override
            public void onResponse(Call<Result<String>> call, Response<Result<String>> response) {
                System.out.println(Thread.currentThread().getName());
                RequestBody body = RequestBody.create(MediaType.parse("application/otcet-stream"), new File("1.txt"));
                MultipartBody.Part part = MultipartBody.Part.createFormData("file", "1.txt", body);
                api.upload(part).enqueue(new Callback<Result<String>>() {
                    @Override
                    public void onResponse(Call<Result<String>> call, Response<Result<String>> response) {
                        System.out.println(Thread.currentThread().getName());
                        System.out.println(response.body().data);
                        countDownLatch.countDown();
                    }

                    @Override
                    public void onFailure(Call<Result<String>> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }

            @Override
            public void onFailure(Call<Result<String>> call, Throwable t) {
                t.printStackTrace();

            }
        });
        countDownLatch.await();

    }
}
