package jandi.server.util.response;

import jandi.server.model.BaseExceptionType;
import lombok.Getter;

public class CustomException extends RuntimeException {

    @Getter
    private BaseExceptionType exceptionType;

    public CustomException(BaseExceptionType exceptionType) {
        super(exceptionType.getErrorMessage());
        this.exceptionType = exceptionType;
    }
}
