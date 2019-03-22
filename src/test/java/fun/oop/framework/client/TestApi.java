package fun.oop.framework.client;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface TestApi {
    @FormUrlEncoded
    @POST("/demo/mongodb/save")
    Call<ResponseBody> test(@Field("name") String name, @Field("age") Integer age);
}
