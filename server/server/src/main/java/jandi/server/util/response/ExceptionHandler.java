package jandi.server.util.response;

import jandi.server.model.BaseExceptionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionHandler {

    @ResponseBody
    public ResponseEntity<ErrorMessage> exception(CustomException exception) {
        return new ResponseEntity<>(ErrorMessage.create(exception.getExceptionType()), HttpStatus.OK);
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    static class ErrorMessage {
        private int code;
        private int status;
        private String message;

        static ErrorMessage create(BaseExceptionType exception) {
            return new ErrorMessage(exception.getErrorCode(), exception.getHttpStatus(), exception.getErrorMessage());
        }
    }
}
