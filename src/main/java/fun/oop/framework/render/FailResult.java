package fun.oop.framework.render;

public class FailResult extends BaseResult {

    public FailResult(Integer code, String info) {
        this.setCode(code);
        this.setInfo(info);
    }

    public FailResult() {
    }
}