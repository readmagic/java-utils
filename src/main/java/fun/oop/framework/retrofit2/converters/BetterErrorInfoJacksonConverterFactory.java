package fun.oop.framework.retrofit2.converters;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.google.common.base.Preconditions;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 */
public final class BetterErrorInfoJacksonConverterFactory extends Converter.Factory {
    private final ObjectMapper objectMapper;

    /**
     * Create an instance using a default {@link ObjectMapper} instance for conversion.
     */
    public static BetterErrorInfoJacksonConverterFactory create() {
        return new BetterErrorInfoJacksonConverterFactory(new ObjectMapper());
    }

    /**
     * Create an instance using {@code mapper} for conversion.
     */
    public static BetterErrorInfoJacksonConverterFactory create(ObjectMapper mapper) {
        return new BetterErrorInfoJacksonConverterFactory(mapper);
    }

    private BetterErrorInfoJacksonConverterFactory(ObjectMapper mapper) {
        Preconditions.checkNotNull(mapper, "mapper == null");
        this.objectMapper = mapper;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
                                                            Retrofit retrofit) {
        JavaType javaType = objectMapper.getTypeFactory().constructType(type);
        ObjectReader reader = objectMapper.readerFor(javaType);
        return new JacksonResponseBodyConverter<>(reader);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type,
                                                          Annotation[] parameterAnnotations,
                                                          Annotation[] methodAnnotations,
                                                          Retrofit retrofit) {
        return new JacksonRequestBodyConverter(objectMapper);
    }

    private static final class JacksonResponseBodyConverter<T>
                                                           implements Converter<ResponseBody, T> {
        private final ObjectReader adapter;

        JacksonResponseBodyConverter(ObjectReader adapter) {
            this.adapter = adapter;
        }

        @Override
        public T convert(ResponseBody value) throws IOException {
            String string = value.string();
            return adapter.readValue(string);
        }
    }

    private static final class JacksonRequestBodyConverter<T> implements Converter<T, RequestBody> {
        private static final MediaType MEDIA_TYPE = MediaType
            .parse("application/json; charset=UTF-8");

        private final ObjectMapper objectMapper;

        JacksonRequestBodyConverter(ObjectMapper objectMapper) {
            this.objectMapper = objectMapper;
        }

        @Override
        public RequestBody convert(T value) throws IOException {
            byte[] bytes = objectMapper.writeValueAsBytes(value);
            return RequestBody.create(MEDIA_TYPE, bytes);
        }
    }

}
