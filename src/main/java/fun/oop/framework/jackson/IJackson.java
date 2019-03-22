package fun.oop.framework.jackson;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

public interface IJackson<TValue> {

    TValue encode(Object obj);

    <T> T decode(TValue bytes, TypeReference<T> typeReference);

    <T> T decode(TValue content, JavaType valueType);

    <T> T decode(TValue content, Class<T> valueType);

    ObjectMapper getObjectMapper();

    Jacksons getJacksons();

    /**
     * @return decode(encode(obj))
     */
    default <T> T deepCopy(T obj) {
        return decode(encode(obj),
            getObjectMapper().getTypeFactory().constructType(obj.getClass()));
    }

}