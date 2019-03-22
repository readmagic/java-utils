package fun.oop.framework.jackson;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * byte[] <-> java object .
 * <p>
 */
public final class JacksonForBytes implements IJackson<byte[]> {
    private final ObjectMapper objectMapper;
    private Jacksons            jackson;

    JacksonForBytes(Jacksons jackson) {
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
    public byte[] encode(Object obj) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            objectMapper.writeValue(out, obj);
            return out.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Failed to encode as JSON: " + e.getMessage(), e);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
    }

    @Override
    public <T> T decode(byte[] bytes, TypeReference<T> typeReference) {
        try {
            return objectMapper.readValue(bytes, typeReference);
        } catch (IOException e) {
            throw new RuntimeException("Failed to decode JSON ", e);
        }
    }

    @Override
    public <T> T decode(byte[] content, JavaType valueType) {
        try {
            return objectMapper.readValue(content, valueType);
        } catch (IOException e) {
            throw new RuntimeException("Failed to decode JSON ", e);
        }
    }

    @Override
    public <T> T decode(byte[] content, Class<T> valueType) {
        try {
            return objectMapper.readValue(content, valueType);
        } catch (IOException e) {
            throw new RuntimeException("Failed to decode JSON ", e);
        }
    }
}
