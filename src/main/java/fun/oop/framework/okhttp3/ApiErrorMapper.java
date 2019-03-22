package fun.oop.framework.okhttp3;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import okhttp3.Response;
import fun.oop.framework.dto.ApiErrorDTO;
import fun.oop.framework.exception.ApiException;
import fun.oop.framework.exception.ApiProtocolException;

import java.io.IOException;

public final class ApiErrorMapper implements ErrorMapper {
    private ObjectMapper objectMapper;
    public ApiErrorMapper(ObjectMapper objectMapper) {
    	    this.objectMapper=objectMapper;
    }

    @Override
    public Throwable map(Response response) {
        if (response.body() == null) {
            return new ApiException("Server Fail : response body should not be null.");
        }

        String bodyString = null;
        try {
            bodyString = response.body().string();
        } catch (IOException e) {
            return new ApiException("Server Fail : read response body error.",e);
        }
        
        if (Strings.isNullOrEmpty(bodyString)) {
            return new ApiException("Server Fail : response body should not be empty.");
        }

        try {
            ApiErrorDTO error = objectMapper.readValue(bodyString, ApiErrorDTO.class);
            return new ApiProtocolException(error);
        } catch (Throwable e) {
            return new ApiException("Server Fail : api body should be json format");
        }
    }
}
