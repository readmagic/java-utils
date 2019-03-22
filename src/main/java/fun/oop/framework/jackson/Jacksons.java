package fun.oop.framework.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.base.Preconditions;

/**
 */
public final class Jacksons {
    public static final Jacksons         JACKSONS = Jacksons.create(newDefaultObjectMapper());
    public static final JacksonForString JACKSON  = JACKSONS.forString();
    private final ObjectMapper objectMapper;
    private JacksonForString             forString;
    private JacksonForBytes              forForBytes;

    private static ObjectMapper newDefaultObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        mapper.disable(SerializationFeature.INDENT_OUTPUT);
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, false);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);

        // disable auto detect getter and setter
        mapper.configure(MapperFeature.AUTO_DETECT_GETTERS, false);
        mapper.configure(MapperFeature.AUTO_DETECT_SETTERS, false);
        mapper.configure(MapperFeature.AUTO_DETECT_IS_GETTERS, false);

        // disable auto detect fields
        mapper.configure(MapperFeature.AUTO_DETECT_FIELDS, false);

        //mapper.registerModule(DefaultScalaModule);

        // com.fasterxml.jackson.databind.util.StdDateFormat#format()
        // 2016-06-27T03:03:53.536Z
        mapper.setDateFormat(new StdDateFormatCopyFromJackson());
//        mapper.setDateFormat(Dates.getFormatyyyyMMddHHmmss());

        return mapper;
    }

    private Jacksons(ObjectMapper mapper) {
        this.objectMapper = mapper;
        this.forString = new JacksonForString(this);
        this.forForBytes = new JacksonForBytes(this);
    }

    /**
     */
    public static Jacksons create(ObjectMapper objectMapper) {
        Preconditions.checkNotNull(objectMapper);
        return new Jacksons(objectMapper);
    }

    /**
     * string <-> java object .
     */
    public JacksonForString forString() {
        return forString;
    }

    /**
     * bytes <-> java object .
     */
    public JacksonForBytes forBytes() {
        return forForBytes;
    }

    public Jacksons withIndentOutput(boolean value) {
        ObjectMapper copy = this.objectMapper.copy();
        copy.configure(SerializationFeature.INDENT_OUTPUT, value);
        return new Jacksons(copy);
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}
