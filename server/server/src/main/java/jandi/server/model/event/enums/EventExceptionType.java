package jandi.server.model.event.enums;

import jandi.server.model.BaseExceptionType;
import lombok.Getter;

@Getter
public enum EventExceptionType implements BaseExceptionType {

    NOT_FOUND(1001, 401, "해당 ID가 존재하지 않습니다."),;

    private int errorCode;
    private int httpStatus;
    private String errorMessage;

    EventExceptionType(int errorCode, int httpStatus, String errorMessage) {
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }
}
