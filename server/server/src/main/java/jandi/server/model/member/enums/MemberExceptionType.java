package jandi.server.model.member.enums;

import jandi.server.model.BaseExceptionType;
import lombok.Getter;

@Getter
public enum MemberExceptionType implements BaseExceptionType {

    NOT_FOUND(1001, 401, "해당하는 사용자가 존재하지 않습니다."),
    DUPLICATED_USER(1002, 401, "이미 존재하는 사용자 아이디입니다."),
    WRONG_PASSWORD(1003, 401, "패스워드를 잘못 입력하였습니다."),
    LOGIN_INFORMATION_NOT_FOUND(1004, 401, "로그인 정보를 찾을 수 없습니다. (세션 만료)");

    private int errorCode;
    private int httpStatus;
    private String errorMessage;

    MemberExceptionType(int errorCode, int httpStatus, String errorMessage) {
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }
}
