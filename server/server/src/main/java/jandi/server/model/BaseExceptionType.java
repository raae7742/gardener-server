package jandi.server.model;

public interface BaseExceptionType {
    int getErrorCode();
    int getHttpStatus();
    String getErrorMessage();
}
