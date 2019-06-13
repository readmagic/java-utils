package fun.oop.framework.validate;


public class SingleResult {

    /**
     * 是否验证成功，只要有一个失败就为false
     */
    private boolean isSuccess;

    /**
     * 错误消息
     */
    protected String error;

    public SingleResult(boolean isSuccess, String error) {
        this.isSuccess = isSuccess;
        this.error = error;
    }

    @Override
    public String toString() {
        return String.format("Result{isSuccess=%s, error=%s}", isSuccess(), error);
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
