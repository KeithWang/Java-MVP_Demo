package vicwang.demo.mvp.model;

import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import vicwang.demo.mvp.presenter.MainBridge;

public class ApiRepository implements MainBridge.Model {

    private final String mHouseData = "https://data.taipei/opendata/datalist/apiAccess?scope=resourceAquire&rid=5a0e5fbb-72f8-41c6-908e-2fb25eff9b8a";

    private final String mAnimalData = "https://data.taipei/opendata/datalist/apiAccess?scope=resourceAquire&rid=a3e2b221-75e0-45c1-8f97-75acbd43d613&q=";

    @Override
    public void getHouseData(final ApiCallBack callback) {
        OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .build();
        Request request = new Request.Builder()
                .url(mHouseData)
                .method("GET", null)
                .build();
        Call mcall = mOkHttpClient.newCall(request);
        mcall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailed("fail", e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = "";
                if (response.cacheResponse() != null) {
                    str = response.cacheResponse().toString();
                    Log.d("okhttp", "cache---" + str);

                } else {
                    str = response.body().string();
                    Log.d("okhttp", "network---" + str);
                }
                callback.onSuccess(str);
            }
        });
    }

    @Override
    public void getAnimalData(final String targetArea, final ApiCallBack callback) {
        String url = mAnimalData + targetArea;

        OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .method("GET", null)
                .build();
        Call mcall = mOkHttpClient.newCall(request);
        mcall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailed("fail", e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = "";
                if (response.cacheResponse() != null) {
                    str = response.cacheResponse().toString();
                    Log.d("okhttp", "cache---" + str);

                } else {
                    str = response.body().string();
                    Log.d("okhttp", "network---" + str);
                }
                callback.onSuccess(str);
            }
        });
    }
}
