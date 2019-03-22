package fun.oop.framework.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude;
import fun.oop.framework.jackson.Jacksons;

import java.io.Serializable;

@JsonInclude(value = JsonInclude.Include.NON_NULL, content = JsonInclude.Include.NON_NULL)
@JsonAutoDetect(getterVisibility = Visibility.NONE, creatorVisibility = Visibility.ANY, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE, fieldVisibility = Visibility.ANY)
public class JacksonBaseDTO implements Serializable {


    private static final long serialVersionUID = 2042709805769927640L;

    @Override
    public String toString() {
        return Jacksons.JACKSONS.forString().encode(this);
    }
}
