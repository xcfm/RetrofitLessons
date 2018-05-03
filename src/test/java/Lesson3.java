import com.parkingwang.okhttp3.LogInterceptor.LogInterceptor;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import org.junit.Before;
import org.junit.Test;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.File;
import java.util.concurrent.CountDownLatch;

public class Lesson3 {
    private Api3 api;


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
        api = retrofit.create(Api3.class);
    }


    @Test
    public void testUpload() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        System.out.println(Thread.currentThread().getName());
        api.info("abc")
                .subscribeOn(Schedulers.io())
                .flatMap(data -> {
                    System.out.println(Thread.currentThread().getName());
                    RequestBody body = RequestBody.create(MediaType.parse("application/otcet-stream"), new File("1.txt"));
                    MultipartBody.Part part = MultipartBody.Part.createFormData("file", "1.txt", body);
                    return api.upload(part,data.code);
                })
                .flatMap(upRes -> api.put(upRes.data))
                .observeOn(Schedulers.newThread())
                .subscribe(stringResult -> {
                            System.out.println(Thread.currentThread().getName());
                            System.out.println(stringResult.data);
                            countDownLatch.countDown();
                        },
                        Throwable::printStackTrace);
        countDownLatch.await();
    }

}
