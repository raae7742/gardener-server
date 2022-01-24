package jandi.server.model.attendance.enums;

import jandi.server.model.BaseExceptionType;
import lombok.Getter;

@Getter
public enum AttendanceExceptionType implements BaseExceptionType {

    NOT_FOUND(1001, 401, "해당 ID가 존재하지 않습니다."),
    COMMIT_ERROR(1001, 500, "커밋을 불러오는 데에 오류가 발생했습니다.");

    private int errorCode;
    private int httpStatus;
    private String errorMessage;

    AttendanceExceptionType(int errorCode, int httpStatus, String errorMessage) {
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }
}
