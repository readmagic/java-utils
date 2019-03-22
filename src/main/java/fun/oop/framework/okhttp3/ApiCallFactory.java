package fun.oop.framework.okhttp3;


import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.Request;
import fun.oop.framework.client.ApiClient;

public final class ApiCallFactory implements Call.Factory {
    private ApiClient client;

    public ApiCallFactory(ApiClient client) {
        this.client = client;
    }

    @Override
    public Call newCall(Request request) {

        HttpUrl url = request.url();
        if (client.isTrace()) {
            url = request.url().newBuilder().addQueryParameter("trace", "true").build();
        }

        Request.Builder b = request.newBuilder().url(url);

        return client.getOkHttpClient().newCall(b.build());
    }

}
