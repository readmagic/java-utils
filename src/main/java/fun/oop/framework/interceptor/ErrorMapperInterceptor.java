package fun.oop.framework.interceptor;

import com.google.common.base.Throwables;
import okhttp3.Interceptor;
import okhttp3.Response;
import fun.oop.framework.okhttp3.ErrorMapper;

import java.io.IOException;

public class ErrorMapperInterceptor implements Interceptor {
    private ErrorMapper errorMapper;

    public ErrorMapperInterceptor(ErrorMapper errorMapper) {
        this.errorMapper = errorMapper;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        if (!response.isSuccessful()) {
            Throwable e = errorMapper.map(response);
            Throwables.propagateIfPossible(e, IOException.class);
        }
        return response;
    }
}
