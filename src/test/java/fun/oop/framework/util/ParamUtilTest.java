package fun.oop.framework.util;

import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;


public class ParamUtilTest {

    @Test
    public void testMapToUrlParams() {
        Map<String,Object> map = Maps.newHashMap();
        map.put("111","aaa");
        map.put("222","bbb");
        map.put("ccc",1);
        assertEquals(ParamUtil.mapToUrlParams(map),"111=aaa&222=bbb&ccc=1");
    }

    @Test
    public void testUrlParamsToMap() {
        String urlParams = "a=123&b=321321&c=11ba";
        Map map = ParamUtil.urlParamsToMap(urlParams);
        assertEquals(map.get("a"),"123");


    }
}
