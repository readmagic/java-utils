package fun.oop.framework.exception;


import fun.oop.framework.dto.ApiErrorDTO;

import java.util.List;


public class ApiProtocolException extends ApiResponseException {
    private static final long serialVersionUID = 1L;
    private ApiErrorDTO error;
    public ApiProtocolException(ApiErrorDTO error) {
        super(errorMessage(error));
        this.error = error;
    }

    private static String errorMessage(ApiErrorDTO error) {
        return error.getMessage()+"\n"+error;
    }

    public ApiErrorDTO getError() {
        return error;
    }


    public String getDebugMessage() {
        return error.getDebugMessage();
    }

    public List<ApiErrorDTO.SubError> getErrors() {
        return error.getFields();
    }
}
