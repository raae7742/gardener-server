package jandi.server.util.response;

import jandi.server.model.StatusEnum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Message {
    private StatusEnum status;
    private String message;
    private Object data;
}
