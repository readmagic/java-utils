package fun.oop.framework.dto;

import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;

import java.util.*;

public class ApiErrorDTO extends JacksonBaseDTO {

    private static final long serialVersionUID = 1L;
    private int code;
    private String message;
    private String debugMessage;
    private Date responseTime = new Date();
    private List<SubError> errors = new ArrayList<>();
    private String trace;

    // for serializable
    private ApiErrorDTO() {
    }

    public int getCode() {
        return code;
    }

    public List<SubError> getFields() {
        return errors;
    }

    public String getMessage() {
        return message;
    }

    public String getDebugMessage() {
        return debugMessage;
    }

    public String getCause() {
        return trace;
    }

    public static final class SubError extends JacksonBaseDTO {
        private static final long serialVersionUID = 1L;
        private String message;
        private String key;
        private String code;

        // for serializable
        private SubError() {
        }

        public SubError(String message, String key, String code) {
        }

        public String getCode() {
            return code;
        }

        public String getKey() {
            return key;
        }

        @Override
        public String toString() {
            LinkedHashMap<String, Object> map = new LinkedHashMap<>();
            map.put("objectErrors", key);
            map.put("code", code);
            map.put("message", message);
            return String.format("%s-%s", this.getClass().getSimpleName(), map);
        }

    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Throwable cause;
        private List<SubError> errors = new ArrayList<>();
        private int code;
        private String debugMessage;
        private String message;

        private Builder() {
        }

        public ApiErrorDTO build(boolean debug) {
            ApiErrorDTO result = new ApiErrorDTO();
            result.code = code;
            result.message = message;
            result.errors = Collections.unmodifiableList(errors);
            if (debug) {
                result.debugMessage = debugMessage;
                result.trace = cause == null ? null : Throwables.getStackTraceAsString(cause);
            }
            return result;
        }

        public Builder code(int code) {
            this.code = code;
            return this;
        }

        /**
         * Check#code为i18n objectErrors，可以用此方法设置message中的参数.
         */
        public Builder message(String message) {
            this.message = message;
            return this;
        }

        /**
         * Fail#debugMessage.
         */
        public Builder debugMessage(String message) {
            this.debugMessage = message;
            return this;
        }

        public Builder cause(Throwable e) {
            this.cause = e;
            return this;
        }

        public Builder addFieldError(SubError fieldError) {
            this.errors.add(fieldError);
            return this;
        }

        public Builder addError(String key, String code, String message) {
            this.errors.add(new SubError(key, code, message));
            return this;
        }

        public void addFieldErrors(List<SubError> errors) {
            Preconditions.checkNotNull(errors);
            this.errors = errors;
        }
    }

}