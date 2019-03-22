package fun.oop.framework.okhttp3;

import okhttp3.Response;

public interface ErrorMapper {

    ErrorMapper DO_NOTHING = new ErrorMapper() {
        @Override
        public Throwable map(Response response) {
            return null;
        }
    };


    Throwable map(Response response);
}
