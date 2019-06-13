package fun.oop.framework.check;

import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.*;


public final class CheckError implements Serializable {
    private static final long serialVersionUID = 1L;

    private int code;
    private String messageKey;
    private Object[] messageArgs;
    private String debugMessage;
    private List<FieldError> errors;
    private Throwable cause;

    public CheckError(Builder builder) {
        this.code = builder.code;
        this.messageKey = builder.messageKey;
        this.messageArgs = builder.messageArgs;
        this.debugMessage = builder.debugMessage;
        this.errors = builder.errors;
        this.cause = builder.cause;
    }

    public int getCode() {
        return code;
    }

    public String getMessageKey() {
        return messageKey;
    }

    private static final Object[] EMPTY_ARRAY = new Object[0];

    public Object[] getMessageArgs() {
        return errors == null ? EMPTY_ARRAY : messageArgs;
    }

    public String getDebugMessage() {
        return debugMessage;
    }

    public List<FieldError> getErrors() {
        return errors == null ? Collections.emptyList() : errors;
    }

    public Throwable getCause() {
        return cause;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getExceptionMessage() {
        return String.format("%s: %s %s", code, messageKey, Arrays.toString(messageArgs));
    }

    @Override
    public String toString() {
        LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("code", code);
        map.put("messageKey", messageKey);
        map.put("messageArgs", messageArgs);
        map.put("debugMessage", debugMessage);
        map.put("errors", errors);
        return String.format("%s-%s", this.getClass().getSimpleName(), map);
    }

    public static class Builder {
        private Throwable cause;
        private List<FieldError> errors = new ArrayList<>();
        private int code;
        private String debugMessage;
        private String messageKey;
        private Object[] messageArgs;
        private Object[] EMPTY_ARRAY = new Object[0];
        private String key;

        private Builder() {
        }

        public CheckError build() {
            this.key = key == null ? "default" : key;
            this.messageKey = messageKey == null ? String.format("check.error.%s", key)
                    : messageKey;
            this.messageArgs = messageArgs == null ? EMPTY_ARRAY : messageArgs;
            return new CheckError(this);
        }

        public Builder code(int code) {
            this.code = code;
            return this;
        }

        /**
         * Check#code为i18n objectErrors，可以用此方法设置message中的参数.
         */
        public Builder message(String messageKey, Object... args) {
            this.messageKey = messageKey;
            this.messageArgs = args;
            return this;
        }

        /**
         * Fail#debugMessage.
         */
        public Builder debugMessage(String message, Object... args) {
            this.debugMessage = String.format(message, args);
            return this;
        }

        public Builder cause(Throwable e) {
            this.cause = e;
            return this;
        }

        public Builder addFieldError(FieldError fieldError) {
            this.errors.add(fieldError);
            return this;
        }

        public Builder addError(String field, String code, String messageKey, Serializable... messageParams) {
            this.errors.add(FieldError.of(field, code, messageKey, messageParams));
            return this;
        }

        public Builder addErrors(List<FieldError> fieldErrors) {
            this.errors.addAll(fieldErrors);
            return this;
        }

        public Builder visit(Check.ErrorBuilderVisitor visitor) {
            visitor.call(this);
            return this;
        }
    }

    public static final class FieldError implements Serializable {
        private static final long serialVersionUID = 1L;
        private String field;
        private String code;
        private String messageKey;
        private Serializable[] messageArgs;

        private FieldError(String field, String code, String messageKey, Serializable[] messageArgs) {
            this.field = field;
            this.code = code;
            this.messageKey = messageKey;
            this.messageArgs = messageArgs;
        }

        public static FieldError of(String field, String code, String messageKey, Serializable... messageParams) {
            return new FieldError(field, code, messageKey, messageParams);
        }

        public String getCode() {
            return code;
        }

        public String getField() {
            return field;
        }


        public String getMessageKey() {
            return messageKey;
        }

        public Serializable[] getMessageArgs() {
            return messageArgs;
        }

        @Override
        public String toString() {
            LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
            map.put("field", field);
            map.put("code", code);
            map.put("messageKey", messageKey);
            map.put("messageArgs", Lists.newArrayList(messageArgs));
            return String.format("%s-%s", this.getClass().getSimpleName(), map);
        }

    }


}
