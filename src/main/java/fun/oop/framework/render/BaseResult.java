package fun.oop.framework.render;


import fun.oop.framework.dto.JacksonBaseDTO;

public class BaseResult extends JacksonBaseDTO {
    private Integer code;
    private String info;
    private Object response;

    public BaseResult() {
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Object getResponse() {
        return response;
    }

    public void setResponse(Object response) {
        this.response = response;
    }
}