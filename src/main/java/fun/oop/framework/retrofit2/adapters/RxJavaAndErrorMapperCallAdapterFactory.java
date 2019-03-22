package fun.oop.framework.retrofit2.adapters;

import com.google.common.base.Preconditions;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.HttpException;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.Single;
import rx.SingleSubscriber;
import rx.functions.Func1;
import fun.oop.framework.okhttp3.ErrorMapper;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;


public final class RxJavaAndErrorMapperCallAdapterFactory extends CallAdapter.Factory {
    public static final int DEFAULT_TIMEOUT_SECONDS = 60;
    private RxJavaCallAdapterFactory factory = RxJavaCallAdapterFactory.create();
    private ErrorMapper errorMapper;

    private RxJavaAndErrorMapperCallAdapterFactory(ErrorMapper errorMapper) {
        this.errorMapper = errorMapper;
    }

    public static RxJavaAndErrorMapperCallAdapterFactory create(ErrorMapper errorMapper) {
        Preconditions.checkNotNull(errorMapper);
        return new RxJavaAndErrorMapperCallAdapterFactory(errorMapper);
    }

    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        CallAdapter<?, ?> callAdapter = factory.get(returnType, annotations, retrofit);
        if (callAdapter == null) {
            return null;
        }
        Class<?> rawType = getRawType(returnType);
        if (rawType == Observable.class) {
            return makeObservable((CallAdapter<?, Observable<?>>) callAdapter);
        }
        if (rawType == Single.class) {
            return makeSingle((CallAdapter<?, Single<?>>) callAdapter);
        }
        return null;
    }

    private <R> CallAdapter<R, Single<?>> makeSingle(final CallAdapter<R, Single<?>> callAdapter) {
        return new CallAdapter<R, Single<?>>() {
            
            @Override
            public Type responseType() {
                return callAdapter.responseType();
            }

            @Override
            public Single<?> adapt(Call<R> call) {
                Single<?> result = callAdapter.adapt(call);
                return Single
                        .create(singleSubscriber -> result.subscribe(new SingleSubscriber<Object>() {
                            @Override
                            public void onSuccess(Object value) {
                                singleSubscriber.onSuccess(value);
                            }

                            @Override
                            public void onError(Throwable error) {
                                singleSubscriber.onError(mapError(error));
                            }
                        })).timeout(DEFAULT_TIMEOUT_SECONDS, TimeUnit.SECONDS);
            }
        };
    }

    private Throwable mapError(Throwable error) {
        if (!(error instanceof HttpException)) {
            return error;
        }
        HttpException httpException = (HttpException) error;
        try {
            Throwable result = errorMapper.map(httpException.response().raw());
            return result == null ? httpException : result;
        } catch (Throwable e) {
            return e;
        }
    }

    private <R> CallAdapter<R, Observable<?>> makeObservable(final CallAdapter<R, Observable<?>> callAdapter) {
        return new CallAdapter<R, Observable<?>>() {
            @Override
            public Type responseType() {
                return callAdapter.responseType();
            }

            @Override
            public Observable<?> adapt(Call<R> call) {
                Observable<?> result = callAdapter.adapt(call);
                return result
                        .map(castToCallParamType(call))
                        .onErrorResumeNext(new Func1<Throwable, Observable<? extends R>>() {
                            @Override
                            public Observable<? extends R> call(Throwable e) {
                                return Observable.<R>error(mapError(e));
                            }
                        }).timeout(DEFAULT_TIMEOUT_SECONDS, TimeUnit.SECONDS);
            }

            private <R> Func1<Object, R> castToCallParamType(Call<R> call) {
                return x -> (R) x;
            }
        };
    }


}
