package fun.oop.framework.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import fun.oop.framework.interceptor.ErrorMapperInterceptor;
import fun.oop.framework.jackson.Jacksons;
import fun.oop.framework.okhttp3.ErrorMapper;
import fun.oop.framework.okhttp3.ApiCallFactory;
import fun.oop.framework.okhttp3.ApiErrorMapper;
import fun.oop.framework.retrofit2.adapters.RxJavaAndErrorMapperCallAdapterFactory;
import fun.oop.framework.retrofit2.converters.BetterErrorInfoJacksonConverterFactory;

import java.util.concurrent.TimeUnit;

public class ApiClient implements Cloneable {
    final private Retrofit retrofit;
    final private ObjectMapper objectMapper;
    final private String baseUrl;
    final private OkHttpClient okHttpClient;
    final private ApiErrorMapper errorMapper;

    final private String nameVersion;

    private String lang;
    private boolean trace;
    private String moduleName;

    private ApiClient(Builder builder) {
        Preconditions.checkNotNull(builder.baseUrl);
        this.baseUrl = builder.baseUrl;
        this.nameVersion = builder.nameVersion;
        this.lang = builder.lang;
        this.objectMapper = Jacksons.JACKSON.getObjectMapper();
        this.errorMapper = new ApiErrorMapper(this.objectMapper);

        this.okHttpClient = builder._okHttpClient != null ? builder._okHttpClient
                : newOkHttpClient(this.errorMapper);

        this.retrofit = new Retrofit.Builder().client(this.okHttpClient).baseUrl(this.baseUrl)
                .addCallAdapterFactory(RxJavaAndErrorMapperCallAdapterFactory.create(this.errorMapper))
                .addConverterFactory(BetterErrorInfoJacksonConverterFactory.create(this.objectMapper))
                .callFactory(new ApiCallFactory(this)).build();
    }

    public static Builder builder() {
        return new Builder();
    }

    public <T> T create(final Class<T> service) {
        return retrofit.create(service);
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    public String getNameVersion() {
        return nameVersion;
    }

    public String getLang() {
        return lang;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }


    public void setLang(String lang) {
        this.lang = lang;
    }

    private static OkHttpClient newOkHttpClient(ErrorMapper errorMapper) {
        return new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(30,TimeUnit.SECONDS)
                .addNetworkInterceptor(new ErrorMapperInterceptor(errorMapper)).addNetworkInterceptor(
                        new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
    }

    public static final class Builder {
        private String baseUrl;
        private OkHttpClient _okHttpClient;
        private String nameVersion;
        private String lang;

        private Builder() {
        }


        public Builder baseUrl(String value) {
            this.baseUrl = value;
            return this;
        }


        public Builder okHttpClient(OkHttpClient value) {
            this._okHttpClient = value;
            return this;
        }

        public ApiClient build() {
            return new ApiClient(this);
        }
    }





    public void setTrace(boolean trace) {
        this.trace = trace;
    }

    public boolean isTrace() {
        return trace;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

}
