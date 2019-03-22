package fun.oop.framework.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;

import java.io.IOException;
import java.util.*;

/**
 * string <-> java object .
 * <p>
 */
public final class JacksonForString implements IJackson<String> {

    private final ObjectMapper objectMapper;

    /** internal */
    private Jacksons            jackson;

    JacksonForString(Jacksons jackson) {
        this.jackson = jackson;
        this.objectMapper = jackson.getObjectMapper();
    }

    @Override
    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    @Override
    public Jacksons getJacksons() {
        return jackson;
    }

    @Override
    public String encode(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (IOException e) {
            throw new RuntimeException("Failed to encode as JSON: " + e.getMessage(), e);
        }
    }

    @Override
    public <T> T decode(String content, JavaType valueType) {
        try {
            return objectMapper.readValue(content, valueType);
        } catch (IOException e) {
            throw new RuntimeException("Failed to decode JSON ", e);
        }
    }

    @Override
    public <T> T decode(String content, TypeReference<T> typeReference) {
        try {
            return objectMapper.readValue(content, typeReference);
        } catch (IOException e) {
            throw new RuntimeException("Failed to decode JSON ", e);
        }
    }

    @Override
    public <T> T decode(String content, Class<T> typeToken) {
        try {
            return objectMapper.readValue(content, typeToken);
        } catch (IOException e) {
            throw new RuntimeException("Failed to decode JSON :" + e.getMessage(), e);
        }
    }

    /**
     * <p>
     * find("{a:1,b:{c:2},d:3}","a","d") //result> {a:1,d:3}
     */
    public Map<String, String> findLevalOneFields(String json, String... fields) {
        if (Strings.isNullOrEmpty(json)) {
            return Collections.emptyMap();
        }

        Map<String, String> result = new HashMap<>();

        JsonParser jsonParser = null;
        try {
            jsonParser = objectMapper.getFactory().createParser(json);
            Set<String> fieldsSet = new HashSet<>(Arrays.asList(fields));
            int finded = 0;
            int currentLevel = 0;
            while (jsonParser.nextToken() != null) {
                if (finded == fields.length) {
                    break;
                }
                JsonToken current = jsonParser.getCurrentToken();
                if (current.isStructStart()) {
                    currentLevel += 1;
                } else if (current.isStructEnd()) {
                    currentLevel -= 1;
                }
                if (currentLevel == 1) {
                    String name = jsonParser.getCurrentName();
                    if (fieldsSet.contains(name)) {
                        jsonParser.nextToken();
                        result.put(name, jsonParser.getText());
                        finded += 1;
                    }
                }
            }
            return result;
        } catch (IOException e) {
            throw new RuntimeException("Failed to findLevalOneFields", e);
        } finally {
            if (jsonParser != null) {
                try {
                    jsonParser.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }

    }

}
