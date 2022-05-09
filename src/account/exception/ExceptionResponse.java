package account.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.net.URI;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class ExceptionResponse {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
}
