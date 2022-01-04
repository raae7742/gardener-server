package jandi.server.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Message {
    private StatusEnum status;
    private String message;
    private Object data;
}
