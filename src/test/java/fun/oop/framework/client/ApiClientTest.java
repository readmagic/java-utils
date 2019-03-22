package fun.oop.framework.client;

import okhttp3.ResponseBody;
import org.junit.Test;
import retrofit2.Call;


public class ApiClientTest {
    private ApiClient httpClient = ApiClient.builder().baseUrl("http://localhost:9000").build();
    private TestApi testApi = httpClient.create(TestApi.class);

    @Test
    public void test() {
        Long startTime = System.currentTimeMillis();
        try {
            Call<ResponseBody> re =  testApi.test("12",12);
            re.execute();
        } catch (Exception e) {
            e.printStackTrace();

        }
        System.out.println(System.currentTimeMillis() - startTime);
    }
}
