package jandi.server.model.member.enums;

import jandi.server.model.BaseExceptionType;
import lombok.Getter;

@Getter
public enum MemberExceptionType implements BaseExceptionType {

    NOT_FOUND(1001, 401, "해당하는 사용자가 존재하지 않습니다.");

    private int errorCode;
    private int httpStatus;
    private String errorMessage;

    MemberExceptionType(int errorCode, int httpStatus, String errorMessage) {
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }
}
