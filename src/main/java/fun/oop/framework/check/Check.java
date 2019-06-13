package fun.oop.framework.check;

import java.util.List;


public class Check implements Cloneable {

    private int code = 0;

    @SuppressWarnings("unused")
    private Check() {
    }

    private Check(Check from) {
        this.code = from.code;
    }

    protected Check(int code) {
        this.code = code;
    }

    @Override
    protected Check clone() {
        return new Check(this);
    }

    public static Check error(int code) {
        return new Check(code);
    }

    public void check(boolean condition) {
        if (!condition) {
            throw new fun.oop.framework.check.CheckException(newErrorBuilder().build());
        }
    }

    /**
     * if condition is false throw CheckException
     * */
    public void check(boolean condition, ErrorBuilderVisitor visitor) {
        if (!condition) {
            throw new fun.oop.framework.check.CheckException(toError(visitor));
        }
    }

    public void check(final List<fun.oop.framework.check.CheckError.FieldError> errors) {
        if (errors.isEmpty()) {
            return;
        }
        throw new fun.oop.framework.check.CheckException(newErrorBuilder().addErrors(errors).build());
    }

    public void check(final List<fun.oop.framework.check.CheckError.FieldError> errors, final ErrorBuilderVisitor visitor) {
        if (errors.isEmpty()) {
            return;
        }
        throw new fun.oop.framework.check.CheckException(newErrorBuilder().addErrors(errors).visit(visitor).build());
    }

    public <T> T checkNotNull(T obj) {
        if (obj == null) {
            throw new fun.oop.framework.check.CheckException(newErrorBuilder().build());
        }
        return obj;
    }

    public <T> T checkNotNull(T obj, ErrorBuilderVisitor visitor) {
        if (obj == null) {
            throw new fun.oop.framework.check.CheckException(toError(visitor));
        }
        return obj;
    }

    /**
     * long ageLong = check.checkNoFailed(() -> Long.parseLong(age), e -> e.message("参数age不是数字"));
     */
    public <T> T checkNotFailed(Supplier<T> action, ErrorBuilderVisitor visitor) {
        try {
            return action.get();
        } catch (Throwable e) {
            throw new fun.oop.framework.check.CheckException(
                    newErrorBuilder().cause(e).debugMessage(e.getMessage()).visit(visitor).build());
        }
    }

    /**
     * long ageLong = check.checkNoFailed(() -> Long.parseLong(age));
     */
    public <T> T checkNotFailed(Supplier<T> action) {
        try {
            return action.get();
        } catch (Throwable e) {
            throw new fun.oop.framework.check.CheckException(
                    newErrorBuilder().cause(e).debugMessage(e.getMessage()).build());
        }
    }

    public void checkNotFailed(Action0 action, ErrorBuilderVisitor visitor) {
        try {
            action.call();
        } catch (Throwable e) {
            throw new fun.oop.framework.check.CheckException(
                    newErrorBuilder().cause(e).debugMessage(e.getMessage()).visit(visitor).build());
        }
    }

    public void checkNotFailed(Action0 action) {
        try {
            action.call();
        } catch (Throwable e) {
            throw new fun.oop.framework.check.CheckException(
                    newErrorBuilder().cause(e).debugMessage(e.getMessage()).build());
        }
    }

    public void fail(ErrorBuilderVisitor visitor) {
        throw new fun.oop.framework.check.CheckException(toError(visitor));
    }

    public void fail() {
        throw new fun.oop.framework.check.CheckException(newErrorBuilder().build());
    }

    public fun.oop.framework.check.CheckException toException(ErrorBuilderVisitor visitor) {
        return new fun.oop.framework.check.CheckException(toError(visitor));
    }

    public fun.oop.framework.check.CheckError toError(ErrorBuilderVisitor visitor) {
        return newErrorBuilder().visit(visitor).build();
    }

    public int getCode() {
        return code;
    }

    protected fun.oop.framework.check.CheckError.Builder newErrorBuilder() {
        return fun.oop.framework.check.CheckError.builder().code(code);
    }

    public interface ErrorBuilderVisitor {
        void call(fun.oop.framework.check.CheckError.Builder builder);
    }

    @FunctionalInterface
    public interface Action0 {
        void call() throws Exception;
    }

    @FunctionalInterface
    public interface Supplier<T> {
        T get() throws Exception;
    }

}
