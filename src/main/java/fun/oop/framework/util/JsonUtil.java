package fun.oop.framework.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;


public class JsonUtil {

    private static ObjectMapper mapper = new ObjectMapper();
    private static Gson gson = new Gson();

    static {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                false);
        mapper.registerModule(new JodaModule());
    }


    public static <T> String toJson(T t) {
        try {
            return mapper.writeValueAsString(t);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T fromJson(String json, Class<T> vt) {
        try {
            return mapper.readValue(json, vt);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static Map toMap(String json){
        return fromJson(json,Map.class);
    }


    public static <T> T fromJson(String json, Type type) {
        return gson.fromJson(json, type);
    }



}
