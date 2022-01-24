package jandi.server.model.user.enums;

import jandi.server.model.BaseExceptionType;
import lombok.Getter;

@Getter
public enum UserExceptionType implements BaseExceptionType {

    NOT_FOUND(1001, 401, "해당하는 사용자가 존재하지 않습니다."),
    DUPLICATED_USER(1002, 401, "이미 같은 github id를 사용하는 유저가 존재합니다."),
    DUPLICATED_NAME(1002, 401, "이미 같은 이름을 사용하는 유저가 존재합니다."),
    WRONG_PASSWORD(1003, 401, "패스워드를 잘못 입력하였습니다."),
    LOGIN_INFORMATION_NOT_FOUND(1004, 401, "로그인 정보를 찾을 수 없습니다. (세션 만료)");

    private int errorCode;
    private int httpStatus;
    private String errorMessage;

    UserExceptionType(int errorCode, int httpStatus, String errorMessage) {
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }

}
