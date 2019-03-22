package fun.oop.framework.render;

import fun.oop.framework.util.JsonUtil;


public class RenderJson {
    public static <T> String success(T any) {
        SuccessResult response = new SuccessResult<T>(any, ResponseCode.SUCCESS, "success");
        return JsonUtil.toJson(response);
    }

    public static <T> String fail(String errorMsg) {
        FailResult response = new FailResult(ResponseCode.FAIL, errorMsg);
        return JsonUtil.toJson(response);
    }

    public static <T> String fail(String errorMsg, Integer code) {
        FailResult response = new FailResult(code, errorMsg);
        return JsonUtil.toJson(response);
    }

    public static <T> String error(String errorMsg) {
        FailResult response = new FailResult(ResponseCode.ERROR, errorMsg);
        return JsonUtil.toJson(response);
    }
}
